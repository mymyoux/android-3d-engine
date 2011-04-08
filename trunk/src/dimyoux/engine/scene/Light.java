package dimyoux.engine.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import dimyoux.engine.opengl.GLConstants;
import dimyoux.engine.utils.Color;
import dimyoux.engine.utils.Log;
import dimyoux.engine.utils.math.Coord3D;
import dimyoux.engine.utils.math.Coord4D;

public class Light implements Serializable{
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Type of the light 
	 * (point, spot or directional)
	 */
	public enum LightType {
		POINT_LIGHT, DIRECTIONAL_LIGHT, SPOT_LIGHT
	}
	
	// Lights of the scene [static]
	static private List<Light> lights = new ArrayList<Light> ();
	static private int lightsCount = 0;
	
	// Number of the light
	private int numLight = -1;
	
	// Type of the light
	private LightType lightType = LightType.POINT_LIGHT;
	
	// Light parameters (for point and directional lights)
	private float[] position;
	private float[] ambient;
	private float[] diffuse;
	private float[] specular;
	
	// Light parameters (for spot lights)
	private float[] spotDirection;
	private boolean hasSpotCutoff = false;
	private float spotCutoff;
	private boolean hasSpotExponent = false;
	private float spotExponent;
	
	
	/**
	 * Default constructor
	 */
	private Light() {
		numLight = getGLNumber();
		
		// position.w = 1 : point light
		// position.w = 0 : directional light
		position = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
		ambient  = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
		diffuse  = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
		specular = new float[] {1.0f, 1.0f, 1.0f, 1.0f};
	}	
	
	/**
	 * Constructor
	 * @param position position of the light
	 * @param ambient ambient component of the light
	 * @param diffuse diffuse component of the light
	 * @param specular specular component of the light
	 */
	private Light(Coord4D position, Color ambient, Color diffuse, Color specular) {
		numLight = getGLNumber();
		
		this.position = position.toFloatArray();
		this.ambient  = ambient.toFloatArray();
		this.diffuse  = diffuse.toFloatArray();
		this.specular = specular.toFloatArray();
	}
	
	/**
	 * Constructor
	 * @param position position of the light
	 * @param ambient ambient component of the light
	 * @param diffuse diffuse component of the light
	 * @param specular specular component of the light
	 * @param spotDirection direction of the spot light
	 * @param spotCutOff cut off of the spot light
	 * @param spotExponent exponent of the spot light
	 */
	private Light(
			Coord4D position, 
			Color ambient, 
			Color diffuse, 
			Color specular,
			Coord3D spotDirection,
			float spotCutOff,
			float spotExponent) {
		numLight = getGLNumber();
		
		this.position = position.toFloatArray();
		this.ambient  = ambient.toFloatArray();
		this.diffuse  = diffuse.toFloatArray();
		this.specular = specular.toFloatArray();
		
		this.spotDirection = spotDirection.toFloatArray();
		
		this.hasSpotCutoff = true;
		this.spotCutoff = spotCutOff;
		
		this.hasSpotExponent = true;
		this.spotExponent = spotExponent;
	}
	
	/**
	 * Returns the OpenGL number of the new light
	 * @return the light number
	 */
	private static int getGLNumber() {
		if(lightsCount < GLConstants.MAX_LIGHTS) {
			lightsCount++;
			switch(lightsCount) {
			case 1:
				return GL10.GL_LIGHT0;
			case 2:
				return GL10.GL_LIGHT1;
			case 3:
				return GL10.GL_LIGHT2;
			case 4:
				return GL10.GL_LIGHT3;
			case 5:
				return GL10.GL_LIGHT4;
			case 6:
				return GL10.GL_LIGHT5;
			case 7:
				return GL10.GL_LIGHT6;
			case 8:
				return GL10.GL_LIGHT7;
			}
		}
		Log.warning("The maximum number of lights are already created");
		return -1;
	}
	
	/** Getters/setters **/
	
	/**
	 * Returns the current light number
	 * @return the current light number
	 */
	public int getNumber() {
		return numLight;
	}
	
	/**
	 * Returns the type of the light
	 * @return the type of the light
	 */
	public LightType getType() {
		return lightType;
	}
	
	/**
	 * Sets the type of the light
	 * @param type type of the light
	 */
	public void setType(LightType type) {
		lightType = type;
		
		if (position != null) {
			if (lightType == LightType.DIRECTIONAL_LIGHT)
				position[3] = 0; // w
			else
				position[3] = 1; // w
		}
		
		if (lightType == LightType.SPOT_LIGHT) {
			if (spotDirection == null)
				spotDirection = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
			
			hasSpotCutoff = true;
			spotCutoff = 0.0f;
			
			hasSpotExponent = true;
			spotExponent = 0.0f;
		}
	}
	
