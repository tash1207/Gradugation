package com.gradugation;

import org.andengine.util.math.MathUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class ColorMiniGame extends Activity {
	
	private enum TextColor {BLACK, BLUE, GREEN, RED, YELLOW, ORANGE, PURPLE, PINK};
	private TextColor[] colors = TextColor.values();
	private String currentColorText;
	private int currentColor;
	private TextView colorTextView;
	private int points;
	private int total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_mini_game);
		
		this.generateRandomColorString();
		
		this.points = 0;
		this.total = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_mini_game, menu);
		return true;
	}
	
	public void blackRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.BLACK)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_black_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void blueRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.BLUE)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_blue_radio)).setChecked(false);
		this.generateRandomColorString();	
	}
	
	public void greenRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.GREEN)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_green_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void redRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.RED)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_red_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void yellowRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.YELLOW)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_yellow_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void orangeRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.ORANGE)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_orange_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	
	public void purpleRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.PURPLE)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_purple_radio)).setChecked(false);
		this.generateRandomColorString();
	}
	public void pinkRadioClick(View v) {
		if (this.currentColor == this.getColor(TextColor.RED)) {
			this.points++;
		}
		
		((RadioButton)findViewById(R.id.color_mini_game_red_radio)).setChecked(false);
		this.generateRandomColorString();
	}

	private int getColor(TextColor color) {
		switch (color) {
			case BLACK:
				return Color.BLACK;//"android:color/black";
			case BLUE:
				return Color.BLUE;//"android:color/blue";
			case GREEN:
				return Color.GREEN;//"android:color/green";
			case RED:
				return Color.RED;//"android:color/purple";
			case YELLOW:
				return Color.YELLOW;//"android:color/yellow";
			case PINK:
				return Color.MAGENTA;
			case PURPLE:
				return getResources().getColor(R.color.purple);
			case ORANGE:
				return getResources().getColor(R.color.gator_orange);//"#E67022");//"android:color/orange";
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
}
