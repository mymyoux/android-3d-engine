package dimyoux.engine.scene;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.utils.Buffer;
import dimyoux.engine.utils.Log;

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
		bufferize();
		Scene.gl.glPushMatrix();
         
		Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.verticesBufferIndex);
		Scene.gl.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
         
         // enable non-mandatory arrays if found
		if(mesh.hasColorsBuffer()) {
			Scene.gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.colorsBufferIndex);
			Scene.gl.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 0, 0);
		}
		if(mesh.hasNormalsBuffer()) {
			Scene.gl.glEnableClientState(GL11.GL_NORMAL_ARRAY);
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.normalsBufferIndex);
			Scene.gl.glNormalPointer(GL11.GL_FLOAT, 0, 0);
		}
		if(mesh.hasTexCoordsBuffer()) {
			Scene.gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
			Scene.gl.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		}
         
		Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mesh.indexesBufferIndex);
		Scene.gl.glDrawElements(GL10.GL_TRIANGLES, mesh.indexesBuffer.limit(),
                 GL11.GL_UNSIGNED_SHORT, 0);
         
		Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
         
         if(mesh.hasColorsBuffer()) {
        	 Scene.gl.glDisableClientState(GL11.GL_COLOR_ARRAY);
		}
		if(mesh.hasNormalsBuffer()) {
			Scene.gl.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		}
		if(mesh.hasTexCoordsBuffer()) {
			Scene.gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);				
		}
		Scene.gl.glPopMatrix();
	}
	/**
	 * Bufferizes this mesh if it is not already 
	 */
	private void bufferize()
	{
		if(!mesh.isBuffered())
		{
			Log.warning("bufferisation");
			IntBuffer buffer = Buffer.createIntBuffer(1);
			//vertices
			if(mesh.hasVerticesBuffer())
			{
				Log.verbose("Creating vertices buffer");
				Scene.gl.glGenBuffers(1, buffer);
				mesh.verticesBufferIndex = buffer.get(0);
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.verticesBufferIndex);
				mesh.verticesBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.verticesBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.verticesBuffer, GL11.GL_STATIC_DRAW);
			}
			//normals
			if(mesh.hasNormalsBuffer())
			{
				Log.verbose("Creating normals buffer");
				Scene.gl.glGenBuffers(1, (IntBuffer)buffer.rewind());
				mesh.normalsBufferIndex = buffer.get(0);
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.normalsBufferIndex);
				mesh.normalsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.normalsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.normalsBuffer, GL11.GL_STATIC_DRAW);
			}
			//colors
			if(mesh.hasColorsBuffer())
			{
				Log.verbose("Creating colors buffer");
				Scene.gl.glGenBuffers(1, (IntBuffer)buffer.rewind());
				mesh.colorsBufferIndex = buffer.get(0);
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.colorsBufferIndex);
				mesh.colorsBuffer.rewind();
				//TODO : maybe colors will be a BytesBuffer so will use mesh.colorsBuffer.capacity()*Buffer.BYTE_SIZE instead
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.colorsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.colorsBuffer, GL11.GL_STATIC_DRAW);
			}
			//texture coordinates
			if(mesh.hasTexCoordsBuffer())
			{
				Log.verbose("Creating texture coordinates buffer");
				Scene.gl.glGenBuffers(1, (IntBuffer)buffer.rewind());
				mesh.texCoordsBufferIndex = buffer.get(0);
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
				mesh.texCoordsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.texCoordsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.texCoordsBuffer, GL11.GL_STATIC_DRAW);
			}
			//end of array binding
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			//indexes
			if(mesh.hasIndexesBuffer())
			{
				Log.verbose("Creating indexes buffer");
				Scene.gl.glGenBuffers(1, (IntBuffer)buffer.rewind());
				mesh.indexesBufferIndex = buffer.get(0);
				Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mesh.indexesBufferIndex);
				mesh.indexesBuffer.rewind();
				//TODO : maybe colors will be a CharVuffer so will use mesh.colorsBuffer.capacity()*Buffer.CHAR_SIZE instead
				Scene.gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER,mesh.indexesBuffer.capacity()*Buffer.INT_SIZE, mesh.indexesBuffer, GL11.GL_STATIC_DRAW);
				//end of element array binding
				Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
			}
		//	Log.verbose("Entity is buffered");
		}
	}
}
