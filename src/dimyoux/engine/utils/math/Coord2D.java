package dimyoux.engine.utils.math;
/**
 * Coord2D
 */
public class Coord2D {

	/**
	 * X
	 */
	public float x;
	/**
	 * Y
	 */
	public float y;
	/**
	 * Constructor
	 */
	public Coord2D()
	{
		
	}
	/**
	 * Constructor
	 * @param x X
	 * @param y Y
	 */
	public Coord2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructor
	 * @param x X
	 * @param y Y
	 */
	public Coord2D(String x, String y)
	{
		this.x = Float.parseFloat(x);
		this.x = Float.parseFloat(y);
	}
	/**
	 * Constructor
	 * @param coord2D Syntax : "x y"
	 */
	public Coord2D(String coord2D)
	{
		String[]coords = coord2D.split(" ");
		this.x = Float.parseFloat(coords[0]);
		this.y = Float.parseFloat(coords[1]);
	}
	/**
	 * Returns the number of coordinates
	 * @return Returns the number of coordinates
	 */
	public int size()
	{
		return 2;
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String string = "[Coord2D";
		string+=" X="+x;
		string+=" Y="+y;
		return string+"]";
	}
	/**
	 * Returns the corresponding float array
	 * @return the float array [r, g, b, a]
	 */
	public float[] toFloatArray()
	{
		return new float[]{x, y};
	}
}
