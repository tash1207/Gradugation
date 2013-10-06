package com.gradugation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends BaseActivity {

	public final static String SOUND_PREFERENCE = "com.gradugation.SOUND_PREFERENCE";
	public final static String SOUND_ON = "com.gradugation.SOUND_ON";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SOUND_PREFERENCE, 0);
		boolean isSoundOn = settings.getBoolean(SettingsActivity.SOUND_ON, true);
		CheckBox soundButton = (CheckBox) findViewById(R.id.soundCheckBox);
		soundButton.setChecked(isSoundOn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void soundCheckBoxClick(View view) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(SOUND_PREFERENCE, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		CheckBox soundButton = (CheckBox) findViewById(R.id.soundCheckBox);
		editor.putBoolean(SOUND_ON, soundButton.isChecked());

	    editor.commit();
	    
	    if (soundButton.isChecked()) {
	    	SongPlayer.playSong();
	    } else {
	    	SongPlayer.stopSong();
	    }
	}

}
