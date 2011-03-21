package dimyoux.engine.core.signals;
/**
 * Signal interface for change of light level
 */
public interface ISensorLight extends ISignal {
	/**
	 * Called when the light level has changed
	 * @param light Ambient light level in SI lux units
	 *  
	 */
	public void onLightChanged(float light);
}
