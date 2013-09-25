package com.gradugation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ChooseCharacterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_character);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_character, menu);
		return true;
	}

	public void chooseCharacter(View view) {
		
	}
	
	public void onPause() {
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
}
