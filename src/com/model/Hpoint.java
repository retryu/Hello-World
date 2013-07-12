package com.model;

import android.R.integer;

public class Hpoint {
	float x;
	float y;
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}  
	public void setY(float y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Hpoint [x=" + x + ", y=" + y + "]";
	}
	
	
	
}
