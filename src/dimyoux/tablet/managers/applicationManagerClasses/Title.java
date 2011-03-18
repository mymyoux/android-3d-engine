package dimyoux.tablet.managers.applicationManagerClasses;

import dimyoux.tablet.core.signals.IStatusBarDisabled;
import dimyoux.tablet.core.signals.ITitleDisabled;
import dimyoux.tablet.core.Signal;


/**
 * Title Manager.
 */
public class Title
{
	/**
	 * Dispatch an signal when the title is disabled or enabled
	 * Signal<TitleDisabled>.dispatch(Boolean);
	 */
	private Signal<ITitleDisabled> signalTitleDisabled;
	/**
	 * Dispatch an signal when the status bar is disabled or enabled
	 * Signal<TitleDisabled>.dispatch(Boolean);
	 */
	private Signal<IStatusBarDisabled> signalStatusBarDisabled;
	
	/**
	 * Constructor.
	 */
	public Title()
	{
		this.signalTitleDisabled = new Signal<ITitleDisabled>()
        {
        	@Override
        	protected void _dispatch(ITitleDisabled listener, Object... params)
        	{
        		listener.onTitleDisabled((Boolean)params[0]);
        	}
        }; 
        this.signalStatusBarDisabled = new Signal<IStatusBarDisabled>()
        {
        	@Override
        	protected void _dispatch(IStatusBarDisabled listener, Object... params)
        	{
        		listener.onStatusBarDisabled((Boolean)params[0]);
        	}
        }; 
	}
	
	/**
	 * Disable title.
	 */
	public void disable()
	{
		signalTitleDisabled.dispatch(true);
	}
	
	/**
	 * Enable title (not yet implemented).
	 */
	public void enable()
	{
		signalTitleDisabled.dispatch(false);
	}
	/**
	 * Disable status bar
	 */
	public void disableStatusBar()
	{
		signalStatusBarDisabled.dispatch(true);
	}
	/**
	 * Enable status bar
	 */
	public void enableStatusBar()
	{
		signalStatusBarDisabled.dispatch(false);
	}
	/**
	 * Hide status bar and title
	 */
	public void fullScreen()
	{
		disable();
		disableStatusBar();
	}
	/**
	 * Dispatch an signal when the title is disabled or enabled
	 * Signal<ITitleDisabled>.dispatch(Boolean);
	 * @return Signal<ITitleDisabled>.dispatch(Boolean);
	 */
	public Signal<ITitleDisabled> getSignalTitleDisabled()
	{
		return signalTitleDisabled;
	}
	/**
	 * Dispatch an signal when the status bar is disabled or enabled
	 * Signal<IStatusBarDisabled>.dispatch(Boolean);
	 * @return Signal<IStatusBarDisabled>.dispatch(Boolean);
	 */
	public Signal<IStatusBarDisabled> getSignalStatusBarDisabled()
	{
		return signalStatusBarDisabled;
	}
}