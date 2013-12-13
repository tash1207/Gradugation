package com.gradugation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MinigamesActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minigames);
	}
	
	public void minigameWiresButtonClick(View v) {
		Intent intent = new Intent(this, WiresMiniGame.class);
    	startActivity(intent);
	}
	
	public void minigameBenchPressButtonClick(View v) {
		Intent intent = new Intent(this, BenchPressMinigame.class);
    	startActivity(intent);
	}
	
	public void minigameWhackAFlyerButtonClick(View v) {
		Intent intent = new Intent(this, WhackAFlyerMiniGame.class);
		startActivity(intent);
	}
	
	public void minigameMathButtonClick(View v) {
		Intent intent = new Intent(this, MathMiniGame.class);
		startActivity(intent);
	}
	
	
	public void minigameColorButtonClick(View v) {
		Intent intent = new Intent(this, ColorMiniGame.class);
		startActivity(intent);
	}
	
	public void minigameFoodButtonClick(View v) {
		Intent intent = new Intent(this, FoodMiniGame.class);
		startActivity(intent);
	}
}
