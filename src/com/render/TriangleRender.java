package com.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 上午10:21:30 file declare:
 */
public class TriangleRender implements Renderer {
	float[] ff = new float[] { 60, 200, // 左上角
			180, 200, // 右上角
			120, 300, // 下顶角
	};

	protected FloatBuffer vertextBuffer;
	public TriangleRender() {
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(ff.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertextBuffer = vbb.asFloatBuffer();
		vertextBuffer.put(ff);
		vertextBuffer.position(0);
		
		
	}
	
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDrawFrame(GL10 gl) {

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glPushMatrix();

		gl.glPushMatrix();
		gl.glColor4f(1, 0, 0, 0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertextBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glPopMatrix();

	}

	public IntBuffer bufferUtil(int[] arr) {
		IntBuffer buffer;

		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		qbb.order(ByteOrder.nativeOrder());

		buffer = qbb.asIntBuffer();
		buffer.put(arr);
		buffer.position(0);

		return buffer;
	}

	public FloatBuffer bufferUtil(float[] arr) {
		FloatBuffer buffer;

		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		qbb.order(ByteOrder.nativeOrder());

		buffer = qbb.asFloatBuffer();
		buffer.put(arr);
		buffer.position(0);

		return buffer;
	}

}
