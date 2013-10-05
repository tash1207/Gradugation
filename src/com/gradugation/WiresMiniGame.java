package com.gradugation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class WiresMiniGame extends Activity {
	static int CREDITS_EARNED = 3;
	
	// Which wire you will find (1-4).
	private int gameNumber;
	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;
	
	boolean win;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		gameNumber = 1 + (int) (Math.random() * 4);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wires_mini_game);

		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder.setTitle("Press the button that goes to Wire "
				+ gameNumber + ".");
		alertDialogBuilder.setMessage("Press Continue to play.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// if this button is clicked, close
						// dialog box
						dialog.cancel();
						dialog.dismiss();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_wires_mini_game, menu);
		return true;
	}

	public void buttonOneClick(View view) {
		if (gameNumber == 1) {
			win();
		} else {
			lose();
		}
	}

	public void buttonTwoClick(View view) {
		if (gameNumber == 4) {
			win();
		} else {
			lose();
		}
	}

	public void buttonThreeClick(View view) {
		if (gameNumber == 2) {
			win();
		} else {
			lose();
		}
	}

	public void buttonFourClick(View view) {
		if (gameNumber == 3) {
			win();
		} else {
			lose();
		}
	}

	public void win() {
		//update the player's credits
		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder.setTitle("Congradugation! You win!");
		alertDialogBuilder.setMessage("Press Continue.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						WiresMiniGame.this.finish();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	public void lose() {
		alertDialogBuilder = new AlertDialog.Builder(this);

		// set title and message
		alertDialogBuilder.setTitle("Sorry, you lose.");
		alertDialogBuilder.setMessage("Press Continue.");
		alertDialogBuilder.setCancelable(false);

		// create continue button
		alertDialogBuilder.setNeutralButton("Continue",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						WiresMiniGame.this.finish();
					}
				});

		// create alert dialog
		alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	public void onPause() {
		super.onPause();
		alertDialog.dismiss();
	}
}