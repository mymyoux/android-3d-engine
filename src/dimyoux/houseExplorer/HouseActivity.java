package dimyoux.houseExplorer;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;


import android.opengl.GLES20;

import dimyoux.engine.EngineActivity;
import dimyoux.engine.R;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.managers.ApplicationManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation {
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	public void onDrawFrame(GL10 gl) {
	}
	@Override
	public void onOrientationChanged(float yaw, float pitch, float roll) {

	}

	@Override
	public void onProximityEvent(Boolean present) {
		// TODO Auto-generated method stub
		
	}
	@Override
	 public void initScene()
    {
    	super.initScene();
    	Log.info("Démarrage de l'application houseExplorer");
    }
}
