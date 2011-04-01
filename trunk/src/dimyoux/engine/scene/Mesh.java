package dimyoux.engine.scene;

import java.io.IOException;
import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import dimyoux.engine.utils.Buffer;
import dimyoux.engine.utils.parsers.Material;

/**
 * Mesh
 */
public class Mesh implements Serializable {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Current Material
	 */
	private String currentMaterial;
	/**
	 * List of materials
	 */
	private List<String> materials;
	/**
	 * Name
	 */
	public String name;
	/**
	 * Index buffer of vertices
	 */
	transient protected int verticesBufferIndex = 0;
	/**
	 * Buffer of vertices
	 */
	transient public FloatBuffer verticesBuffer;
	/**
	 * Index buffer of normals
	 */
	transient protected int normalsBufferIndex = 0;
	/**
	 * Buffer of normals
	 */
	transient public FloatBuffer normalsBuffer;
	/**
	 * Index buffer of colors
	 */
	transient protected int colorsBufferIndex = 0;
	/**
	 * Buffer of colors
	 */
	transient public FloatBuffer colorsBuffer;
	/**
	 * Index buffer of textures coordinates
	 */
	transient protected int texCoordsBufferIndex = 0;
	/**
	 * Buffer  of textures coordinates
	 */
	transient public FloatBuffer texCoordsBuffer;
	/**
	 * Indicates it texCoords are based on 2 or 3 coordinates
	 */
	public int texCoordsSize = 2;
	/**
	 * Index buffer of indexes
	 */
	transient protected int indexesBufferIndex = 0;
	/**
	 * Buffer of indexes
	 */
	transient public ShortBuffer indexesBuffer;
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
		//return false;
		return normalsBuffer!=null;
	}
	/**
	 * Indicates if this mesh has a colors buffer
	 * @return True or false
	 */
	protected boolean hasColorsBuffer()
	{
		//return false;
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
	/**
	 * Indicates if this mesh has a material
	 * @return True or false
	 */
	protected boolean hasMaterial()
	{
		return currentMaterial != null;
	}
	/**
	 * Indicates if this mesh has a texture
	 * @return True or false
	 */
	protected boolean hasTexture()
	{
		return this.currentMaterial != null && this.getCurrentMaterial() != null && this.getCurrentMaterial().textureBitmap != null;
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String txt = "[Mesh";
		if(name != null)
		{
			txt+="["+name+"]";
		}
		if(verticesBuffer!=null && verticesBuffer.limit()>0)
		{
			txt+=" V=\""+verticesBuffer.limit()+"\"";
		}
		if(normalsBuffer!=null && normalsBuffer.limit()>0)
		{
			txt+=" N=\""+normalsBuffer.limit()+"\"";
		}
		if(texCoordsBuffer!=null && texCoordsBuffer.limit()>0)
		{
			txt+=" T=\""+texCoordsBuffer.limit()+"\"";
		}
		if(colorsBuffer!=null && colorsBuffer.limit()>0)
		{
			txt+=" C=\""+colorsBuffer.limit()+"\"";
		}
		if(indexesBuffer!=null && indexesBuffer.limit()>0)
		{
			txt+=" I=\""+indexesBuffer.limit()+"\"";
		}
		if(currentMaterial != null)
		{
			txt+=" mat=\""+currentMaterial+"\"";
		}
			txt+=" matCount=\""+materials.size()+"\"";
	
		return txt+"]";
	}
	/**
	 * Serializes the object
	 * @param out ObjectOutputStream
	 * @throws IOException Error
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.defaultWriteObject();
		Buffer.serialize(verticesBuffer, out);
		Buffer.serialize(normalsBuffer, out);
		Buffer.serialize(colorsBuffer, out);
		Buffer.serialize(texCoordsBuffer, out);
		Buffer.serialize(indexesBuffer, out);
	}
	/**
	 * Deserializes the object
	 * @param out ObjectOutputStream
	 * @throws IOException Error
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		verticesBuffer = Buffer.deserializeFloatBuffer(in);
		normalsBuffer = Buffer.deserializeFloatBuffer(in);
		colorsBuffer = Buffer.deserializeFloatBuffer(in);
		texCoordsBuffer = Buffer.deserializeFloatBuffer(in);
		indexesBuffer = Buffer.deserializeShortBuffer(in);
	}
	/**
	 * Constructor
	 */
	public Mesh()
	{
		materials = new ArrayList<String>();
	}
	/**
	 * Return the current material
	 * @return Current Material
	 */
	public Material getCurrentMaterial()
	{
		return Material.getMaterial(currentMaterial);
	}
	/**
	 * Adds a new material to the mesh
	 * @param material Material name
	 * @return True if the material name is an existing material
	 */
	public boolean addMaterial(String material)
	{
		if(!materials.contains(material) && Material.hasMaterial(material))
		{
			materials.add(material);
			return true;
		}
		return Material.hasMaterial(material);
	}
	/**
	 * Sets the current material 
	 * @param material Material name
	 * @return True if the material name is correct
	 */
	public boolean setCurrentMaterial(String material)
	{
		if(addMaterial(material))
		{
			currentMaterial = material;
			return true;
		}
		return false;
	}
	/**
	 * Sets the current material 
	 * @param material Material
	 * @return True if the material name is correct
	 */
	public boolean setCurrentMaterial(Material material)
	{
		if(material == null)
		{
			return false;
		}
		return setCurrentMaterial(material.name);
	}
}
