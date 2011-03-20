package dimyoux.houseExplorer;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import android.opengl.GLES20;
import dimyoux.engine.core.GLRenderer;
import dimyoux.engine.utils.Color;
/**
 * Example application : A house explorer 
 */
public class HouseRenderer extends GLRenderer {

	FloatBuffer buffer;
	/**
	 * Constructor
	 */
	public HouseRenderer()
	{
		super();
		this.setClearColor(new Color(Color.BLACK));
		buffer = FloatBuffer.allocate(9);
		buffer.put(4);
		buffer.put(5);
		buffer.put(6);
		buffer.put(7);
		buffer.put(8);
		buffer.put(10);
		buffer.put(11);
		buffer.put(12);
		buffer.put(13);
	}
	/**
	 * Called when a frame is drawn
	 * @param gl GL10 controller
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		super.onDrawFrame(gl);
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	    
	    gl.glLoadIdentity();
	  //  gl.glScalef(1.0f, 1.0f, 1.0f);
	    //
	    /*
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, boothVerticies);
	    int i = 0;
	    for (Polygon b : allPolygons) {
	        if (b.indicies != null) {
	            gl.glColor4f(0.6f, 0.6f, 0.6f, 1.0f);
	            gl.glDrawElements(GL10.GL_TRIANGLE_FAN, b.numberOfVerticies, GL10.GL_UNSIGNED_SHORT, b.indicies);
	            gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
	            gl.glDrawElements(GL10.GL_LINE_LOOP, b.numberOfVerticies, GL10.GL_UNSIGNED_SHORT, b.indicies);
	            if (i++ > 20) break;
	        }
	    }
	    
	     */
	   
	    gl.glPushMatrix();
	    gl.glRotatef(0, 10, 0, 0);
	    Triangle t = new Triangle();
	    t.setColor(Color.create(Color.YELLOW));
	    t.draw(gl);
	    gl.glPopMatrix();
	    gl.glTranslatef(0,0, 100);
	    
    

	    /*
	      gl.glPushMatrix();
          gl.glTranslatef(0.0f,-10,-1.0f);
          gl.glRotatef(0, 1, 0, 0);
          gl.glRotatef(0, 0, 1, 0);
          
        //  gl.glVertexPointer(3, GL10.GL_FLOAT, 0, buffer);

          gl.glColor4f(1.0f, 0, 0, 1.0f);
          gl.glNormal3f(0,0,1);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
          gl.glNormal3f(0,0,-1);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
  
          gl.glColor4f(0, 1.0f, 0, 1.0f);
          gl.glNormal3f(-1,0,0);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
          gl.glNormal3f(1,0,0);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
          
          gl.glColor4f(0, 0, 1.0f, 1.0f);
          gl.glNormal3f(0,1,0);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
          gl.glNormal3f(0,-1,0);
          gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
          gl.glPopMatrix();*/
		
	}

}
