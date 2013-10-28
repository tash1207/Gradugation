package com.gradugation;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseActivity {
	private DbHelper dbhelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SongPlayer.initializePlayer(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        //Open Database
        dbhelper = new DbHelper(this);
        SQLiteDatabase db = dbhelper.openDB();
        //dbhelper.onCreate(db);
        Log.d("TEST", "Database has been created");
        
        //Insert Some Data into Database
        String[] tableValues = {"128","1","2"};
        
        //Update method 
        
        String[] key = {"128"};
        String[] newValues = {"128","16","2009"};
//        dbhelper.insertRow(1, tableValues);
//        Log.d("TEST", "Update method stopped here");
//        dbhelper.updateRow(1, key, newValues);
        
        Log.d("Update Method", "Method has worked");
        //dbhelper.insertRow(1, tableValues);
        Cursor c = db.rawQuery("SELECT * FROM games;", null);
        while (c.moveToNext()){
	        Log.d("TEST", "Data has been put into database");
	        Log.d("RESULTS", c.getString(c.getColumnIndex("id")));
	        Log.d("RESULTS", c.getString(c.getColumnIndex("num_of_players")));
	        Log.d("RESULTS", c.getString(c.getColumnIndex("current_player")));
	        
        }   
        /*
        String[] key = {"65"};
     
        dbhelper.deleteRow(1, key);
        while (c.moveToNext()){
	        //c.moveToFirst();
	        Log.d("RESULTS", c.getString(c.getColumnIndex("id")));
	        Log.d("RESULTS", c.getString(c.getColumnIndex("num_of_players")));
	        Log.d("RESULTS", c.getString(c.getColumnIndex("current_player")));
	        Log.d("TEST", "Data has been put into database");
        }
        */
        
        //Close Database
        Log.d("TEST", "stopped before db.close");
        db.close();
        Log.d("TEST", "stopped after db.close");
        dbhelper.close();
        Log.d("TEST", "Database has been closed");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void newGameButtonClick(View view) {
    	Intent intent = new Intent(this, NewGameActivity.class);
    	startActivity(intent);
    }
    
    public void settingsButtonClick(View view) {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	
    }
    
    public void minigamesButtonClick(View view){
    	Intent intent = new Intent(this, MinigamesActivity.class);
    	startActivity(intent);
    }

    public void continueGameButtonClick(View view) {
    	Intent intent = new Intent(this, ContinueActivity.class);
    	startActivity(intent);
    }

    public void howToPlayClick(View view) {
    	Intent intent = new Intent(this, HowToPlayActivity.class);
    	startActivity(intent);
    }

}
