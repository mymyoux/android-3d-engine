package dimyoux.tablet.scene;

import java.util.ArrayList;
import java.util.List;

import dimyoux.tablet.utils.Log;
/**
 * Node
 */
public class Node {
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
	 * Attach a entity to this node
	 * One entity by node
	 * @param entity Entity to add
	 */
	public void attachEntity(Entity entity)
	{
		this.entity = entity;
	}
	/**
	 * Return the entity attached to the node if it exists
	 * @return eEntity
	 */
	public Entity getEntity()
	{
		return entity;
	}
	/**
	 * Test if this node has an entity
	 * @return True of false
	 */
	public boolean hasEntity()
	{
		return entity!=null;
	}
	/**
	 * Attach a node to this current node. An error is thrown (throw LogCat) if there is a recursive link 
	 * @param node Node to attach
	 */
	public void attachChildNode(Node node)
	{
		if(!childNodes.contains(node))
		{
			if(!this.hasParentNode(node, true))
			{
				childNodes.add(node);
				node.setParentNode(this);
			}else
			{
				Log.error("Node "+node+" has "+this+" had child or grand child\nAnd you tried to create a recursive link");
			}	
		}
	}
	public Node getChildNode(int indice)
	{
		if(childNodes.size()>indice)
		{
			return childNodes.get(indice);
		}
		return null;
	}
	public int getNumNodes()
	{
		return childNodes.size();
	}
	public List<Node> getChildNodes()
	{
		return childNodes;
	}
	public Node getParentNode()
	{
		return parentNode;
	}
	public void setParentNode(Node node)
	{
		this.parentNode = node;
	}
	public boolean isAttached()
	{
		return parentNode != null;
	}
	public boolean hasParentNode()
	{
		return isAttached();
	}
	public boolean hasChildNode(Node node)
	{
		return childNodes.contains(node);
	}
	public boolean hasChildNode(Node node, boolean recursive)
	{
		if(hasChildNode(node))
		{
			return true;
		}
		if(recursive)
		{
			for(final Node childNode : childNodes)
			{
				if(childNode.hasChildNode(node, true))
				{
					return true;
				}
			}
		}
		return false;
	}
	public boolean hasParentNode(Node node)
	{
		return node == parentNode;
	}
	public boolean hasParentNode(Node node, boolean recursive)
	{
		if(hasParentNode())
		{
			return true;
		}
		if(recursive && parentNode != null)
		{
			return parentNode.hasParentNode(node, true);
		}
		return false;
	}
}
