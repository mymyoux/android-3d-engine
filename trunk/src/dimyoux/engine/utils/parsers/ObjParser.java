package dimyoux.engine.utils.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dimyoux.engine.managers.FileManager;
import dimyoux.engine.scene.Entity;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord2D;
import dimyoux.engine.utils.math.Coord3D;

import android.content.res.Resources.NotFoundException;


/**
 * Parses .obj files
 * 
 */
public class ObjParser {
	private static final String vertex = "v";
	private static final String object = "o";
	private static final String normal = "vn";
	private static final String color = "vc";
	private static final String face = "f";
	private static final String ignore = "#";
	private static final String texCoord = "vt";
	private static final String useMaterial = "usemtl";
	private static final String materialFile = "mtllib";

	
	//TODO:Integer, Node and Node.clone
	private static Map<Integer, Map<String, MeshBuilder>> meshesManager;
	/**
	 * Load file
	 * @param filename File name 
	 * @return A node object
	 */
	public Node load(String filename)
	{
		return load(FileManager.getInstance().getFileID(filename));
	}
	/**
	 * Load file
	 * @param filename File id 
	 * @return A node object
	 */
	public Node load(int id)
	{
		Node node = new Node();
		try
		{
			Map<String, MeshBuilder> meshes = loadMeshBuilders(id);
			if(meshes != null)
			{
				Log.verbose(meshes.size()+" MeshBuilder"+(meshes.size()>1?"s":"")+" loaded");
				if(meshes.size() == 1)
				{
					Entity entity = new Entity();
					node.attachEntity(entity);
					entity.setMesh(((MeshBuilder)meshes.values().toArray()[0]).toMesh());
					node.x = ((MeshBuilder)meshes.values().toArray()[0]).translation.x;
					node.y = ((MeshBuilder)meshes.values().toArray()[0]).translation.y;
					node.z = ((MeshBuilder)meshes.values().toArray()[0]).translation.z;
					Log.verbose("Parsing file "+FileManager.getInstance().getFileName(id)+" is finished");
					Log.verbose("Get Mesh : "+entity.getMesh());
				}else
				{
					Entity entity;
					Node _node;
					for(final MeshBuilder mesh : meshes.values())
					{
						entity = new Entity();
						_node = new Node();
						node.attachChildNode(_node);
						_node.attachEntity(entity);
						entity.setMesh(mesh.toMesh());
						_node.x = mesh.translation.x;
						_node.y = mesh.translation.y;
						_node.z = mesh.translation.z;
					}
					Log.verbose("Parsing file "+FileManager.getInstance().getFileName(id)+" is finished");
					Log.verbose("Get "+node.getNumChildNodes()+" meshes");
				}
			}else
			{
				try
				{
					Log.error("Parsing of "+FileManager.getInstance().getFileName(id)+" failed");
				}catch(NotFoundException e)
				{
					Log.error("Parsing of file with id='"+FileManager.getInstance().getFileName(id)+"' failed");
				}
			}
			return node;
		}
		catch(NotFoundException e)
		{
			try
			{
				Log.error("Parsing of "+FileManager.getInstance().getFileName(id)+" failed");
				Log.error(e);
			}catch(NotFoundException error)
			{
				Log.error("Parsing of file with id='"+FileManager.getInstance().getFileName(id)+"' failed");
				Log.error(e);
			}
		}
		return node;
	}
	/**
	 * Get All MeshBuilds described inside the file
	 * @param id File id
	 * @return Meshbuilders
	 */
	public Map<String, MeshBuilder> loadMeshBuilders(int id)
	{
		Map<String, MeshBuilder> meshes = new HashMap<String, MeshBuilder>();
		if(id != 0)
		{
			
			try
			{
				if(meshesManager == null)
				{
					meshesManager = new HashMap<Integer, Map<String, MeshBuilder>>();
				}
				if(meshesManager.containsKey(id))
				{
					Log.verbose("File "+FileManager.getInstance().getFileName(id)+" already parsed");
					return meshesManager.get(id);
				}
				MeshBuilder mesh;
				InputStream file = FileManager.getInstance().openFile(id);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
				String line;
				String[] values;
				List<Coord3D> vertices = new ArrayList<Coord3D>();
				List<Coord2D> textures = new ArrayList<Coord2D>();
				List<Coord3D> normals = new ArrayList<Coord3D>();
				List<Color> colors = new ArrayList<Color>();
				Log.verbose("Parsing "+FileManager.getInstance().getFileName(id));
				//first phase, records
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					//ignore
					if(values[0].equals(ignore))
					{
						continue;
					}else
					if(values[0].equals(materialFile))
					{
						//read Material file
						Material.parse(values[1]);
					}else
					if(values[0].equals(vertex))
					{
						//add vertex
						vertices.add(new Coord3D(values[1], values[2], values[3]));
					}
					else
					if(values[0].equals(normal))
					{
						//add normal
						normals.add(new Coord3D(values[1], values[2], values[3]));
					}
					else
					if(values[0].equals(texCoord))
					{
						//add tex
						if(values.length == 3)
						{
							textures.add(new Coord2D(Float.parseFloat(values[1]), Float.parseFloat(values[2])*-1.0f));
						}else
						{
							if(values.length == 4)
							{
								textures.add(new Coord3D(Float.parseFloat(values[1]), Float.parseFloat(values[2])*-1.0f, Float.parseFloat(values[3])));
							}
						}
					}else
					if(values[0].equals(color))
					{
						if(values.length == 4)
						{
							colors.add(new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3])));
						}else
						{
							if(values.length == 5)
							{
								colors.add(new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4])));
							}
						}
					}else
					if(values[0].equals(object))
					{
						if(!meshes.containsKey(values[1]))
						{
							Log.verbose("Found object : "+values[1]);
							mesh = new MeshBuilder();
							mesh.name = values[1];
							meshes.put(values[1], mesh);
						}
					}
				}	
				file.reset();
				Log.verbose("Building objects from "+FileManager.getInstance().getFileName(id));
				mesh = null;
				//if only one object sometimes no name is given
				if(meshes.size() == 0)
				{
					mesh = new MeshBuilder();
					mesh.name = FileManager.getInstance().getFileName(id);
					meshes.put(mesh.name, mesh);
				}
				int objectBuilt = 0;
				int i;
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					//ignore
					if(values[0].equals(ignore))
					{
						continue;
					}else
					if(values[0].equals(object))
					{
						//Building
						if(mesh != null && !mesh.isCorrect())
						{
							for(final Face face : mesh.faces)
							{
								for(i=0; i<face.size(); i++)
								{
									if(face.hasVertices())
									{
										mesh.vertices.add(vertices.get(face.vertices.get(i)-1).clone());
										face.vertices.set(i, mesh.vertices.size()-1);
									}
									if(face.hasTexturesCoordinates())
									{
										mesh.textureCoordinates.add(textures.get(face.textures.get(i)-1).clone());
										face.textures.set(i, mesh.textureCoordinates.size()-1);
									}
									if(face.hasNormals())
									{
										mesh.normals.add(normals.get(face.normals.get(i)-1).clone());
										face.normals.set(i, mesh.normals.size()-1);
									}
									if(face.hasColors())
									{
										mesh.colors.add(colors.get(face.colors.get(i)-1).clone());
										face.colors.set(i, mesh.colors.size()-1);
									}
								}
							}
						}
						mesh = meshes.get(values[1]);
						Log.verbose("Building object ["+(++objectBuilt)+"/"+meshes.size()+"]: "+ values[1]);
					}else
					if(values[0].equals(useMaterial))
					{
						if(mesh!=null)
						{
							if(values[1].equals("(null)"))
							{
								mesh.material = null;
							}else
							{
								mesh.material = Material.parse(values[1]);
							}
						}
					}else
					if(values[0].equals(face))
					{
						if(mesh != null)
						{
							mesh.faces.add(new Face(line.substring(line.indexOf(" ")+1)));
						}
					}
				}
				if(mesh != null && !mesh.isCorrect())
				{
					for(final Face face : mesh.faces)
					{
						for(i=0; i<face.size(); i++)
						{
							if(face.hasVertices())
							{
								mesh.vertices.add(vertices.get(face.vertices.get(i)-1).clone());
								face.vertices.set(i, mesh.vertices.size()-1);
							}
							if(face.hasTexturesCoordinates())
							{
								mesh.textureCoordinates.add(textures.get(face.textures.get(i)-1).clone());
								face.textures.set(i, mesh.textureCoordinates.size()-1);
							}
							if(face.hasNormals())
							{
								mesh.normals.add(normals.get(face.normals.get(i)-1).clone());
								face.normals.set(i, mesh.normals.size()-1);
							}
							if(face.hasColors())
							{
								mesh.colors.add(colors.get(face.colors.get(i)-1).clone());
								face.colors.set(i, mesh.colors.size()-1);
							}
						}
					}
				}
				buffer.close();
				file.close();
				Log.warning("All objects are parsed");
				Log.warning("Meshes:"+meshes.size());
				Log.warning("Vertices:"+vertices.size());
				Log.warning("Normals:"+normals.size());
				Log.warning("Textures:"+textures.size());
				Log.warning("Colors:"+colors.size());
				Log.warning("For all objects:");
				int countVertices = 0, countTextures = 0, countNormals = 0, countColors = 0;
				for(final MeshBuilder _mesh : meshes.values())
				{
					countVertices +=_mesh.vertices.size();
					countNormals +=_mesh.normals.size();
					countTextures +=_mesh.textureCoordinates.size();
					countColors +=_mesh.colors.size();
				}
				Log.warning("Vertices:"+countVertices);
				Log.warning("Normals:"+countNormals);
				Log.warning("Textures:"+countTextures);
				Log.warning("Colors:"+countColors);
				countVertices = countTextures = countNormals =  countColors = 0;
				Log.verbose("optimizing");
				for(final MeshBuilder _mesh : meshes.values())
				{
					_mesh.optimize();
					_mesh.normalize();
					countVertices +=_mesh.vertices.size();
					countNormals +=_mesh.normals.size();
					countTextures +=_mesh.textureCoordinates.size();
					countColors +=_mesh.colors.size();
				}
				Log.verbose("Result :");
				Log.warning("Vertices:"+countVertices);
				Log.warning("Normals:"+countNormals);
				Log.warning("Textures:"+countTextures);
				Log.warning("Colors:"+countColors);
				meshesManager.put(id, meshes);
			}catch(NotFoundException e)
			{
				Log.error(id +" file has not been found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.error("Error I/O while reading file "+id+"["+FileManager.getInstance().getFileName(id)+"]");
				Log.error(e);
			}
			catch(Exception e)
			{
				Log.error("Error during parsing file "+FileManager.getInstance().getFileName(id));
				Log.error(e);
			}
		}
		return meshes;
	}

}
