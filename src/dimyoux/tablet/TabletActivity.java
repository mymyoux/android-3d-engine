package dimyoux.tablet;

import android.app.Activity;
import android.os.Bundle;
import dimyoux.tablet.core.signals.ISensorOrientation;
import dimyoux.tablet.core.signals.ISensorProximity;
import dimyoux.tablet.managers.ApplicationManager;
import dimyoux.tablet.utils.Log;
import dimyoux.tablet.managers.SensorManager;
/**
 * The Class TabletActivity.
 */
public class TabletActivity extends Activity implements ISensorProximity, ISensorOrientation{
    
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
        ApplicationManager.getInstance().createOpenGL2SurfaceView();
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