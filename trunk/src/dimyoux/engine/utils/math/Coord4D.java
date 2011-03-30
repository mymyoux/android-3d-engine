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
		length = 4;
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
		length = 4;
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
		length = 4;
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
		length = 4;
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
		length = 4;
		String[]coords = coord4D.split(" ");
		this.x = Float.parseFloat(coords[0]);
		this.y = Float.parseFloat(coords[1]);
		this.z = Float.parseFloat(coords[2]);
		this.w = Float.parseFloat(coords[4]);
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public float[] toFloatArray()
	{
		return new float[]{x, y, z, w};
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
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object object)
	{
		try
		{
			Coord4D coord = (Coord4D)object;
			return coord!=null && coord.x == x && coord.y == y && coord.z == z && coord.w == w;
		}catch(Exception e)
		{
			return false;
		}
	}
}
