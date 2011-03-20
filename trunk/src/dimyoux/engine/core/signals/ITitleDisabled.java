package dimyoux.engine.core.signals;

/**
 * Signal interface for change of title.
 */
public interface ITitleDisabled extends ISignal
{
	
	/**
	 * Called when the title is disabled or enabled.
	 * @param disabled True if the title is disabled, false otherwise.
	 */
	public void onTitleDisabled(Boolean disabled);
}
