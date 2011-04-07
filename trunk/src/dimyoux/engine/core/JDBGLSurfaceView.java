package dimyoux.engine.core;


import dimyoux.engine.managers.SensorManager;
import dimyoux.engine.utils.Log;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.GestureDetector;
import android.view.MotionEvent;
/**
 *  GLSurfaceView 
 */

public class JDBGLSurfaceView extends GLSurfaceView {

	private GestureDetector gestureDetector;
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
		gestureDetector = new GestureDetector(context, SensorManager.getInstance());
		this.renderer = renderer;
		setRenderer(renderer);
		 
	}


	@Override
    public boolean onTouchEvent(final MotionEvent event) {
    	 gestureDetector.onTouchEvent(event);
    	return true;
    }
}
