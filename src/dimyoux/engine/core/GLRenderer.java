package dimyoux.engine.core;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dimyoux.engine.scene.Camera;
import dimyoux.engine.utils.Color;

/**
 * OpenGLES2 Renderer.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

	private Color clearColor;
	protected Camera camera;
	public GLRenderer()
	{
		clearColor = new Color(Color.BLACK);
		camera = new Camera();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		 gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
         gl.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
	}

	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Defines the GlClearColor
	 * @param color Color
	 */
	public void setClearColor(Color color)
	{
		clearColor = color;
	}
}
