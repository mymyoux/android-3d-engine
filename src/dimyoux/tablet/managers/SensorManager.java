package dimyoux.tablet.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;



import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import dimyoux.tablet.core.Signal;
import dimyoux.tablet.core.signals.ISensorOrientation;
import dimyoux.tablet.core.signals.ISensorProximity;
import dimyoux.tablet.utils.Log;
/**
 * [Singleton]Sensor manager
 */
public class SensorManager implements SensorEventListener 
{
	/**
	 * All sensors type
	 */
	public final static int TYPE_ALL = Sensor.TYPE_ALL;
	/**
	 * Accelerometer type
	 */
	public final static int TYPE_ACCELEROMETER = Sensor.TYPE_ACCELEROMETER;
	/**
	 * Gyroscope type
	 */
	public final static int TYPE_GYROSCOPE = Sensor.TYPE_GYROSCOPE;
	/**
	 * Light type
	 */
	public final static int TYPE_LIGHT = Sensor.TYPE_LIGHT;
	
	/**
	 * Magnetic type
	 */
	public final static int TYPE_MAGNETIC_FIELD = Sensor.TYPE_MAGNETIC_FIELD;
	/**
	 * Orientation type
	 */
	public final static int TYPE_ORIENTATION = Sensor.TYPE_ORIENTATION;
	/**
	 * Pressure type
	 */
	public final static int TYPE_PRESSURE = Sensor.TYPE_PRESSURE;
	/**
	 * Proximity type
	 */
	public final static int TYPE_PROXIMITY = Sensor.TYPE_PROXIMITY;
	/**
	 * Temperature type
	 */
	public final static int TYPE_TEMPERATURE = Sensor.TYPE_TEMPERATURE;
	/**
	 * Android native sensor manager
	 */
	private android.hardware.SensorManager _androidSensorManager;
	/**
	 * Instance of the sensor manager
	 */
	private static SensorManager _instance;
	/**
	 * Sensors
	 */
	private List<Sensor> sensors;
	/**
	 * Sensors Types
	 */
	final private static List<Integer> sensorsTypes = new ArrayList<Integer>() {{
	    add(TYPE_ACCELEROMETER);
	    add(TYPE_GYROSCOPE);
	    add(TYPE_LIGHT);
	    add(TYPE_MAGNETIC_FIELD);
	    add(TYPE_ORIENTATION);
	    add(TYPE_PRESSURE);
	    add(TYPE_PROXIMITY);
	    add(TYPE_TEMPERATURE);
	}}; 
	/**
	 * Names of Sensors Types
	 */
	final private Map<Integer, String> sensorsNames = new Hashtable<Integer, String>()
	{{
		put(TYPE_ACCELEROMETER, "accelerometer");
		put(TYPE_GYROSCOPE, "gyroscope");
		put(TYPE_LIGHT, "light");
		put(TYPE_MAGNETIC_FIELD, "magnetic");
		put(TYPE_ORIENTATION, "orientation");
		put(TYPE_PRESSURE, "pressure");
		put(TYPE_PROXIMITY, "proximity");
		put(TYPE_TEMPERATURE, "temperature");
	}};
	/**
	 * Dispatch an signal when the proximity sensor status changes
	 * Signal<ISensorProximity>.dispatch(Boolean);
	 */
	private Signal<ISensorProximity> signalProximity;
	/**
	 * Dispatch an signal when orientation sensors status changed
	 * Signal<ISensorOrientation>.dispatch(float(yaw), float(pitch), float(roll));
	 */
	private Signal<ISensorOrientation> signalOrientation;
	private float[] mag;
	private float[] acc;
	/**
	 * Constructor
	 */
	private SensorManager()
	{
		signalProximity = new Signal<ISensorProximity>(){
			@Override
        	protected void _dispatch(ISensorProximity listener, Object... params)
        	{
        		listener.onProximityEvent((Boolean)params[0]);
        	}
		};
		signalOrientation = new Signal<ISensorOrientation>(){
			@Override
        	protected void _dispatch(ISensorOrientation listener, Object... params)
        	{
        		listener.onOrientationChanged((Float)params[0],(Float)params[1],(Float)params[2]);
        	}
		};
		mag = new float[3];
		acc = new float[3];
		/*
		signalOrientation = new Signal<ISensorProximity>(){
			@Override
        	protected void _dispatch(ISensorProximity listener, Object... params)
        	{
        		listener.onProximityEvent((Boolean)params[0]);
        	}
		};*/
		_androidSensorManager = (android.hardware.SensorManager)ApplicationManager.getInstance().getActivity().getSystemService(Context.SENSOR_SERVICE);
		sensors = _androidSensorManager.getSensorList(Sensor.TYPE_ALL);
		Log.info("Il y'a "+size()+" sensors");
		for(Integer i :sensorsTypes)
		{
			if(size(i)>0)
			{
				Log.info("Il y'a "+size(i)+" sensor(s) de type "+sensorsNames.get(i));
			}
		}
		for(Integer i :sensorsTypes)
		{
			if(size(i)>0)
			{
				if((size(TYPE_GYROSCOPE) > 0 && i!=TYPE_ACCELEROMETER && i!=TYPE_MAGNETIC_FIELD && i!=TYPE_ORIENTATION)|| (size(TYPE_GYROSCOPE)==0 && size(TYPE_MAGNETIC_FIELD)>0 && size(TYPE_ACCELEROMETER)>0 && i!=TYPE_ORIENTATION) || ((size(TYPE_MAGNETIC_FIELD)==0 || size(TYPE_ACCELEROMETER)==0) && size(TYPE_GYROSCOPE)==0 && i!=TYPE_ACCELEROMETER))
				{
					_androidSensorManager.registerListener(this, _androidSensorManager.getSensorList(i).get(0), android.hardware.SensorManager.SENSOR_DELAY_GAME);
				}
			}
		}
		
		
		
	}
	/**
	 * Return the instance of SensorManager
	 * @return Instance of SensorManager
	 */
	public static SensorManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new SensorManager();
		}
		return _instance;
	}
	/**
	 * Return the sensor
	 * @param type Type of the sensor
	 * @return Sensor
	 */
	public Sensor getSensor(int type)
	{
		for(Sensor s:sensors)
		{
			if(s.getType() == type)
			{
				return s;
			}
		}
		return null;
	}
	/**
	 * Number of sensors
	 * @return Quantity of sensors
	 */
	public int size()
	{
		return sensors.size();
	}
	/**
	 * Number of sensors of a type
	 * @param type Type of sensor
	 * @return Quantity of sensors of this type
	 */
	public int size(int type)
	{
		int count = 0;
		for(final Sensor sensor : sensors)
		{
			if(sensor.getType() == type)
			{
				count++;
			}
		}
		return count;
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//Log.error("Accurency for "+sensorsNames.get(sensor.getType())+" : "+accuracy);
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		switch (event.sensor.getType()) {
		case TYPE_ACCELEROMETER:
			//Log.debug("acceleration : " + event.values[0] + ";" + event.values[1] + ";" + event.values[2]);
			acc = event.values.clone();
			if((event.values[0]==1 && event.values[1] == 0 && event.values[2] == 0) || (event.values[0]==0 && event.values[1] == 1 && event.values[2] == 0) || (event.values[0]==0 && event.values[1] == 0 && event.values[2] == 1) )
			{
				_androidSensorManager.unregisterListener(this, getSensor(TYPE_ACCELEROMETER));
				_androidSensorManager.unregisterListener(this, getSensor(TYPE_MAGNETIC_FIELD));
				_androidSensorManager.registerListener(this, getSensor(TYPE_ORIENTATION), android.hardware.SensorManager.SENSOR_DELAY_GAME);
				sensors.remove(getSensor(TYPE_ACCELEROMETER));
				Log.warning("Accelerometer sensor is a fake");
			}
			if(mag != null)
			{
				float[] result = new float[16];
				if(android.hardware.SensorManager.getRotationMatrix(result, null, acc, mag))
				{
					float[] rotation = new float[3];
					android.hardware.SensorManager.getOrientation(result, rotation);
					signalOrientation.dispatch(rotation[0], rotation[1], rotation[2]);
					mag = null;
					acc = null;
				}
			}
			break;
		case TYPE_MAGNETIC_FIELD:
			mag = event.values.clone();
			//Log.info("magnet :values: " + event.values[0] + ";" + event.values[1] + ";" + event.values[2]);
			if((event.values[0]==1 && event.values[1] == 0 && event.values[2] == 0) || (event.values[0]==0 && event.values[1] == 1 && event.values[2] == 0) || (event.values[0]==0 && event.values[1] == 0 && event.values[2] == 1) )
			{
				_androidSensorManager.unregisterListener(this, getSensor(TYPE_ACCELEROMETER));
				_androidSensorManager.unregisterListener(this, getSensor(TYPE_MAGNETIC_FIELD));
				_androidSensorManager.registerListener(this, getSensor(TYPE_ORIENTATION), android.hardware.SensorManager.SENSOR_DELAY_GAME);
				sensors.remove(getSensor(TYPE_MAGNETIC_FIELD));
				Log.warning("Magnetic sensor is a fake");
			}
			if(acc != null)
			{
				float[] result = new float[16];
				if(android.hardware.SensorManager.getRotationMatrix(result, null, acc, mag))
				{
					float[] rotation = new float[3];
					android.hardware.SensorManager.getOrientation(result, rotation);
					signalOrientation.dispatch(rotation[0], rotation[1], rotation[2]);
					mag = null;
					acc = null;
				}
			}
			break;
		case TYPE_ORIENTATION:
			signalOrientation.dispatch(event.values);
			break;
		case TYPE_GYROSCOPE:
			//TODO:signal with a value for compatible phones
			break;
		case TYPE_PROXIMITY:
			//TODO:more complicated signal with a value for compatible phones
			signalProximity.dispatch(event.values[0]==0.0?true:false);
			break;
		case TYPE_LIGHT:
			//TODO:signal with a value for compatible phones
				Log.debug("light:"+event.values[0]);
			break;
		}
	}
	/**
	 * Dispatch an signal when the proximity sensor status changes
	 * Signal<ISensorProximity>.dispatch(Boolean);
	 * @return Signal<ISensorProximity>.dispatch(Boolean);
	 */
	public Signal<ISensorProximity> getSignalProximity()
	{
		return signalProximity;
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
}

