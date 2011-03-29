package dimyoux.engine.scene;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLUtils;

import dimyoux.engine.opengl.GLConstants;
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
	private static int id = 0;
	private static boolean vbo = true;
	public void draw2()
	{
		GL11 gl = Scene.gl;
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mesh.verticesBuffer);
		// Set flat color
		gl.glColor4f(1,1,1,1);
		// Smooth color
		if (mesh.colorsBuffer != null) {
			// Enable the color array buffer to be used during rendering.
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, mesh.colorsBuffer);
		}

		// New part...
		if (!mesh.currentMaterial.textureSent) {
			mesh.currentMaterial.sendTexture();
		}
		if (mesh.currentMaterial.textureIndex != -1 && mesh.currentMaterial.textureBitmap != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// Enable the texture state
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// Point to our buffers
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mesh.texCoordsBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mesh.currentMaterial.textureIndex);
		}
		// ... end new part.



		// Point out the where the color buffer is.
		gl.glDrawElements(GL10.GL_TRIANGLES, mesh.indexesBuffer.capacity(),
				GL10.GL_UNSIGNED_SHORT, mesh.indexesBuffer);
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		// New part...
		if (mesh.currentMaterial.textureIndex != -1 && mesh.texCoordsBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		// ... end new part.

		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}
	/**
	 * Called for drawing
	 */
	public void draw()
	{
		
		Scene.gl.glPushMatrix();
		if(mesh.hasVerticesBuffer())
		{
			Scene.gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if(mesh.hasColorsBuffer())
		{
			Scene.gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		}
		if(mesh.hasNormalsBuffer())
		{
			Scene.gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		}
		if(mesh.hasTexCoordsBuffer())
		{
			Scene.gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			if(mesh.hasTexture())
			{
				Scene.gl.glEnable(GL10.GL_TEXTURE_2D);
			}
		}
		if(!GLConstants.USE_VBO)
		{
			bufferize();
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.verticesBufferIndex);
			Scene.gl.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			
		     // enable non-mandatory arrays if found
			if(mesh.hasColorsBuffer()) {
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.colorsBufferIndex);
				Scene.gl.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
				
			}
			if(mesh.hasNormalsBuffer()) {
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.normalsBufferIndex);
				Scene.gl.glNormalPointer(GL11.GL_FLOAT, 0, 0);
			}
			if(mesh.hasTexCoordsBuffer()) {
				if(mesh.hasTexture())
				{
					if(!mesh.currentMaterial.textureSent)
					{
						Log.warning("Sending texture");
						mesh.currentMaterial.sendTexture();
					}
				}
				
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
				Scene.gl.glTexCoordPointer(mesh.texCoordsSize, GL11.GL_FLOAT, 0, 0);
				if(mesh.hasTexture())
				{
					Scene.gl.glBindTexture(GL10.GL_TEXTURE_2D, mesh.currentMaterial.textureIndex);
				}
			}
		
			Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mesh.indexesBufferIndex);
			
			Scene.gl.glDrawElements(GL10.GL_TRIANGLES, mesh.indexesBuffer.capacity(), GL11.GL_UNSIGNED_SHORT, 0);
		     
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		}else
		{
			if(mesh.hasVerticesBuffer())
			{
				Scene.gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mesh.verticesBuffer);
			}
			if(mesh.hasNormalsBuffer()) 
			{
				Scene.gl.glNormalPointer(GL11.GL_FLOAT, 0, mesh.normalsBuffer);
			}
			if(mesh.hasColorsBuffer())
			{
				Scene.gl.glColorPointer(4, GL10.GL_FLOAT, 0, mesh.colorsBuffer);
			}
			
			if(mesh.hasTexCoordsBuffer())
			{
				Scene.gl.glTexCoordPointer(mesh.texCoordsSize, GL10.GL_FLOAT, 0, mesh.texCoordsBuffer);
				if(mesh.hasTexture())
				{
					if(!mesh.currentMaterial.textureSent)
					{
						Log.warning("Sending texture");
						mesh.currentMaterial.sendTexture();
					}
					Scene.gl.glBindTexture(GL10.GL_TEXTURE_2D, mesh.currentMaterial.textureIndex);
				}
			}
		
			Scene.gl.glDrawElements(GL10.GL_TRIANGLES, mesh.indexesBuffer.capacity(), 
					GL10.GL_UNSIGNED_SHORT, mesh.indexesBuffer);
		}	
		if(mesh.hasVerticesBuffer())
		{
			Scene.gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		if(mesh.hasColorsBuffer())
		{
			Scene.gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
		if(mesh.hasNormalsBuffer())
		{
			Scene.gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
		if(mesh.hasTexCoordsBuffer())
		{
			Scene.gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			if(mesh.hasTexture())
			{
				Scene.gl.glDisable(GL10.GL_TEXTURE_2D);
			}
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
			//IntBuffer buffer = Buffer.createIntBuffer(1);
			int[] buffer = new int[1];
			//vertices
			if(mesh.hasVerticesBuffer())
			{
				Log.verbose("Creating vertices buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.verticesBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.verticesBufferIndex);
				mesh.verticesBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.verticesBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.verticesBuffer, GL11.GL_STATIC_DRAW);
			}
			//normals
			if(mesh.hasNormalsBuffer())
			{
				Log.verbose("Creating normals buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.normalsBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.normalsBufferIndex);
				mesh.normalsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.normalsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.normalsBuffer, GL11.GL_STATIC_DRAW);
			}
			//colors
			if(mesh.hasColorsBuffer())
			{
				Log.verbose("Creating colors buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.colorsBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.colorsBufferIndex);
				mesh.colorsBuffer.rewind();
				//TODO : maybe colors will be a BytesBuffer so will use mesh.colorsBuffer.capacity()*Buffer.BYTE_SIZE instead
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.colorsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.colorsBuffer, GL11.GL_STATIC_DRAW);
			}
			//texture coordinates
			if(mesh.hasTexCoordsBuffer())
			{
				Log.verbose("Creating texture coordinates buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.texCoordsBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
				mesh.texCoordsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.texCoordsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.texCoordsBuffer, GL11.GL_STATIC_DRAW);
				/*
				if(mesh.hasTexture())
				{
					Scene.gl.glGenTextures(1, buffer, 0);
					mesh.currentMaterial.textureIndex = buffer[0];

					// ...and bind it to our array
					Scene.gl.glBindTexture(GL10.GL_TEXTURE_2D,mesh.currentMaterial.textureIndex);

					// Create Nearest Filtered Texture
					Scene.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
							GL10.GL_LINEAR);
					Scene.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
							GL10.GL_LINEAR);

					// Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
					Scene.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
							GL10.GL_CLAMP_TO_EDGE);
					Scene.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
							GL10.GL_REPEAT);

					// Use the Android GLUtils to specify a two-dimensional texture image
					// from our bitmap
					GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mesh.currentMaterial.textureBitmap, 0);
				}*/
			}
			
			//indexes
			if(mesh.hasIndexesBuffer())
			{
				Log.verbose("Creating indexes buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.indexesBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mesh.indexesBufferIndex);
				mesh.indexesBuffer.rewind();
				//TODO : maybe colors will be a CharVuffer so will use mesh.colorsBuffer.capacity()*Buffer.CHAR_SIZE instead
				Scene.gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER,mesh.indexesBuffer.capacity()*Buffer.SHORT_SIZE, mesh.indexesBuffer, GL11.GL_STATIC_DRAW);
				
				
			}
			//end of array binding
			Scene.gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
			//end of element array binding
			Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			
		//	Log.verbose("Entity is buffered");
		}
	}
}
