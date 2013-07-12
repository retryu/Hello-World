package com.example.toshiba.interfaze;

import java.util.ArrayList;
import java.util.List;

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

	public onLineTouchListenner() {
		hairPoints = new ArrayList<Hpoint>();
		facePoints = new ArrayList<Hpoint>();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();

		Log.e("debug", "onTouch");
		float x = event.getX();
		float y = event.getY();
		switch (action) {

		case MotionEvent.ACTION_DOWN:
			printPoints();

			break;

		case MotionEvent.ACTION_MOVE:
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

			break;

		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}

		return true;
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
