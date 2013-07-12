package com.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-7-12 下午04:39:25 file declare:
 */
public class DisplayUtil {
	private Activity activity;

	public DisplayUtil(Activity activity) {
		this.activity = activity; 
	}
  
	public int getWidth() {

		Display display = activity.getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		Log.i("view", "widgth" + width);
		return width;
	}
  
	public int getHeigth() {
		Display display = activity.getWindowManager().getDefaultDisplay();
		int height = display.getHeight();

		Log.i("view", "height:" + display.getHeight());
		return height;
	}
}
