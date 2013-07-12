package com.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.toshiba.interfaze.onLineTouchListenner;
import com.model.Hpoint;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 下午03:18:09 file declare:
 */
public class FaceImage extends ImageView {
	Paint paint;
	onLineTouchListenner onLineTouchListenner;
	private int type = -1;

	public FaceImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);

	}

	public onLineTouchListenner getOnLineTouchListenner() {
		return onLineTouchListenner;
	}

	public void setOnLineTouchListenner(
			onLineTouchListenner onLineTouchListenner) {
		this.onLineTouchListenner = onLineTouchListenner;
		this.setOnTouchListener(onLineTouchListenner);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.e("onDraw", "onDrawing" + getType());
		if (onLineTouchListenner != null) {
			List<Hpoint> hpoints = onLineTouchListenner.getHairPoints();
			// if (type == 1 && hpoints != null) {
			if (hpoints != null) {
				drawLines(canvas, hpoints);
				paint.setColor(Color.RED);
			}
			// if (type == 2 && onLineTouchListenner.getFacePoints() != null) {
			if (onLineTouchListenner.getFacePoints() != null) {
				drawLines(canvas, onLineTouchListenner.getFacePoints());
				paint.setColor(Color.BLUE);

			}
		} else {
			Log.e("onDraw", "onLineTouchListener  is  null");
		}

	}

	public void drawLines(Canvas canvas, List<Hpoint> hpoints) {
		for (int i = 0; i < hpoints.size(); i++) {

			if (i != 0) {
				Hpoint hpoint = hpoints.get(i);
				Hpoint prePoint = hpoints.get(i - 1);
				// canvas.drawPoint(hpoint.getX(), hpoint.getY(), paint);
				canvas.drawLine(prePoint.getX(), prePoint.getY(),
						hpoint.getX(), hpoint.getY(), paint);

			}
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
