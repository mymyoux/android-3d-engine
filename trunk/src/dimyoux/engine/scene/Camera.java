package dimyoux.engine.scene;

import java.util.Hashtable;
import java.util.Map;

import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;
import dimyoux.engine.opengl.GLConstants;
import dimyoux.engine.utils.math.Matrix;
/**
 * Camera class
 * 
 */
public class Camera {
	
	/**
	 * Cameras map (key = name, value = Camera)
	 */
	static private Map<String, Camera> cameras = new Hashtable<String, Camera> ();
	
	/**
	 * Name of the camera
	 */
	private String camName;
	
	/**
	 * Projection matrix
	 */
	private Matrix projection;
	/**
	 * Model view matrix
	 */
	private Matrix modelView;
	/**
	 * Camera's position [x ,y, z]
	 */
	private float[] position;
	/**
	 * Camera's up vector
	 */
	private float[] upVector;
	
	/**
	 * Constructor
	 * @param name name of the camera
	 */
	public Camera(String name)
	{
		camName = name;
		
		projection = new Matrix(4, 4);
		projection.setIdentity();
		modelView = new Matrix(4, 4);
		modelView.setIdentity();
		
		position = new float[] {0.0f, 0.0f, 0.0f}; // camera initial position
		upVector = new float[] {0.0f, 1.0f, 0.0f}; // camera pointing upward
		
		cameras.put(camName, this);
	}
	
	/**
	 * Returns the specified camera, or null if it doesn't exist
	 * @param name the name of the camera
	 * @return the specified camera
	 */
	static public Camera get(String name)
	{
		return cameras.get(name);
	}
	
	/**
	 * Removes the specified camera, or does nothing if the name doesn't exist
	 * @param name name of the camera to be removed
	 */
	static public void remove(String name)
	{
		cameras.remove(name);
	}
	
	/**
	 * Translates the camera by this vector 
	 * @param x value to add to the position[x] value of the camera
	 * @param y value to add to the position[y] value of the camera
	 * @param z value to add to the position[z] value of the camera
	 */
	public void translate(float x,float y, float z)
	{
		position[0]+=x;
		position[1]+=y;
		position[2]+=z;
	}
	/**
	 * Translates the camera by this vector 
	 * @param translate value to add to the position[] value of the camera
	 * [Not Safe]
	 */
	public void translate(float[] translate)
	{
		position[0]+=translate[0];
		position[1]+=translate[1];
		position[2]+=translate[2];
	}
	/**
	 * Translates the camera by this vector 
	 * @param translate value to add to the position[] value of the camera
	 * [Not Safe]
	 */
	public void translate(Matrix translate)
	{
		if(translate.isColumnMatrix())
		{
			position[0]+=translate.get(0,0);
			position[1]+=translate.get(1,0);
			position[2]+=translate.get(2,0);
		}else
		{
			position[0]+=translate.get(0,0);
			position[1]+=translate.get(0,1);
			position[2]+=translate.get(0,2);
		}
	}
	/**
	 * Set the new position of the camera
	 * @param x New position[x]
	 * @param y New position[y]
	 * @param z New position[z]
	 */
	public void setPosition(float x, float y, float z)
	{
		position[0]=x;
		position[1]=y;
		position[2]=z;
	}
	/**
	 * Set the new position of the camera
	 * @param destination position will be destination
	 * [Not Safe]
	 */
	public void setPosition(float[] destination)
	{
		position[0]=destination[0];
		position[1]=destination[1];
		position[2]=destination[2];
	}
	/**
	 * Set the new position of the camera
	 * @param destination position will be destination
	 * [Not Safe]
	 */
	public void setPosition(Matrix translate)
	{
		if(translate.isColumnMatrix())
		{
			position[0]=translate.get(0,0);
			position[1]=translate.get(1,0);
			position[2]=translate.get(2,0);
		}else
		{
			position[0]=translate.get(0,0);
			position[1]=translate.get(0,1);
			position[2]=translate.get(0,2);
		}
	}
	/**
	 * Returns position
	 * @return The position (float[x, y, z])
	 */
	public float[] getPosition()
	{
		return position;
	}
	/**
	 * Returns the matrix position
	 * @return The position (Matrix[x, y, z])
	 */
	public Matrix getMatrixPosition()
	{
		return new Matrix(position);
	}
	/**
	 * Guides the camera to a point
	 * @param x X point
	 * @param y Y point
	 * @param z Z point
	 */
	public void lookAt(float x, float y, float z)
	{
		GL11 gl = Scene.gl;
		
		if (position != null && upVector != null)
		{
			GLU.gluLookAt(
					gl, 									// OpenGL context
					position[0], position[1], position[2], 	// cam position
					x, y, z, 								// cam reference point
					upVector[0], upVector[1], upVector[2]); // cam up vector
		}
	}
	/**
	 * Guides the camera to a point [x, y, z]
	 * @param destination Point float[x, y, z]
	 */
	public void lookAt(float[] point)
	{
		// TODO
	}
	/**
	 * Guides the camera to a point [x, y, z]
	 * @param destination Point Matrix[x, y, z]
	 */
	public void lookAt(Matrix destination)
	{
		// TODO
	}
}
