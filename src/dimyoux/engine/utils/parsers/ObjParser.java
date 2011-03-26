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
	private static final String material = "mtllib";
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
				Log.verbose("loading object:"+id);
				Log.verbose("object["+id+"]="+FileManager.resources.getString(id));
				InputStream file = FileManager.resources.openRawResource(id);
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
					if(values[0].equals(material))
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
				}
				//close
				file.close();
				Log.verbose(vertices+" vertices");
				Log.verbose(mesh.verticesBuffer);
			}catch(NotFoundException e)
			{
				Log.error(id +" file has not been found");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.error("Error while reading file "+id);
			}
		}
		Log.verbose("End loading "+id+" object");
		
	}
	private void readMaterialFile(String fileName)
	{
		try
		{
			Log.warning(R.class.getPackage().getName());
			Log.warning("res/raw/"+fileName.replace(".","_"));
			int id = FileManager.resources.getIdentifier(":/raw/"+fileName.replace(".","_"), null, null);
			Log.warning(id);
			InputStream file = FileManager.resources.openRawResource(id);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
			Log.debug("Read Filename: "+fileName);
		}catch(Exception e)
		{
			Log.error(e);
		}

	}
}
