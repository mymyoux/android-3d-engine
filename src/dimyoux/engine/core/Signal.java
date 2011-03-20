package dimyoux.engine.core;

import java.util.ArrayList;
import java.util.List;

import dimyoux.engine.core.signals.ISignal;
import dimyoux.engine.utils.Log;

/**
 * Signal Class.
 *
 * @param <T> Interface that must implement listeners
 */
public class Signal<T extends ISignal>{

	/** Listeners. */
	private List<T> listeners;
	
	/** One time listeners. */
	private List<T> listenersOnce;
	
	/**
	 * Constructor.
	 */
	public Signal()
	{
		listeners = new ArrayList<T>();
		listenersOnce = new ArrayList<T>();
	}
	/**
	 * Record a new listener. If the listener is already recorded nothing happens.
	 * @param listener Listener
	 */
	public void add(T listener)
	{
		if(!listeners.contains(listener))
		{
			listeners.add(listener);
		}
	}
	/**
	 * Record a new listener for one dispatch. If the listener is already recorded nothing happens.
	 * @param listener Listener
	 */
	public void addOnce(T listener)
	{
		if(!listeners.contains(listener) && !listenersOnce.contains(listener))
		{
			listenersOnce.add(listener);
		}
	}
	
	/**
	 * clear all listeners.
	 */
	public void clear()
	{
		listeners.clear();
	}
	/**
	 * Remove a recorded listener. If the listener is not yet recorded nothing happens.
	 * @param listener Listener
	 */
	public void remove(T listener)
	{
		if(listeners.contains(listener))
		{
				listeners.remove(listener);
		}
		if(listenersOnce.contains(listener))
		{
			listenersOnce.remove(listener);
		}
	}
	
	/**
	 * Dispatch the signal.
	 */
	public void dispatch()
	{
		this.dispatch((Object[])null);
	}
	
	/**
	 * Dispatch the signal with parameters.
	 *
	 * @param params Parameters
	 */
	public void dispatch(Object... params)
	{
		for(T listener : listeners)
		{
			try
			{
				this._dispatch(listener, params);
			}catch(ClassCastException e)
			{
				Log.error("Mauvais type de paramètre envoyé au signal : "+e.toString());
			}catch(NullPointerException e)
			{
				Log.error("Vous n'avez pas passé d'arguments au signal alors qu'au moins un est nécessaire : "+e.toString());
			}catch(ArrayIndexOutOfBoundsException e)
			{
				Log.error("Le nombre d'arguments envoyé au signal est trop faible : "+e.toString());
			}
			
		}
		for(T listener : listenersOnce)
		{
			try
			{
				this._dispatch(listener, params);
			}catch(ClassCastException e)
			{
				Log.error("Mauvais type de paramètre envoyé au signal : "+e.toString());
			}catch(NullPointerException e)
			{
				Log.error("Vous n'avez pas passé d'arguments au signal alors qu'au moins un est nécessaire : "+e.toString());
			}catch(ArrayIndexOutOfBoundsException e)
			{
				Log.error("Le nombre d'arguments envoyé au signal est trop faible : "+e.toString());
			}
		}
		listenersOnce.clear();
	}
	
	/**
	 * Must be overridden!.
	 *
	 * @param listener Listener
	 * @param params Parameters
	 */
	protected void _dispatch(T listener, Object ...params)
	{ 
		Log.error("This method must be overridden");
	} 
}
