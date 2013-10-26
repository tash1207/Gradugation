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
	
	private static MiniGameCoordinate wiresMiniGame = new MiniGameCoordinate(new MapCoordinate(9,1));
	private static MiniGameCoordinate benchPressGame = new MiniGameCoordinate(new MapCoordinate(7,12));
	
	
	//public void method for each event, use switch case for each eventID
	//eventID 0 - do nothing, 1 - lose a turn? depends on turn mechanics - maybe do something else, 2 - pick up an item
	
	public Event() {
	}
	
	
	public static void getEvent(SpriteCoordinate coordinate, boolean doneSwiping, 
			boolean gameDone, boolean move, Activity context) {
		if (!doneSwiping) {
			//only checking for boundaries here
			return;
		}
		
		if (wiresMiniGame.inRange(coordinate) && !gameDone && !move) {
			//call wires mini game
			Intent intent = new Intent(context, WiresMiniGame.class);
			context.startActivity(intent);
		} else if (benchPressGame.inRange(coordinate) && !gameDone && !move) {
			//call bench press game
			Intent intent = new Intent(context, BenchPressMinigame.class);
			context.startActivity(intent);
		} else {
			//generate random event here
		}
	}
}