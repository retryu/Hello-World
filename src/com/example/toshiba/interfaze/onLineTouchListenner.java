package com.example.toshiba.interfaze;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.model.Hpoint;
import com.widget.FaceImage;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 下午02:37:16 file declare:
 */
public class onLineTouchListenner implements OnTouchListener {

	List<Hpoint> hairPoints;
	List<Hpoint> facePoints;
	FaceImage faceImage;
	int mode;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	float startX1;
	float startY1;
	float startX2;
	float startY2;
	float dis;

	public onLineTouchListenner() {
		hairPoints = new ArrayList<Hpoint>();
		facePoints = new ArrayList<Hpoint>();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getActionMasked();

		Log.e("debug", "onTouch");
		float x = event.getX();
		float y = event.getY();
		switch (action) {

		case MotionEvent.ACTION_DOWN:
			// printPoints();
			Log.d("debug", "down");
			mode = DRAG;
			startX1 = event.getX(0);
			startY1 = event.getY(0);
			break;

		case MotionEvent.ACTION_POINTER_DOWN:
			Log.d("debug", "ACTION_POINTER_DOWN");
			startX1 = event.getX(0);
			startY1 = event.getY(0);

			startX2 = event.getX(1);
			startY2 = event.getY(1);

			Log.e("debug", "start_x1:" + startX1 + "  start_y1:" + startY1
					+ "  start_x2:" + startX2 + "  start_Y2:" + startY2);
			dis = getDistance(event);
			mode = ZOOM;
			break;

		case MotionEvent.ACTION_MOVE:

			if (mode == DRAG) {
				Log.d("debug", "DRAG");
				float x1 = event.getX();
				float y1 = event.getY();
				Log.e("debug", "x偏移:" + (x1 - startX1) + " y轴偏移"
						+ (y1 - startY1));
			}
			if (mode == ZOOM) {
				Log.d("debug", "ZOOM");
				int pointCount = event.getPointerCount();
				if (pointCount >= 2) {
					float disNos = getDistance(event);
					Log.e("debug", "dis:" + dis + "  disNow:" + disNos
							+ "  放大倍数：" + disNos / dis);

				}
			}

			if (faceImage.getType() == 1) {
				Hpoint point = new Hpoint();
				point.setX(x);
				point.setY(y);
				hairPoints.add(point);
				if (faceImage != null) {
					faceImage.invalidate();
				}
			}
			if (faceImage.getType() == 2) {
				Hpoint point = new Hpoint();
				point.setX(x);
				point.setY(y);
				facePoints.add(point);
				if (faceImage != null) {
					faceImage.invalidate();
				}
			}
			Log.e("debug", "ACTION_MOVE");
			break;

		case MotionEvent.ACTION_UP:
			Log.e("debug", "ACTION_UP");
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}

		return true;
	}

	public float getDistance(MotionEvent event) {
		int distance = 0;

		float x1 = event.getX(0);
		float y1 = event.getY(0);

		float x2 = event.getX(1);
		float y2 = event.getY(1);

		float d1 = x1 - x2;
		float d2 = y2 - y2;
		float s = (float) (Math.pow(d1, 2) + Math.pow(d2, 2));
		s = (float) Math.sqrt(s);
		return s;
	}

	public void printPoints() {
		if (hairPoints == null)
			return;
		for (Hpoint p : hairPoints) {
			System.out.println(p);
		}
	}

	public List<Hpoint> getHairPoints() {
		return hairPoints;
	}

	public void setHairPoints(List<Hpoint> hairPoints) {
		this.hairPoints = hairPoints;
	}

	public List<Hpoint> getFacePoints() {
		return facePoints;
	}

	public void setFacePoints(List<Hpoint> facePoints) {
		this.facePoints = facePoints;
	}

	public FaceImage getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(FaceImage faceImage) {
		this.faceImage = faceImage;
	}

	public void clear() {
		hairPoints = new ArrayList<Hpoint>();
		facePoints = new ArrayList<Hpoint>();
		faceImage.invalidate();
	}

}
