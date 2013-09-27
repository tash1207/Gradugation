package com.gradugation;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ContinueActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_continue, menu);
        return true;
    }
    
    //saved game buttons will need to go to the saved game
    //public void savedGame1Button(View view) {}
}
