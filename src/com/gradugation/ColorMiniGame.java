package com.gradugation;

import org.andengine.util.math.MathUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ColorMiniGame extends Activity {
	
	private static final float PERCENTAGE_REQUIRED = .8f;
	private static final int CREDITS_EARNED = 3;
	private enum TextColor {BLACK, BLUE, GREEN, RED, YELLOW, ORANGE, PURPLE, PINK};
	private TextColor[] colors = TextColor.values();
	private String currentColorText;
	private int currentColor;
	private float points, total;
	private TextView colorTextView, seconds_text, reps_text;
	private CountDownTimer timer;
	private boolean gameFinished;
	private AlertDialog.Builder builder;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_mini_game);
		
		this.generateRandomColorString();
		
		seconds_text = (TextView)(findViewById(R.id.color_mini_game_time_left));
		reps_text = (TextView)(findViewById(R.id.color_mini_game_points));
		
		this.points = 0.0f;
		this.total = 0.0f;
		
		timer = new CountDownTimer(10500, 1000) {
			public void onTick(long millisUntilFinished) {
				seconds_text.setText("Time Left: " + millisUntilFinished / 1000 + " secs");
			}
			
			public void onFinish() {
				seconds_text.setText("Time Left: 0 secs");
				gameFinished = true;
				
				if (points/total >= PERCENTAGE_REQUIRED) {
					Toast.makeText(ColorMiniGame.this, getString(R.string.color_mini_game_success, (int)points, 
							(int)total, CREDITS_EARNED), Toast.LENGTH_LONG).show();
					// Code to add CREDITS_EARNED number of credits to the character
				}
				else {
					Toast.makeText(ColorMiniGame.this, getString(R.string.color_mini_game_failure, (int)points,
							(int)total), Toast.LENGTH_LONG).show();
				}
				Button continueButton = (Button)findViewById(R.id.color_mini_game_continue_button);
				continueButton.setVisibility(View.VISIBLE);
				((RadioButton)findViewById(R.id.color_mini_game_black_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_blue_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_green_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_purple_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_pink_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_red_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_orange_radio)).setClickable(false);
				((RadioButton)findViewById(R.id.color_mini_game_yellow_radio)).setClickable(false);
				
				
			}
			
		};
		
		builder = new AlertDialog.Builder(this);
		int needToPass = (int)(PERCENTAGE_REQUIRED * 100);
        builder.setMessage(getString(R.string.color_mini_game_instructions, needToPass));
        builder.setCancelable(false);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            //@Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); 
                timer.start();
            }
        });
        
        dialog = builder.create();
        dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_mini_game, menu);
		return true;
	}
	
	public void continueButtonClick(View v) {
		super.finish();
		timer.cancel();
		dialog.dismiss();
	}

	public void blackRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.BLACK)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_black_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void blueRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.BLUE)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_blue_radio)).setChecked(false);
		this.generateRandomColorString();	
	}
	
	public void greenRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.GREEN)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_green_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void redRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.RED)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_red_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void yellowRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.YELLOW)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_yellow_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void orangeRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.ORANGE)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_orange_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void purpleRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.PURPLE)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_purple_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	public void pinkRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.PINK)) {
			this.points++;
			reps_text.setText("Points: " + (int)this.points);
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_pink_radio)).setChecked(false);
		this.generateRandomColorString();
	}

	private int getColor(TextColor color) {
		switch (color) {
			case BLACK:
				return Color.BLACK;
			case BLUE:
				return Color.BLUE;
			case GREEN:
				return Color.GREEN;
			case RED:
				return Color.RED;
			case YELLOW:
				return Color.YELLOW;
			case PINK:
				return getResources().getColor(R.color.pink);
			case PURPLE:
				return getResources().getColor(R.color.purple);
			case ORANGE:
				return getResources().getColor(R.color.orange);
			default:
				return 0;
		}
	}
	
	private String getColorString(TextColor color) {
		switch (color) {
			case BLACK:
				return "Black";
			case BLUE:
				return "Blue";
			case GREEN:
				return "Green";
			case RED:
				return "Red";
			case YELLOW:
				return "Yellow";
			case ORANGE:
				return "Orange";
			case PURPLE:
				return "Purple";
			case PINK:
				return "Pink";
			default:
				return "";
		}
	}
	
	private void generateRandomColorString() {
		this.total++;
		
		this.currentColorText = this.getColorString(this.colors[MathUtils.random(this.colors.length)]);
		this.currentColor = this.getColor(colors[MathUtils.random(this.colors.length)]);
		this.colorTextView = (TextView) findViewById(R.id.color_mini_game_text);
		this.colorTextView.setText(this.currentColorText);
		this.colorTextView.setTextColor(this.currentColor);
	}
	
	public void onPause() {
		super.onPause();
		dialog.dismiss();
		timer.cancel();
	}
	 
}
