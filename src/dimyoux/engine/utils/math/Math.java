package dimyoux.engine.utils.math;

/**
 * Math class
 */
public class Math {
	/**
	 * factor to convert radians to degrees
	 */
	public static final float RAD2DEG = (float) (180.0/java.lang.Math.PI);
	/**
	 * factor to convert degrees to radians
	 */
	public static final float DEG2RAD = (float) (java.lang.Math.PI/180.0);
	
	/**
	 * Returns cosinus in degrees of the specified angle
	 * @param angle the angle
	 * @return the cosinus in degrees
	 */
	public static double cosd(double angle) 
	{
		return java.lang.Math.cos(angle * DEG2RAD);
	}

	/**
	 * Returns sinus in degrees of the specified angle
	 * @param angle the angle
	 * @return the sinus in degrees
	 */
	public static double sind(double angle) 
	{
		return java.lang.Math.sin(angle * DEG2RAD);
	}
	/**
	 * Returns tangent in degrees of the specified angle
	 * @param angle the angle
	 * @return the tangent in degrees
	 */
	public static double tand(double angle) 
	{
		return java.lang.Math.tan(angle * DEG2RAD);
	}
}
