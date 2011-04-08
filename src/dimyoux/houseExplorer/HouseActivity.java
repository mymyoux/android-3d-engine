package dimyoux.houseExplorer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import dimyoux.engine.EngineActivity;
import dimyoux.engine.core.signals.ISensorLight;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.core.signals.ISensorTouchDoubleTap;
import dimyoux.engine.core.signals.ISensorTouchTap;
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Light;
import dimyoux.engine.scene.Node;
import dimyoux.engine.scene.Scene;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.parsers.ObjParser;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation, ISensorLight, ISensorTouchDoubleTap, ISensorTouchTap {
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	private Node node;
	public void onDrawFrame(GL10 gl) 
	{
		/*
		node.x +=0.001;
			node.getParentNode().y +=0.001;
			node.getParentNode().z+=0.005;*/
			float[] pos =Light.getLight(0).getPosition();
			pos[0]-=0.5;
			pos[1]++;
		//	Light.getLight(0).setPosition(new Coord3D(pos));
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
    	SensorManager.getInstance().getSignalTap().add(this);
    	SensorManager.getInstance().getSignalDoubleTap().add(this);
    	

    }
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		/*  = new Node();
		root.attachChildNode(node);
		Entity entity = new Entity();
	//	node.attachEntity(entity);
		Log.debug("Scene created");
		ObjParser parser = new ObjParser();
		node = parser.load("camaro_obj");
		//Log.error(mesh.faces.get(1).toLongString());
		//entity.setMesh(mesh.toMesh());
			*/
		
		ObjParser parser = new ObjParser();
		/*node = parser.load("house_obj");
		root.attachChildNode(node);
		node = parser.load("camaro_obj");
		root.attachChildNode(node);
		root.attachChildNode( parser.load("camaro_obj"));
		root.getChildNode(1).x += 2;
		//root.getChildNode(1).getEntity().getMesh().currentMaterial = null;
		node.attachChildNode(parser.load("test_obj"));
		node = node.getChildNode(0);
		ObjParser parser = new ObjParser();*/
		long debut = System.currentTimeMillis();
		long fin = debut;
		
		if(!root.load("house"))
		{
			Log.error("no loading");
			node =parser.load("house_obj");
			node.y -=5;
			root.attachChildNode(node);
			fin = System.currentTimeMillis();
			root.save("house");
			Log.verbose(FileManager.getInstance().deserialize("house"));
		}else
		{
			node = root.getChildNode(0);
			fin = System.currentTimeMillis();
			Log.warning("house scene loaded!!!!!!");
		}
		Log.verbose("Total load time : "+(fin - debut)/1000);
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
		node.z+=3;	
	}
}
