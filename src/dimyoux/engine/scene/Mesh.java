package dimyoux.engine.scene;

import java.io.IOException;
import java.io.Serializable;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.opengl.GLConstants;
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
	
	/**
	 * Called for drawing
	 */
	public void draw()
	{
		GL11 gl = Scene.gl;
		
		gl.glPushMatrix();
		if(this.hasVerticesBuffer())
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if(this.hasColorsBuffer())
		{
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		}
		if(this.hasNormalsBuffer())
		{
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		}
		if(this.hasTexCoordsBuffer() && this.hasTexture())
		{
			gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);
		}
		
		
		if(this.hasMaterial())
		{
			
			if(this.getCurrentMaterial().hasDiffuseColor())
			{
			    
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, this.getCurrentMaterial().diffuseColor.toFloatBuffer());
			}
			if(this.getCurrentMaterial().hasAmbientColor())
			{
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, this.getCurrentMaterial().ambientColor.toFloatBuffer());
			}
			if(this.getCurrentMaterial().hasSpecularColor())
			{	 
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, this.getCurrentMaterial().specularColor.toFloatBuffer());
			}
			
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, Buffer.toFloatBuffer(this.getCurrentMaterial().shininess));
			if(this.hasMaterial())
			{
			//	gl.glEnable(GL10.GL_COLOR_MATERIAL);
			}
    	    	    	    
		}
		if(GLConstants.USE_VBO)
		{
			bufferize();
			gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.verticesBufferIndex);
			gl.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			
		     // enable non-mandatory arrays if found
			if(this.hasColorsBuffer()) {
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.colorsBufferIndex);
				gl.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
				
			}
			if(this.hasNormalsBuffer()) {
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.normalsBufferIndex);
				gl.glNormalPointer(GL11.GL_FLOAT, 0, 0);
			}
			if(this.hasTexCoordsBuffer() && this.hasTexture()) {
				if(!this.getCurrentMaterial().textureSent)
				{
					this.getCurrentMaterial().sendTexture();
				}
				
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.texCoordsBufferIndex);
				gl.glTexCoordPointer(this.texCoordsSize, GL11.GL_FLOAT, 0, 0);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, this.getCurrentMaterial().textureIndex);
				
			}
			gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, this.indexesBufferIndex);
			
			gl.glDrawElements(GL10.GL_TRIANGLES, this.indexesBuffer.capacity(), GL11.GL_UNSIGNED_SHORT, 0);
		     
			gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		}else
		{
			if(this.hasVerticesBuffer())
			{
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.verticesBuffer);
			}
			if(this.hasNormalsBuffer()) 
			{
				gl.glNormalPointer(GL11.GL_FLOAT, 0, this.normalsBuffer);
			}
			if(this.hasColorsBuffer())
			{
				gl.glColorPointer(4, GL10.GL_FLOAT, 0, this.colorsBuffer);
			}
			
			if(this.hasTexCoordsBuffer() && this.hasTexture())
			{
				gl.glTexCoordPointer(this.texCoordsSize, GL10.GL_FLOAT, 0, this.texCoordsBuffer);
				if(!this.getCurrentMaterial().textureSent)
				{
					this.getCurrentMaterial().sendTexture();
				}
					gl.glBindTexture(GL10.GL_TEXTURE_2D, this.getCurrentMaterial().textureIndex);
			}
		
			gl.glDrawElements(GL10.GL_TRIANGLES, this.indexesBuffer.capacity(), 
					GL10.GL_UNSIGNED_SHORT, this.indexesBuffer);
		}	
		if(this.hasMaterial())
		{
		//	gl.glDisable(GL10.GL_COLOR_MATERIAL);
		}
		if(this.hasVerticesBuffer())
		{
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if(this.hasColorsBuffer())
		{
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
		if(this.hasNormalsBuffer())
		{
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
		if(this.hasTexCoordsBuffer() && this.hasTexture())
		{
			gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
		
		gl.glPopMatrix();
	}
	
	/**
	 * Bufferizes this mesh if it is not already 
	 */
	private void bufferize()
	{
		GL11 gl = Scene.gl;
		
		if(!this.isBuffered())
		{
			//IntBuffer buffer = Buffer.createIntBuffer(1);
			int[] buffer = new int[1];
			//vertices
			if(this.hasVerticesBuffer())
			{
				//Log.verbose("Creating vertices buffer");
				gl.glGenBuffers(1, buffer, 0);
				this.verticesBufferIndex = buffer[0];
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.verticesBufferIndex);
				this.verticesBuffer.rewind();
				gl.glBufferData(GL11.GL_ARRAY_BUFFER,this.verticesBuffer.capacity()*Buffer.FLOAT_SIZE, this.verticesBuffer, GL11.GL_STATIC_DRAW);
			}
			//normals
			if(this.hasNormalsBuffer())
			{
			//	Log.verbose("Creating normals buffer");
				gl.glGenBuffers(1, buffer, 0);
				this.normalsBufferIndex = buffer[0];
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.normalsBufferIndex);
				this.normalsBuffer.rewind();
				gl.glBufferData(GL11.GL_ARRAY_BUFFER,this.normalsBuffer.capacity()*Buffer.FLOAT_SIZE, this.normalsBuffer, GL11.GL_STATIC_DRAW);
			}
			//colors
			if(this.hasColorsBuffer())
			{
				//Log.verbose("Creating colors buffer");
				gl.glGenBuffers(1, buffer, 0);
				this.colorsBufferIndex = buffer[0];
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.colorsBufferIndex);
				this.colorsBuffer.rewind();
				//TODO : maybe colors will be a BytesBuffer so will use this.colorsBuffer.capacity()*Buffer.BYTE_SIZE instead
				gl.glBufferData(GL11.GL_ARRAY_BUFFER,this.colorsBuffer.capacity()*Buffer.FLOAT_SIZE, this.colorsBuffer, GL11.GL_STATIC_DRAW);
			}
			//texture coordinates
			if(this.hasTexCoordsBuffer())
			{
			//	Log.verbose("Creating texture coordinates buffer");
				gl.glGenBuffers(1, buffer, 0);
				this.texCoordsBufferIndex = buffer[0];
				gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.texCoordsBufferIndex);
				this.texCoordsBuffer.rewind();
				gl.glBufferData(GL11.GL_ARRAY_BUFFER,this.texCoordsBuffer.capacity()*Buffer.FLOAT_SIZE, this.texCoordsBuffer, GL11.GL_STATIC_DRAW);
			}
			
			//indexes
			if(this.hasIndexesBuffer())
			{
			//	Log.verbose("Creating indexes buffer");
				gl.glGenBuffers(1, buffer, 0);
				this.indexesBufferIndex = buffer[0];
				gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, this.indexesBufferIndex);
				this.indexesBuffer.rewind();
				//TODO : maybe colors will be a CharVuffer so will use this.colorsBuffer.capacity()*Buffer.CHAR_SIZE instead
				gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER,this.indexesBuffer.capacity()*Buffer.SHORT_SIZE, this.indexesBuffer, GL11.GL_STATIC_DRAW);
				
				
			}
			//end of array binding
			gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
			//end of element array binding
			gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			
		//	Log.verbose("Entity is buffered");
		}
	}

}
