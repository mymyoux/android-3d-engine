package dimyoux.engine.scene;




import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;
import dimyoux.engine.utils.HashMapList;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.math.Matrix;
/**
 * Camera class
 * 
 */
public class Camera {
	
	/**
	 * Cameras map (key = name, value = Camera)
	 */
	static private HashMapList<String, Camera> cameras = new HashMapList<String, Camera> ();
	
	/**
	 * Position node
	 */
	private Node positionNode;
	/**
	 * Target node
	 */
	private Node targetNode;
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
	 * Target's position [x, y, z]
	 */
	private float[] target;
	/**
	 * Flag when the camera needs an update
	 */
	private boolean invalidated;
	/**
	 * Active camera
	 */
	static private Camera activeCamera; 
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
		upVector = new float[] {1.0f, 0.0f, 0.0f}; // camera pointing upward
		target 	 = new float[] {1.0f, 0.0f, 1.0f}; // camera initial target
		cameras.put(camName, this);
		if(activeCamera == null)
		{
			setActiveCamera(this);
		}
		
	}
	/**
	 * Returns the specified camera, or null if it doesn't exist
	 * @param id ID
	 * @return the specified camera
	 */
	static public Camera get(int id)
	{
		return cameras.get(id);
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
		Camera camera = cameras.remove(name);
		if(camera != null && camera.equals(activeCamera))
		{
			activeCamera = null;
		}
	}
	/**
	 * Removes the specified camera, or does nothing if the ID isn't correct
	 * @param id ID
	 */
	static public void remove(int id)
	{
		Camera camera = cameras.remove(id);
		if(camera != null && camera.equals(activeCamera))
		{
			activeCamera = null;
		}
	}
	/**
	 * Returns the number of cameras
	 * @return Number of cameras 
	 */
	static public int GetNumCamera()
	{
		return cameras.size();
	}
	/**
	 * Returns the active camera
	 * @return Active Camera or null
	 */
	static public Camera getActiveCamera()
	{
		return activeCamera;
	}
	/**
	 * Sets this camera to active
	 * @param id ID Camera
	 */
	static public void setActiveCamera(int id)
	{
		setActiveCamera(get(id));
	}
	/**
	 * Sets this camera to active
	 * @param name Name's Camera
	 */
	static public void setActiveCamera(String name)
	{
		setActiveCamera(get(name));
	}
	/**
	 * Sets this camera to active
	 * @param camera Camera
	 */
	static public void setActiveCamera(Camera camera)
	{
		activeCamera = camera;
	}	
	/**
	 * Updates Camera view
	 */
	static public void update()
	{
		if(activeCamera != null)
		{
			activeCamera._update();
		}
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
	 * @param point the point to look at
	 */
	public void lookAt(Coord3D point)
	{
		if (point == null)
		{
			return;
		}
		
		GL11 gl = Scene.gl;
		
		if (position != null && upVector != null)
		{
			GLU.gluLookAt(
					gl, 									// OpenGL context
					position[0], position[1], position[2], 	// cam position
					point.x, point.y, point.z, 				// cam reference point
					upVector[0], upVector[1], upVector[2]); // cam up vector
		}
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
		if (point == null || point.length != 3)
		{
			return;
		}
		
		GL11 gl = Scene.gl;
		
		if (position != null && upVector != null)
		{
			GLU.gluLookAt(
					gl, 									// OpenGL context
					position[0], position[1], position[2], 	// cam position
					point[0], point[1], point[2], 			// cam reference point
					upVector[0], upVector[1], upVector[2]); // cam up vector
		}
	}
	/**
	 * Guides the camera to a point [x, y, z]
	 * @param destination Point Matrix[x, y, z]
	 */
	public void lookAt(Matrix destination)
	{
		if (destination == null)
		{
			return;
		}
		
		if (destination.isRowMatrix())
		{
			GL11 gl = Scene.gl;
			
			if (position != null && upVector != null)
			{
				
				GLU.gluLookAt(
					gl, 																 // OpenGL context
					position[0], position[1], position[2], 								 // cam position
					destination.get(0, 0), destination.get(0, 1), destination.get(0, 2), // cam reference point
					upVector[0], upVector[1], upVector[2]); 							 // cam up vector
			}	
		}
		else if (destination.isColumnMatrix())
		{
			GL11 gl = Scene.gl;
			
			if (position != null && upVector != null)
			{
				GLU.gluLookAt(
					gl, 																 // OpenGL context
					position[0], position[1], position[2], 								 // cam position
					destination.get(0, 0), destination.get(1, 0), destination.get(2, 0), // cam reference point
					upVector[0], upVector[1], upVector[2]); 							 // cam up vector
			}
		}
	}
	public void attachTargetNode(Node targetNode)
	{
		this.targetNode = targetNode;
		invalidated = true;
	}
	public void attachPositionNode(Node positionNode)
	{
		this.positionNode = positionNode;
		invalidated = true;
	}
	/**
	 * Updates camera position and orientation
	 */
	private void _update()
	{
		if(invalidated)
		{
			if(positionNode != null)
			{
				position[0] = positionNode.getWorldX();
				position[1] = positionNode.getWorldY();
				position[2] = positionNode.getWorldZ();
			}
			if(targetNode != null)
			{
				target[0] = targetNode.getWorldX();
				target[1] = targetNode.getWorldY();
				target[2] = targetNode.getWorldZ();
			}
			if(position != null && upVector != null && target!= null)
			{
				GL11 gl = Scene.gl;

				GLU.gluLookAt(
						gl, 									// OpenGL context
						position[0], position[1], position[2], 	// cam position
						target[0], target[1], target[2], 		// cam reference point
						upVector[0], upVector[1], upVector[2]); // cam up vector	
				//invalidated = false;
			}
		}
	}
}
