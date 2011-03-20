package dimyoux.engine.scene;
/**
 * Entity
 */
public class Entity {

	/**
	 * Mesh
	 */
	private Mesh mesh;
	/**
	 * Constructor
	 */
	public Entity()
	{
		
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
}
