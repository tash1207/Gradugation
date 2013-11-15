package com.gradugation;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
        SQLiteDatabase db;
        public static final String DB_NAME = "gradugation";
        public static final int DB_VERSION = 3;
        
        //Game Table
        public static final String GAMES = "games";
        
        public static final String G_ID = "id";
        public static final String CHAR_BASE_ID = "char_base_id";
        public static final String NUM_OF_PLAYERS = "num_of_players";
        public static final String CURRENT_PLAYER = "current_player";
        
        public static final String[] GAME_TABLE = {G_ID, CHAR_BASE_ID, NUM_OF_PLAYERS, CURRENT_PLAYER};
        
        
        //Character Table
        public static final String CHARACTER = "character";
        
        public static final String CHARACTER_ID = "id";
        public static final String CHARACTER_TYPE = "type";
        public static final String CHARACTER_NAME = "name";
        public static final String X_COORD = "x_coord";
        public static final String Y_COORD = "y_coord";
        public static final String CREDITS = "credits";
        public static final String COINS = "coins";
        public static final String PLAYER_ORDER = "player_order";
        
        public static final String[] CHARACTER_TABLE = {CHARACTER_ID, CHARACTER_TYPE, CHARACTER_NAME, X_COORD, Y_COORD, CREDITS, COINS, PLAYER_ORDER};
              
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
        
        //Minigame Character Table
        public static final String MINIGAME = "minigame";
        
        public static final String MINIGAME_GAME_ID = "game_id";
        public static final String MINIGAME_GID = "minigame_id";
        public static final String CHAR_ID1 = "char_id1";
        public static final String CHAR_ID2 = "char_id2";
        public static final String CHAR_ID3 = "char_id3";
        public static final String CHAR_ID4 = "char_id4";
        
        public static final String[] MINIGAME_CHARACTER_TABLE = {MINIGAME_GAME_ID, MINIGAME_GID, CHAR_ID1, CHAR_ID2, CHAR_ID3, CHAR_ID4};
        
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
                /*
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
                        */
                db.execSQL("create table if not exists " + GAMES +
                		"(" + G_ID + " integer, " + CHAR_BASE_ID + " integer, " + NUM_OF_PLAYERS + " integer," + CURRENT_PLAYER + " integer, " +
                        "primary key  (" + G_ID + "));");
                db.execSQL("create table if not exists " + CHARACTER +
                		"(" + CHARACTER_ID + " integer, " + CHARACTER_TYPE + " text, " + CHARACTER_NAME + " TEXT," + X_COORD + " integer, " +
                		Y_COORD + " integer, " + CREDITS + " integer, " + COINS + " integer, " + PLAYER_ORDER + " integer, " +
                        "primary key  (" + CHARACTER_ID + "));");
                db.execSQL("create table if not exists " + ITEMS +
                		"(" + ITEM_ID + " integer, " + ITEM_NAME + " text, " + COST + " integer," + ITEM_TYPE + " text, " + 
                		ITEM_AFFECTS + " text, " + ITEM_AMOUNT + " integer, " + ITEM_TXT + " text, " +
                        "primary key  (" + ITEM_ID + "));");
                db.execSQL("create table if not exists " + MINIGAME +
                		"(" + MINIGAME_GAME_ID + " integer, " + MINIGAME_GID + " integer, " + 
                		CHAR_ID1 + " integer," + CHAR_ID2 + " integer, " + CHAR_ID3 + " integer," + CHAR_ID4 + " integer," +
                        "primary key  ("+MINIGAME_GAME_ID+", "+MINIGAME_GID+"));");
                
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
                                values.put(CHAR_BASE_ID, tableValues[1]);
                                values.put(NUM_OF_PLAYERS, tableValues[2]);
                                values.put(CURRENT_PLAYER, tableValues[3]);
                                break;
                        case 2: 
                                tableName = CHARACTER;
                                values.put(CHARACTER_ID, tableValues[0]);
                                values.put(CHARACTER_TYPE, tableValues[1]);
                                values.put(CHARACTER_NAME, tableValues[2]);
                                values.put(X_COORD, tableValues[3]);
                                values.put(Y_COORD, tableValues[4]);
                                values.put(CREDITS, tableValues[5]);
                                values.put(COINS, tableValues[6]);
                                values.put(PLAYER_ORDER, tableValues[7]);
                                break;
                        case 3: 
                                tableName = ITEMS;
                                values.put(ITEM_ID, tableValues[0]);
                                values.put(ITEM_NAME, tableValues[1]);
                                values.put(COST, tableValues[2]);
                                values.put(ITEM_TYPE, tableValues[3]);
                                values.put(ITEM_AFFECTS, tableValues[4]);
                                values.put(ITEM_AMOUNT, tableValues[5]);
                                values.put(ITEM_TXT, tableValues[6]);
                                break;
                        case 4: 
                                tableName = MINIGAME;
                                values.put(MINIGAME_GAME_ID, tableValues[0]);
                                values.put(MINIGAME_GID, tableValues[1]);
                                values.put(CHAR_ID1, tableValues[2]);
                                values.put(CHAR_ID2, tableValues[3]);
                                values.put(CHAR_ID3, tableValues[4]);
                                values.put(CHAR_ID4, tableValues[5]);
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
                            	values.put(CHAR_BASE_ID, newValues[1]);
                            	values.put(NUM_OF_PLAYERS, newValues[2]);
                            	values.put(CURRENT_PLAYER, newValues[3]);
                                db.update(tableName, values, G_ID + " = " + key[0], null);
                                break;
                        case 2: 
	                        	tableName = CHARACTER;
	                            values.put(CHARACTER_ID, newValues[0]);
	                            values.put(CHARACTER_TYPE, newValues[1]);
	                            values.put(CHARACTER_NAME, newValues[2]);
	                            values.put(X_COORD, newValues[3]);
	                            values.put(Y_COORD, newValues[4]);
	                            values.put(CREDITS, newValues[5]);
	                            values.put(COINS, newValues[6]);
	                            values.put(PLAYER_ORDER, newValues[7]);
                                db.update(tableName, values, CHARACTER_ID + " = " + key[0], null);
                                break;
                        case 3: 
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
                        case 4: 
	                        	tableName = MINIGAME;
	                            values.put(MINIGAME_GAME_ID, newValues[0]);
	                            values.put(MINIGAME_GID, newValues[1]);
	                            values.put(CHAR_ID1, newValues[2]);
	                            values.put(CHAR_ID2, newValues[3]);
	                            values.put(CHAR_ID3, newValues[4]);
	                            values.put(CHAR_ID4, newValues[5]);
                                db.update(tableName, values, MINIGAME_GAME_ID + " = " + key[0] + 
                                                " AND " + MINIGAME_GID + " = " + key[1], null);
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
                                tableName = ITEMS;
                                db.delete(tableName, ITEM_ID + " = " + key[0], null);
                                break;
                        case 4: 
                                tableName = MINIGAME;
                                db.delete(tableName, MINIGAME_GAME_ID + " = " + key[0] + 
                                                " AND " + MINIGAME_GID + " = " + key[1], null);
                                break;
                        default: 
                                Log.e("deleteRow Error", "Not valid table number");
                                break;
                }
                
        }
        
        public ArrayList<Object> getRow(int tableNum, String[] key){
        	ArrayList<Object> rowArray = new ArrayList<Object>();
        	Cursor cursor;
        	
        	try{
                String tableName = null;
                
                switch (tableNum){
                        case 1: 
                                tableName = GAMES;
                                cursor = db.query
                        				(
                        						tableName,
                        						null,
                        						G_ID + " = " + key[0],
                        						null, null, null, null, null
                        				);
                        		cursor.moveToFirst();
                        		if (!cursor.isAfterLast())
                        		{
                        			do
                        			{
                        				rowArray.add(cursor.getString(0));
                        				rowArray.add(cursor.getString(1));
                        				rowArray.add(cursor.getString(2));
                        				rowArray.add(cursor.getString(3));
                        			}
                        			while (cursor.moveToNext());
                        		}
                        		cursor.close();
                                break;
                        case 2: 
                                tableName = CHARACTER;
                                cursor = db.query
                        				(
                        						tableName,
                        						null,
                        						CHARACTER_ID + " = " + key[0],
                        						null, null, null, null, null
                        				);
                                cursor.moveToFirst();
                        		if (!cursor.isAfterLast())
                        		{
                        			do
                        			{
                        				rowArray.add(cursor.getString(0));
                        				rowArray.add(cursor.getString(1));
                        				rowArray.add(cursor.getString(2));
                        				rowArray.add(cursor.getString(3));
                        				rowArray.add(cursor.getString(4));
                        				rowArray.add(cursor.getString(5));
                        				rowArray.add(cursor.getString(6));
                        				rowArray.add(cursor.getString(7));
                        			}
                        			while (cursor.moveToNext());
                        		}
                        		cursor.close();
                                break;
                        case 3: 
                                tableName = ITEMS;
                                cursor = db.query
                        				(
                        						tableName,
                        						null,
                        						ITEM_ID + " = " + key[0],
                        						null, null, null, null, null
                        				);
                                cursor.moveToFirst();
                        		if (!cursor.isAfterLast())
                        		{
                        			do
                        			{
                        				rowArray.add(cursor.getString(0));
                        				rowArray.add(cursor.getString(1));
                        				rowArray.add(cursor.getString(2));
                        				rowArray.add(cursor.getString(3));
                        				rowArray.add(cursor.getString(4));
                        				rowArray.add(cursor.getString(5));
                        				rowArray.add(cursor.getString(6));
                        			}
                        			while (cursor.moveToNext());
                        		}
                        		cursor.close();
                                break;
                        case 4: 
                                tableName = MINIGAME;
                                cursor = db.query
                        				(
                        						tableName,
                        						null,
                        						MINIGAME_GAME_ID + " = " + key[0] + " AND " + MINIGAME_GID + " = " + key[1],
                        						null, null, null, null, null
                        				);
                                cursor.moveToFirst();
                        		if (!cursor.isAfterLast())
                        		{
                        			do
                        			{
                        				rowArray.add(cursor.getString(0));
                        				rowArray.add(cursor.getString(1));
                        				rowArray.add(cursor.getString(2));
                        				rowArray.add(cursor.getString(3));
                        				rowArray.add(cursor.getString(4));
                        				rowArray.add(cursor.getString(5));
                        			}
                        			while (cursor.moveToNext());
                        		}
                        		cursor.close();
                                break;
                        default: 
                                Log.e("getRow Error", "Not valid table number");
                                break;
                }
        	}catch (SQLException e) {
        		Log.e("DB ERROR", e.toString());
        		e.printStackTrace();
        	}
        	return rowArray;
        }

}

                
