package dimyoux.engine.utils.parsers;

import java.util.ArrayList;
import java.util.List;

import dimyoux.engine.scene.Mesh;
import dimyoux.engine.utils.Buffer;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord2D;
import dimyoux.engine.utils.math.Coord3D;
/**
 * Mesh builder
 * It's a "pre-mesh" that can be use has a constructor of mesh
 * It's easier to build
 *
 */
public class MeshBuilder
{
	/**
	 * Vertices List
	 */
	public List<Coord3D> vertices;
	/**
	 * Normals List
	 */
	public List<Coord3D> normals;
	/**
	 * Texture coordinates List
	 */
	public List<Coord2D> textureCoordinates;
	/**
	 * Colors List
	 */
	public List<Color> colors;
	/**
	 * Facs List
	 */
	public List<Face> faces;
	/**
	 * Name
	 */
	public String name;
	/**
	 * Material
	 */
	public Material material;
	/**
	 * Translation from normalization
	 */
	protected Coord3D translation;
	/**
	 * Constructor
	 */
	public MeshBuilder()
	{
		vertices = new ArrayList<Coord3D>();
		normals = new ArrayList<Coord3D>();
		textureCoordinates = new ArrayList<Coord2D>();
		faces = new ArrayList<Face>();
		colors = new ArrayList<Color>();
		translation = new Coord3D();
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String txt = "[Mesh";
		if(name != null)
		{
			txt+="["+name+"]";
		}
		if(vertices.size()>0)
		{
			txt+=" V=\""+vertices.size()+"\"";
		}
		if(normals.size()>0)
		{
			txt+=" N=\""+normals.size()+"\"";
		}
		if(textureCoordinates.size()>0)
		{
			txt+=" T=\""+textureCoordinates.size()+"\"";
		}
		if(faces.size()>0)
		{
			txt+=" F=\""+faces.size()+"\"";
		}
		if(material != null)
		{
			txt+=" Mat=\""+material+"\"";
		}
		return txt+"]";
	}
	/**
	 * Converts this MeshBuilder to a Mesh
	 * @return Mesh
	 */
	/*
	public Mesh toMesh()
	{
		Mesh mesh = new Mesh();
		List<Coord3D> nFaces = new ArrayList<Coord3D>();
		List<Coord3D> nVertex = new ArrayList<Coord3D>();
		List<Coord2D> nTextCoord = new ArrayList<Coord2D>();
		List<Coord3D> nNormals = new ArrayList<Coord3D>();
		
		int i;
		int index = 0;
		if(textureCoordinates.size()>0)
		{
			mesh.texCoordsSize = textureCoordinates.get(0).size();
		}
		if(faces.size()>0)
		{
			for(final Face face : faces)
			{
				for(i=0; i< face.size(); i++)
				{
					if(face.hasVertices)
					{
						
						nVertex.add(vertices.get(face.vertices[i]-1));
					}
					if(face.hasNormalVertices)
					{
						Log.info(i+"/"+normals.size()+" => "+face.normalVertices[i]+"/"+face.normalVertices.length);
						nNormals.add(normals.get(face.normalVertices[i]-1));
					}
					if(face.hasTextVertices)
					{
						nTextCoord.add(textureCoordinates.get(face.textVertices[i]-1));
					}
				}
				if(face.size() == 3)
				{
					nFaces.add(new Coord3D(index, index+1, index+2));
				}else
				{
					if(face.size()==4)
					{
						nFaces.add(new Coord3D(index, index+1, index+3));
						nFaces.add(new Coord3D(index+1, index+2, index+3));
					}
				}
				index+=face.size();				
			}
			mesh.indexesBuffer = Buffer.createShortBuffer(index);
			mesh.verticesBuffer = Buffer.createFloatBuffer(index*3);
			if(this.normals.size()>0)
			{
				mesh.normalsBuffer = Buffer.createFloatBuffer(index*3);
			}
			if(this.textureCoordinates.size()>0)
			{
				mesh.texCoordsBuffer = Buffer.createFloatBuffer(index*mesh.texCoordsSize);
			}
			for(final Coord3D face : nFaces)
			{
					mesh.indexesBuffer.put((short)face.x);
					mesh.indexesBuffer.put((short)face.y);
					mesh.indexesBuffer.put((short)face.z);
			}
			for(final Coord3D vertex : nVertex)
			{
					mesh.verticesBuffer.put(vertex.x);
					mesh.verticesBuffer.put(vertex.y);
					mesh.verticesBuffer.put(vertex.z);
			}
			for(final Coord3D normal : nNormals)
			{
					mesh.normalsBuffer.put(normal.x);
					mesh.normalsBuffer.put(normal.y);
					mesh.normalsBuffer.put(normal.z);
			}
			for(final Coord2D text : nTextCoord)
			{
					mesh.texCoordsBuffer.put(text.x);
					mesh.texCoordsBuffer.put(text.y);
					if(mesh.texCoordsSize>2)
					{
						mesh.texCoordsBuffer.put(((Coord3D)text).z);
					}
			}
		}
		mesh.indexesBuffer.position(0);
		if(this.normals.size()>0)
		{
			mesh.normalsBuffer.position(0);
		}
		if(this.textureCoordinates.size()>0)
		{
			mesh.texCoordsBuffer.position(0);
		}
		mesh.verticesBuffer.position(0);
		
		mesh.currentMaterial = this.material;
		mesh.name = this.name;
		Log.info(mesh.currentMaterial.textureBitmap);
		Log.info(mesh.indexesBuffer.get(0));
		return mesh;
	}*/
	public Mesh toMesh()
	{
		Log.verbose("Buffering "+name);
		Mesh mesh = new Mesh();
		if(faces.size()>0)
		{
			mesh.indexesBuffer = Buffer.createShortBuffer(faces.size()*faces.get(0).size());
			if(vertices.size()>0)
			{
				mesh.verticesBuffer = Buffer.createFloatBuffer(vertices.size()*vertices.get(0).size());
			}
			if(this.normals.size()>0)
			{
				mesh.normalsBuffer = Buffer.createFloatBuffer(normals.size()*normals.get(0).size());
			}
			if(this.textureCoordinates.size()>0)
			{
				mesh.texCoordsBuffer = Buffer.createFloatBuffer(textureCoordinates.size()*textureCoordinates.get(0).size());
			}
			if(this.colors.size()>0)
			{
				mesh.colorsBuffer = Buffer.createFloatBuffer(colors.size()*4);
			}
			for(final Face face : faces)
			{
				for(Integer index : face.vertices)
				{
				/*	mesh.indexesBuffer.put((short)face.x);
					mesh.indexesBuffer.put((short)face.y);
					mesh.indexesBuffer.put((short)face.z);*/
					mesh.indexesBuffer.put((short)((int)index));
				}
			}
			for(final Coord3D vertex : vertices)
			{
					mesh.verticesBuffer.put(vertex.x);
					mesh.verticesBuffer.put(vertex.y);
					mesh.verticesBuffer.put(vertex.z);
			}
			for(final Coord3D normal : normals)
			{
					mesh.normalsBuffer.put(normal.x);
					mesh.normalsBuffer.put(normal.y);
					mesh.normalsBuffer.put(normal.z);
			}
			for(final Coord2D text : textureCoordinates)
			{
					mesh.texCoordsBuffer.put(text.x);
					mesh.texCoordsBuffer.put(text.y);
					if(mesh.texCoordsSize>2)
					{
						mesh.texCoordsBuffer.put(((Coord3D)text).z);
					}
			}
			mesh.indexesBuffer.position(0);
			if(this.normals.size()>0)
			{
				mesh.normalsBuffer.position(0);
			}
			if(this.textureCoordinates.size()>0)
			{
				mesh.texCoordsBuffer.position(0);
			}
			mesh.verticesBuffer.position(0);
			
			mesh.setCurrentMaterial(this.material);
			mesh.name = this.name;			
		}
		return mesh;
	}
	/**
	 * Translates the mesh to (0, 0, 0)
	 */
	public void normalize()
	{
		Log.verbose("normalization");
		float x = 0, y = 0, z = 0;
		if(vertices != null && vertices.size()>0)
		{
			for(final Coord3D coord : vertices)
			{
				x += coord.x;
				y += coord.y;
				z += coord.z;
			}
			x/=vertices.size();
			y/=vertices.size();
			z/=vertices.size();
			for(Coord3D coord : vertices)
			{
				coord.x -= x;
				coord.y -= y;
				coord.z -= z;
			}
			translation.x = x;
			translation.y = y;
			translation.z = z;
		}
	}
	//TODO:optimize function
	public void optimize()
	{
		/*
		List<Coord4D> quadruplets = new ArrayList<Coord4D>();
		Coord4D quadruplet;
		for(final Face face : faces)
		{
			for(final Integer i : face.vertices)
			{
				quadruplet = new Coord4D(-1, -1, 1, -1);
				quadruplet.x = i;
				if(face.hasTexturesCoordinates())
				{
					quadruplet.y = face.textures.get(i);
				}
				if(face.hasNormals())
				{
					quadruplet.z = face.normals.get(i);
				}
				if(face.hasTexturesCoordinates())
				{
					quadruplet.w = face.colors.get(i);
				}
			}
		}*/
	}
	/**
	 * Indicates if the building mesh has correct indexes
	 * @return True or false
	 */
	public boolean isCorrect()
	{
		boolean correct = false;
		for(final Face face : faces)
		{
			for(final Integer index : face.vertices)
			{
				if(index == 0)
				{
					return true;
				}
			}
		}
		return correct;
	}
}