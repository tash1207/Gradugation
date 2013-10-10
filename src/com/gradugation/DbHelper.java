package com.gradugation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "gradugation";
	public static final int DB_VERSION = 1;
	
	//Game Table
	public static final String GAMES = "games";
	
	public static final String G_ID = "id";
	public static final String NUM_OF_PLAYERS = "num_of_players";
	public static final String CURRENT_PLAYER = "current_player";
	
	
	//Character Table
	public static final String CHARACTER = "character";
	
	public static final String CHARACTER_ID = "id";
	public static final String CHARACTER_TXT = "text";
	public static final String CHARACTER_NAME = "name";
	public static final String PICTURE = "picture";
	

	//Game Character Table
	public static final String GAME_CHARACTER = "game_character";
	
	public static final String GAME_ID = "game_id";
	public static final String CHAR_ID = "char_id";
	public static final String PLAYER_ORDER = "player_order";
	public static final String CREDITS = "credits";
	public static final String COINS = "coins";
	public static final String CURRENT_TILE = "current_tile";
	
	
	//Tiles Table
	public static final String TILES = "tiles";
	
	public static final String TILE_ID = "id";
	public static final String TILE_TYPE = "type";
	public static final String TILE_NAME = "name";
	public static final String X_COORD = "x_coord";
	public static final String Y_COORD = "y_coord";
	
	
	//Tile_Tile Table
	public static final String TILE_TILE = "tile_tile";
	
	public static final String TILE_ID1 = "tile_id1";
	public static final String TILE_ID2 = "tile_id2";
	public static final String DIRECTION = "direction";
	
	
	//Events Table
	public static final String EVENTS = "events";
	
	public static final String EVENT_ID = "id";
	public static final String EVENT_TYPE = "type";
	public static final String EVENT_AFFECTS = "affects";
	public static final String EVENT_AMOUNT = "amount";
	public static final String EVENT_TXT = "text";
	
	
	//Items Table
	public static final String ITEMS = "items";
	
	public static final String ITEM_ID = "id";
	public static final String ITEM_NAME = "name";
	public static final String COST = "cost";
	public static final String ITEM_TYPE = "type";
	public static final String ITEM_AFFECTS = "affects";
	public static final String ITEM_AMOUNT = "amount";
	public static final String ITEM_TXT = "text";
	
	//Minigames Table
	public static final String MINIGAMES = "minigames";
	
	public static final String MINIGAME_ID = "id";
	public static final String MINIGAME_CREDITS = "credits";
	
	
	//Minigame Character Table
	public static final String MINIGAME_CHARACTER = "minigame_character";
	
	public static final String MINIGAME_GAME_ID = "game_id";
	public static final String MINIGAME_GID = "minigame_id";
	public static final String MINIGAME_CHAR_ID = "char_id";
	public static final String TIMES_PLAYED = "num_of_times_played";
	
	
	Context context;
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		//create database tables
        db.execSQL("create table if not exists " + GAMES +
        		"(id integer, num_of_players integer, current_player integer, " +
        		"primary key (id));");
        db.execSQL("create table if not exists " + CHARACTER +
        		"(id integer, type text, name text, picture text, " +
        		"primary key (id));");
        db.execSQL("create table if not exists " + GAME_CHARACTER +
        		"(game_id integer, char_id integer, player_order integer, credits integer, coins integer, current_tile integer," +
        		"primary key (game_id, char_id));");
        db.execSQL("create table if not exists " + TILES +
        		"(id integer, type text, name text, x_coord integer, y_coord integer," +
        		"primary key (id));");
        db.execSQL("create table if not exists " + TILE_TILE + 
        		"(tile_id1 integer, tile_id2 integer, direction text, " +
        		"primary key (tile_id1, tile_id2));");
        db.execSQL("create table if not exists "+ EVENTS +
        		"(id integer, type text, affects text, amount integer, text text, " +
        		"primary key (id));");
        db.execSQL("create table if not exists " + ITEMS +
        		"(id integer, name text, cost integer, type text, affects text, amount integer, text text," +
        		"primary key (id));");
        db.execSQL("create table if not exists " + MINIGAMES +
        		"(id integer, credits integer, " +
        		"primary key (id));");
        db.execSQL("create table if not exists " + MINIGAME_CHARACTER +
        		"(game_id integer, minigame_id integer, char_id integer, num_of_times_played integer," +
        		"primary key (game_id, minigame_id, char_id));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	public void insertRow(SQLiteDatabase db, int tableNum, String[] tableValues){ 
		ContentValues values = new ContentValues();
		
		String tableName = null;
		
		switch(tableNum){
			case 1: 
				tableName = GAMES;
				values.put(G_ID, tableValues[0]);
				values.put(NUM_OF_PLAYERS, tableValues[1]);
				values.put(CURRENT_PLAYER, tableValues[2]);
			case 2: 
				tableName = CHARACTER;
				values.put(CHARACTER_ID, tableValues[0]);
				values.put(CHARACTER_TXT, tableValues[1]);
				values.put(CHARACTER_NAME, tableValues[2]);
				values.put(PICTURE, tableValues[3]);
			case 3: 
				tableName = GAME_CHARACTER;
				values.put(GAME_ID, tableValues[0]);
				values.put(CHAR_ID, tableValues[1]);
				values.put(PLAYER_ORDER, tableValues[2]);
				values.put(CREDITS, tableValues[3]);
				values.put(COINS, tableValues[4]);
				values.put(CURRENT_TILE, tableValues[5]);
			case 4: 
				tableName = TILES;
				values.put(TILE_ID, tableValues[0]);
				values.put(TILE_TYPE, tableValues[1]);
				values.put(TILE_NAME, tableValues[2]);
				values.put(X_COORD, tableValues[3]);
				values.put(Y_COORD, tableValues[4]);
			case 5: 
				tableName = TILE_TILE;
				values.put(TILE_ID1, tableValues[0]);
				values.put(TILE_ID2, tableValues[1]);
				values.put(DIRECTION, tableValues[2]);
			case 6: 
				tableName = EVENTS;
				values.put(EVENT_ID, tableValues[0]);
				values.put(EVENT_TYPE, tableValues[1]);
				values.put(EVENT_AFFECTS, tableValues[2]);
				values.put(EVENT_AMOUNT, tableValues[3]);
				values.put(EVENT_TXT, tableValues[4]);
			case 7: 
				tableName = ITEMS;
				values.put(ITEM_ID, tableValues[0]);
				values.put(ITEM_NAME, tableValues[1]);
				values.put(COST, tableValues[2]);
				values.put(ITEM_TYPE, tableValues[3]);
				values.put(ITEM_AFFECTS, tableValues[4]);
				values.put(ITEM_AMOUNT, tableValues[5]);
				values.put(ITEM_TXT, tableValues[6]);
			case 8: 
				tableName = MINIGAMES;
				values.put(MINIGAME_ID, tableValues[0]);
				values.put(MINIGAME_CREDITS, tableValues[1]);
			case 9: 
				tableName = MINIGAME_CHARACTER;
				values.put(MINIGAME_GAME_ID, tableValues[0]);
				values.put(MINIGAME_GID, tableValues[1]);
				values.put(MINIGAME_CHAR_ID, tableValues[2]);
				values.put(TIMES_PLAYED, tableValues[3]);
		}
		try{
			db.insert(tableName, null, values);
		}catch (Exception e){
			Log.e("DB ERROR", e.toString()); // prints the error message to the log
			e.printStackTrace(); // prints the stack trace to the log
		}
	}
	
	public void updateRow(){
		//ContentValues values = new ContentValues();
		
	}
	
	public void deleteRow(){
		
	}

}
