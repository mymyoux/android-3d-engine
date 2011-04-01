package dimyoux.engine.utils.parsers;

import java.util.ArrayList;
import java.util.List;

import dimyoux.engine.utils.Log;
/**
 * Face object
 *
 */
public class Face
{
	/**
	 * Vertices
	 */
	public List<Integer> vertices;
	/**
	 * Normals
	 */
	public List<Integer> normals;
	/**
	 * Textures
	 */
	public List<Integer> textures;
	/**
	 * Colors
	 */
	public List<Integer> colors;
	/**
	 * Constructor
	 * @param face "v[/t][/n][/c] v2..."
	 */
	public Face(String face)
	{
		int id;
		String[] metaVertices = face.split(" ");
		vertices = new ArrayList<Integer>();
		normals = new ArrayList<Integer>();
		textures = new ArrayList<Integer>();
		colors = new ArrayList<Integer>();
		//foreach "metaVertex"
		for(id=0; id<metaVertices.length; id++)
		{
			add(metaVertices[id]);
		}
	}
	/**
	 * Adds a metaVertex to the face
	 * @param metaVertex MetaVertex : "v[/t][/n][/c]"
	 */
	public void add(String metaVertex)
	{
		String[] metaVertexParts;
		int i;
		int[] indices = new int[4];
		for(i=0; i<4; i++)
		{
			indices[i] = -1;
		}
		metaVertexParts = metaVertex.split("/");		
		for(i=0; i<metaVertexParts.length; i++)
		{
			try
			{
				if(metaVertexParts[i].length()>0)
				{
					indices[i] = Integer.parseInt(metaVertexParts[i]);
				}
			}
			catch(NumberFormatException e)
			{
				Log.warning(e);
			}
		}
		add(indices);
	}
	/**
	 * Adds a metaVertex to the face
	 * @param vertexIndice Vertex Indice (-1 if it doesn't exist)
	 * @param textureIndice Texture Coordinates Indice (-1 if it doesn't exist)
	 * @param normalIndice Normal Indice (-1 if it doesn't exist)
	 * @param colorIndice Color Indice (-1 if it doesn't exist)
	 */
	public void add(int vertexIndice, int textureIndice, int normalIndice, int colorIndice)
	{
		if(vertexIndice>-1)
		{
			vertices.add(vertexIndice);
		}
		if(textureIndice>-1)
		{
			textures.add(textureIndice);
		}
		if(normalIndice>-1)
		{
			normals.add(normalIndice);
		}
		if(colorIndice>-1)
		{
			colors.add(colorIndice);
		}
	}
	/**
	 * Adds a metaVertex to the face
	 * @param indices Indices [vertex, texture, normal, color] if a value is equal to -1, this element is ignored
	 */
	public void add(int[] indices)
	{
		int indexVertice, indexTexture, indexNormal, indexColor;
		indexVertice = indexTexture = indexNormal = indexColor = -1;
		if(indices.length>0)
		{
			indexVertice = indices[0];
			if(indices.length>1)
			{
				indexTexture = indices[1];
				if(indices.length>2)
				{
					indexNormal = indices[2];
					if(indices.length>3)
					{
						indexColor = indices[3];
					}
				}
			}
		}
		add(indexVertice, indexTexture, indexNormal, indexColor);
	}
	/**
	 * Indicates if this face contains vertices indexes
	 * @return True or false
	 */
	public boolean hasVertices()
	{
		return vertices.size() > 0;
	}
	/**
	 * Indicates if this face contains textures coordinates indexes
	 * @return True or false
	 */
	public boolean hasTexturesCoordinates()
	{
		return textures.size() > 0;
	}
	/**
	 * Indicates if this face contains normals indexes
	 * @return True or false
	 */
	public boolean hasNormals()
	{
		return normals.size() > 0;
	}
	/**
	 * Indicates if this face contains colors indexes
	 * @return True or false
	 */
	public boolean hasColors()
	{
		return colors.size() > 0;
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String txt = "[Face";
		if(hasVertices())
		{
			txt+=" V=\""+vertices.size()+"\"";
		}
		if(hasTexturesCoordinates())
		{
			txt+=" T=\""+textures.size()+"\"";
		}
		if(hasNormals())
		{
			txt+=" N=\""+normals.size()+"\"";
		}
		if(hasColors())
		{
			txt+=" C=\""+colors.size()+"\"";
		}
		return txt+"]";
	}
	/**
	 * Return the number of MetaVertex of the face
	 * @return Size
	 */
	public int size()
	{
		return vertices.size();
	}
}