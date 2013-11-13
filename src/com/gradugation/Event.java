package com.gradugation;

import android.app.Activity;
import android.content.Intent;

import com.coordinates.MapCoordinate;
import com.coordinates.MiniGameCoordinate;
import com.coordinates.SpriteCoordinate;

/*main class extends event class, when player steps onto a tile, randomly generate a number b/w 0 and 3 then call 
create event object in maingame class, every time player steps on tile, do [eventObject].eventAction();
replace checkMiniGameHotspot() in mainGameScreen
*/

public class Event {
	
	private final static MapCoordinate OCONNELL_CENTER = new MapCoordinate(3,11);
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
	
	private final static MiniGameCoordinate GRADUATION = new MiniGameCoordinate(OCONNELL_CENTER);
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
	
	
	public static void getEvent(SpriteCoordinate coordinate, boolean doneSwiping, 
			boolean gameDone, boolean move, Activity context, String characterType) {
		if (!doneSwiping) {
			//only checking for boundaries here
			return;
		}
		
		if (GRADUATION.inRange(coordinate) && !gameDone && !move) {
			//graduate!
		} else if (BENCH_PRESS_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			//call bench press game
			Intent intent = new Intent(context, BenchPressMinigame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else if (WIRES_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			//call wires mini game
			Intent intent = new Intent(context, WiresMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else if (WAIT_IN_LINE_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			// call wait in line
			Intent intent = new Intent(context, WaitInLineMinigame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else if (WHACK_AFLYER_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			// call whack a flyer
			Intent intent = new Intent(context, WhackAFlyerMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else if (COLOR_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			// call colors
			Intent intent = new Intent(context, ColorMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else if (FOOD_MINI_GAME.inRange(coordinate) && !gameDone && !move) {
			Intent intent = new Intent(context, FoodMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivity(intent);
		} else {
			// generate random event
		}
	}
}