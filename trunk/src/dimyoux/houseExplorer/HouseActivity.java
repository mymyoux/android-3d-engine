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
import dimyoux.engine.managers.FileManager;
import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.scene.Entity;
import dimyoux.engine.scene.Node;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.parsers.Face;
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
		MeshBuilder mesh = parser.load("camaro.obj");
		//entity.setMesh(mesh.toMesh());
		MeshBuilder test = new MeshBuilder();
		test.name = "test";
		test.vertices.add(new Coord3D(-1,1,0));
		test.vertices.add(new Coord3D(-1,-1,0));
		test.vertices.add(new Coord3D(1,-1,0));
		test.vertices.add(new Coord3D(1,1,0));
		/*
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
		t[1] = 3;
		t[2] = 4;
		test.faces.add(new Face(t, null,null, "test"));
		 float[] colors = {
	                1f, 0f, 0f, 1f, // vertex 0 red
	                0f, 1f, 0f, 1f, // vertex 1 green
	                0f, 0f, 1f, 1f, // vertex 2 blue
	                1f, 0f, 1f, 1f, // vertex 3 magenta	
	        };
		 test.colors.add(new Color(1.0f,0.0f,0.0f,1.0f));
		 test.colors.add(new Color(0,1,0,1));
		 test.colors.add(new Color(0,0,1,1));
		 test.colors.add(new Color(1,0,1,1));
		entity.setMesh(test.toMesh());
		entity.setMesh(mesh.toMesh());
		Log.error(FileManager.getInstance().loadBitmap("camaro"));
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
