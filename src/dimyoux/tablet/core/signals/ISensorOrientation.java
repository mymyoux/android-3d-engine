package dimyoux.tablet.core.signals;
/**
 * Signal interface for change of orientation
 */
public interface ISensorOrientation extends ISignal {
	/**
	 * Called when the orientation has changed
	 * @param yaw Yaw value
	 * @param pitch Pitch value
	 * @param roll Roll value
	 *  
	 */
	public void onOrientationChanged(float yaw, float pitch, float roll);
}
