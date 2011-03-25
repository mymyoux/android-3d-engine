package dimyoux.engine.managers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources.NotFoundException;

import dimyoux.engine.R;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.parsers.ObjParser;

/**
 * File Manager
 */
public class FileManager {
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
	public FileManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new FileManager();
		}
		return _instance;
	}
	//TODO:resources can't be in objParser !!!
	/**
	 * Open a file
	 * @param name Name of file
	 */
	public InputStream openFile(String name) throws NotFoundException
	{
		name = name.replace(".","_");
		String packageName = this.packageName+":";
		int id = 0;
		for(final String folder : folders)
		{
			id = ObjParser.resources.getIdentifier(packageName+folder+"/"+name, null, null);
			if(id > 0)
			{
				break;
			}
		}
		return ObjParser.resources.openRawResource(id);
	}
	/**
	 * Open a file
	 * @param name Name of file
	 * @param folder Folder
	 */
	public InputStream openFile(String name, String folder) throws NotFoundException
	{
		String packageName = this.packageName+":";
		return ObjParser.resources.openRawResource(ObjParser.resources.getIdentifier(packageName+folder+"/"+name, null, null));
	}
}
