package dimyoux.engine.utils.parsers;

import dimyoux.engine.utils.Log;

public class Face
{
	private int length;
	public int[] vertices;
	public int[] textVertices;
	public int[] normalVertices;
	public String material;
	public boolean hasVertices = false;
	public boolean hasTextVertices = false;
	public boolean hasNormalVertices = false;
	public Face(String face, String material)
	{
		int i, id;
		String f;
		String[] faces = face.split(" ");
		String[] faceCoords;
		vertices = new int[faces.length];
		textVertices = new int[faces.length];
		normalVertices = new int[faces.length];
		length = faces.length;
		for(id=0; id<faces.length; id++)
		{
			f = faces[id];
			faceCoords = f.split("/");
			for(i=0; i<faceCoords.length; i++)
			{
				if(i==0)
				{
					try
					{
						vertices[id] = Integer.parseInt(faceCoords[i]);
						hasVertices = true;
					}
					catch(NumberFormatException e)
					{
						Log.warning(e);
					}
				}else
				{
					if(i==1)
					{
						try
						{
							textVertices[id] = Integer.parseInt(faceCoords[i]);	
							hasTextVertices = true;
						}
						catch(NumberFormatException e)
						{
							Log.warning(e);
						}
						
					}else
					{
						if(i==2)
						{
							try
							{
								normalVertices[id] = Integer.parseInt(faceCoords[i]);	
								hasNormalVertices = true;
							}
							catch(NumberFormatException e)
							{
								Log.warning(e);
							}								
						}
					}
				}
			}
		}
		this.material = material;
	}
	public Face(int[] vertices, int[] textVertices, int[] normalVertices, String material)
	{
		length = vertices.length;
		if(vertices!=null)
		{
			this.vertices = vertices.clone();
		}
		if(textVertices != null)
		{
			this.textVertices = textVertices.clone();
		}
		if(normalVertices != null)
		{
			this.normalVertices = normalVertices.clone();
		}
		this.material = material;
	}
	@Override
	public String toString()
	{
		String txt = "[Face";
		if(hasVertices)
		{
			txt+=" V=\""+vertices.length+"\"";
		}
		if(hasTextVertices)
		{
			txt+=" T=\""+textVertices.length+"\"";
		}
		if(hasNormalVertices)
		{
			txt+=" N=\""+normalVertices.length+"\"";
		}
		if(material != null)
		{
			txt+=" mat=\""+material+"\"";
		}
		return txt+"]";
	}
	public int size()
	{
		return length;
	}
}