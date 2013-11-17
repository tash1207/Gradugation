package com.gradugation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.coordinates.MapCoordinate;
import com.coordinates.MapSet;
import com.coordinates.MiniGameCoordinate;
import com.coordinates.SpriteCoordinate;

/*main class extends event class, when player steps onto a tile, randomly generate a number b/w 0 and 3 then call 
 create event object in maingame class, every time player steps on tile, do [eventObject].eventAction();
 replace checkMiniGameHotspot() in mainGameScreen
 */

public class Event {

	public enum DIRECTION {
		RIGHT(0, "Right"), LEFT(1, "Left"), DOWN(2, "Down"), UP(3, "Up");

		private final int index;
		private final String name;

		private DIRECTION(int index, String name) {
			this.index = index;
			this.name = name;
		}

		public int getIndex() {
			return this.index;
		}

		public String getName() {
			return this.name;
		}
	}

	private final static MapCoordinate OCONNELL_CENTER = new MapCoordinate(3,
			11);
	private final static MapCoordinate STADIUM = new MapCoordinate(7, 12);
	private final static MapCoordinate NEW_PHYSICS_BUILDING = new MapCoordinate(
			5, 4);
	private final static MapCoordinate NEW_ENGINEERING_BUILDING = new MapCoordinate(
			9, 1);
	private final static MapCoordinate REITZ = new MapCoordinate(9, 6);
	private final static MapCoordinate HUB = new MapCoordinate(11, 9);
	private final static MapCoordinate TURLINGTON = new MapCoordinate(14, 11);
	private final static MapCoordinate COMPUTER_SCIENCE = new MapCoordinate(14,
			7);
	private final static MapCoordinate PSYCHOLOGY_BUILDING = new MapCoordinate(
			12, 1);
	private final static MapCoordinate FOOD_SCIENCE = new MapCoordinate(17, 5);
	private final static MapCoordinate LIBRARY_WEST = new MapCoordinate(20, 12);
	private final static MapCoordinate LITTLE_HALL = new MapCoordinate(22, 7);
	private final static MapCoordinate SHANDS = new MapCoordinate(19, 0);
	private final static MapCoordinate HOUGH = new MapCoordinate(23, 10);

	private final static MiniGameCoordinate GRADUATION = new MiniGameCoordinate(
			OCONNELL_CENTER);
	private final static MiniGameCoordinate BENCH_PRESS_MINI_GAME = new MiniGameCoordinate(
			STADIUM);
	private final static MiniGameCoordinate WIRES_MINI_GAME = new MiniGameCoordinate(
			NEW_ENGINEERING_BUILDING);
	private final static MiniGameCoordinate WAIT_IN_LINE_MINI_GAME = new MiniGameCoordinate(
			HUB);
	private final static MiniGameCoordinate WHACK_AFLYER_MINI_GAME = new MiniGameCoordinate(
			TURLINGTON);
	private final static MiniGameCoordinate COLOR_MINI_GAME = new MiniGameCoordinate(
			PSYCHOLOGY_BUILDING);
	private final static MiniGameCoordinate FOOD_MINI_GAME = new MiniGameCoordinate(
			FOOD_SCIENCE);

	private MapSet mapPath = new MapSet();

	public final static int BENCH_PRESS_REQUEST_CODE = 0;
	public final static int WIRES_REQUEST_CODE = 1;
	public final static int WAIT_IN_LINE_REQUEST_CODE = 2;
	public final static int WHACK_AFLYER_REQUEST_CODE = 3;
	public final static int COLOR_REQUEST_CODE = 4;
	public final static int FOOD_REQUEST_CODE = 5;
	public final static int GRADUATION_REQUEST_CODE = 6;

	// public void method for each event, use switch case for each eventID
	// eventID 0 - do nothing, 1 - lose a turn? depends on turn mechanics -
	// maybe do something else, 2 - pick up an item

	public Event() {
	}

