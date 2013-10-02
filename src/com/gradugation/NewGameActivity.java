package com.gradugation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewGameActivity extends Activity {

	public static final String NUMBER_OF_PLAYERS = "com.gradugation.number_of_players";
	public static final int MAX_NUMBER_PLAYERS = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}
	
	public void chooseCharacter(View view) {
		Intent intent = new Intent(this, ChooseCharacterActivity.class);
    	EditText editText = (EditText) findViewById(R.id.number_of_players);
    	int numberOfPlayers = Integer.parseInt(editText.getText().toString());
    	
    	if (numberOfPlayers > MAX_NUMBER_PLAYERS || numberOfPlayers <= 0) {
    		editText.setError("Invalid number of players.");
    		return;
    	}
    	
    	intent.putExtra(NUMBER_OF_PLAYERS, numberOfPlayers);
    	startActivity(intent);
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
