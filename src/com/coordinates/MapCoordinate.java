package com.coordinates;

public class MapCoordinate extends Coordinate {
	
	public MapCoordinate(float x, float y) {
		super.x = x;
		super.y = y;
	}
	
	public MapCoordinate() {
		this(0f,0f);
	}
	
	public SpriteCoordinate mapToSprite() {
		float x = (this.x + 1) * 32 - 16;
		float y = (this.y + 1) * 32 - 10;
		
		return new SpriteCoordinate(x,y);
	}

	@Override
	public MapCoordinate add(Coordinate toAdd) {
		return new MapCoordinate (this.x + toAdd.getX(), this.y + toAdd.getY());
	}
	
	public int compareTo(Coordinate toCompare) {
		if (this.x == toCompare.getX() && this.y == toCompare.getY()) {
			return 0;
		} else {
			return 1;
		}
	}

}
