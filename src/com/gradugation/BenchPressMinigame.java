package com.gradugation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BenchPressMinigame extends Activity {
	ImageView image;
	TextView reps_text;
	int clicks = 0;
	int reps = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.minigame_bench_press);
		
		image = (ImageView) findViewById(R.id.bench_press_image);
		reps_text = (TextView) findViewById(R.id.bench_press_reps);
	}
	
	public void benchPress(View view) {
		clicks++;
		reps = clicks / 4;
		
		reps_text.setText("Reps: " + reps);
		
		if (clicks % 4 == 0) {
			image.setImageResource(R.drawable.bench_press3);
		}
		else if (clicks == 2 || clicks % 2 == 1) {
			image.setImageResource(R.drawable.bench_press2);
		}
		else {
			image.setImageResource(R.drawable.bench_press1);
		}
	}
}
