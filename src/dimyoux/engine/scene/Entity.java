package dimyoux.engine.scene;

import java.io.Serializable;


import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


import dimyoux.engine.opengl.GLConstants;
import dimyoux.engine.utils.Buffer;


/**
 * Entity
 */
public class Entity implements Serializable{

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Mesh
	 */
	private Mesh mesh;
	/**
	 * Constructor
	 */
	public Entity()
	{
		mesh = new Mesh();
	}
	/***
	 * Get the mesh
	 * @return Mesh 
	 */
	public Mesh getMesh()
	{
		return mesh;
	}
	/**
	 * Set the mesh
	 * @param mesh Mesh
	 */
	public void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
	}
	/**
	 * Indicate if this entity has a mesh
	 * @return True or false
	 */
	public boolean hasMesh()
	{
		return mesh!=null;
	}
	/**
	 * Called for drawing
	 */
	public void draw()
	{
		mesh.draw();
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		return "[Entity Mesh=\""+mesh+"\"]";
	}
}
