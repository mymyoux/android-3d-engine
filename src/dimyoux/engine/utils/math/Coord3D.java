package dimyoux.engine.utils.math;
/**
 * Coord3D
 */
public class Coord3D extends Coord2D{

	public float z;
	/**
	 * Constructor
	 */
	public Coord3D()
	{
		length = 3;
	}
	/**
	 * Constructor
	 * @param x X 
	 * @param y Y
	 * @param z Z
	 */
	public Coord3D(float[] coordinates)
	{
		length = 3;
		if(coordinates.length>0)
		{
			this.x = coordinates[0];
			if(coordinates.length>1)
			{
				this.y = coordinates[1];
			}
			if(coordinates.length>2)
			{
				this.z = coordinates[2];
			}
		}
	}
	/**
	 * Constructor
	 * @param x X 
	 * @param y Y
	 * @param z Z
	 */
	public Coord3D(float x, float y, float z)
	{
		length = 3;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Constructor
	 * @param x X
	 * @param y Y
	 * @param z Z
	 */
	public Coord3D(String x, String y, String z)
	{
		length = 3;
		this.x = Float.parseFloat(x);
		this.y = Float.parseFloat(y);
		this.z = Float.parseFloat(z);
	}
	/**
	 * Constructor
	 * @param coord3D Syntax : "x y z"
	 */
	public Coord3D(String coord3D)
	{
		length = 3;
		String[]coords = coord3D.split(" ");
		this.x = Float.parseFloat(coords[0]);
		this.y = Float.parseFloat(coords[1]);
		this.z = Float.parseFloat(coords[2]);
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String string = "[Coord3D";
		string+=" X="+x;
		string+=" Y="+y;
		string+=" Z="+z;
		return string+"]";
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public float[] toFloatArray()
	{
		return new float[]{x, y, z};
	}
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object object)
	{
		try
		{
			Coord3D coord = (Coord3D)object;
			return coord!=null && coord.x == x && coord.y == y && coord.z == z;
		}catch(Exception e)
		{
			return false;
		}
	}
}
