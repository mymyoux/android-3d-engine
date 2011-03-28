package dimyoux.engine;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
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
		root.draw();
		onDrawFrame(gl);
	}
	@Override
	public void _onSurfaceChanged(GL10 gl, int width, int height) {
		//update viewport
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		onSurfaceChanged(gl, width, height);
	}
	@Override
	public void _onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLConstants.getConstants();
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_DEPTH_TEST);									
		gl.glClearDepthf(1.0f);
		gl.glDepthFunc(GL10.GL_LESS);										
		gl.glDepthRangef(0,1f);											
		gl.glDepthMask(true);												

		// Alpha enabled
		gl.glEnable(GL10.GL_BLEND);										
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 	
		
		// "Transparency is best implemented using glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA) 
		// with primitives sorted from farthest to nearest."

		// Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST); // (OpenGL default is GL_NEAREST_MIPMAP)
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); // (is OpenGL default)
		
		// CCW frontfaces only, by default
		gl.glFrontFace(GL10.GL_CCW);
	    gl.glCullFace(GL10.GL_BACK);
	    gl.glEnable(GL10.GL_CULL_FACE);

	    // Disable lights by default
	    /*
	    for (int i = GL10.GL_LIGHT0; i < GL10.GL_LIGHT0 + root.getNumLights(); i++) {
	    	gl.glDisable(i);
	    }
	    */
	    
	    // Add a simple light to the scene
	    Light.addLight(
	    		Light.LightType.POINT_LIGHT, 
	    		new Coord3D(0, 0, -150), // position 
	    		new Color(1, 1, 1, 1),   // ambient color
	    		new Color(1, 1, 1, 1),   // diffuse color
	    		new Color(1, 1, 1, 1));  // specular color
	    
	    // Enable of the lights added to the scene by default
	    Light.enable(gl);
	    
	    Scene.gl = (GL11)gl;
	    
		onSurfaceCreated(gl, config);
	}
} 