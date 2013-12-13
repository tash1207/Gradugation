package com.gradugation;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RandomEventGood extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("INTENT Before", "Did this thing work");
		Dialog d = new Dialog(this);
		d.setCancelable(true);
		d.setCanceledOnTouchOutside(true);
		d.setTitle("Wow!");
		TextView tv = new TextView(this);
		tv.setText("Finders Keepers!\nYou just found 5 coins.\nGO GATORS!");
		d.setContentView(tv);
		d.show();
		Log.d("INTENT After", "Did this thing work");
	}

}
