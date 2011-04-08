package dimyoux.engine.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dimyoux.engine.R;
import dimyoux.engine.utils.Log;

/**
 * File Manager
 */
public class FileManager {
	/**
	 * Resources Access
	 */
	static public Resources resources;
	/**
	 * Instance [Singleton]
	 */
	static private FileManager _instance;
	/**
	 * Package Name. Useful for opening files
	 */
	static private String packageName; 
	/**
	 * Potential folders
	 */
	static private List<String> folders;
	/**
	 * Constructor
	 */
	private FileManager()
	{
		packageName = R.class.getPackage().getName();
		folders = new ArrayList<String>();
		folders.add("raw");
		folders.add("drawable");
	}
	/**
	 * Adds a potential folder
	 * @param folderName Folder's name
	 */
	public void addFolder(String folderName)
	{
		if(!folders.contains(folderName))
		{
			folders.add(folderName);
		}
	}
	/**
	 * Removes a potential folder
	 * @param folderName Folder's name
	 */
	public void remFolder(String folderName)
	{
		if(folders.contains(folderName))
		{
			folders.remove(folderName);
		}
	}
	/**
	 * @return Return the FileManager instance
	 */
	public static FileManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new FileManager();
		}
		return _instance;
	}
	/**
	 * Open a file as text file
	 * @param name File's name
	 * @param folder Folder's name
	 * @return File
	 */
	public BufferedReader openTextFile(String name, String folder)
	{
		return new BufferedReader(new InputStreamReader(openFile(name, folder)));
	}
	/**
	 * Open a file as text file
	 * @param name File's name
	 * @return File
	 */
	public BufferedReader openTextFile(String name)
	{
		return new BufferedReader(new InputStreamReader(openFile(name)));
	}
	/**
	 * Open a file
	 * @param id File's ID
	 * @return File
	 */
	public BufferedReader openTextFile(int id)
	{
		return new BufferedReader(new InputStreamReader(openFile(id)));
	}
	//TODO:resources can't be in objParser !!!
	/**
	 * Open a file
	 * @param name Name of file
	 * @return File
	 */
	public InputStream openFile(String name) throws NotFoundException
	{
		int id = getFileID(name);
		return resources.openRawResource(id);
	}
	/**
	 * Open a file
	 * @param id File's ID
	 * @return File 
	 */
	public InputStream openFile(int id)
	{
		return resources.openRawResource(id);
	}
	/**
	 * Open a file
	 * @param name Name of file
	 * @param folder Folder
	 * @return File
	 */
	public InputStream openFile(String name, String folder) throws NotFoundException
	{
		int id = getFileID(name, folder);
		return resources.openRawResource(id);
	}
	/**
	 * Gets file name associated with id
	 * @param id ID of file
	 * @return URL of file
	 */
	public String getFileName(int id)
	{
		return resources.getString(id);
	}
	/**
	 * Gets ID of a file
	 * @param name File's name
	 * @return ID of the file. 0 if the file hasn't been found
	 */
	public int getFileID(String name)
	{
		if(name.startsWith("/"))
		{
			name = name.substring(1);
		}
		String packageName = FileManager.packageName+":";
		int id = 0;
		for(final String folder : folders)
		{
			id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
			if(id > 0)
			{
				break;
			}
		}
		if(id==0)
		{
			name = name.replaceAll("\\.","_");
			for(final String folder : folders)
			{
				id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
				if(id > 0)
				{
					break;
				}
			}
		}
		if(id == 0)
		{
			id = resources.getIdentifier(packageName+"/"+name, null, null);
			if(id==0)
			{
				id = resources.getIdentifier(name, null, null);
			}
		}
		if(id==0)
		{
			if(name.lastIndexOf("_")>-1)
			{
				name = name.substring(0, name.lastIndexOf("_"));
				for(final String folder : folders)
				{
					id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
					if(id > 0)
					{
						break;
					}
				}
			}
			if(id == 0)
			{
				id = resources.getIdentifier(packageName+"/"+name, null, null);
				if(id==0)
				{
					id = resources.getIdentifier(name, null, null);
				}
			}
		}
		return id;
	}
	/**
	 * Gets ID of a file
	 * @param name File's name
	 * @param folder Folder where the file is
	 * @return ID of the file. 0 if the file hasn't been found
	 */
	public int getFileID(String name, String folder)
	{
		if(name.startsWith("/"))
		{
			name = name.substring(1);
		}
		String packageName = FileManager.packageName+":";
		int id;
		try
		{
			id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
		}catch(NotFoundException e)
		{
			name = name.replaceAll("\\.","_");
			id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
		}
		if(id == 0)
		{
			id = resources.getIdentifier(folder+"/"+name, null, null);
		}
		if(id == 0)
		{
			name = name.substring(0, name.lastIndexOf("_"));
			id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
			if(id == 0)
			{
				id = resources.getIdentifier(folder+"/"+name, null, null);
			}
		}
		return id;
	}
	/**
	 * Open a bitmap file
	 * @param id File's id
	 * @return Bitmap
	 */
	public Bitmap loadBitmap(int id)
	{
		//need to use BitmapFactory.decodeStream because BitmapFactory.decodeBitmap load a picture with height and width 64 pixels higher !??
		InputStream is = resources.openRawResource(id);
		Bitmap bitmap;
		try {
		   bitmap = BitmapFactory.decodeStream(is);
		   return bitmap;
		} catch(Exception error) {
		   try {
			   Log.error("ID["+id+"] bitmap has not been loaded");
			   Log.error("ID["+id+"]=>"+getFileName(id));
		      is.close();
		   } catch(IOException e) {
		      // Ignore.
		   }
		}
		
		return null;
	}
	/**
	 * Open a bitmap file
	 * @param name Name of file
	 * @return Bitmap
	 */
	public Bitmap loadBitmap(String name)
	{
		Log.verbose("Load bitmap["+name+"]");
		return  loadBitmap(getFileID(name));
	}
	/**
	 * Open a bitmap file
	 * @param name Name of file
	 * @param folder Folder
	 * @return Bitmap
	 */
	public Bitmap loadBitmap(String name, String folder)
	{
		Log.verbose("Load bitmap["+name+"]");
		return  loadBitmap(getFileID(name, folder));
	}
	
	/**
	 * Serializes an object
	 * @param object Object
	 * @param name Name
	 * @return True if the serializing has succeeded
	 */
	public boolean serialize(Serializable object, String name)
	{
		if(name != null && name.length()>0)
		{
	        try {
	        	ObjectOutputStream serialise;
	        	File cache = ApplicationManager.getInstance().getActivity().getExternalFilesDir(null);
	        	if(cache == null)
	        	{
	        		ApplicationManager.getInstance().getActivity().getCacheDir();
	        	}
	        	 File file = new File(cache, name+".serialized");
	        	 
	        	 serialise = new ObjectOutputStream(new FileOutputStream(file));
		            serialise.writeObject(object);
		            serialise.flush();            
		            serialise.close();
		            Log.verbose("Serialized file : "+name);
		            return true;
	        }catch(Exception e) {
	        	Log.error(e.getMessage());
	        	Log.error(e);
	        	
			}
		}
        return false;
	}
	
	/**
	 * Deserializes an object
	 * @param object Object
	 * @param name Name
	 * @return Deserialized oject
	 */
	public Object deserialize(String name)
	{
		if(name != null && name.length()>0)
		{
	        try {
	        	ObjectInputStream deserialise;
	        	File cache = ApplicationManager.getInstance().getActivity().getExternalFilesDir(null);
	        	
	        	if(cache == null)
	        	{
	        		ApplicationManager.getInstance().getActivity().getCacheDir();
	        	}
	        	 File file = new File(cache, name+".serialized");
	        	 deserialise = new ObjectInputStream(new FileInputStream(file));
	        	 Object object = deserialise.readObject();         
	        	 deserialise.close();
		            Log.verbose("Deserialized file : "+name);
		            return object;
	        }catch(Exception e) {
	        	Log.error(e.getMessage());
	        	Log.error(e);
	        	
			}
		}
        return null;
	}
	/**
	 * Deletes a serialized file
	 * @param name File's name
	 */
	public void deleteSerializedFile(String name)
	{
		if(name != null && name.length()>0)
		{
	        try {
	        	File cache = ApplicationManager.getInstance().getActivity().getExternalFilesDir(null);
	        	
	        	if(cache == null)
	        	{
	        		ApplicationManager.getInstance().getActivity().getCacheDir();
	        	}
	        	 File file = new File(cache, name+".serialized");
	        	 file.delete();
	        }catch(Exception e) {
	        	Log.error(e.getMessage());
	        	Log.error(e);
	        	
			}
		}
	}
}
