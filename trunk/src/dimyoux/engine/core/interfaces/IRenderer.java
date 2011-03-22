package dimyoux.engine.core.interfaces;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Interface used by dimyoux.engine.core.Renderer
 */
public interface IRenderer extends android.opengl.GLSurfaceView.Renderer{
	/**
	 * Called by the Renderer
	 * @param gl GL instance
	 */
	public void _onDrawFrame(GL10 gl);
	/**
	 * Called by the Renderer
	 * @param gl GL instance
	 * @param width Width
	 * @param height Height
	 */
	public void _onSurfaceChanged(GL10 gl, int width, int height);
	/**
	 * Called by the Renderer
	 * @param gl GL instance
	 * @param config EGLConfig
	 */
	public void _onSurfaceCreated(GL10 gl, EGLConfig config);
}
