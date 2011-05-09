package dimyoux.engine.utils.filters;

import java.util.Timer;
import java.util.TimerTask;

import dimyoux.engine.core.Signal;
import dimyoux.engine.core.signals.ISensorOrientation;
import dimyoux.engine.managers.SensorManager;

/**
 * Class implementing a band-pass filter used to
 * filter the sensors values. 
 *
 */
public class FIRFilter extends TimerTask implements ISensorOrientation {
	
	/**
	 * Value used by the FIRFilter
	 */
	private final float k = 0.9f; 
	
	/**
	 * Stored filtered values to dispatch
	 */
	private float yawF, pitchF, rollF;
	
	/**
	 * Values received from the SensorManager
	 */
	private float yaw, pitch, roll;
	
	private long timestamp = 0;
	
	/**
	 * Instance of the filter
	 */
	private static FIRFilter _instance;
	
	/**
	 * Timer used to execute the run function
	 */
	private Timer timer;

	/**
	 * Dispatches a signal containing filtered values 
	 * when orientation sensors status changed
	 * Signal<ISensorOrientation>.dispatch(float(yaw), float(pitch), float(roll));
	 */
	private Signal<ISensorOrientation> signalOrientation;
	
	
	
	/**
	 * BandPass constructor
	 */
	private FIRFilter() {
		SensorManager.getInstance().getSignalOrientation().add(this);
		
		signalOrientation = new Signal<ISensorOrientation>(){
			@Override
        	protected void _dispatch(ISensorOrientation listener, Object... params)
        	{
        		listener.onOrientationChanged((Float)params[0],(Float)params[1],(Float)params[2], (Long)params[3]);
        	}
		};
		
		timer = new Timer();
		
		// Timer period at 83 ms
		timer.scheduleAtFixedRate(this, 0, 50);
	}
	
	/**
	 * Return the instance of BandPassFilter
	 * @return Instance of BandPassFilter
	 */
	public static FIRFilter getInstance()
	{
		if(_instance == null)
		{
			_instance = new FIRFilter();
		}
		return _instance;
	}
	
	/**
	 * Dispatch an signal when the orientation is changed
	 * Signal<ISensorOrientation>.dispatch(float yaw, float pitch, float roll);
	 * @return Signal<ISensorOrientation>.dispatch(float yaw, float pitch, float roll);
	 */
	public Signal<ISensorOrientation> getSignalOrientation()
	{
		return signalOrientation;
	}
	
	@Override
	public void onOrientationChanged(float yaw, float pitch, float roll, long timestamp) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.timestamp = timestamp;
	}

	@Override
	/**
	 * Filtering function called by the Timer
	 */
	public void run() {
		
		yawF = k * yawF + (1.0f - k) * this.yaw;
		pitchF = k * pitchF + (1.0f - k) * this.pitch;
		rollF = k * rollF + (1.0f - k) * this.roll;
		
		signalOrientation.dispatch(yawF, pitchF, rollF, timestamp);
	}

}
