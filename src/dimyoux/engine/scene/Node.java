package dimyoux.engine.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.math.Matrix;
/**
 * Node
 */
public class Node implements Serializable {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	public static final byte AXIS_X = 0x01;
	public static final byte AXIS_Y = 0x02;
	public static final byte AXIS_Z = 0x03;
	
	public float x;
	public float y;
	public float z;
	
	public float angleX;
	public float angleY;
	public float angleZ;
	
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
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
		
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
	
	/**
	 * Moves the node to the specified location
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public void setPosition(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Moves the node to the specified location
	 * @param point the wanted location
	 */
	public void setPosition(Coord3D point)
	{
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}
	
	/**
	 * Translates the node of the specified values
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public void translate(float x, float y, float z)
	{
		this.x += x;
		this.y += y;
		this.z += z;
	}

	/**
	 * Translates the node of the specified values
	 * @param point the point
	 */
	public void translate(Coord3D point)
	{
		this.x += point.x;
		this.y += point.y;
		this.z += point.z;
	}
	
	/**
	 * Rotates the node of the specified angles on the 3 axes
	 * @param angleX the X angle
	 * @param angleY the Y angle
	 * @param angleZ the Z angle
	 */
	public void rotate(float angleX, float angleY, float angleZ)
	{
		this.angleX += angleX;
		this.angleY += angleY;
		this.angleZ += angleZ;
	}
	
	/**
	 * Rotates the node of the specified angle and on the specified axis
	 * @param angle the angle
	 * @param axis the axis {AXIS_X; AXIS_Y; AXIS_Z}
	 */
	public void rotate(float angle, byte axis)
	{
		if (axis != AXIS_X && axis != AXIS_Y && axis != AXIS_Z)
		{
			return;
		}
		
		switch(axis)
		{
		case AXIS_X: rotate(angle, 0.0f, 0.0f); break;
		case AXIS_Y: rotate(0.0f, angle, 0.0f); break;
		case AXIS_Z: rotate(0.0f, 0.0f, angle); break;
		}
	}
	public Matrix getPosition()
	{
		float[] position = new float[3];
		position[0] = x;
		position[1] = y;
		position[2] = z;
		Matrix pos = new Matrix(position);
		if(hasParentNode())
		{
			pos.multiply(parentNode.getRotation());		
			pos.sum(parentNode.getPosition());
		}
		return pos;
	}
	public float getWorldX()
	{
		return getPosition().get(0,0);
	}
	public float getWorldY()
	{
		return getPosition().get(0,1);
	}
	public float getWorldZ()
	{
		return getPosition().get(0,2);
	}
	public Matrix getWorldPosition()
	{
		float[] position= new float[3];
		position[0] = x;
		position[1] = y;
		position[2] = z;
		return new Matrix(position);
	}
	public Matrix getRotation()
	{
		Matrix rot = new Matrix(3, 3);
		rot.set(0,0,1);
		rot.set(1,1,(float)Math.cos(angleX));
		rot.set(1,2,(float)-Math.sin(angleX));
		rot.set(2,1,(float)Math.sin(angleX));
		rot.set(2,2,(float)Math.cos(angleX));
		Matrix rot2 = new Matrix(3, 3);
		rot2.set(1,1,1);
		rot2.set(0,0,(float)Math.cos(angleY));
		rot2.set(0,2,(float)-Math.sin(angleY));
		rot2.set(2,0,(float)Math.sin(angleY));
		rot2.set(2,2,(float)Math.cos(angleY));
		if(rot.multiply(rot2))
		{
			rot2 = new Matrix(3, 3);
			rot2.set(2,2,1);
			rot2.set(0,0,(float)Math.cos(angleZ));
			rot2.set(0,1,(float)-Math.sin(angleZ));
			rot2.set(1,0,(float)Math.sin(angleZ));
			rot2.set(1,1,(float)Math.cos(angleZ));
			if(rot.multiply(rot2))
			{
				return rot;
			}
		}
		
		return null;
	}
}
