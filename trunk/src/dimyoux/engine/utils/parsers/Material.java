package dimyoux.engine.utils.parsers;

import dimyoux.engine.managers.FileManager;
import dimyoux.engine.utils.Color;
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
	 * Optical Density or index of refraction. 1.0f means there is no refraction.
	 */
	public float opticalDensity = 1.0f;
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
}