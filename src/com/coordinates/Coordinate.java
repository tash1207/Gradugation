package com.coordinates;

import java.io.Serializable;

public abstract class Coordinate implements Serializable {
	
	protected float x;
	protected float y;
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public String toString() {
		return this.x + "," + this.y;
	}
	
	abstract Coordinate add(Coordinate toAdd);
	
	abstract int compareTo(Coordinate toCompare);
}
