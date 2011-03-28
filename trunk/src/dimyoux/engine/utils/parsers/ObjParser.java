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
	private static Map<String, Material> materials;
	//TODO:Integer, Mesh
	private static Map<Integer, MeshBuilder> meshs;
	/**
	 * Load file
	 * @param filename File name 
	 */
	public MeshBuilder load(String filename)
	{
		return load(FileManager.getInstance().getFileID(filename));
	}
	/**
	 * Load file
	 * @param id File id
	 */
	public MeshBuilder load(int id)
	{
		MeshBuilder mesh = new MeshBuilder();
		if(id != 0)
		{
			if(meshs == null)
			{
				meshs = new HashMap<Integer, MeshBuilder>();
			}
			if(meshs.containsKey(id))
			{
				Log.verbose("Already exists");
				//TODO:clone
				return meshs.get(id);
			}else
			{
				int vertices = 0;
				int normals = 0;
				int texCoords = 0;
				if(materials == null)
				{
					materials = new HashMap<String, Material>();
				}
				Material currentMaterial = new Material();
				try
				{
					Log.verbose("Loading object["+id+"]="+FileManager.resources.getString(id));
					//needed for reset (not anymore but maybe it's better to close the file
					InputStream file = FileManager.getInstance().openFile(id);
					BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
					String line;
					String[] values;
					//TODO:can have more than one mesh by .obj file
					mesh = new MeshBuilder();
					 
					while((line = buffer.readLine()) != null)
					{
						values = line.split(" ");
						if(values[0].equals(ignore))
						{
							continue;
						}
						if(values[0].equals(materialFile))
						{
							readMaterialFile(values[1]);
						}
						else
						if(values[0].equals(vertex))
						{
							mesh.vertices.add(new Coord3D(values[1], values[2], values[3]));
						}
						else
						if(values[0].equals(normal))
						{
							mesh.normals.add(new Coord3D(values[1], values[2], values[3]));
						}
						else
						if(values[0].equals(texCoord))
						{
							if(values.length == 3)
							{
								mesh.textureCoordinates.add(new Coord2D(Float.parseFloat(values[1]), Float.parseFloat(values[2])*-1.0f));
							}else
							{
								if(values.length == 4)
								{
									mesh.textureCoordinates.add(new Coord3D(Float.parseFloat(values[1]), Float.parseFloat(values[2])*-1.0f, Float.parseFloat(values[3])));
								}
							}
						}
						else
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
						else
						if(values[0].equals(object))
						{
							if(mesh.name==null)
							{
								mesh.name = values[1];
							}else
							{
								mesh = new MeshBuilder();
								mesh.name = values[1];
							}
						}
						else
						if(values[0].equals(face))
						{
							mesh.faces.add(new Face(line.substring(line.indexOf(" ")+1), currentMaterial.name));
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
					meshs.put(id, mesh);
				}catch(NotFoundException e)
				{
					Log.error(id +" file has not been found");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.error("Error while reading file "+id+"["+FileManager.getInstance().getFileName(id)+"]");
					Log.error(e);
				}
			}
		}
		//TODO:convert Mesh file to Mesh
		//TODO:save Mesh inside some map
		Log.verbose("End loading "+FileManager.getInstance().getFileName(id));
		return mesh;
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

	

}
