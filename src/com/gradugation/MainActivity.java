package com.gradugation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SongPlayer.initializePlayer(this);
		
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
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
    
    public void wiresMiniGameClick(View view){
    	Intent intent = new Intent(this, WiresMiniGame.class);
    	startActivity(intent);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	SongPlayer.stopSongDelayed();
    }
    
    protected void onResume() {
    	super.onResume();
    	SharedPreferences settings = getSharedPreferences(SettingsActivity.SOUND_PREFERENCE, 0);
		boolean isSoundOn = settings.getBoolean(SettingsActivity.SOUND_ON, true);
		
		if (isSoundOn) {
			SongPlayer.playSong();
		}
    }
    
    @Override
    protected void onDestroy(){
       super.onDestroy();
       SongPlayer.stopSong();
       
    }
    
}
