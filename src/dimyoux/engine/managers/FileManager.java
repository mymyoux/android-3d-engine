package dimyoux.engine.managers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;

import dimyoux.engine.R;

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
			name = name.substring(0, name.lastIndexOf("_"));
			for(final String folder : folders)
			{
				id = resources.getIdentifier(packageName+folder+"/"+name, null, null);
				if(id > 0)
				{
					break;
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
}
