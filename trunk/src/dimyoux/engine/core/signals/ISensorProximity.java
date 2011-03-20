package dimyoux.engine.core.signals;
/**
 * Signal interface for change of proximity sensor status
 */
public interface ISensorProximity extends ISignal{
	/**
	 * Called when the proximity sensor detect a move
	 * @param present True if someone is detected false otherwise
	 */
	public void onProximityEvent(Boolean present);
}
