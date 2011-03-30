package dimyoux.houseExplorer;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import dimyoux.engine.EngineActivity;
import dimyoux.engine.R;
import dimyoux.engine.core.signals.ISensorLight;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Entity;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord2D;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.parsers.Face;
import dimyoux.engine.utils.parsers.Material;
import dimyoux.engine.utils.parsers.ObjParser;
import dimyoux.engine.utils.parsers.MeshBuilder;
/**
 * Example application : A house explorer 
 */
public class HouseActivity extends EngineActivity implements ISensorProximity, ISensorOrientation, ISensorLight {
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	private Node node;
	private int id;
	public void onDrawFrame(GL10 gl) 
	{
			node.x +=0.01;
			node.getParentNode().y +=0.01;
			node.getParentNode().z+=0.05;
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
		root.attachChildNode(node);*/
		node = parser.load("camaro_obj");
		root.attachChildNode(node);
		node.attachChildNode(parser.load("test_obj"));
		node = node.getChildNode(0);
		
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
