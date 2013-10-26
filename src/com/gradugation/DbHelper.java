package com.gradugation;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
        SQLiteDatabase db;
        public static final String DB_NAME = "gradugation";
        public static final int DB_VERSION = 1;
        
        //Game Table
        public static final String GAMES = "games";
        
        public static final String G_ID = "id";
        public static final String NUM_OF_PLAYERS = "num_of_players";
        public static final String CURRENT_PLAYER = "current_player";
        
        public static final String[] GAME_TABLE = {G_ID, NUM_OF_PLAYERS, CURRENT_PLAYER};
        
        
        //Character Table
        public static final String CHARACTER = "character";
        
        public static final String CHARACTER_ID = "id";
        public static final String CHARACTER_TYPE = "type";
        public static final String CHARACTER_NAME = "name";
        public static final String PICTURE = "picture";
        
        public static final String[] CHARACTER_TABLE = {CHARACTER_ID, CHARACTER_TYPE, CHARACTER_NAME, PICTURE};
        
        //Game Character Table
        public static final String GAME_CHARACTER = "game_character";
        
        public static final String GAME_ID = "game_id";
        public static final String CHAR_ID = "char_id";
        public static final String PLAYER_ORDER = "player_order";
        public static final String CREDITS = "credits";
        public static final String COINS = "coins";
        public static final String CURRENT_TILE = "current_tile";
        
        public static final String[] GAME_CHARACTER_TABLE = {GAME_ID, CHAR_ID, PLAYER_ORDER, CREDITS, COINS, CURRENT_TILE};
        
        //Tiles Table
        public static final String TILES = "tiles";
        
        public static final String TILE_ID = "id";
        public static final String TILE_TYPE = "type";
        public static final String TILE_NAME = "name";
        public static final String X_COORD = "x_coord";
        public static final String Y_COORD = "y_coord";
        
        public static final String[] TILE_TABLE = {TILE_ID, TILE_TYPE, TILE_NAME, X_COORD, Y_COORD};
        
        //Tile_Tile Table
        public static final String TILE_TILE = "tile_tile";
        
        public static final String TILE_ID1 = "tile_id1";
        public static final String TILE_ID2 = "tile_id2";
        public static final String DIRECTION = "direction";
        
        public static final String[] TILE_TILE_TABLE = {TILE_ID1, TILE_ID2, DIRECTION};
        
        //Events Table
        public static final String EVENTS = "events";
        
        public static final String EVENT_ID = "id";
        public static final String EVENT_TYPE = "type";
        public static final String EVENT_AFFECTS = "affects";
        public static final String EVENT_AMOUNT = "amount";
        public static final String EVENT_TXT = "text";
        
        public static final String[] EVENT_TABLE = {EVENT_ID, EVENT_TYPE, EVENT_AFFECTS, EVENT_AMOUNT, EVENT_TXT};
        
        //Items Table
        public static final String ITEMS = "items";
        
        public static final String ITEM_ID = "id";
        public static final String ITEM_NAME = "name";
        public static final String COST = "cost";
        public static final String ITEM_TYPE = "type";
        public static final String ITEM_AFFECTS = "affects";
        public static final String ITEM_AMOUNT = "amount";
        public static final String ITEM_TXT = "text";
        
        public static final String[] ITEM_TABLE = {ITEM_ID, ITEM_NAME, COST, ITEM_TYPE, ITEM_AFFECTS, ITEM_AMOUNT, ITEM_TXT};
        
        //Minigames Table
        public static final String MINIGAMES = "minigames";
        
        public static final String MINIGAME_ID = "id";
        public static final String MINIGAME_CREDITS = "credits";
        
        public static final String[] MINIGAME_TABLE = {MINIGAME_ID, MINIGAME_CREDITS};
        
        //Minigame Character Table
        public static final String MINIGAME_CHARACTER = "minigame_character";
        
        public static final String MINIGAME_GAME_ID = "game_id";
        public static final String MINIGAME_GID = "minigame_id";
        public static final String MINIGAME_CHAR_ID = "char_id";
        public static final String TIMES_PLAYED = "num_of_times_played";
        
        public static final String[] MINIGAME_CHARACTER_TABLE = {MINIGAME_GAME_ID, MINIGAME_GID, MINIGAME_CHAR_ID, TIMES_PLAYED};
        
        public final Context context;
        
        public DbHelper(Context context) {
                super(context, DB_NAME, null, DB_VERSION);
                this.context = context;
        }
        
    public SQLiteDatabase openDB() {
        db = this.getWritableDatabase();
        Log.d("openDB", "Database Opened");
        return db;
        
    }

        @Override
        public void onCreate(SQLiteDatabase database) {
                Log.d("onCreate", "Has been called");
                db = database;
                //create database tables
        db.execSQL("create table if not exists " + GAMES +
                        "(" + G_ID + " integer, "  + NUM_OF_PLAYERS + " integer," + CURRENT_PLAYER + " integer, " +
                        "primary key  (" + G_ID + "));");
        db.execSQL("create table if not exists " + CHARACTER +
                        "(" + CHARACTER_ID + " integer," + CHARACTER_TYPE + " text," + CHARACTER_NAME + " text, " + 
                        PICTURE + " text, primary key (" + CHARACTER_ID + "));");
        db.execSQL("create table if not exists " + GAME_CHARACTER +
                        "("+GAME_ID+" integer, "+CHAR_ID+" integer, "+PLAYER_ORDER+" integer, "+CREDITS+" integer, "+COINS+" integer, "+CURRENT_TILE+" integer, " +
                        "primary key ("+GAME_ID+", "+CHAR_ID+"));");
        db.execSQL("create table if not exists " + TILES +
                        "("+TILE_ID+" integer, "+TILE_TYPE+" text, "+TILE_NAME+" text, "+X_COORD+" integer, "+Y_COORD+" integer," +
                        "primary key  ("+TILE_ID+"));");
        db.execSQL("create table if not exists " + TILE_TILE + 
                        "("+TILE_ID1+" integer, "+TILE_ID2+" integer, "+DIRECTION+" text, " +
                        "primary key  ("+TILE_ID1+", "+TILE_ID2+"));");
        db.execSQL("create table if not exists "+ EVENTS +
                        "("+EVENT_ID+" integer, "+EVENT_TYPE+" text, "+EVENT_AFFECTS+" text, "+EVENT_AMOUNT+" integer, "+EVENT_TXT+" text, " +
                        "primary key  ("+EVENT_ID+"));");
        db.execSQL("create table if not exists " + ITEMS +
                        "("+ITEM_ID+" integer, "+ITEM_NAME+" text, "+COST+" integer, "+ITEM_TYPE+" text, "+ITEM_AFFECTS+" text, "+ITEM_AMOUNT+" integer, "+ITEM_TXT+" text," +
                        "primary key  ("+ITEM_ID+"));");
        db.execSQL("create table if not exists " + MINIGAMES +
                        "("+MINIGAME_ID+" integer, "+MINIGAME_CREDITS+" integer, " +
                        "primary key  ("+MINIGAME_ID+"));");
        db.execSQL("create table if not exists " + MINIGAME_CHARACTER +
                        "("+MINIGAME_GAME_ID+" integer, "+MINIGAME_GID+" integer, "+MINIGAME_CHAR_ID+" integer, "+TIMES_PLAYED+" integer," +
                        "primary key  ("+MINIGAME_GAME_ID+", "+MINIGAME_GID+", "+MINIGAME_CHAR_ID+"));");
        Log.d("onCreate", "Has been executed");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // TODO Auto-generated method stub
        }
        
        public void insertRow(int tableNum, String[] tableValues){
                // tableNum corresponds to which Table data is inserted to
                // tableValues is array of values to be put into row
                ContentValues values = new ContentValues();
                
                String tableName = null;
                
                switch(tableNum){
                        case 1: 
                                tableName = GAMES;
                                values.put(G_ID, tableValues[0]);
                                values.put(NUM_OF_PLAYERS, tableValues[1]);
                                values.put(CURRENT_PLAYER, tableValues[2]);
                                break;
                        case 2: 
                                tableName = CHARACTER;
                                values.put(CHARACTER_ID, tableValues[0]);
                                values.put(CHARACTER_TYPE, tableValues[1]);
                                values.put(CHARACTER_NAME, tableValues[2]);
                                values.put(PICTURE, tableValues[3]);
                                break;
                        case 3: 
                                tableName = GAME_CHARACTER;
                                values.put(GAME_ID, tableValues[0]);
                                values.put(CHAR_ID, tableValues[1]);
                                values.put(PLAYER_ORDER, tableValues[2]);
                                values.put(CREDITS, tableValues[3]);
                                values.put(COINS, tableValues[4]);
                                values.put(CURRENT_TILE, tableValues[5]);
                                break;
                        case 4: 
                                tableName = TILES;
                                values.put(TILE_ID, tableValues[0]);
                                values.put(TILE_TYPE, tableValues[1]);
                                values.put(TILE_NAME, tableValues[2]);
                                values.put(X_COORD, tableValues[3]);
                                values.put(Y_COORD, tableValues[4]);
                                break;
                        case 5: 
                                tableName = TILE_TILE;
                                values.put(TILE_ID1, tableValues[0]);
                                values.put(TILE_ID2, tableValues[1]);
                                values.put(DIRECTION, tableValues[2]);
                                break;
                        case 6: 
                                tableName = EVENTS;
                                values.put(EVENT_ID, tableValues[0]);
                                values.put(EVENT_TYPE, tableValues[1]);
                                values.put(EVENT_AFFECTS, tableValues[2]);
                                values.put(EVENT_AMOUNT, tableValues[3]);
                                values.put(EVENT_TXT, tableValues[4]);
                                break;
                        case 7: 
                                tableName = ITEMS;
                                values.put(ITEM_ID, tableValues[0]);
                                values.put(ITEM_NAME, tableValues[1]);
                                values.put(COST, tableValues[2]);
                                values.put(ITEM_TYPE, tableValues[3]);
                                values.put(ITEM_AFFECTS, tableValues[4]);
                                values.put(ITEM_AMOUNT, tableValues[5]);
                                values.put(ITEM_TXT, tableValues[6]);
                                break;
                        case 8: 
                                tableName = MINIGAMES;
                                values.put(MINIGAME_ID, tableValues[0]);
                                values.put(MINIGAME_CREDITS, tableValues[1]);
                                break;
                        case 9: 
                                tableName = MINIGAME_CHARACTER;
                                values.put(MINIGAME_GAME_ID, tableValues[0]);
                                values.put(MINIGAME_GID, tableValues[1]);
                                values.put(MINIGAME_CHAR_ID, tableValues[2]);
                                values.put(TIMES_PLAYED, tableValues[3]);
                                break;
                        default: 
                                Log.e("insertRow Error", "Not valid table number");
                                break;
                }
                Log.d("Table Name", tableName);
                Log.d("ContentValues", values.toString());
                try{
                        db.insert(tableName, null, values);
                        Log.d("Insert Method", "Method worked");
                }catch (Exception e){
                        Log.e("DB ERROR", e.toString()); // prints the error message to the log
                        e.printStackTrace(); // prints the stack trace to the log
                }
        }
        
        public void updateRow(int tableNum, String[] key, String[] newValues){
                ContentValues values = new ContentValues();                
                String tableName = null;
                switch(tableNum){
                        case 1: 
                                tableName = GAMES;
                                values.put(G_ID, newValues[0]);
                                values.put(NUM_OF_PLAYERS, newValues[1]);
                                values.put(CURRENT_PLAYER, newValues[2]);
                                db.update(tableName, values, G_ID + " = " + key[0], null);
                                break;
                        case 2: 
                                tableName = CHARACTER;
                                values.put(CHARACTER_ID, newValues[0]);
                                values.put(CHARACTER_TYPE, newValues[1]);
                                values.put(CHARACTER_NAME, newValues[2]);
                                values.put(PICTURE, newValues[3]);
                                db.update(tableName, values, CHARACTER_ID + " = " + key[0], null);
                                break;
                        case 3: 
                                tableName = GAME_CHARACTER;
                                values.put(GAME_ID, newValues[0]);
                                values.put(CHAR_ID, newValues[1]);
                                values.put(PLAYER_ORDER, newValues[2]);
                                values.put(CREDITS, newValues[3]);
                                values.put(COINS, newValues[4]);
                                values.put(CURRENT_TILE, newValues[5]);
                                db.update(tableName, values, GAME_ID + " = " + key[0] + 
                                                " AND " + CHAR_ID + " = " + key[1], null);
                                break;
                        case 4: 
                                tableName = TILES;
                                values.put(TILE_ID, newValues[0]);
                                values.put(TILE_TYPE, newValues[1]);
                                values.put(TILE_NAME, newValues[2]);
                                values.put(X_COORD, newValues[3]);
                                values.put(Y_COORD, newValues[4]);
                                db.update(tableName, values, TILE_ID + " = " + key[0], null);
                                break;
                        case 5: 
                                tableName = TILE_TILE;
                                values.put(TILE_ID1, newValues[0]);
                                values.put(TILE_ID2, newValues[1]);
                                values.put(DIRECTION, newValues[2]);
                                db.update(tableName, values, TILE_ID1 + " = " + key[0] + 
                                                " AND " + TILE_ID2 + " = " + key[1], null);
                                break;
                        case 6: 
                                tableName = EVENTS;
                                values.put(EVENT_ID, newValues[0]);
                                values.put(EVENT_TYPE, newValues[1]);
                                values.put(EVENT_AFFECTS, newValues[2]);
                                values.put(EVENT_AMOUNT, newValues[3]);
                                values.put(EVENT_TXT, newValues[4]);
                                db.update(tableName, values, EVENT_ID + " = " + key[0], null);
                                break;
                        case 7: 
                                tableName = ITEMS;
                                values.put(ITEM_ID, newValues[0]);
                                values.put(ITEM_NAME, newValues[1]);
                                values.put(COST, newValues[2]);
                                values.put(ITEM_TYPE, newValues[3]);
                                values.put(ITEM_AFFECTS, newValues[4]);
                                values.put(ITEM_AMOUNT, newValues[5]);
                                values.put(ITEM_TXT, newValues[6]);
                                db.update(tableName, values, ITEM_ID + " = " + key[0], null);
                                break;
                        case 8: 
                                tableName = MINIGAMES;
                                values.put(MINIGAME_ID, newValues[0]);
                                values.put(MINIGAME_CREDITS, newValues[1]);
                                db.update(tableName, values, MINIGAME_ID + " = " + key[0], null);
                                break;
                        case 9: 
                                tableName = MINIGAME_CHARACTER;
                                values.put(MINIGAME_GAME_ID, newValues[0]);
                                values.put(MINIGAME_GID, newValues[1]);
                                values.put(MINIGAME_CHAR_ID, newValues[2]);
                                values.put(TIMES_PLAYED, newValues[3]);
                                db.update(tableName, values, MINIGAME_GAME_ID + " = " + key[0] + 
                                                " AND " + MINIGAME_GID + " = " + key[1] + 
                                                " AND " + MINIGAME_CHAR_ID + " = " + key[2], null);
                                break;
                        default: 
                                Log.e("updateRow Error", "Not valid table number");
                                break;        
                }
                
        }
        
        public void deleteRow(int tableNum, String[] key){
                String tableName = null;
                
                switch (tableNum){
                        case 1: 
                                tableName = GAMES;
                                db.delete(tableName, G_ID + " = " + key[0], null);
                                break;
                        case 2: 
                                tableName = CHARACTER;
                                db.delete(tableName, CHARACTER_ID + " = " + key[0], null);
                                break;
                        case 3: 
                                tableName = GAME_CHARACTER;
                                db.delete(tableName, GAME_ID + " = " + key[0] + 
                                                " AND " + CHAR_ID + " = " + key[1], null);
                                break;
                        case 4: 
                                tableName = TILES;
                                db.delete(tableName, TILE_ID + " = " + key[0], null);
                                break;
                        case 5: 
                                tableName = TILE_TILE;
                                db.delete(tableName, TILE_ID1 + " = " + key[0] + 
                                                " AND " + TILE_ID2 + " = " + key[1], null);
                                break;
                        case 6: 
                                tableName = EVENTS;
                                db.delete(tableName, EVENT_ID + " = " + key[0], null);
                                break;
                        case 7: 
                                tableName = ITEMS;
                                db.delete(tableName, ITEM_ID + " = " + key[0], null);
                                break;
                        case 8: 
                                tableName = MINIGAMES;
                                db.delete(tableName, MINIGAME_ID + " = " + key[0], null);
                                break;
                        case 9: 
                                tableName = MINIGAME_CHARACTER;
                                db.delete(tableName, MINIGAME_GAME_ID + " = " + key[0] + 
                                                " AND " + MINIGAME_GID + " = " + key[1] +
                                                " AND " + MINIGAME_CHAR_ID + " = " + key[2], null);
                                break;
                        default: 
                                Log.e("deleteRow Error", "Not valid table number");
                                break;
                }
                
        }


}
