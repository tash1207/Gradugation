package com.gradugation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewGameActivity extends BaseActivity implements
		OnItemSelectedListener {

	private Spinner spinner1;
	private int numCharacters;

	public static final String NUMBER_OF_PLAYERS = "com.gradugation.number_of_players";

	// public static final int MAX_NUMBER_PLAYERS = 4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_game);
		addItemsOnSpinner1();
		addListenerOnSpinnerItemSelection();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
		return true;
	}

	public void addItemsOnSpinner1() {

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.number_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
	}

	public void chooseCharacter(View view) {
		Intent intent = new Intent(this, ChooseCharacterActivity.class);

		intent.putExtra(NUMBER_OF_PLAYERS, numCharacters);
		startActivity(intent);
	}

	public void addListenerOnSpinnerItemSelection() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		numCharacters = Integer.parseInt(parent.getItemAtPosition(pos)
				.toString());
	}

	public void onPause() {
		super.onPause();
		SongPlayer.stopSongDelayed();
	}

	protected void onResume() {
		super.onResume();
		SharedPreferences settings = getSharedPreferences(
				SettingsActivity.SOUND_PREFERENCE, 0);
		boolean isSoundOn = settings
				.getBoolean(SettingsActivity.SOUND_ON, true);

		if (isSoundOn) {
			SongPlayer.playSong();
		}
	}

	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}