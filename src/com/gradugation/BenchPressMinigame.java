package com.gradugation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BenchPressMinigame extends Activity {
	// The number of reps needed in order to "pass" this minigame
	static int REPS_REQUIRED = 8;
	// The number of credits earned by completing this minigame
	static int CREDITS_EARNED = 3;
	
	CountDownTimer timer;
	TextView seconds_text, reps_text;
	int clicks = 0;
	int reps = 0;
	ImageView image;
	
	boolean game_finished = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.minigame_bench_press);
		
		seconds_text = (TextView) findViewById(R.id.bench_press_time);
		reps_text = (TextView) findViewById(R.id.bench_press_reps);
		image = (ImageView) findViewById(R.id.bench_press_image);

		timer = new CountDownTimer(10500, 1000) {
			public void onTick(long millisUntilFinished) {
				seconds_text.setText("Time Left: " + millisUntilFinished / 1000 + " secs");
			}
			
			public void onFinish() {
				seconds_text.setText("Time Left: 0 secs");
				game_finished = true;
				
				if (reps >= REPS_REQUIRED) {
					Toast.makeText(BenchPressMinigame.this, getString(R.string.bench_press_success, reps, 
							CREDITS_EARNED), Toast.LENGTH_LONG).show();
					// Code to add CREDITS_EARNED number of credits to the character
				}
				else {
					Toast.makeText(BenchPressMinigame.this, getString(R.string.bench_press_failure, reps), 
							Toast.LENGTH_LONG).show();
				}
				// Code to make continue button no longer disabled
			}
			
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.bench_press_instructions1));
        builder.setCancelable(false);
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        
                		AlertDialog.Builder builder = new AlertDialog.Builder(BenchPressMinigame.this);
                        builder.setMessage(getString(R.string.bench_press_instructions2, REPS_REQUIRED));
                        builder.setCancelable(false);
                        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                                
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        timer.start();
                                }
                        });
                        
                        AlertDialog dialog2 = builder.create();
                        dialog2.show();
                }
        });
        
        AlertDialog dialog1 = builder.create();
        dialog1.show();
	}
	
	public void benchPress(View view) {
		Character instance = Character.getInstance();
		if (!game_finished) {
			clicks++;
			reps = clicks / 4;
			
			reps_text.setText("Reps: " + reps);
			
			if (clicks % 4 == 0) {
				image.setImageResource(getResources().getIdentifier("bench_press3"+instance.getName().toLowerCase(), "drawable", getPackageName()));
			}
			else if (clicks == 2 || clicks % 2 == 1) {
				image.setImageResource(getResources().getIdentifier("bench_press2"+instance.getName().toLowerCase(), "drawable", getPackageName()));
			}
			else {
				image.setImageResource(getResources().getIdentifier("bench_press1"+instance.getName().toLowerCase(), "drawable", getPackageName()));
			}
		}
	}
	
	public void ContinueGame(View view) {
		if (game_finished) {
			Toast.makeText(this, "Now we would continue back to the main game board", 
					Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "The minigame is still going on so this button is disabled", 
					Toast.LENGTH_SHORT).show();
		}
	}
}
