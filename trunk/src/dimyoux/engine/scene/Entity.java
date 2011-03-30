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
	/**
	 * Called for drawing
	 */
	public void draw()
	{
		GL11 gl = Scene.gl;
		
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
		if(mesh.hasTexCoordsBuffer() && mesh.hasTexture())
		{
			Scene.gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			Scene.gl.glEnable(GL10.GL_TEXTURE_2D);
		}
		
		
		if(mesh.hasMaterial())
		{
			
			if(mesh.currentMaterial.hasDiffuseColor())
			{
			    
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mesh.currentMaterial.diffuseColor.toFloatBuffer());
			}
			if(mesh.currentMaterial.hasAmbientColor())
			{
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mesh.currentMaterial.ambientColor.toFloatBuffer());
			}
			if(mesh.currentMaterial.hasSpecularColor())
			{	 
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mesh.currentMaterial.specularColor.toFloatBuffer());
			}
			
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, Buffer.toFloatBuffer(mesh.currentMaterial.shininess));
			if(mesh.hasMaterial())
			{
			//	Scene.gl.glEnable(GL10.GL_COLOR_MATERIAL);
			}
    	    	    	    
		}
		if(GLConstants.USE_VBO)
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
			if(mesh.hasTexCoordsBuffer() && mesh.hasTexture()) {
				if(!mesh.currentMaterial.textureSent)
				{
					mesh.currentMaterial.sendTexture();
				}
				
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
				Scene.gl.glTexCoordPointer(mesh.texCoordsSize, GL11.GL_FLOAT, 0, 0);
				Scene.gl.glBindTexture(GL10.GL_TEXTURE_2D, mesh.currentMaterial.textureIndex);
				
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
			
			if(mesh.hasTexCoordsBuffer() && mesh.hasTexture())
			{
				Scene.gl.glTexCoordPointer(mesh.texCoordsSize, GL10.GL_FLOAT, 0, mesh.texCoordsBuffer);
				if(!mesh.currentMaterial.textureSent)
				{
					mesh.currentMaterial.sendTexture();
				}
					Scene.gl.glBindTexture(GL10.GL_TEXTURE_2D, mesh.currentMaterial.textureIndex);
			}
		
			Scene.gl.glDrawElements(GL10.GL_TRIANGLES, mesh.indexesBuffer.capacity(), 
					GL10.GL_UNSIGNED_SHORT, mesh.indexesBuffer);
		}	
		if(mesh.hasMaterial())
		{
		//	Scene.gl.glDisable(GL10.GL_COLOR_MATERIAL);
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
		if(mesh.hasTexCoordsBuffer() && mesh.hasTexture())
		{
			Scene.gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			Scene.gl.glDisable(GL10.GL_TEXTURE_2D);
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
			//IntBuffer buffer = Buffer.createIntBuffer(1);
			int[] buffer = new int[1];
			//vertices
			if(mesh.hasVerticesBuffer())
			{
				//Log.verbose("Creating vertices buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.verticesBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.verticesBufferIndex);
				mesh.verticesBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.verticesBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.verticesBuffer, GL11.GL_STATIC_DRAW);
			}
			//normals
			if(mesh.hasNormalsBuffer())
			{
			//	Log.verbose("Creating normals buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.normalsBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.normalsBufferIndex);
				mesh.normalsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.normalsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.normalsBuffer, GL11.GL_STATIC_DRAW);
			}
			//colors
			if(mesh.hasColorsBuffer())
			{
				//Log.verbose("Creating colors buffer");
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
			//	Log.verbose("Creating texture coordinates buffer");
				Scene.gl.glGenBuffers(1, buffer, 0);
				mesh.texCoordsBufferIndex = buffer[0];
				Scene.gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mesh.texCoordsBufferIndex);
				mesh.texCoordsBuffer.rewind();
				Scene.gl.glBufferData(GL11.GL_ARRAY_BUFFER,mesh.texCoordsBuffer.capacity()*Buffer.FLOAT_SIZE, mesh.texCoordsBuffer, GL11.GL_STATIC_DRAW);
			}
			
			//indexes
			if(mesh.hasIndexesBuffer())
			{
			//	Log.verbose("Creating indexes buffer");
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
