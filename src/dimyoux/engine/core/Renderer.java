package dimyoux.engine.core;


import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.core.interfaces.IRenderer;
import dimyoux.engine.opengl.GLConstants;
import dimyoux.engine.scene.Scene;
/**
 * OpenGLES2 Renderer.
 */
public class Renderer implements GLSurfaceView.Renderer{

	/**
	 * Listener (IRenderer). In a normal way, it is the main activity
	 */
	private IRenderer listener;
	/**
	 * Constructor
	 * @param listener
	 */
    public Renderer(IRenderer listener)
    {
    	this.listener = listener;
    }
    /**
     * Set a new listener
     * @param listener Listener
     */
    public void setListener(IRenderer listener)
    {
    	this.listener = listener;
    }
    /**
     * Get the listener
     */
    public IRenderer getListener()
    {
    	return listener;
    }
    @Override
    public void onDrawFrame(GL10 gl) {
    	listener._onDrawFrame(gl);
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	Scene.gl = (GL11)gl;
    	GLConstants.getConstants();
	    
    	listener._onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	listener._onSurfaceCreated(gl, config);
    }
}