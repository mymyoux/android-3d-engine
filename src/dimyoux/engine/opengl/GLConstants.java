package dimyoux.engine.opengl;

import java.nio.IntBuffer;

import android.opengl.GLES10;
import android.opengl.GLES11;
import android.opengl.GLES20;

import dimyoux.engine.utils.Buffer;
/**
 * Calculated GL Constants
 * 
 */
public class GLConstants {
	//source : http://www.kludx.com/browse_capabilities.php
	/**
	 * The recommended maximum number of vertex array indices
	 */
	public static int MAX_ELEMENTS_INDICES = 0;
	/**
	 * The recommended maximum number of vertex array vertices
	 */
	public static int MAX_ELEMENTS_VERTICES = 0;
	/**
	 * Max number of lights
	 */
	public static int MAX_LIGHTS = 0;	
	/**
	 * The maximum supported depth of the modelview matrix stack
	 */
	public static int MAX_MODELVIEW_STACK_DEPTH = 0;
	/**
	 * The maximum supported depth of the projection matrix stack
	 */
	public static int MAX_PROJECTION_STACK_DEPTH = 0;	
	/**
	 * The value gives a rough estimate of the largest texture that the GL can handle
	 */
	public static int MAX_TEXTURE_SIZE = 0;	
	/**
	 * The maximum supported depth of the texture matrix stack
	 */
	public static int MAX_TEXTURE_STACK_DEPTH = 0;	
	/**
	 * Indicating the number of conventional texture units supported
	 */
	public static int MAX_TEXTURE_UNITS = 0;	
	/**
	 * The maximum supported width and height of the viewport
	 */
	public static int MAX_VIEWPORT_DIMS = 0;
	/**
	 * The maximum number of application-defined clipping planes
	 */
	public static int MAX_CLIP_PLANES = 0;
	/**
	 * The smallest supported widths for aliased lines
	 */
	public static int ALIASED_LINE_WIDTH_RANGE_MIN = 0;
	/**
	 * The largest supported widths for aliased lines
	 */
	public static int ALIASED_LINE_WIDTH_RANGE_MAX = 0;
	/**
	 * The smallest supported widths for antialiased lines
	 */
	public static int SMOOTH_LINE_WIDTH_RANGE_MIN = 0;
	/**
	 * The largest supported widths for antialiased lines
	 */
	public static int SMOOTH_LINE_WIDTH_RANGE_MAX = 0;
	/**
	 * The smallest supported sizes for aliased points
	 */
	public static int ALIASED_POINT_SIZE_RANGE_MIN = 0;
	/**
	 * The largest supported sizes for aliased points
	 */
	public static int ALIASED_POINT_SIZE_RANGE_MAX = 0;
	/**
	 * The smallest supported widths for antialiased points
	 */
	public static int SMOOTH_POINT_SIZE_RANGE_MIN = 0;
	/**
	 * The largest supported widths for antialiased points
	 */
	public static int SMOOTH_POINT_SIZE_RANGE_MAX = 0;
	/**
	 * The maximum supported texture image units that can be used to access texture maps from the vertex shader and the fragment processor combined
	 */
	public static int MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0;
	/**
	 * The value gives a rough estimate of the largest cube-map texture that the GL can handle
	 */
	public static int MAX_CUBE_MAP_TEXTURE_SIZE = 0;
	/**
	 * The maximum supported texture image units that can be used to access texture maps from the fragment shader
	 */
	public static int MAX_TEXTURE_IMAGE_UNITS = 0;
	/**
	 * Indicating the number of available compressed texture formats
	 */
	public static int NUM_COMPRESSED_TEXTURE_FORMATS = 0;
	/**
	 * The maximum number of 4-component generic vertex attributes accessible to a vertex shader
	 */
	public static int MAX_VERTEX_ATTRIBS = 0;
	/**
	 * The maximum supported texture image units that can be used to access texture maps from the vertex shader
	 */
	public static int MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0;
	/**
	 * Gets GL constants
	 */
	static public void getConstants()
	{
	    IntBuffer params =  Buffer.createIntBuffer(2);
	    //GLES10
	    GLES10.glGetIntegerv(GLES10.GL_MAX_ELEMENTS_INDICES, (IntBuffer)params.rewind());
	    MAX_ELEMENTS_INDICES = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_ELEMENTS_VERTICES, (IntBuffer)params.rewind());
	    MAX_ELEMENTS_VERTICES = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_LIGHTS, (IntBuffer)params.rewind());
	    MAX_LIGHTS = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_MODELVIEW_STACK_DEPTH, (IntBuffer)params.rewind());
	    MAX_MODELVIEW_STACK_DEPTH = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_PROJECTION_STACK_DEPTH, (IntBuffer)params.rewind());
	    MAX_PROJECTION_STACK_DEPTH = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, (IntBuffer)params.rewind());
	    MAX_TEXTURE_SIZE = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_STACK_DEPTH, (IntBuffer)params.rewind());
	    MAX_TEXTURE_STACK_DEPTH = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_UNITS, (IntBuffer)params.rewind());
	    MAX_TEXTURE_UNITS = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_MAX_VIEWPORT_DIMS, (IntBuffer)params.rewind());
	    MAX_VIEWPORT_DIMS = params.get(0);
	    GLES10.glGetIntegerv(GLES10.GL_ALIASED_LINE_WIDTH_RANGE, (IntBuffer)params.rewind());
	    ALIASED_LINE_WIDTH_RANGE_MIN = params.get(0);
	    ALIASED_LINE_WIDTH_RANGE_MAX = params.get(1);
	    GLES10.glGetIntegerv(GLES10.GL_SMOOTH_LINE_WIDTH_RANGE, (IntBuffer)params.rewind());
	    SMOOTH_LINE_WIDTH_RANGE_MIN = params.get(0);
	    SMOOTH_LINE_WIDTH_RANGE_MAX = params.get(1);
	    GLES10.glGetIntegerv(GLES10.GL_ALIASED_POINT_SIZE_RANGE, (IntBuffer)params.rewind());
	    ALIASED_POINT_SIZE_RANGE_MIN = params.get(0);
	    ALIASED_POINT_SIZE_RANGE_MAX = params.get(1);
	    GLES10.glGetIntegerv(GLES10.GL_SMOOTH_POINT_SIZE_RANGE, (IntBuffer)params.rewind());
	    SMOOTH_POINT_SIZE_RANGE_MIN = params.get(0);
	    SMOOTH_POINT_SIZE_RANGE_MAX = params.get(1);
	    GLES10.glGetIntegerv(GLES10.GL_NUM_COMPRESSED_TEXTURE_FORMATS, (IntBuffer)params.rewind());
	    NUM_COMPRESSED_TEXTURE_FORMATS = params.get(0);
	    //GLES11
	    GLES11.glGetIntegerv(GLES11.GL_MAX_CLIP_PLANES, (IntBuffer)params.rewind());
	    MAX_CLIP_PLANES = params.get(0);
	    //GLES20
	    GLES20.glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, (IntBuffer)params.rewind());
	    MAX_COMBINED_TEXTURE_IMAGE_UNITS = params.get(0);
	    GLES20.glGetIntegerv(GLES20.GL_MAX_CUBE_MAP_TEXTURE_SIZE, (IntBuffer)params.rewind());
	    MAX_CUBE_MAP_TEXTURE_SIZE = params.get(0);
	    GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS, (IntBuffer)params.rewind());
	    MAX_TEXTURE_IMAGE_UNITS = params.get(0);
	    GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS, (IntBuffer)params.rewind());
	    MAX_VERTEX_ATTRIBS = params.get(0);
	    GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS, (IntBuffer)params.rewind());
	    MAX_VERTEX_TEXTURE_IMAGE_UNITS = params.get(0);
	    params.clear();
	}
}
