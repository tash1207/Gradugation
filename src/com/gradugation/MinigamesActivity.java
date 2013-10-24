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

	public void minigameBalanceButtonClick(View v) {
		Intent intent = new Intent(this, BalanceMinigame.class);
		startActivity(intent);
	}
	
	public void minigameWhackAFlyerButtonClick(View v) {
		Intent intent = new Intent(this, WhackAFlyerGame.class);
		startActivity(intent);
	}
}