	/**
	 * Returns the position of the light
	 * @return the position of the light
	 */
	public float[] getPosition() {
		return position;
	}
	
	/**
	 * Sets the position of the light
	 * @param pos
	 */
	public void setPosition(Coord3D pos) {
		position[0] = pos.x;
		position[1] = pos.y;
		position[2] = pos.z;
		enable(this);
	}
	
	/** 
	 * Returns the ambient component of the light
	 * @return the ambient component of the light
	 */
	public float[] getAmbient() {
		return ambient;
	}
	
	/**
	 * Sets the ambient component of the light
	 * @param col color of the ambient component
	 */
	public void setAmbient(Color col) {
		ambient[0] = col.r;
		ambient[1] = col.g;
		ambient[2] = col.b;
		ambient[3] = col.a;
		enable(this);
	}

	/** 
	 * Returns the diffuse component of the light
	 * @return the diffuse component of the light
	 */
	public float[] getDiffuse() {
		return diffuse;
	}
	
	/**
	 * Sets the diffuse component of the light
	 * @param col color of the diffuse component
	 */
	public void setDiffuse(Color col) {
		diffuse[0] = col.r;
		diffuse[1] = col.g;
		diffuse[2] = col.b;
		diffuse[3] = col.a;
		enable(this);
	}
	
	/** 
	 * Returns the specular component of the light
	 * @return the specular component of the light
	 */
	public float[] getSpecular() {
		return specular;
	}
	
	/**
	 * Sets the specular component of the light
	 * @param col color of the specular component
	 */
	public void setSpecular(Color col) {
		specular[0] = col.r;
		specular[1] = col.g;
		specular[2] = col.b;
		specular[3] = col.a;
		enable(this);
	}
	
	/**
	 * Returns the spot direction
	 * @return the spot direction
	 */
	public float[] getSpotDirection() {
		return spotDirection;
	}
	
	/**
	 * Sets the spot direction
	 * @param direction direction of the spot light
	 */
	public void setSpotDirection(Coord3D direction) {
		spotDirection = direction.toFloatArray();	
		enable(this);
	}
	
	/**
	 * Returns the spot light cutoff
	 * @return the spot light cutoff
	 */
	public float getSpotCutoff() {
		return spotCutoff;
	}
	
	/**
	 * Indicates if the spot light has spot cutoff
	 * @return true if it has, false otherwise
	 */
	public boolean hasSpotCutoff() {
		return hasSpotCutoff;
	}
	
	/**
	 * Sets the spot light cutoff
	 * @param cutoff the spot light cutoff
	 */
	public void setSpotCutOff(float cutoff) {
		this.spotCutoff = cutoff;
		this.hasSpotCutoff = true;
		enable(this);
	}
	
	/**
	 * Returns the spot light exponent
	 * @return the spot light exponent
	 */
	public float getSpotExponent() {
		return spotExponent;
	}

	/**
	 * Indicates if the spot light has spot exponent
	 * @return true if it has, false otherwise
	 */
	public boolean hasSpotExponent() {
		return hasSpotExponent;
	}
	
	/**
	 * Sets the spot light exponent
	 * @param exp the spot light exponent
	 */
	public void setSpotExponent(float exp) {
		this.spotExponent = exp;
		this.hasSpotExponent = true;
		enable(this);
	}
	
	/** Static functions **/
	
	/**
	 * Adds a new light with default settings
	 * (a white and shiny point light at {0, 0, 0})
	 */
	static public void addLight() {
		lights.add(new Light());
	}
	
	/**
	 * Adds a default light of the given type
	 * @param lightType the type of the light (point, spot or directional)
	 */
	static public void addLight(LightType lightType) {
		int w = (lightType == LightType.DIRECTIONAL_LIGHT) ? 0 : 1;
		
		Light l = new Light(
				new Coord4D(0, 0, 0, w), // position 
				Color.WHITE,   // ambient
				Color.WHITE,   // diffuse
				Color.WHITE);  // specular
		
		lights.add(l);
	}
	
	/**
	 * Adds a light of the given type and with the given settings
	 * @param lightType the type of the light (point, spot or directional)
	 * @param position position of the light
	 * @param ambient ambient component of the light
	 * @param diffuse diffuse component of the light
	 * @param specular specular component of the light
	 */
	static public void addLight(
			LightType lightType, 
			Coord3D pos, 
			Color ambient, 
			Color diffuse, 
			Color specular) {
		
		int w = (lightType == LightType.DIRECTIONAL_LIGHT) ? 0 : 1;
		
		Light l = new Light(
				new Coord4D(pos.x, pos.y, pos.z, w), // position 
				ambient,
				diffuse,
				specular);
		
		lights.add(l);
	}
	
