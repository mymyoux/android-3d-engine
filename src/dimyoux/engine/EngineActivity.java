package dimyoux.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.os.Bundle;
import dimyoux.engine.core.interfaces.IRenderer;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.utils.Log;
/**
 * The Class TabletActivity.
 */
public class EngineActivity extends Activity implements IRenderer{
    
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
        preinitScene();
        initScene();
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
    public void preinitScene()
    {
    	ApplicationManager.getInstance().getTitle().fullScreen();
    }
    /**
     * Can be overridden
     */
    public void initScene()
    {
    	//TODO:override
    }
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
} 