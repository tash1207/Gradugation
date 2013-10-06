package com.gradugation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ChooseCharacterActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_character);
	}
	
	public void startGame(View view) {
    	Intent intent = new Intent(this, MainGameScreen.class);
    	startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_character, menu);
		return true;
	}

}
