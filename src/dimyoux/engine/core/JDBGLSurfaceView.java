package dimyoux.engine.core;


import dimyoux.engine.core.interfaces.IRenderer;
import dimyoux.engine.utils.Color;
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
	protected IRenderer renderer;
	/**
	 * Constructor
	 * @param context Context(activity)
	 * @param renderer Class that extends from GLRenderer 
	 */
	public JDBGLSurfaceView(Context context, IRenderer renderer) {
		super(context);
		this.renderer = renderer;
		setRenderer(renderer);
	}


}
