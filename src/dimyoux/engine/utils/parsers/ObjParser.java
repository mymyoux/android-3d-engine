package dimyoux.engine.utils.parsers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;


import dimyoux.engine.R;
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
	/**
	 * Resources
	 */
	public static Resources resources;
	public static final String vertex = "v";
	/**
	 * Load file
	 * @param filename File name 
	 */
	public void load(String filename)
	{
		Log.verbose("Load:"+filename);
		load(resources.getIdentifier(filename, null, null));
	}
	/**
	 * Load file
	 * @param id File id
	 */
	public void load(int id)
	{
		
		Log.verbose("loading object:"+id);
		//Log.debug(resources.getString(R.raw.camaro_obj));
		if(id != 0)
		{
			int vertices = 0;
			try
			{
				InputStream file = resources.openRawResource(id);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(file));
				String line;
				
				while((line = buffer.readLine()) != null)
				{
					if(line.split(" ")[0].equals(vertex))
					{
						vertices++;
					}
				}
				//rewind
				file.reset();
				Mesh mesh = new Mesh();
				mesh.verticesBuffer = Buffer.CreateFloatBuffer(vertices*3);
				String[] values;
				//TODO : a String[] or List<String> instead of reading twice ?
				while((line = buffer.readLine()) != null)
				{
					values = line.split(" ");
					if(values[0].equals(vertex))
					{
						mesh.verticesBuffer.put(Float.parseFloat(values[1]));
						mesh.verticesBuffer.put(Float.parseFloat(values[2]));
						mesh.verticesBuffer.put(Float.parseFloat(values[3]));
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
}
