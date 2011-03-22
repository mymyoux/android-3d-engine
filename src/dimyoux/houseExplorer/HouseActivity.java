package dimyoux.houseExplorer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import dimyoux.engine.EngineActivity;
import dimyoux.engine.core.signals.ISensorLight;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Entity;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Log;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation, ISensorLight {
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	public void onDrawFrame(GL10 gl) 
	{
		//Log.debug("frame");
	}
	@Override
	public void onOrientationChanged(float yaw, float pitch, float roll) {
		//Log.info(yaw + ";" + pitch + ";" + roll);
	}

	@Override
	public void onProximityEvent(Boolean present) 
	{
		Log.info("Present:"+present);
	}
	@Override
	public void preinitScene()
    {
    	Log.info("Démarrage de l'application houseExplorer");
    	
    	// Sensors configuration
    	SensorManager.getInstance().getSignalOrientation().add(this);
    	SensorManager.getInstance().getSignalLight().add(this);
    	SensorManager.getInstance().getSignalProximity().add(this);
    	

    }
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Node node  = new Node();
		root.attachChildNode(node);
		Entity entity = new Entity();
		node.attachEntity(entity);
		Log.debug("Scene created");
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onLightChanged(float light) {
		Log.info("Light level : "+light);
	}
}
