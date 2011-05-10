package dimyoux.houseExplorer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dimyoux.engine.EngineActivity;
import dimyoux.engine.core.signals.ISensorLight;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.core.signals.ISensorTouchDoubleTap;
import dimyoux.engine.core.signals.ISensorTouchTap;
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Camera;
import dimyoux.engine.scene.Light;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.filters.AveragingFilter;
import dimyoux.engine.utils.filters.FIRFilter;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.parsers.ObjParser;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation, ISensorLight, ISensorTouchDoubleTap, ISensorTouchTap {

	private Node node;
	private Node camPositionNode;
	private Node camTargetNode;
	
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	public void onDrawFrame(GL10 gl) 
	{
	}
	@Override
	public void onOrientationChanged(float yaw, float pitch, float roll, long timestamp) {
		//Log.d("yaw  : " + yaw);
		//Log.d("pitch: " + pitch);
		//Log.d("roll : " + roll);
		camTargetNode.rotateTo(-roll, Node.AXIS_X);
		camTargetNode.rotateTo(-yaw, Node.AXIS_Y);
		//camTargetNode.rotateTo(-pitch, Node.AXIS_Z);
	}

	@Override
	public void onProximityEvent(Boolean present) 
	{
		/*
		//if(present && !SensorManager.getInstance().getSignalOrientation().contains(this))
		//if(present && !FIRFilter.getInstance().getSignalOrientation().contains(this))
		if(present && !AveragingFilter.getInstance().getSignalOrientation().contains(this))
		{
			//SensorManager.getInstance().getSignalOrientation().add(this);
			//FIRFilter.getInstance().getSignalOrientation().add(this);
			AveragingFilter.getInstance().getSignalOrientation().add(this);
		}else
		{
			//if(!present && SensorManager.getInstance().getSignalOrientation().contains(this))
			//if(!present && FIRFilter.getInstance().getSignalOrientation().contains(this))
			if(!present && AveragingFilter.getInstance().getSignalOrientation().contains(this))
			{
				//SensorManager.getInstance().getSignalOrientation().remove(this); 
				//FIRFilter.getInstance().getSignalOrientation().remove(this);
				AveragingFilter.getInstance().getSignalOrientation().remove(this);
			}
		}
		*/
		Log.info("Present:"+present);
	}
	@Override
	public void preinitScene()
    {
    	Log.info("Démarrage de l'application houseExplorer");
       	// Sensors configuration
    	//SensorManager.getInstance().getSignalOrientation().add(this);
    	AveragingFilter.getInstance().getSignalOrientation().add(this);
    	SensorManager.getInstance().getSignalLight().add(this);
    	SensorManager.getInstance().getSignalProximity().add(this);
    	SensorManager.getInstance().getSignalTap().add(this);
    	SensorManager.getInstance().getSignalDoubleTap().add(this);
    }
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		ObjParser parser = new ObjParser();
		long debut = System.currentTimeMillis();
		long fin = debut;
		 if(!root.load("house"))
         {
                 Light.addLight(
                         Light.LightType.POINT_LIGHT, 
                         new Coord3D(0, -1, 0), // position 
                         new Color(0.2f, 0.2f, 0.2f),                    // ambient color
                         Color.WHITE,                    // diffuse color
                         Color.WHITE);           
                 
                 Log.error("no loading");
                 node = parser.load("house_obj");
                 node.y -=5;
                 root.attachChildNode(node);
                 //node.rotate(90, Node.AXIS_X);
                 fin = System.currentTimeMillis();
                 root.save("house");
                 Log.verbose(FileManager.getInstance().deserialize("house"));
         }else
         {
                 node = root.getChildNode(0);
                 //node.rotate(90, Node.AXIS_X);
                 //node.y -= 5;
                 fin = System.currentTimeMillis();
                 Log.warning("house scene loaded!!!!!!");
         }
         Log.verbose("Total load time : "+(fin - debut)/1000);
         
         Light.addLight(
                 Light.LightType.POINT_LIGHT, 
                 new Coord3D(0, 1, 0), // position 
                 new Color(0.3f, 0.3f, 0.3f),                    // ambient color
                 Color.WHITE,                    // diffuse color
                 Color.WHITE);           
         
         //node.rotate(180, Node.AXIS_X);
         
         camPositionNode = new Node();
         //node.attachChildNode(camPositionNode);
         //camPositionNode.setPosition(0, 5, -17);
         camPositionNode.setPosition(0, 0, 0);
         camTargetNode = new Node();
         camTargetNode.setPosition(0, -10, 0);
         camPositionNode.attachChildNode(camTargetNode);
         
         Camera camera = new Camera("Principale");
         camera.attachPositionNode(camPositionNode);
         camera.attachTargetNode(camTargetNode); 
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onLightChanged(float light) {
		Log.info("Light level : "+light);
	}
	
	@Override
	public void onTap(float x, float y, float pressure, float size) {
		// TODO Auto-generated method stub
		Log.verbose("Single tap");
	
	}
	@Override
	public void onDoubleTap(float x, float y, float pressure, float size) {
		// TODO Auto-generated method stub
		Log.verbose("Double tap"); 
		camTargetNode.rotate(90, Node.AXIS_Y);
		Log.w("targetPos: "  + camTargetNode.getPosition());
	}
}
