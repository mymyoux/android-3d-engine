package dimyoux.engine.utils;
import dimyoux.engine.utils.math.Matrix;
/**
 * Color
 */
public class Color implements Cloneable{
	/**
	 * Black color	(float[])
	 */
	public static final float[] BLACK={0.f,0.f,0.f,1.f};
	/**
	 * White color (float[])
	 */
	public static final float[] WHITE={1.0f,1.0f,1.0f,1.0f};
	/**
	 * Red color (float[])
	 */
	public static final float[] RED={1.0f,0.0f,0.0f,1.0f};
	/**
	 * Yellow color (float[])
	 */
	public static final float[] YELLOW={1.0f,1.0f,0.0f,1.0f};
	/**
	 * Green color (float[])
	 */
	public static final float[] GREEN={0.0f,1.0f,0.0f,1.0f};
	/**
	 * Blue color (float[])
	 */
	public static final float[] BLUE={0.0f,0.0f,1.0f,1.0f};
	/**
	 * Purple color (float[])
	 */
	public static final float[] PURPLE={1.0f,0.0f,1.0f,1.0f};
	/**
	 * Cyan color (float[])
	 */
	public static final float[] CYAN={0.0f,1.0f,1.0f,1.0f};
	/**
	 * Transparent color (float[])
	 */
	public static final float[] TRANSPARENT={0.0f,0.0f,0.0f,0.0f};
	/**
	 * Red value [0,1]
	 */
	public float r;
	/**
	 * Green value [0,1]
	 */
	public float g;
	/**
	 * Blue value [0,1]
	 */
	public float b;
	/**
	 * Alpha value [0,1]
	 */
	public float a;
	/**
	 * Constructor. Default alpha = 1
	 */
	public Color()
	{
		this.a = 1;
	}
	/**
	 * Constructor
	 */
	public Color(Matrix color)
	{
		this.a = 1;
		if(color.getNumColumns()>=3)
		{
			this.r = Math.max(0.f,Math.min(1.0f,color.get(0, 0)));
			this.g = Math.max(0.f,Math.min(1.0f,color.get(0, 1)));
			this.b = Math.max(0.f,Math.min(1.0f,color.get(0, 2)));
			if(color.getNumColumns()>=4)
			{
				this.a = Math.max(0.f,Math.min(1.0f,color.get(0, 3)));
			}
		}else
		{
			if(color.getNumRows()>=3)
			{
				this.r = Math.max(0.f,Math.min(1.0f,color.get(0, 0)));
				this.g = Math.max(0.f,Math.min(1.0f,color.get(1, 0)));
				this.b = Math.max(0.f,Math.min(1.0f,color.get(2, 0)));
				if(color.getNumRows()>=4)
				{
					this.a = Math.max(0.f,Math.min(1.0f,color.get(3, 0)));
				}
			}else
			{
				Log.error("This matrix:"+color+" can't be converted to a color. This color will be (0,0,0,1)");
			}
		}
	}
	/**
	 * Constructor
	 */
	public Color(Color color)
	{
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}
	/**
	 * Constructor.
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 * @param a Alpha value
	 */
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.r = Math.max(0.f,Math.min(1.0f, this.r));
		this.g = Math.max(0.f,Math.min(1.0f, this.g));
		this.b = Math.max(0.f,Math.min(1.0f, this.b));
		this.a = Math.max(0.f,Math.min(1.0f, this.a));
	}
	/**
	 * Constructor.
	 * @param components float[red, green, blue, alpha]
	 */
	public Color(float[] components)
	{
		this.r = components[0];
		this.g = components[1];
		this.b = components[2];
		if(components.length>3)
			this.a = components[3];
	}
	/**
	 * Constructor
	 * Default alpha = 1
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 */
	public Color(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
		this.r = Math.max(0.f,Math.min(1.0f, this.r));
		this.g = Math.max(0.f,Math.min(1.0f, this.g));
		this.b = Math.max(0.f,Math.min(1.0f, this.b));
	}
	
	/**
	 * Indicates if color and this color are equals (same red, green, blue and alpha values)
	 * @return True or false
	 */
	public boolean equals(Color color)
	{
		return color.r == r && color.g == g && color.b == b && color.a == a;
	}
	/**
	 * Clone this color
	 * @return Cloned color
	 */
	@Override
	public Color clone()
	{
		return new Color(this);
	}
	/**
	 * Return the matrix of the color. It's a row matrix
	 * @return Matrix
	 */
	public Matrix getMatrix()
	{
		Matrix matrix = new Matrix(0, 4);
		matrix.set(0, 0, r);
		matrix.set(0, 1, g);
		matrix.set(0, 2, b);
		matrix.set(0, 3, a);
		return matrix;
	}
	/**
	 * Return the matrix of the color
	 * @param row If true returns a column matrix otherwise it returns a column matrix
	 * @return Matrix
	 */
	public Matrix getMatrix(boolean row)
	{
		if(row)
		{
			return getMatrix();
		}else
		{
			Matrix matrix = new Matrix(4, 0);
			matrix.set(0, 0, r);
			matrix.set(1, 0, g);
			matrix.set(2, 0, b);
			matrix.set(3, 0, a);
			return matrix;
		}
	}
	/**
	 * Sums with a color (this Color is modified)
	 * @param Color color
	 */
	public void add(Color color)
	{
		add(color.r, color.g, color.b, color.a);
	}
	/**
	 * Sums with a color 
	 * @param r Red value to add
	 * @param g Green value to add
	 * @param b Blue value to add
	 * @param a Alpha value to add
	 */
	public void add(float r, float g, float b, float a)
	{
		this.r = Math.max(0.f, Math.min(1.0f, r+this.r));
		this.g = Math.max(0.f, Math.min(1.0f, g+this.g));
		this.b = Math.max(0.f, Math.min(1.0f, b+this.b));
		this.a = Math.max(0.f, Math.min(1.0f, a+this.a));
	}
	/**
	 * Makes a composition between color and this color
	 * @param color Color
	 */
	public void comp(Color color)
	{
		this.r = color.r*this.r;
		this.g = color.g+this.g;
		this.b = color.b+this.b;
		this.a = color.a+this.a;
	}
	/**
	 * Makes a composition between color and this color
	 * @param color Color
	 */
	public void comp(int r, int g, int b, float a)
	{
		this.r = Math.max(0.f,Math.min(1.0f, r*this.r));
		this.g = Math.max(0.f,Math.min(1.0f, g+this.g));
		this.b = Math.max(0.f,Math.min(1.0f, b+this.b));
		this.a = Math.max(0.f,Math.min(1.0f, a+this.a));
	}
	/**
	 * Multiplies this color by a scalar
	 * @param scalar Scalar
	 */
	public void mul(float scalar)
	{
		this.r = Math.max(0.f, Math.min(1.0f, r * scalar));
		this.g = Math.max(0.f, Math.min(1.0f, g * scalar));
		this.b = Math.max(0.f, Math.min(1.0f, b * scalar));
		this.a = Math.max(0.f, Math.min(1.0f, a * scalar));
	}
	/**
	 * Makes a composition between two colors and return the new Color result
	 * @param color1 Color 1
	 * @param color2 Color 2
	 * @return Color created by composition between both colors
	 */
	public Color comp(Color color1, Color color2)
	{
		Color color = new Color(color1);
		color.comp(color2);
		return color;
	}
	/**
	 * Multiply a color by a scalar and return a new color
	 * @param color Color 
	 * @param scalar Scalar
	 * @return A new color created by the multiply of both parameters
	 */
	public static Color mul(Color color, float scalar)
	{
		color = new Color(color);
		color.mul(scalar);
		return color;
	}
	/**
	 * Sum two colors
	 * @param color1 Color one
	 * @param color2 Color two
	 * @return A new color created by the addition of both parameters
	 */
	public static Color sum(Color color1, Color color2)
	{
		Color color = new Color(color1);
		color.add(color2);
		return color;
	}
	/**
	 * Create a new color
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 * @param a Alpha valaue
	 * @return A new color
	 */
	public static Color create(int r, int g, int b, float a)
	{
		return new Color(r, g, b, a);
	}
	/**
	 * Create a new color
	 * @param color Color (float[])
	 */
	public static Color create(float[] color)
	{
		return new Color(color);
	}
	/**
	 * Create a new color
	 * @param color Matrix of a color
	 */
	public static Color create(Matrix color)
	{
		return new Color(color);
	}
}
