package dimyoux.engine;

import android.app.Activity;
import android.os.Bundle;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.utils.Log;
import dimyoux.houseExplorer.HouseRenderer;
/**
 * The Class TabletActivity.
 */
public class EngineActivity extends Activity implements ISensorProximity, ISensorOrientation{
    
    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationManager.initialization(this);
        ApplicationManager.getInstance().getTitle().fullScreen();
        //listen sensors 
        SensorManager.getInstance().getSignalProximity().add(this);
        SensorManager.getInstance().getSignalOrientation().add(this);
        //create openGLES2.0 view
        try
        {
        	ApplicationManager.getInstance().createOpenGL2SurfaceView(new HouseRenderer());
        }catch(Exception error)
        {
        	Log.error(error);
        }
        
       // ApplicationManager.getInstance().showFatalErrorDialog("test");
    }

	@Override
	public void onProximityEvent(Boolean present) {
		Log.error("Présent : "+present);
	}

	@Override
	public void onOrientationChanged(float yaw, float pitch, float roll) {
		// TODO Auto-generated method stub
	//	Log.debug(yaw+":"+pitch+":"+roll);
	}
} 