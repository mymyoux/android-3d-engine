package dimyoux.engine.utils.parsers;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import dimyoux.engine.managers.FileManager;
import dimyoux.engine.scene.Scene;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
//see http://paulbourke.net/dataformats/mtl/ for more details and complete the parser
/**
 * Material
 */
public class Material {
	/**
	 * Diffuse color
	 */
	public Color diffuseColor;
	/**
	 * Ambient color
	 */
	public Color ambientColor;
	/**
	 * Specular color
	 */
	public Color specularColor;
	/**
	 * Material's name
	 */
	public String name;
	/**
	 * Shininess
	 */
	public float shininess;
	/**
	 * Flag that indicates the use of the specular color
	 */
	public boolean useSpecularColor;
	/**
	 * Id of the texture file
	 */
	public int idTextureFile = 0;
	/**
	 * OpenGL index of the texture
	 */
	public int textureIndex = 0;
	/**
	 * Optical Density or index of refraction. 1.0f means there is no refraction.
	 */
	public float opticalDensity = 1.0f;
	/**
	 * Texture sent to openGL ?
	 */
	public boolean textureSent = false;
	/**
	 * Texture Bitmap
	 */
	public Bitmap textureBitmap;
	/**
	 * Indicates if the material has a diffuse color
	 * @return True or false
	 */
	public boolean hasDiffuseColor()
	{
		return diffuseColor != null;
	}
	/**
	 * Indicates if the material has a ambient color
	 * @return True or false
	 */
	public boolean hasAmbientColor()
	{
		return ambientColor != null;
	}
	/**
	 * Indicates if the material has a specular color
	 * @return True or false
	 */
	public boolean hasSpecularColor()
	{
		return specularColor != null;
	}
	/**
	 * Sends the texture to openGL
	 */
	public void sendTexture()
	{
		GL11 gl = Scene.gl;

		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		textureIndex = textures[0];

		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);


		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);


		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
		Log.warning(textureBitmap.getWidth());
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, textureBitmap, 0);
		textureSent = true;
		Log.warning("texture sended:"+textureIndex);
	}
	/**
	 * Returns a string containing a concise, human-readable description of this object.
	 */
	@Override
	public String toString()
	{
		String txt = "[Material";
		if(name != null)
		{
			txt+="["+name+"]";
		}
		if(hasAmbientColor())
		{
			txt+=" A("+ambientColor.r+";"+ambientColor.g+";"+ambientColor.b+";"+ambientColor.a+")";
		}
		if(hasDiffuseColor())
		{
			txt+=" D("+diffuseColor.r+";"+diffuseColor.g+";"+diffuseColor.b+";"+diffuseColor.a+")";
		}
		if(hasSpecularColor())
		{
			txt+=" S("+specularColor.r+";"+specularColor.g+";"+specularColor.b+";"+specularColor.a+")";
		}
		if(shininess > 0)
		{
			txt+=" shi=\""+shininess+"\"";
		}
		if(opticalDensity > 0)
		{
			txt+=" refract=\""+opticalDensity+"\"";
		}
		if(useSpecularColor)
		{
			txt+=" useSpecular=\""+(useSpecularColor?"true":"false")+"\"";
		}
		if(idTextureFile > 0)
		{
			txt+=" file=\""+FileManager.getInstance().getFileName(idTextureFile)+"\"";
		}
		return txt+"]";
	}
	/*
	 * Static Part
	 */
	/**
	 * Materials Library
	 */
	private static Map<String, Material> materials = new HashMap<String, Material>();
	/**
	 * Defines a new material
	 */
	private static final String materialName = "newmtl";
	/**
	 * Defines material shininess
	 */
	private static final String materialShininess = "Ns";
	/**
	 * Defines material ambient color
	 */
	private static final String materialAmbientColor = "Ka";
	/**
	 * Defines material diffuse color
	 */
	private static final String materialDiffuseColor = "Kd";
	/**
	 * Defines material specular color
	 */
	private static final String materialSpecularColor = "Ks";
	/**
	 * Defines material alpha
	 */
	private static final String materialAlpha = "d";
	/**
	 * Defines material alpha
	 */
	private static final String materialAlpha2 = "Tr";
	/**
	 * Defines material illumination model
	 */
	private static final String materialIlluminationModel = "illum";
	/**
	 * Defines material texture
	 */
	private static final String materialTexture = "map_Kd";
	/**
	 * Defines material optical density
	 */
	private static final String materialOpticalDensity = "Ni";
	/**
	 * Ignore caracter
	 */
	private static final String ignore = "#";
	/**
	 * Indicates if the material is in the library
	 * @param name Name of material (file)
	 * @return True or false
	 */
	public static boolean hasMaterial(String name)
	{
		return materials.containsKey(name);
	}
	/**
	 * Return a Material if the name is correct
	 * @param name Name of the material (file)
	 * @return Material
	 */
	public static Material getMaterial(String name)
	{
		return Material.parse(name);
	}
	/**
	 * Parses a material
	 * @param name File name or material name
	 * @return A material if the file is correct
	 */
	public static Material parse(String name)
	{
		if(hasMaterial(name))
		{
			return materials.get(name);
		}
		int id = FileManager.getInstance().getFileID(name);
		if(id>0)
		{
			return Material.parse(id);
		}
		return null;
	}
	/**
	 * Adds a material to the list
	 * @param material Material
	 */
	public static void addMaterial(Material material)
	{
		if(!materials.containsKey(material.name))
		{
			materials.put(material.name, material);
		}
	}
	/**
	 * Parses a material
	 * @param id File id
	 * @return A material if the file is correct
	 */
	public static Material parse(int id)
	{
		try
		{
			BufferedReader buffer = FileManager.getInstance().openTextFile(id);
			Log.verbose("Material file found : "+FileManager.getInstance().getFileName(id));
			String line;
			String[] values;
			Material material = new Material();
			float alpha = 1.0f;
			while((line = buffer.readLine()) != null)
			{
				values = line.split(" ");
				if(values[0].equals(ignore))
				{
					continue;
				}
				if(values[0].equals(materialName))
				{
					material = new Material();
					material.name = values[1];
					if(materials.containsKey(material.name))
					{
						Log.verbose("Material["+material.name+"] already exists");
						return materials.get(material.name);
					}
					materials.put(values[1], material);
				}
				if(values[0].equals(materialShininess))
				{
					material.shininess = Float.parseFloat(values[1]);
				}
				if(values[0].equals(materialShininess))
				{
					material.shininess = Float.parseFloat(values[1]);
				}
				if(values[0].equals(materialAmbientColor))
				{
					material.ambientColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialDiffuseColor))
				{
					material.diffuseColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialSpecularColor))
				{
					material.specularColor = new Color(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), alpha);
				}
				if(values[0].equals(materialAlpha) || values[0].equals(materialAlpha2))
				{
					alpha = Float.parseFloat(values[1]);
					if(material.hasAmbientColor())
					{
						material.ambientColor.a = alpha;
					}
					if(material.hasDiffuseColor())
					{
						material.diffuseColor.a = alpha;
					}
					if(material.hasSpecularColor())
					{
						material.specularColor.a = alpha;
					}
				}
				if(values[0].equals(materialIlluminationModel))
				{
					material.useSpecularColor = Integer.parseInt(values[1])==2;
				}
				if(values[0].equals(materialTexture))
				{
					Log.verbose("Load a texture file :"+values[1]);
					material.idTextureFile = FileManager.getInstance().getFileID(values[1]);
					material.textureBitmap = FileManager.getInstance().loadBitmap(material.idTextureFile);
				}
				if(values[0].equals(materialOpticalDensity))
				{
					material.opticalDensity = Float.parseFloat(values[1]);
				}
			}
			materials.put(material.name, material);
			return material;
		}catch(Exception e)
		{
			Log.error(e);
		}
		return null;
	}
}