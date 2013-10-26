package com.gradugation;

/**import java.util.*;
import java.util.Random;

/*main class extends event class, when player steps onto a tile, randomly generate a number b/w 0 and 3 then call 
create event object in maingame class, every time player steps on tile, do [eventObject].eventAction();
replace checkMiniGameHotspot() in mainGameScreen
*/

/*public class Event {

	private int eventID;
	
	//public void method for each event, use switch case for each eventID
	//eventID 0 - do nothing, 1 - lose a turn? depends on turn mechanics - maybe do something else, 2 - pick up an item
	
	public Event(eventID) {
		//default constructor
	}
	public String eventAction(int currentX, int currentY, int eventID) {
		this.eventID = eventID;
		String str;

			switch(currentY) {
				case 375:
							if(currentX == 239 && move == false && gameDone == false) {
								Intent intent = new Intent(MainGameScreen.this,BenchPressMinigame.class);
								startActivity(intent);
								gameDone = true;
							}
							str = "minigame";
							break;
				case 471:
							if(currentX == 239 && move == false && gameDone == false) {
								Intent intent = new Intent(MainGameScreen.this,WiresMinigame.class);
								startActivity(intent);
								gameDone = true;
							}
							str = "minigame";
							break;
				//case for finish tile, do string "You win!"
				default:
						switch (eventID) {
							case 0:	//do nothing
									str = "";
									break;
							case 1:
									str = "You've picked up an item!";
									//update count of item in character property
									break;
							case 2:
									break;
						}
					
		}
		return str;
	}
	
	public int getEventID() {
		return eventID;
	}
	public void setEventID() {
		this.eventID = eventID;
	}*/

public class Event {
	
}