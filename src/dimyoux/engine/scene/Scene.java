package dimyoux.engine.scene;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;


import dimyoux.engine.utils.Log;
/**
 * Scene class
 */
public class Scene {
	/**
	 * GL instance
	 */
	public static GL10 gl;
	/**
	 * Singleton Scene instance 
	 */
	private static Scene _instance;
	/**
	 * ChildNodes (can be empty)
	 */
	private List<Node> childNodes;
	/**
	 * Constructor
	 */
	private Scene()
	{
		childNodes = new ArrayList<Node>();
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
	 * Get the number of childNodes
	 * @return Number of childNodes
	 */
	public int getNumChildNodes()
	{
		return childNodes.size();
	}
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
	{
		for(final Node node : childNodes)
		{
			node.draw();
		}
	}
}
