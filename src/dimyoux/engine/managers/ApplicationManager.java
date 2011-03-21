package dimyoux.engine.managers;

import dimyoux.engine.EngineActivity;
import dimyoux.engine.core.JDBGLSurfaceView;
import dimyoux.engine.core.signals.IStatusBarDisabled;
import dimyoux.engine.core.signals.ITitleDisabled;
import dimyoux.engine.managers.applicationManagerClasses.Title;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.view.Window;
import android.view.WindowManager;
/**
 * [Singleton] Application Manager.
 */
public class ApplicationManager implements ITitleDisabled, IStatusBarDisabled {
	
	/** Instance of Application Manager. */
	private static ApplicationManager _instance; 
	
	/** Main activity. */
	private EngineActivity activity;
	
	/** Title. */
	private Title title;
	/**
	 * OpenGLSurface View
	 */
	private JDBGLSurfaceView openGLSurface;
	/**
	 * Constructor.
	 *
	 * @param activity Main activity
	 */
	private ApplicationManager(EngineActivity activity)
	{
		this.activity = activity;
		this.title = new Title();
		this.title.getSignalTitleDisabled().add(this);
		this.title.getSignalStatusBarDisabled().add(this);
	}
	
	/**
	 * Must be called first
	 * Initialize the singleton.
	 *
	 * @param activity Main Activity
	 */
	public static void initialization(EngineActivity activity)
	{
		_instance = new ApplicationManager(activity);
	}
	
	/**
	 * Return the ApplicationManager instance.
	 *
	 * @return ApplicationManager instance
	 */
	public static ApplicationManager getInstance()
	{
		return _instance;
	}
	
	/**
	 * Title Manager.
	 *
	 * @return The Title Manager
	 */
	public Title getTitle()
	{
		return title;
	}
	/**
	 * Main Activity
	 *
	 * @return Main Activity
	 */
	public Activity getActivity()
	{
		return activity;
	}
	/* (non-Javadoc)
	 * @see dimyoux.tablet.core.signals.ITitleDisabled#onTitleDisabled(java.lang.Boolean)
	 */
	@Override
	public void onTitleDisabled(Boolean disabled) {
		if(disabled)
		{
			activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}
	}
	/* (non-Javadoc)
	 * @see dimyoux.tablet.core.signals.IStatusBarDisabled#onStatusBarDisabled(java.lang.Boolean)
	 */
	@Override
	public void onStatusBarDisabled(Boolean disabled) {
		if(disabled)
		{
			int flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
	        activity.getWindow().setFlags(flags, flags);
		}
		
	}
	/**
	 * Indicate if phone is openGLES2.0 compatible
	 * @return True or false
	 */
	public boolean hasGLES20() {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }
	/**
	 * Indicate if phone is openGLES1.0 compatible
	 * @return True or false
	 */
	public boolean hasGLES10() {
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x10000);
    }
	/**
	 * Show an alert that will closed the main activity
	 * @param message Error message to display
	 */
	public void showFatalErrorDialog(String message)
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	}
	/**
	 * Create an openGLES20 surface
	 * @return True if it succeeded
	 */
	public Boolean createOpenGL2SurfaceView()
	{
		if(!hasGLES20())
		{
			boolean isOpenGLES10 = hasGLES10();
        	showFatalErrorDialog("Your phone has to be openGLES2.0 compatible"+(isOpenGLES10?"\nBut it is only openGLES1.0 compatible":"\nAnd it is not openGLES compatible"));
        	return false;
		}
		openGLSurface = new JDBGLSurfaceView(activity, activity);
        activity.setContentView(openGLSurface);
        openGLSurface.requestFocus();
        openGLSurface.setFocusableInTouchMode(true);
		return true;
	}

}
