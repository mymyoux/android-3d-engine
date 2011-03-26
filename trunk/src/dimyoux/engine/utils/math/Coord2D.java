package dimyoux.engine.utils.math;
/**
 * Coord2D
 */
public class Coord2D {

	/**
	 * U
	 */
	public float u;
	/**
	 * V
	 */
	public float v;
	/**
	 * Constructor
	 */
	public Coord2D()
	{
		
	}
	/**
	 * Constructor
	 * @param u U
	 * @param v V
	 */
	public Coord2D(float u, float v)
	{
		this.u = u;
		this.v = v;
	}
	/**
	 * Constructor
	 * @param u U
	 * @param v V
	 */
	public Coord2D(String u, String v)
	{
		this.u = Float.parseFloat(u);
		this.v = Float.parseFloat(v);
	}
	/**
	 * Constructor
	 * @param coord2D Syntax : "x y z"
	 */
	public Coord2D(String coord2D)
	{
		String[]coords = coord2D.split(" ");
		this.u = Float.parseFloat(coords[0]);
		this.v = Float.parseFloat(coords[1]);
	}
}
