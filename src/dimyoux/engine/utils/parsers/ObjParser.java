package dimyoux.engine.utils.parsers;

import java.io.InputStream;


import dimyoux.engine.R;
import dimyoux.engine.utils.Log;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;


/**
 * Parses .obj files
 * 
 */
public class ObjParser {
	/**
	 * Load file
	 * @param file File
	 */
	public void load(String file)
	{
		Resources resources = Resources.getSystem();
		Log.debug("loading object");
		//Log.debug(resources.getString(R.raw.camaro_obj));
		int id = resources.getIdentifier("dimyoux.engine:raw/camaro_mtl", null, null);
		Log.debug("load "+id+" object");
		//id = resources.getIdentifier(R.raw.camaro_obj, null, null);
		Log.debug("load "+id+" object");
		id = R.raw.camaro_obj;
		if(id != 0)
		{
			try
			{
				InputStream fileIn = resources.openRawResource(id);
			}catch(NotFoundException e)
			{
				Log.error(file +" has not been found");
			}
		}
		Log.debug("End loading "+id+" object");
		
	}
}