	/**
	 * Adds a new light with defined settings
	 * @param position position of the light
	 * @param ambient ambient component of the light
	 * @param diffuse diffuse component of the light
	 * @param specular specular component of the light
	 */
	static public void addLight(Coord4D position, Color ambient, Color diffuse, Color specular) {
		lights.add(new Light(position, ambient, diffuse, specular));
	}
	
	/**
	 * Removes the specified light
	 * @param index index of the light to be removed
	 */
	static public void removeLight(int index) {
		disable(index);
		lights.remove(index);
		lightsCount--;
	}
	
	/**
	 * Removes all the light
	 */
	static public void removeAll() {
		disable();
		lights.clear();
		lightsCount = 0;
	}
	
	/**
	 * Returns the number of lights attached to the scene
	 * @return the number of lights attached to the scene
	 */
	static public int getLightCount() {
		return lights.size();
	}

	/**
	 * Enables all the lights added to the scene
	 * @param gl the OpenGL context
	 */
	static public void enable() {
		GL11 gl = Scene.gl;
		
		for (Light l : lights) {
			int glLight = l.getNumber();
			
			if(l.getPosition() != null) {
				gl.glLightfv(glLight, GL10.GL_POSITION, l.getPosition(), 0);
			}
			if(l.getAmbient() != null) {
				gl.glLightfv(glLight, GL10.GL_AMBIENT, l.getAmbient(), 0);
			}
			if(l.getDiffuse() != null) {
				gl.glLightfv(glLight, GL10.GL_SPECULAR, l.getDiffuse(), 0);
			}
			if(l.getSpecular() != null) {
				gl.glLightfv(glLight, GL10.GL_DIFFUSE, l.getSpecular(), 0);
			}
			
			if(l.getSpotDirection() != null) {
				gl.glLightfv(glLight, GL10.GL_SPOT_DIRECTION, l.getSpotDirection(), 0);
			}
			if(l.hasSpotCutoff()) {
				gl.glLightf(glLight, GL10.GL_SPOT_CUTOFF, l.getSpotCutoff());
			}
			if(l.hasSpotExponent()) {
				gl.glLightf(glLight, GL10.GL_SPOT_EXPONENT, l.getSpotExponent());
			}
			
			// Light activation
			gl.glEnable(glLight);	
		}
	}
	/**
	 * Enables the specified light
	 * @param gl the OpenGL context
	 * @param id the number of the light
	 */
	static public void enable(int id) 
	{
		if (id < 0 || id >= lights.size()) {
			Log.error("Incorrect light number");
			return;
		}
		enable(lights.get(id));
	}
	/**
	 * Enables the specified light
	 * @param gl the OpenGL context
	 * @param light Light
	 */
	static public void enable(Light light) {
		GL11 gl = Scene.gl;
		
		int glLight = light.getNumber();
		
		if(light.getPosition() != null) {
			gl.glLightfv(glLight, GL10.GL_POSITION, light.getPosition(), 0);
		}
		if(light.getAmbient() != null) {
			gl.glLightfv(glLight, GL10.GL_AMBIENT, light.getAmbient(), 0);
		}
		if(light.getDiffuse() != null) {
			gl.glLightfv(glLight, GL10.GL_SPECULAR, light.getDiffuse(), 0);
		}
		if(light.getSpecular() != null) {
			gl.glLightfv(glLight, GL10.GL_DIFFUSE, light.getSpecular(), 0);
		}
		
		if(light.getSpotDirection() != null) {
			gl.glLightfv(glLight, GL10.GL_SPOT_DIRECTION, light.getSpotDirection(), 0);
		}
		if(light.hasSpotCutoff()) {
			gl.glLightf(glLight, GL10.GL_SPOT_CUTOFF, light.getSpotCutoff());
		}
		if(light.hasSpotExponent()) {
			gl.glLightf(glLight, GL10.GL_SPOT_EXPONENT, light.getSpotExponent());
		}
		
		gl.glEnable(glLight);
	}
	
	/**
	 * Disables all the lights attached to the scene
	 * @param gl the OpenGL context
	 */
	static public void disable() {
		GL11 gl = Scene.gl;
		
		for (Light l : lights) {
			gl.glDisable(l.getNumber());
		}
	}
	
	/**
	 * Disables a specified light using its number
	 * @param gl the OpenGL context
	 * @param numLight the number of the light
	 */
	static public void disable(int numLight) {
		if (numLight < 0 || numLight >= lights.size()) {
			Log.error("Incorrect light number");
			return;
		}
		
		GL11 gl = Scene.gl;
		
		Light l = lights.get(numLight);
		gl.glDisable(l.getNumber());
	}
	/**
	 * Return a light
	 * @param id Light id
	 * @return Light id or null
	 */
	static public Light getLight(int id)
	{
		if(lights.size()>id)
		{
			return lights.get(id);
		}
		return null;
	}
	/**
	 * Return all lights
	 * @return Lights
	 */
	static public List<Light> getAllLight()
	{
		return lights;
	}
}
