package dimyoux.houseExplorer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import dimyoux.engine.core.GLRenderer;
import dimyoux.engine.utils.Color;
/**
 * Example application : A house explorer 
 */
public class HouseRenderer extends GLRenderer {

	/**
	 * Constructor
	 */
	public HouseRenderer()
	{
		super();
		this.setClearColor(new Color(Color.YELLOW));
	}
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		 super.onDrawFrame(gl);
	}

}
