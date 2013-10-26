package com.gradugation;

import android.graphics.Color;
import org.andengine.util.math.MathUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ColorMiniGame extends Activity {
	
	private TextView colorTextView;
	private enum TextColor {BLACK, BLUE, GREEN, RED, YELLOW, ORANGE, PURPLE};
	private TextColor[] colors = TextColor.values();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_mini_game);
		
		String colorText = getColorString(colors[MathUtils.random(colors.length)]);
		int colorOfText = getColorSetString(colors[MathUtils.random(colors.length)]);
		colorTextView = (TextView) findViewById(R.id.color_mini_game_text);
		colorTextView.setText(colorText);
		colorTextView.setTextColor(colorOfText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_mini_game, menu);
		return true;
	}

	private int getColorSetString(TextColor color) {
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
				return "RED";
			case YELLOW:
				return "Yellow";
			case ORANGE:
				return "Orange";
			case PURPLE:
				return "Purple";
			default:
				return "";
		}
	}
}