	public Event(Activity context, int resId) {
		try {
			InputStream inputStream = context.getResources().openRawResource(
					resId);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);

			String[] rowCol = buffreader.readLine().split(" ");
			int rowDim = Integer.parseInt(rowCol[0]);
			int colDim = Integer.parseInt(rowCol[1]);

			for (int y = rowDim - 1; y >= 0; y--) {
				String mapText = buffreader.readLine();// sc.nextLine();
				for (int x = 0; x < colDim; x++) {
					if (mapText.charAt(x) == 'X') {
						mapPath.add(new MapCoordinate(x, y));
					}
				}
			}
		} catch (IOException e) {
			// problem reading a necessary file
			e.printStackTrace();
		}
	}

	public SpriteCoordinate checkBoundaries(SpriteCoordinate coordinate,
			SpriteCoordinate offset) {
		MapCoordinate mapEndLocation = offset.spriteToMap();

		if (!mapPath.contains(mapEndLocation)) {
			return coordinate;
		}
		return mapEndLocation.mapToSprite();
	}

	public SpriteCoordinate[] getPossiblePath(SpriteCoordinate position) {
		SpriteCoordinate[] possiblePaths = new SpriteCoordinate[4];
		MapCoordinate spritePosition = position.spriteToMap();
		MapCoordinate right = spritePosition.add(new MapCoordinate(1, 0));
		// Check position to the right
		if (mapPath.contains(right)) {// spritePosition.add(new
										// MapCoordinate(1,0)))) {
			possiblePaths[DIRECTION.RIGHT.getIndex()] = new SpriteCoordinate(
					MainGameScreen.CHARACTER_WIDTH, 0);
		}

		// Check position to the left
		if (mapPath.contains(spritePosition.add(new MapCoordinate(-1, 0)))) {
			possiblePaths[DIRECTION.LEFT.getIndex()] = new SpriteCoordinate(
					-MainGameScreen.CHARACTER_WIDTH, 0);
		}

		// Check position behind
		if (mapPath.contains(spritePosition.add(new MapCoordinate(0, 1)))) {
			possiblePaths[DIRECTION.UP.getIndex()] = new SpriteCoordinate(0,
					MainGameScreen.CHARACTER_WIDTH);
		}

		// Check position forward
		if (mapPath.contains(spritePosition.add(new MapCoordinate(0, -1)))) {
			possiblePaths[DIRECTION.DOWN.getIndex()] = new SpriteCoordinate(0,
					-MainGameScreen.CHARACTER_WIDTH);
		}

		return possiblePaths;
	}

	public static SpriteCoordinate getPositionFromDirection(String dirName) {
		SpriteCoordinate newPosition = null;
		DIRECTION dir = DIRECTION.valueOf(dirName);

		switch (dir) {
		case DOWN:
			newPosition = new SpriteCoordinate(0,
					-MainGameScreen.CHARACTER_WIDTH);
			break;
		case UP:
			newPosition = new SpriteCoordinate(0,
					MainGameScreen.CHARACTER_WIDTH);
			break;
		case LEFT:
			newPosition = new SpriteCoordinate(-MainGameScreen.CHARACTER_WIDTH,
					0);
			break;
		case RIGHT:
			newPosition = new SpriteCoordinate(MainGameScreen.CHARACTER_WIDTH,
					0);
			break;
		default:
			break;
		}
		return newPosition;
	}

	public static void getEvent(SpriteCoordinate coordinate, Activity context,
			String characterType, boolean hasGraduated) {

		if (GRADUATION.isEqual(coordinate) && hasGraduated) {
			// graduate!
			Intent intent = new Intent(context, GameOverScreen.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, GRADUATION_REQUEST_CODE);

		} else if (BENCH_PRESS_MINI_GAME.isEqual(coordinate) && !hasGraduated) {
			// call bench press game
			Intent intent = new Intent(context, BenchPressMinigame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, BENCH_PRESS_REQUEST_CODE);
		} else if (WIRES_MINI_GAME.inRange(coordinate) && !hasGraduated) {
			// call wires mini game
			Intent intent = new Intent(context, WiresMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, WIRES_REQUEST_CODE);
		} else if (WAIT_IN_LINE_MINI_GAME.inRange(coordinate) && !hasGraduated) {
			// call wait in line
			Intent intent = new Intent(context, WaitInLineMinigame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, WAIT_IN_LINE_REQUEST_CODE);
		} else if (WHACK_AFLYER_MINI_GAME.inRange(coordinate) && !hasGraduated) {
			// call whack a flyer
			Intent intent = new Intent(context, WhackAFlyerMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, WHACK_AFLYER_REQUEST_CODE);
		} else if (COLOR_MINI_GAME.inRange(coordinate) && !hasGraduated) {
			// call colors
			Intent intent = new Intent(context, ColorMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, COLOR_REQUEST_CODE);
		} else if (FOOD_MINI_GAME.inRange(coordinate) && !hasGraduated) {
			Intent intent = new Intent(context, FoodMiniGame.class);
			intent.putExtra("character_type", characterType);
			context.startActivityForResult(intent, FOOD_REQUEST_CODE);
		} else {
			// generate random event
		}
	}
}