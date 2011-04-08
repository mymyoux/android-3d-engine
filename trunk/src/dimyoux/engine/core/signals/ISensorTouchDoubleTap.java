package dimyoux.engine.core.signals;

/**
 * Signal interface for double tap event
 */
public interface ISensorTouchDoubleTap extends ISignal{
	/**
	 * Called when the user double tap on the screen
	 * @param event MotionEvent
	 */
	public void onDoubleTap(float x, float y, float pressure, float size);
}
