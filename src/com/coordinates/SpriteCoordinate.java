package com.coordinates;

public class SpriteCoordinate extends Coordinate {
	
	public SpriteCoordinate(float x, float y) {
		super.x = x;
		super.y = y;
	}
	
	public SpriteCoordinate() {
		this(0f,0f);
	}
	
	public MapCoordinate spriteToMap() {
		float x = (this.x + 16) / 32 - 1;
		float y = (this.y + 10) / 32 - 1;
		
		return new MapCoordinate(x,y);
	}
	
	public SpriteCoordinate add(Coordinate toAdd) {
		return new SpriteCoordinate (this.x + toAdd.getX(), this.y + toAdd.getY());
	}
	
	public int compareTo(Coordinate toCompare) {
		if (this.x == toCompare.getX() && this.y == toCompare.getY()) {
			return 0;
		} else {
			return 1;
		}
	}

}
