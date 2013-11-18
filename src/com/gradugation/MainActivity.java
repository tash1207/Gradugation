package com.gradugation;

import java.io.File;
import java.util.ArrayList;

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
        Log.d("TEST", "Database has been created");
        
        //Insert Some Data into Database
        String[] game = {"0","0","3","2"};
        String[] character1 = {"000","Engineer","Ahmad","7","7","105","45","3"};
        String[] character2 = {"001","Athlete","Dorian","7","8","105","45","3"};
        String[] character3 = {"002","Gradugator","Ashley","7","9","105","45","3"};
        String[] item = {"12","Scooter","500","Movement","Speed","700","Ridin 'round and I'm gettin it"};
        String[] minigame = {"1","6","4","2","1","3"};
        
        String[] gameNew = {"1","4","3","2"};
        String[] characterNew = {"4","Athlete","Ahmad","34","11","105","45","3"};
        String[] itemNew = {"12","Car","5100","Movement","Speed","700","Ridin 'round and I'm gettin it"};
        String[] minigameNew = {"1","6","1","2","3","4"};
        
        String[] gameKey = {"1"};
        String[] characterKey = {"8"};
        String[] itemKey = {"12"};
        String[] minigameKey = {"1","6"};
        
        //Insert Row method
        /*
        dbhelper.insertRow(1, game);
        dbhelper.insertRow(2, character1);
        dbhelper.insertRow(2, character2);
        dbhelper.insertRow(2, character3);
        dbhelper.insertRow(3, item);
        dbhelper.insertRow(4, minigame);
        */
        
        //Update Row Method
        
//        dbhelper.updateRow(1, gameKey, gameNew);
//        dbhelper.updateRow(2, characterKey, characterNew);
//        dbhelper.updateRow(3, itemKey, itemNew);
//        dbhelper.updateRow(4, minigameKey,minigameNew);
        
        
        //Delete Row Method
        
//        dbhelper.deleteRow(1, gameKey);
//        dbhelper.deleteRow(2, characterKey);
//        dbhelper.deleteRow(3, itemKey);
//        dbhelper.deleteRow(4, minigameKey);
        
        
        //getRow Method
       
//        ArrayList gameList = dbhelper.getRow(1, gameKey);
//        ArrayList characterList = dbhelper.getRow(2, characterKey);
//        ArrayList itemList = dbhelper.getRow(3, itemKey);
//        ArrayList minigameList = dbhelper.getRow(4, minigameKey);
//        
//        
//        for(int i = 0; i < gameList.size(); i++){
//            Log.d("getRow Game", (String)gameList.get(i));
//        }
//        for(int i = 0; i < characterList.size(); i++){
//        	Log.d("getRow Character",(String)characterList.get(i));
//        }
//        for(int i = 0; i < itemList.size(); i++){
//        	Log.d("getRow Item",(String)itemList.get(i));
//        }
//        for(int i = 0; i < minigameList.size(); i++){
//        	Log.d("getRow Minigame",(String)minigameList.get(i));
//        }
        
        //getGameCount
        Log.d("getGameCount", Integer.toString(dbhelper.getGameCount()));
        
        
        
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
