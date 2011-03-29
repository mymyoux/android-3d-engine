package dimyoux.engine;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLU;
import android.os.Bundle;
import dimyoux.engine.core.interfaces.IRenderer;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.opengl.GLConstants;
import dimyoux.engine.scene.Light;
import dimyoux.engine.scene.Scene;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
/**
 * The Class TabletActivity.
 */
public class EngineActivity extends Activity implements IRenderer{
	/**
	 * Scene instance
	 */
    protected Scene root;

	
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationManager.initialization(this);
      	Log.tagName = this.getClass().getSimpleName();
        _preinitScene();
        //create openGLES2.0 view
        try
        {
        	if(ApplicationManager.getInstance().createOpenGL2SurfaceView())
        	{
        		
        	}
        }catch(Exception error)
        {
        	Log.error(error);
        }
        
       // ApplicationManager.getInstance().showFatalErrorDialog("test");
    }
    /**
     * Can be overridden
     * Switches application to fullScreen mode
     * @see dimyoux.engine.manager.ApplicationManager#getTitle()
     */
    public void _preinitScene()
    {
    	ApplicationManager.getInstance().getTitle().fullScreen();
    	root = Scene.getRoot();
    	preinitScene();
    }
    /**
     * Can be overridden
     */
    public void preinitScene()
    {
    	//TODO:override
    }
    /**
     * Can be overridden
     * @param gl GL instance
     */
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}
	 /**
     * Can be overridden
     * @param gl GL instance
     * @param with Width
     * @param height Height
     */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	 /**
     * Can be overridden
     * @param gl GL instance
     * @param config EGLConfig
     */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void _onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		Scene.gl = (GL11)gl;
		root.draw();
		onDrawFrame(gl);
	}
	@Override
	public void _onSurfaceChanged(GL10 gl, int width, int height) {
		/*
		//update viewport
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();*/
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
		onSurfaceChanged(gl, width, height);
	}
	@Override
	public void _onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.warning("Surface created");
		Light.removeAll();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		//lighting
	    gl.glEnable(GL10.GL_LIGHTING);
	    Light.addLight(
	    		Light.LightType.POINT_LIGHT, 
	    		new Coord3D(0, 0, 150), // position 
	    		Color.WHITE,   			// ambient color
	    		Color.WHITE,   			// diffuse color
	    		Color.WHITE);  			// specular color
	    Light.enable();
	    //call to overridden function
	    onSurfaceCreated(gl, config);
	}
} 
