package dimyoux.engine.utils.math;

public class Coord4D extends Coord3D {

	/**
	 * W
	 */
	public float w;
	
	/**
	 * Constructor
	 */
	public Coord4D()
	{
		
	}
	
	/**
	 * Constructor
	 * @param x X 
	 * @param y Y
	 * @param z Z
	 * @param w W
	 */
	public Coord4D(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/**
	 * Constructor
	 * @param values values of the new Coord4D
	 */
	public Coord4D(float[] values)
	{
		if (values.length == 4) 
		{
			this.x = values[0];
			this.y = values[1];
			this.z = values[2];
			this.w = values[3];
		}
	}
	
	/**
	 * Constructor
	 * @param x X
	 * @param y Y
	 * @param z Z
	 * @param w W
	 */
	public Coord4D(String x, String y, String z, String w)
	{
		this.x = Float.parseFloat(x);
		this.y = Float.parseFloat(y);
		this.z = Float.parseFloat(z);
		this.w = Float.parseFloat(w);
	}
	/**
	 * Constructor
	 * @param coord4D Syntax : "x y z w"
	 */
	public Coord4D(String coord4D)
	{
		String[]coords = coord4D.split(" ");
		this.x = Float.parseFloat(coords[0]);
		this.y = Float.parseFloat(coords[1]);
		this.z = Float.parseFloat(coords[2]);
		this.w = Float.parseFloat(coords[4]);
	}
	
	public float[] toFloatArray() 
	{
		float[] array = new float[4];
		
		array[0] = this.x;
		array[1] = this.y;
		array[2] = this.z;
		array[3] = this.w;
		
		return array;
	}
	
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String string = "[Coord4D";
		string += " X=" + x;
		string += " Y=" + y;
		string += " Z=" + z;
		string += " W=" + w;
		return string + "]";
	}
}
