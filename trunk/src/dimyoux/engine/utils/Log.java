package dimyoux.engine.utils;
/**
 * Logs Manager class.
 */
public class Log {

	/** The tag name. */
	public static String tagName = "JDBTablet";
	
	/**
	 * Used to display debug messages to LogCat.
	 *
	 * @param message debug message
	 */
	public static void debug(Object message) 
	{
		debug(message!=null?message.toString():"null");
	}
	
	/**
	 * Used to display debug messages to LogCat.
	 *
	 * @param message debug message
	 */
	public static void debug(String message) 
	{
		android.util.Log.d(tagName, message);
	}	
	
	/**
	 * Used to display debug messages to LogCat.
	 *
	 * @param message debug message
	 */
	public static void d(String message)
	{
		debug(message);
	}
	/**
	 * Used to display error messages to LogCat.
	 *
	 * @param exception exception message
	 */
	public static void error(Exception exception)
	{
		error(exception.getMessage());
		for(final StackTraceElement element : exception.getStackTrace())
		{
			error(element);
		}
	}
	/**
	 * Used to display error messages to LogCat.
	 *
	 * @param message error message
	 */
	public static void error(Object message) 
	{
		error(message!=null?message.toString():"null");	
	}
	
	/**
	 * Used to display error messages to LogCat.
	 *
	 * @param message error message
	 */
	public static void error(String message) 
	{
		android.util.Log.e(tagName, message);
	}
	
	/**
	 * Used to display error messages to LogCat.
	 *
	 * @param message error message
	 */
	public static void e(String message)
	{
		error(message);
	}
	
	/**
	 * Used to display information messages to LogCat.
	 *
	 * @param message information message
	 */
	public static void info(Object message) 
	{
		info(message!=null?message.toString():"null");
	}
	
	/**
	 * Used to display information messages to LogCat.
	 *
	 * @param message information message
	 */
	public static void info(String message) 
	{
		android.util.Log.i(tagName, message);
	}
	
	/**
	 * Used to display information messages to LogCat.
	 *
	 * @param message information message
	 */
	public static void i(String message)
	{
		info(message);
	}
	
	/**
	 * Used to display verbose messages to LogCat.
	 *
	 * @param message verbose message
	 */
	public static void verbose(Object message) 
	{
		verbose(message!=null?message.toString():"");
	}
	
	/**
	 * Used to display verbose messages to LogCat.
	 *
	 * @param message verbose message
	 */
	public static void verbose(String message) 
	{
		android.util.Log.v(tagName, message);
	}
	
	/**
	 * Used to display verbose messages to LogCat.
	 *
	 * @param message verbose message
	 */
	public static void v(String message)
	{
		verbose(message);
	}
	
	/**
	 * Used to display warning messages to LogCat.
	 *
	 * @param message warning message
	 */
	public static void warning(Object message) 
	{
		warning(message!=null?message.toString():"null");
	}
	
	/**
	 * Used to display warning messages to LogCat.
	 *
	 * @param message warning message
	 */
	public static void warning(String message) 
	{
		android.util.Log.w(tagName, message);
	}
	
	/**
	 * Used to display warning messages to LogCat.
	 *
	 * @param message warning message
	 */
	public static void w(String message)
	{
		warning(message);
	}
	
	/* Android Log class compatibility */

	/**
	 * Used to display debug messages to LogCat.
	 *
	 * @param tag message tag
	 * @param message debug message
	 */
	public static void d(String tag, String message) 
	{
		android.util.Log.d(tag, message);
	}	
	
	/**
	 * Used to display error messages to LogCat.
	 *
	 * @param tag message tag
	 * @param message error message
	 */
	public static void e(String tag, String message) 
	{
		android.util.Log.e(tag, message);
	}
	
	/**
	 * Used to display information messages to LogCat.
	 *
	 * @param tag message tag
	 * @param message information message
	 */
	public static void i(String tag, String message) 
	{
		android.util.Log.i(tag, message);
	}
	
	/**
	 * Used to display verbose messages to LogCat.
	 *
	 * @param tag message tag
	 * @param message verbose message
	 */
	public static void v(String tag, String message) 
	{
		android.util.Log.v(tag, message);
	}
	
	/**
	 * Used to display warning messages to LogCat.
	 *
	 * @param tag message tag
	 * @param message warning message
	 */
	public static void w(String tag, String message) 
	{
		android.util.Log.w(tag, message);
	}
}
