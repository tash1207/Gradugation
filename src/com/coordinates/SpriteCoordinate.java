package com.coordinates;

public class SpriteCoordinate extends Coordinate {
	
	public SpriteCoordinate(float x, float y) {
		super.x = x;
		super.y = y;
	}
	
	public MapCoordinate spriteToMap() {
		float x = (this.x + 16) / 32 - 1;
		float y = (this.y + 10) / 32 - 1;
		
		return new MapCoordinate(x,y);
	}

}
