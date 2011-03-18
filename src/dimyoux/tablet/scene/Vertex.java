package dimyoux.tablet.scene;

public class Vertex {
	public float x,y,z;
	public float tx,ty;
	public float r,g,b,a;
	public Vertex()
	{
		//damn it was so cool...
		//x=y=z=tx=ty=r=g=b=a=0;
	}
	public void setPosition(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void setTextureCoord(float tx, float ty)
	{
		this.tx = tx;
		this.ty = ty;
	}
	public void setColor(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	public void setColor(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
