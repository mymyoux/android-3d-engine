package dimyoux.engine.core;


import android.content.Context;
import android.opengl.GLSurfaceView;
/**
 *  GLSurfaceView 
 */

public class JDBGLSurfaceView extends GLSurfaceView {

	/**
	 * Renderer
	 */
	protected GLSurfaceView.Renderer renderer;
	/**
	 * Constructor
	 * @param context Context(activity)
	 * @param renderer Class that extends from GLRenderer 
	 */
	public JDBGLSurfaceView(Context context, GLSurfaceView.Renderer renderer) {
		super(context);
		this.renderer = renderer;
		setRenderer(renderer);
	}


}
