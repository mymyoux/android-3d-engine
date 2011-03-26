package dimyoux.engine.utils.parsers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dimyoux.engine.R;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.scene.Mesh;
import dimyoux.engine.utils.Buffer;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord2D;
import dimyoux.engine.utils.math.Coord3D;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;


/**
 * Parses .obj files
 * 
 */
public class ObjParser {
	private static final String vertex = "v";
	private static final String object = "o";
	private static final String normal = "vn";
	private static final String face = "f";
	private static final String ignore = "#";
	private static final String texCoord = "vt";
	private static final String useMaterial = "usemtl";
	private static final String materialFile = "mtllib";
	private static final String materialName = "newmtl";
	private static final String materialShininess = "Ns";
	private static final String materialAmbiantColor = "Ka";
	private static final String materialDiffuseColor = "Kd";
	private static final String materialSpecularColor = "Ks";
	private static final String materialAlpha = "d";
	private static final String materialAlpha2 = "Tr";
	private static final String materialIlluminationModel = "illum";
	private static final String materialTexture = "map_Kd";
	private static final String materialOpticalDensity = "Ni";
	private Map<String, Material> materials;
	/**
	 * Load file
	 * @param filename File name 
	 */
	public void load(String filename)
	{
		Log.verbose("Load:"+filename);
		load(FileManager.getInstance().getFileID(filename));
	}
	/**
	 * Load file
	 * @param id File id
	 */
	public void load(int id)
	{
		if(id != 0)
		{
			int vertices = 0;
			int normals = 0;
			int texCoords = 0;
			materials = new HashMap<String,Material>();
			Material currentMaterial = new Material();
			try
			{
				Log.verbose("Loading object["+id+"]="+FileManager.resources.getString(id));
				//needed for reset
				InputStream file = FileManager.getInstance().openFile(id);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
				String line;
				String[] values;
				//TODO:still needed ? I think not
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					if(values[0].equals(ignore))
					{
						continue;
					}
					if(values[0].equals(vertex))
					{
						vertices++;
					}
					if(values[0].equals(normal))
					{
						normals++;
					}
					if(values[0].equals(texCoord))
					{
						texCoords++;
					}
					if(values[0].equals(materialFile))
					{
						readMaterialFile(values[1]);
					}
				}
				//rewind
				file.reset();
				//TODO:can have more than one mesh by .obj file
				_Mesh mesh = new _Mesh();
				 
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					if(values[0].equals(ignore))
					{
						continue;
					}
					if(values[0].equals(vertex))
					{
						mesh.vertices.add(new Coord3D(values[1], values[2], values[3]));
					}
					if(values[0].equals(normal))
					{
						mesh.normals.add(new Coord3D(values[1], values[2], values[3]));
					}
					if(values[0].equals(texCoord))
					{
						mesh.textureCoordinates.add(new Coord2D(Float.parseFloat(values[1]), Float.parseFloat(values[2])*-1.0f));
					}
					if(values[0].equals(useMaterial))
					{
						if(materials.containsKey(values[1]))
						{
							currentMaterial = materials.get(values[1]);
							Log.verbose("Use of "+currentMaterial);
						}else
						{
							Log.warning("During parsing "+FileManager.getInstance().getFileName(id)+" material "+values[1]+" has not been found");
						}
					}
					if(values[0].equals(object))
					{
						if(mesh.name==null)
						{
							mesh.name = values[1];
						}else
						{
							mesh = new _Mesh();
							mesh.name = values[1];
						}
					}
					if(values[0].equals(face))
					{
						mesh.faces.add(new _Face(line.substring(line.indexOf(" ")+1), currentMaterial.name));
					}
				}
				//close
				try
				{
					buffer.close();
					file.close();
				}
				catch(IOException exception)
				{
					Log.warning(exception);
				}
				Log.verbose(mesh);
			}catch(NotFoundException e)
			{
				Log.error(id +" file has not been found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.error("Error while reading file "+id+"["+FileManager.getInstance().getFileName(id)+"]");
				Log.error(e);
			}
		}
		//TODO:convert _Mesh file to Mesh
		//TODO:save Mesh inside some map
		Log.verbose("End loading "+FileManager.getInstance().getFileName(id));
		
	}
	private void readMaterialFile(String fileName)
	{
		try
		{
			BufferedReader buffer = FileManager.getInstance().openTextFile(fileName);
			Log.verbose("Material file found : "+fileName);
			String line;
			String[] values;
			Material material = new Material();
			float alpha = 1.0f;
			while((line = buffer.readLine()) != null)
			{
				values = line.split(" ");
				if(values[0].equals(ignore))
				{
					continue;
				}
				if(values[0].equals(materialName))
				{
					material = new Material();
					material.name = values[1];
					materials.put(values[1], material);
				}
				if(values[0].equals(materialShininess))
				{
					material.shininess = Float.parseFloat(values[1]);
				}
				if(values[0].equals(materialShininess))
				{
					material.shininess = Float.parseFloat(values[1]);
				}
				if(values[0].equals(materialAmbiantColor))
				{
					material.ambientColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialDiffuseColor))
				{
					material.diffuseColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialSpecularColor))
				{
					material.specularColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialAlpha) || values[0].equals(materialAlpha2))
				{
					alpha = Float.parseFloat(values[1]);
					if(material.hasAmbientColor())
					{
						material.ambientColor.a = alpha;
					}
					if(material.hasDiffuseColor())
					{
						material.diffuseColor.a = alpha;
					}
					if(material.hasSpecularColor())
					{
						material.specularColor.a = alpha;
					}
				}
				if(values[0].equals(materialIlluminationModel))
				{
					material.useSpecularColor = Integer.parseInt(values[1])==2;
				}
				if(values[0].equals(materialTexture))
				{
					Log.verbose("Load a texture file :"+values[1]);
					material.idTextureFile = FileManager.getInstance().getFileID(values[1]);
				}
				if(values[0].equals(materialOpticalDensity))
				{
					material.opticalDensity = Float.parseFloat(values[1]);
				}
			}
			Log.warning(material);
		}catch(Exception e)
		{
			Log.error(e);
		}

	}
	private class _Mesh
	{
		public List<Coord3D> vertices;
		public List<Coord3D> normals;
		public List<Coord2D> textureCoordinates;
		public List<_Face> faces;
		public String name;
		public _Mesh()
		{
			vertices = new ArrayList<Coord3D>();
			normals = new ArrayList<Coord3D>();
			textureCoordinates = new ArrayList<Coord2D>();
			faces = new ArrayList<_Face>();
		}
		@Override
		public String toString()
		{
			String txt = "[Mesh";
			if(name != null)
			{
				txt+="["+name+"]";
			}
			if(vertices.size()>0)
			{
				txt+=" V=\""+vertices.size()+"\"";
			}
			if(normals.size()>0)
			{
				txt+=" N=\""+normals.size()+"\"";
			}
			if(textureCoordinates.size()>0)
			{
				txt+=" T=\""+textureCoordinates.size()+"\"";
			}
			if(faces.size()>0)
			{
				txt+=" F=\""+faces.size()+"\"";
			}
			return txt+"]";
		}
	}
	private class _Face
	{
		public int[] vertices;
		public int[] textVertices;
		public int[] normalVertices;
		public String material;
		public boolean hasVertices = false;
		public boolean hasTextVertices = false;
		public boolean hasNormalVertices = false;
		public _Face(String face, String material)
		{
			int i, id;
			String f;
			String[] faces = face.split(" ");
			String[] faceCoords;
			vertices = new int[faces.length];
			textVertices = new int[faces.length];
			normalVertices = new int[faces.length];
			for(id=0; id<faces.length; id++)
			{
				f = faces[id];
				faceCoords = f.split("/");
				for(i=0; i<faceCoords.length; i++)
				{
					if(i==0)
					{
						try
						{
							vertices[id] = Integer.parseInt(faceCoords[i]);
							hasVertices = true;
						}
						catch(NumberFormatException e)
						{
							Log.warning(e);
						}
					}else
					{
						if(i==1)
						{
							try
							{
								textVertices[id] = Integer.parseInt(faceCoords[i]);	
								hasTextVertices = true;
							}
							catch(NumberFormatException e)
							{
								Log.warning(e);
							}
							
						}else
						{
							if(i==2)
							{
								try
								{
									normalVertices[id] = Integer.parseInt(faceCoords[i]);	
									hasNormalVertices = true;
								}
								catch(NumberFormatException e)
								{
									Log.warning(e);
								}								
							}
						}
					}
				}
			}
			this.material = material;
		}
		public _Face(int[] vertices, int[] textVertices, int[] normalVertices, String material)
		{
			this.vertices = vertices;
			this.textVertices = textVertices;
			this.normalVertices = normalVertices;
			this.material = material;
		}
		@Override
		public String toString()
		{
			String txt = "[Face";
			if(hasVertices)
			{
				txt+=" V=\""+vertices.length+"\"";
			}
			if(hasTextVertices)
			{
				txt+=" T=\""+textVertices.length+"\"";
			}
			if(hasNormalVertices)
			{
				txt+=" N=\""+normalVertices.length+"\"";
			}
			if(material != null)
			{
				txt+=" mat=\""+material+"\"";
			}
			return txt+"]";
		}
	}

}
