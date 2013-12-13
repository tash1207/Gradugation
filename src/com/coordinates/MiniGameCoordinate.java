package com.coordinates;

import android.util.Log;

public class MiniGameCoordinate {

	private Coordinate lesser;
	private Coordinate greater;
	private Coordinate mapCoordinate;
	
	private Coordinate offset  = new SpriteCoordinate(32,32);

	public MiniGameCoordinate(MapCoordinate miniGame) {
		this.mapCoordinate = miniGame;
		this.lesser = miniGame.mapToSprite();
		this.greater = offset.add(this.lesser);
	}
	
	public MiniGameCoordinate() {
		this(new MapCoordinate());
	}

	public boolean inRange(Coordinate toCompare) {
		if (this.lesser.x <= toCompare.getX() && toCompare.getX() <= this.greater.x 
				&& this.lesser.y <= toCompare.getY() && toCompare.getY() <= this.greater.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEqual(SpriteCoordinate spriteCoordinate) {
		MapCoordinate coordinate = spriteCoordinate.spriteToMap();
		Log.d("MapCoordinate:", coordinate.x + ", " + coordinate.y);
		if ((coordinate.getX() == this.mapCoordinate.x) && (coordinate.getY() == this.mapCoordinate.y)) {
			return true;
		} else {
			return false;
		}
	}

}
