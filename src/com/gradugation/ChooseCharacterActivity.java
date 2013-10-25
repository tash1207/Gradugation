package com.gradugation;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChooseCharacterActivity extends Activity {

	public static final String THE_PLAYERS = "com.gradugation.the_players";
	private RadioGroup radioGroup1;
	private RadioButton radioGroup1Button;
	private Button btnDisplay;
	ArrayList<Character> thePlayers = new ArrayList<Character>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_character);
		
		addListenerOnButton();
	}
	
	public void addListenerOnButton(){
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		btnDisplay = (Button) findViewById(R.id.btn_continue_game);
		Intent intent = getIntent();
		
		btnDisplay.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = getIntent();
			    int	numPlayers = intent.getIntExtra(NewGameActivity.NUMBER_OF_PLAYERS, 1);
				//get selected radio button from radioGroup
				int selectedId = radioGroup1.getCheckedRadioButtonId();
				
				//find the radiobutton by returned id
				radioGroup1Button = (RadioButton) findViewById(selectedId);
				
				Toast.makeText(ChooseCharacterActivity.this, radioGroup1Button.getText(), Toast.LENGTH_SHORT).show();
				
				Character thePlayer = new Character();
				thePlayer.setName((String)radioGroup1Button.getText());
				thePlayers.add(thePlayer);
				
				//choose another player here
				startGame(v);
				
			}
		});
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
	
	public void startGame(View view) {
    	Intent intent = new Intent(this, BenchPressMinigame.class);
    	intent.putExtra(THE_PLAYERS, (Serializable)thePlayers);
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_character, menu);
		return true;
	}

}
