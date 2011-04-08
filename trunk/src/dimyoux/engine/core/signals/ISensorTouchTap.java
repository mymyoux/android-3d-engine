package dimyoux.engine.core.signals;

import android.view.MotionEvent;

/**
 * Signal interface for single tap event
 */
public interface ISensorTouchTap extends ISignal{
	/**
	 * Called when the user single tap on the screen
	 * @param event MotionEvent
	 */
	public void onTap(float x, float y, float pressure, float size);
}
