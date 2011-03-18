package dimyoux.tablet.core;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
/**
 *  GLSurfaceView 
 */

public class JDBGLSurfaceView extends GLSurfaceView {

	/**
	 * Renderer
	 */
	private GLRenderer renderer;
	/**
	 * Constructor
	 * @param context Context(activity)
	 */
	public JDBGLSurfaceView(Context context) {
		super(context);
		renderer = new GLRenderer();
		setRenderer(renderer);
	}
	/**
	 * Constructor
	 * @param context Context(activity)
	 * @param attrs Attributs
	 */
	public JDBGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		renderer = new GLRenderer();
		setRenderer(renderer);
	}


}
