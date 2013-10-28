package com.gradugation;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.coordinates.MapCoordinate;
import com.coordinates.MiniGameCoordinate;
import com.coordinates.SpriteCoordinate;

/*main class extends event class, when player steps onto a tile, randomly generate a number b/w 0 and 3 then call 
create event object in maingame class, every time player steps on tile, do [eventObject].eventAction();
replace checkMiniGameHotspot() in mainGameScreen
*/

public class Event {
	
	private final static MapCoordinate OCONNEL_CENTER = new MapCoordinate(3,11);
	private final static MapCoordinate STADIUM = new MapCoordinate(7,12);
	private final static MapCoordinate NEW_PHYSICS_BUILDING = new MapCoordinate(5,4);
	private final static MapCoordinate NEW_ENGINEERING_BUILDING = new MapCoordinate(9,1);
	private final static MapCoordinate REITZ = new MapCoordinate(9,6);
	private final static MapCoordinate HUB = new MapCoordinate(11,9);
	private final static MapCoordinate TURLINGTON = new MapCoordinate(14,11);
	private final static MapCoordinate COMPUTER_SCIENCE = new MapCoordinate(14,7);
	private final static MapCoordinate PSYCHOLOGY_BUILDING = new MapCoordinate(12,2);
	private final static MapCoordinate FOOD_SCIENCE = new MapCoordinate(17,5);
	private final static MapCoordinate LIBRARY_WEST = new MapCoordinate(20,12);
	private final static MapCoordinate LITTLE_HALL = new MapCoordinate(22,7);
	private final static MapCoordinate SHANDS = new MapCoordinate(19,0);
	private final static MapCoordinate HOUGH = new MapCoordinate(23,10);
	
	private final static MiniGameCoordinate GRADUATION = new MiniGameCoordinate(OCONNEL_CENTER);
	private final static MiniGameCoordinate BENCH_PRESS_MINI_GAME = new MiniGameCoordinate(STADIUM);
	private final static MiniGameCoordinate WIRES_MINI_GAME = new MiniGameCoordinate(NEW_ENGINEERING_BUILDING);
	private final static MiniGameCoordinate WAIT_IN_LINE_MINI_GAME = new MiniGameCoordinate(HUB);
	private final static MiniGameCoordinate WHACK_AFLYER_MINI_GAME = new MiniGameCoordinate(TURLINGTON);
	private final static MiniGameCoordinate COLOR_MINI_GAME = new MiniGameCoordinate(PSYCHOLOGY_BUILDING);
	private final static MiniGameCoordinate FOOD_MINI_GAME = new MiniGameCoordinate(FOOD_SCIENCE);
	
	
	//public void method for each event, use switch case for each eventID
	//eventID 0 - do nothing, 1 - lose a turn? depends on turn mechanics - maybe do something else, 2 - pick up an item
	
	public Event() {
	}
	
	
	public static SpriteCoordinate checkBoundaries(SpriteCoordinate coordinate, SpriteCoordinate offset) {
		MapCoordinate mapEndLocation = offset.spriteToMap();
		MapCoordinate mapStartLocation = coordinate.spriteToMap();
		
		float yDifference = offset.getY() - coordinate.getY();
		float xDifference = offset.getX() - coordinate.getX();
		Log.d("xdiff, ydiff", xDifference + ", " + yDifference);
		if (yDifference >= 32) {
			// swiping up
			if (mapStartLocation.getX() == 2.0) {
				if (mapEndLocation.getY() > 9) {
					// move right instead
					mapEndLocation.setY(9);
					mapEndLocation.setX(3);
				}
			} else if (mapStartLocation.getX() == 3.0) {
				if (mapStartLocation.getY() >= 9.0 && mapEndLocation.getY() > 11 ) {
					// move right instead
					mapEndLocation.setY(11);
					mapEndLocation.setX(4);
				} else if (mapStartLocation.getY() == 7) {
					mapEndLocation = mapStartLocation;
				}
			} else if (mapStartLocation.getX() == 4.0) {
				mapEndLocation = mapStartLocation;
			} else if (mapStartLocation.getX() == 5.0) {
				if (mapStartLocation.getY() >= 4 && mapEndLocation.getY() > 7) {
					// move left instead
					mapEndLocation.setY(7);
					mapEndLocation.setX(3);
				} else  {
					mapEndLocation = mapStartLocation;
				}
					
			} else if (mapStartLocation.getX() == 6.0) {
			} else if (mapStartLocation.getX() == 7.0) {
			} else if (mapStartLocation.getX() == 8.0) {
			} else if (mapStartLocation.getX() == 9.0) {
			} else if (mapStartLocation.getX() == 10.0) {
			} else if (mapStartLocation.getX() == 11.0) {
			} else if (mapStartLocation.getX() == 12.0) {
			} else if (mapStartLocation.getX() == 13.0) {
			} else if (mapStartLocation.getX() == 14.0) {
			} else if (mapStartLocation.getX() == 15.0) {
			} else if (mapStartLocation.getX() == 16.0) {
			} else if (mapStartLocation.getX() == 17.0) {
			} else if (mapStartLocation.getX() == 18.0) {
			} else if (mapStartLocation.getX() == 19.0) {
			} else if (mapStartLocation.getX() == 20.0) {
			} else if (mapStartLocation.getX() == 21.0) {
			} else if (mapStartLocation.getX() == 22.0) {
			} else if (mapStartLocation.getX() == 23.0) {
			}
		} else if (yDifference <= -32) {
			// swiping down
		} else if (xDifference >= 32) {
			// swiping right
		} else if (xDifference <= -32) {
			// swiping left
		}
		
		return mapEndLocation.mapToSprite();
	}
	
	public static void getEvent(SpriteCoordinate coordinate, 
			boolean gameDone, boolean move, Activity context) {
		
		if (GRADUATION.isEqual(coordinate) && !gameDone && !move) {
			//graduate!
		} else if (BENCH_PRESS_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			//call bench press game
			Intent intent = new Intent(context, BenchPressMinigame.class);
			context.startActivity(intent);
		} else if (WIRES_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			//call wires mini game
			Intent intent = new Intent(context, WiresMiniGame.class);
			context.startActivity(intent);
		} else if (WAIT_IN_LINE_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			// call wait in line
			Intent intent = new Intent(context, WaitInLineMinigame.class);
			context.startActivity(intent);
		} else if (WHACK_AFLYER_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			// call whack a flyer
			Intent intent = new Intent(context, WhackAFlyerMiniGame.class);
			context.startActivity(intent);
		} else if (COLOR_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			// call colors
			Intent intent = new Intent(context, ColorMiniGame.class);
			context.startActivity(intent);
		} else if (FOOD_MINI_GAME.isEqual(coordinate) && !gameDone && !move) {
			Intent intent = new Intent(context, FoodMiniGame.class);
			context.startActivity(intent);
		} else {
			// generate random event
		}
	}
}