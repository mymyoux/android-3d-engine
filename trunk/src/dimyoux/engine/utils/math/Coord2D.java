package dimyoux.engine.utils.math;
/**
 * Coord2D
 */
public class Coord2D {

	/**
	 * Indicates if it is a 2D, 3D or 4D coordinates
	 */
	protected int length;
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
		length = 2;
	}
	/**
	 * Constructor
	 * @param x X
	 * @param y Y
	 */
	public Coord2D(float x, float y)
	{
		length = 2;
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
		length = 2;
		this.x = Float.parseFloat(x);
		this.x = Float.parseFloat(y);
	}
	/**
	 * Constructor
	 * @param coord2D Syntax : "x y"
	 */
	public Coord2D(String coord2D)
	{
		length = 2;
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
		return length;
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
	 * @return the float array
	 */
	public float[] toFloatArray()
	{
		return new float[]{x, y};
	}
	/**
	 * Indicates if object is equals to this
	 * @param object
	 * @return True or false
	 */
	@Override
	public boolean equals(Object object)
	{
		try
		{
			Coord2D coord = (Coord2D)object;
			return coord!=null && coord.x == x && coord.y == y;
		}catch(Exception e)
		{
			return false;
		}
	}
}
