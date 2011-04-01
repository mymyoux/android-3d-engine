package dimyoux.engine.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.utils.Log;
/**
 * Node
 */
public class Node implements Serializable {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	public float x;
	public float y;
	public float z;
	/**
	 * Entity (can be null)
	 */
	private Entity entity;
	/**
	 * ChildNodes (can be empty)
	 */
	private List<Node> childNodes;
	/**
	 * ParentNode (can be null)
	 */
	private Node parentNode;
	/**
	 * Constructor
	 */
	public Node()
	{
		childNodes = new ArrayList<Node>();
	}
	/**
	 * Get Root scene access
	 * @return Scene instance
	 */
	public Scene getRoot()
	{
		return Scene.getRoot();
	}
	/**
	 * Attaches a entity to this node
	 * One entity by node
	 * @param entity Entity to add
	 */
	public void attachEntity(Entity entity)
	{
		this.entity = entity;
	}
	/**
	 * Returns the entity attached to the node if it exists
	 * @return eEntity
	 */
	public Entity getEntity()
	{
		return entity;
	}
	/**
	 * Tests if this node has an entity
	 * @return True of false
	 */
	public boolean hasEntity()
	{
		return entity!=null;
	}
	/**
	 * Attaches a node to this current node. An error is thrown (throw LogCat) if there is a recursive link 
	 * @param node Node to attach
	 */
	public void attachChildNode(Node node)
	{
		if(!childNodes.contains(node))
		{
			if(!this.isParentNode(node, true) && !this.isChildNode(node))
			{
				Log.warning("Add "+node+" to "+this);
				childNodes.add(node);
				node.setParentNode(this);
				node.parentNode = this;
			}else
			{
				if(this.isChildNode(node))
				{
					Log.error("This Node "+node+" is already a child of "+this);
				}
				else
				{
					Log.error("Node "+node+" has "+this+" had child or grand child\nAnd you tried to create a recursive link");
				}
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
	 * Get Parent node
	 * @return Parent node or null this node is the root or is not attached
	 */
	public Node getParentNode()
	{
		return parentNode;
	}
	/**
	 * Set the parent node
	 * @param node Future parent node.
	 */
	public void setParentNode(Node node)
	{
		node.attachChildNode(this);
	}
	/**
	 * Indicates if this node has a parent node
	 * @return true of false
	 */
	public boolean hasParentNode()
	{
		return parentNode != null;
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
	 * Indicates if a node is the parent of this one
	 * @param node Node to test
	 * @return true of false
	 */
	public boolean isParentNode(Node node)
	{
		return node == parentNode;
	}
	/**
	 * Indicates if a node is the parent or grand parent of this one
	 * @param node Node to test
	 * @return true of false
	 */
	public boolean isParentNode(Node node, boolean recursive)
	{
		if(isParentNode(node))
		{
			return true;
		}
		if(recursive && parentNode != null)
		{
			return parentNode.isParentNode(node, true);
		}
		return false;
	}
	/**
	 * Called for drawing
	 */
	public void draw()
	{
		GL11 gl = Scene.gl;
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		for(final Node node : childNodes)
		{
			node.draw();
		}
		if(entity != null)
		{
			entity.draw();
		}
		gl.glPopMatrix();
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		return "[Node childrens=\""+childNodes.size()+"\" Entity=\""+entity+"\"]";
	}
}
