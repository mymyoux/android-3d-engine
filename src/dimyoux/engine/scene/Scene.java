package dimyoux.engine.scene;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.managers.FileManager;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.parsers.Material;
/**
 * Scene class
 */
public class Scene implements Serializable {
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * GL instance
	 */
	transient public static GL11 gl;
	/**
	 * Singleton Scene instance 
	 */
	transient private static Scene _instance;
	/**
	 * ChildNodes (can be empty)
	 */
	private List<Node> childNodes;
	/**
	 * Lights (can be empty)
	 */
	//private List<Light> lights;
	/**
	 * Constructor
	 */
	private Scene()
	{
		childNodes = new ArrayList<Node>();
		//lights = new ArrayList<Light>();
	}
	/**
	 * Get Root scene access
	 * @return Scene instance
	 */
	public static Scene getRoot()
	{
		if(_instance == null)
		{
			_instance = new Scene();
		}
		return _instance;
	}
	/**
	 * Attaches a node to this current node. An error is thrown (throw LogCat) if there is a recursive link 
	 * @param node Node to attach
	 */
	public void attachChildNode(Node node)
	{
		if(!childNodes.contains(node))
		{
			if(!this.isChildNode(node, true))
			{
				childNodes.add(node);
			}else
			{
				Log.error("The Scene has already this node");
			}	
		}
	}
	/**
	 * Get the childNode at an index
	 * @param indice Index	
	 * @return The child or null if index >= childNodes.size()
	 */
	public Node getChildNode(int index)
	{
		if(childNodes.size()>index)
		{
			return childNodes.get(index);
		}
		return null;
	}
	/**
	 * Gets the number of childNodes
	 * @return Number of childNodes
	 */
	public int getNumChildNodes()
	{
		return childNodes.size();
	}
	/**
	 * Gets the number of lights
	 * @return Number of lights
	 */
	/*
	public int getNumLights()
	{
		return lights.size();
	}
	*/
	/**
	 * Get ChildNodes
	 * @return ChildNodes
	 */
	public List<Node> getChildNodes()
	{
		return childNodes;
	}
	/**
	 * Indicates if this node has at least a child node
	 * @return true of false
	 */
	public boolean hasChildNode()
	{
		return childNodes.size()>0;
	}
	/**
	 * Indicates if a node is a child of this one
	 * @param node Node to test
	 * @return true of false
	 */
	public boolean isChildNode(Node node)
	{
		return childNodes.contains(node);
	}
	/**
	 * Indicates if a node is a child or grand child of this one
	 * @param node Node to test
	 * @return true of false
	 */

	public boolean isChildNode(Node node, boolean recursive)
	{
		if(isChildNode(node))
		{
			return true;
		}
		if(recursive)
		{
			for(final Node childNode : childNodes)
			{
				if(childNode.isChildNode(node, true))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Called for drawing
	 */
	public void draw()
	{// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		Camera.update();
		// Translates 4 units into the screen.
		gl.glTranslatef(0, 0, -20); 
		// Draw our square.
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
		gl.glColor4f(1, 1, 1, 1);
		// Enabled the vertices buffer for writing and to be used during 
		// rendering.
	
		for(final Node node : childNodes)
		{
			node.draw();
		}
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
		/*
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		//gl.glPushMatrix();
		gl.glLoadIdentity();

	    gl.glTranslatef(0, 0, -4);
	 //   gl.glRotatef(10, 1, 0, 0);   
	  //  gl.glRotatef(10, 0, 1, 0);   

		for(final Node node : childNodes)
		{
			node.draw();
		}
	//	gl.glPopMatrix();
        
		gl.glDisable(GL10.GL_TEXTURE_2D);
       	gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
		gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);		
		gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);				
		*/

	   
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		return "[Scene childNodes=\""+childNodes.size()+"\"]";
	}
	/**
	 * Serializes the object
	 * @param out ObjectOutputStream
	 * @throws IOException Error
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		out.writeObject(Material.getMaterialList());
		out.writeObject(Light.getAllLight());
	}

	/**
	 * Deserializes the object
	 * @param out ObjectOutputStream
	 * @throws IOException Error
	 */
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		Material.getMaterialList().putAll((Map<String, Material>)in.readObject());
		Light.getAllLight().addAll((List<Light>)in.readObject());
	}
	/**
	 * Saves the current scene
	 * @param name Name for the current scene
	 */
	public void save(String name)
	{
		
		FileManager.getInstance().serialize(this, name);
	}
	/**
	 * Load a saved scene
	 * @param name Name of the scene
	 * @return True if the load is a success false otherwise.
	 */
	public boolean load(String name)
	{
		Scene scene = (Scene)FileManager.getInstance().deserialize(name);
		if(scene == null)
		{
			return false;
		}
		this.childNodes = scene.childNodes;
		return true;
	}

	/**
	 * Deletes a save
	 * @param name Save's name
	 */
	public void clearSave(String name)
	{
		FileManager.getInstance().deleteSerializedFile(name);
	}
}
