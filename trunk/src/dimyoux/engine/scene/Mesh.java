package dimyoux.engine.scene;

import java.nio.FloatBuffer;

/**
 * Mesh
 */
public class Mesh {
	public String name;
	/**
	 * Index buffer of vertices
	 */
	protected int verticesBufferIndex = 0;
	/**
	 * Buffer of vertices
	 */
	public FloatBuffer verticesBuffer;
	/**
	 * Index buffer of normals
	 */
	protected int normalsBufferIndex = 0;
	/**
	 * Buffer of normals
	 */
	public FloatBuffer normalsBuffer;
	/**
	 * Index buffer of colors
	 */
	protected int colorsBufferIndex = 0;
	/**
	 * Buffer of colors
	 */
	public FloatBuffer colorsBuffer;
	/**
	 * Index buffer of textures coordinates
	 */
	protected int texCoordsBufferIndex = 0;
	/**
	 * Buffer  of textures coordinates
	 */
	public FloatBuffer texCoordsBuffer;
	/**
	 * Index buffer of indexes
	 */
	protected int indexesBufferIndex = 0;
	/**
	 * Buffer of indexes
	 */
	public FloatBuffer indexesBuffer;
	/**
	 * Indicates if this mesh is already Buffered (see OpenGL VBO)
	 * @return True or false
	 */
	protected boolean isBuffered()
	{
		return verticesBufferIndex != 0;
	}
	/**
	 * Indicates if this mesh has a vertices buffer
	 * @return True or false
	 */
	protected boolean hasVerticesBuffer()
	{
		return verticesBuffer!=null;
	}
	/**
	 * Indicates if this mesh has a normals buffer
	 * @return True or false
	 */
	protected boolean hasNormalsBuffer()
	{
		return normalsBuffer!=null;
	}
	/**
	 * Indicates if this mesh has a colors buffer
	 * @return True or false
	 */
	protected boolean hasColorsBuffer()
	{
		return colorsBuffer!=null;
	}
	/**
	 * Indicates if this mesh has a texture coordinates buffer
	 * @return True or false
	 */
	protected boolean hasTexCoordsBuffer()
	{
		return texCoordsBuffer!=null;
	}
	/**
	 * Indicates if this mesh has a indexes buffer
	 * @return True or false
	 */
	protected boolean hasIndexesBuffer()
	{
		return indexesBuffer!=null;
	}
}
