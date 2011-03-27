package dimyoux.houseExplorer;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources.NotFoundException;
import dimyoux.engine.EngineActivity;
import dimyoux.engine.R;
import dimyoux.engine.core.signals.ISensorLight;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.core.signals.ISensorProximity;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Entity;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.parsers.Face;
import dimyoux.engine.utils.parsers.ObjParser;
import dimyoux.engine.utils.parsers.PreMesh;
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
		ObjParser parser = new ObjParser();
		parser.load(R.raw.camaro_obj);
		PreMesh mesh = parser.load("camaro.obj");
		//entity.setMesh(mesh.toMesh());
		PreMesh test = new PreMesh();
		test.name = "test";
		test.vertices.add(new Coord3D(1,1,1));
		test.vertices.add(new Coord3D(-1,1,1));
		test.vertices.add(new Coord3D(-1,-1,1));/*
		test.vertices.add(new Coord3D(1,-1,1));
		test.vertices.add(new Coord3D(1,-1,-1));
		test.vertices.add(new Coord3D(1,1,-1));
		test.vertices.add(new Coord3D(-1,1,-1));
		test.vertices.add(new Coord3D(-1,-1,-1));
		*/
		int[] t = new int[3];
		t[0] = 1;
		t[1] = 2;
		t[2] = 3;
		test.faces.add(new Face(t, null,null, "test"));
		entity.setMesh(test.toMesh());
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
