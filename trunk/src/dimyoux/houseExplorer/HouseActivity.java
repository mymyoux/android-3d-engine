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
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.utils.Color;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation {
	/*
	 * Constructor
	 */
	public HouseActivity()
	{
		super();
		SensorManager.getInstance().getSignalOrientation().add(this);
		SensorManager.getInstance().getSignalProximity().add(this);
	}

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

}
