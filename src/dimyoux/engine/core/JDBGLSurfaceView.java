package dimyoux.engine.core;


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
	protected GLRenderer renderer;
	/**
	 * Constructor
	 * @param context Context(activity)
	 * @param renderer Class that extends from GLRenderer 
	 */
	public JDBGLSurfaceView(Context context, GLRenderer renderer) {
		super(context);
		this.renderer = renderer;
		setRenderer(renderer);
	}
	/**
	 * Defines the GlClearColor
	 * @param color Color
	 */
	public void setClearColor(Color color)
	{
		renderer.setClearColor(color);
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
