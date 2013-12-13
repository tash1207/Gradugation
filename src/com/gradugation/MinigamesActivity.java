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
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	
	public void minigameBenchPressButtonClick(View v) {
		Intent intent = new Intent(this, BenchPressMinigame.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	
	public void minigameWhackAFlyerButtonClick(View v) {
		Intent intent = new Intent(this, WhackAFlyerMiniGame.class);
		intent.putExtra("character_type", "GRADUGATOR");
		startActivity(intent);
	}
	
	public void minigameMathButtonClick(View v) {
		Intent intent = new Intent(this, MathMiniGame.class);
		intent.putExtra("character_type", "GRADUGATOR");
		startActivity(intent);
	}
	
	
	public void minigameColorButtonClick(View v) {
		Intent intent = new Intent(this, ColorMiniGame.class);
		intent.putExtra("character_type", "GRADUGATOR");
		startActivity(intent);
	}
	
	public void minigameFoodButtonClick(View v) {
		Intent intent = new Intent(this, FoodMiniGame.class);
		intent.putExtra("character_type", "GRADUGATOR");
		startActivity(intent);
	}
	
	public void minigameWaitInLineButtonClick(View v) {
		Intent intent = new Intent(this, WaitInLineMinigame.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	
	public void minigameFindTheMacButtonClick(View v) {
		Intent intent = new Intent(this, FindTheMacMinigame.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	
	public void moneyCountButtonClick(View v) {
		Intent intent = new Intent(this, moneyCount.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	public void libWestButtonClick(View v) {
		Intent intent = new Intent(this, LibraryBombardment.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
	public void BalanceButtonClick(View v) {
		Intent intent = new Intent(this, BalancingMiniGame.class);
		intent.putExtra("character_type", "GRADUGATOR");
    	startActivity(intent);
	}
}
