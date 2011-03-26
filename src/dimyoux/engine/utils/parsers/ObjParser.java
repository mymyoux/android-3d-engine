package dimyoux.engine.utils.parsers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;


import dimyoux.engine.R;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.scene.Mesh;
import dimyoux.engine.utils.Buffer;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;


/**
 * Parses .obj files
 * 
 */
public class ObjParser {
	private static final String vertex = "v";
	private static final String normal = "vn";
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
	private Material currentMaterial;
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
		
		
		//Log.debug(resources.getString(R.raw.camaro_obj));
		if(id != 0)
		{
			int vertices = 0;
			int normals = 0;
			int texCoords = 0;
			materials = new HashMap<String,Material>();
			try
			{
				Log.verbose("Loading object["+id+"]="+FileManager.resources.getString(id));
				//needed for reset
				InputStream file = FileManager.getInstance().openFile(id);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
				String line;
				String[] values;
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
				Mesh mesh = new Mesh();
				mesh.verticesBuffer = Buffer.CreateFloatBuffer(vertices*3);
				mesh.normalsBuffer = Buffer.CreateFloatBuffer(normals*3);
				//TODO: can have 3 coordinates
				mesh.texCoordsBuffer = Buffer.CreateFloatBuffer(texCoords*2);
				//TODO : a String[] or List<String> instead of reading twice ?
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					if(values[0].equals(ignore))
					{
						continue;
					}
					if(values[0].equals(vertex))
					{
						mesh.verticesBuffer.put(Float.parseFloat(values[1]));
						mesh.verticesBuffer.put(Float.parseFloat(values[2]));
						mesh.verticesBuffer.put(Float.parseFloat(values[3]));
					}
					if(values[0].equals(normals))
					{
						mesh.normalsBuffer.put(Float.parseFloat(values[1]));
						mesh.normalsBuffer.put(Float.parseFloat(values[2]));
						mesh.normalsBuffer.put(Float.parseFloat(values[3]));
					}
					if(values[0].equals(texCoord))
					{
						mesh.texCoordsBuffer.put(Float.parseFloat(values[1]));
						//TODO:to verify
						mesh.texCoordsBuffer.put(Float.parseFloat(values[2])*-1.0f);
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
				Log.verbose(vertices+" vertices");
				Log.verbose(mesh.verticesBuffer);
			}catch(NotFoundException e)
			{
				Log.error(id +" file has not been found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.error("Error while reading file "+id+"["+FileManager.getInstance().getFileName(id)+"]");
				Log.error(e);
			}
		}
		Log.verbose("End loading "+id+" object");
		
	}
	private void readMaterialFile(String fileName)
	{
		try
		{
			Log.debug("Read Filename: "+fileName);
			BufferedReader buffer = FileManager.getInstance().openTextFile(fileName);
			
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

