package dimyoux.engine.scene;

import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Matrix;
/**
 * Camera class
 * 
 */
public class Camera {
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
	 * Constructor
	 */
	public Camera()
	{
		projection = new Matrix(4,4);
		projection.setIdentity();
		modelView = new Matrix(4, 4);
		modelView.setIdentity();
		Log.error(modelView);
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
		
	}
	/**
	 * Guides the camera to a point [x, y, z]
	 * @param destination Point float[x, y, z]
	 */
	public void lookAt(float[] point)
	{
		
	}
	/**
	 * Guides the camera to a point [x, y, z]
	 * @param destination Point Matrix[x, y, z]
	 */
	public void lookAt(Matrix destination)
	{
		
	}
}
