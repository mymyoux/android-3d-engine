package dimyoux.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.app.Activity;
import android.os.Bundle;
import dimyoux.engine.core.interfaces.IRenderer;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.scene.Scene;
import dimyoux.engine.utils.Log;
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
		onDrawFrame(gl);
	}
	@Override
	public void _onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		onSurfaceChanged(gl, width, height);
	}
	@Override
	public void _onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		onSurfaceCreated(gl, config);
	}
} 