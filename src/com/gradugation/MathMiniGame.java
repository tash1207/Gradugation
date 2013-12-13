package com.gradugation;

import java.io.IOException;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MathMiniGame extends Activity {

	CountDownTimer timer;
	TextView seconds_text;
	int num = 0;
	Boolean selected = false;
	int[][] colors;
	int[][] user_solution = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
	
	
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_math_mini_game);
        
	    final GridView gridview = (GridView) findViewById(R.id.gridview);
	    ImageAdapter theImageAdapter = new ImageAdapter(this);
	   
	    gridview.setAdapter(theImageAdapter);
	    
	    colors = theImageAdapter.values;
	   
        Toast.makeText(MathMiniGame.this, "Assign a number to each color so that the rows and columns add up to the numbers at the ends!", Toast.LENGTH_LONG).show();

        gridview.setOnItemClickListener(new OnItemClickListener() {
	        
	    	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	     
	            if (position == 6 || position == 13 || position == 20 || position == 27){
	            	num = (position+1)/7;
	            	selected = true;
	            	//Toast.makeText(MathMiniGame.this, "You have picked " + num, Toast.LENGTH_SHORT).show();
	            }
	            
	            
	            else if(selected&&((position >= 0 && position <= 3)||(position >= 7 && position <= 10)||(position >= 14 && position <= 17)||(position >= 21 && position <=24))){
	            	//Toast.makeText(MathMiniGame.this, "You have chosen to mark this color as " + num, Toast.LENGTH_SHORT).show();
	            	TextView t1 = (TextView) parent.getChildAt(position);
	            	
	            	int j1=position%7;
        			int i1=position/7;
        			int color = colors[i1][j1];
        			
        			for(int j=0; j<4;j++){
        				for(int i=0; i<4; i++){
        					if (color == colors[j][i])
	            			{
        						user_solution[j][i] = num;
	            				int pos2 = 7*j+i;
	            				TextView t2 = (TextView) parent.getChildAt(pos2);
	            				t2.setText(Integer.toString(num));
	            				t2.setTextColor(Color.BLACK);
	            			}
	            			
	            		}
	            	}
        			selected = false;            		
	            }
	            else{
	            	selected = false;
	            }
	        }
	    });
	}
	
	
	public void Finished(View v) {
    	Boolean correct = true;
        for (int i = 0; i<4;i++){
        	for(int j=0;j<4;j++){
        		if (colors[i][j] != user_solution[i][j]){
        			correct = false;
        			break;
        		}
        	}
        }

        Intent output = new Intent();
        if(correct){
        	//add three credits to the player passed in
        	Toast.makeText(MathMiniGame.this,  "Algebraic! You earned 3 credits!", Toast.LENGTH_LONG).show();
        	output.putExtra(Event.MATH_REQUEST_CODE+"", 3);
        }
        else{
        	Toast.makeText(MathMiniGame.this, "Sorry, try again later", Toast.LENGTH_LONG).show();
        	output.putExtra(Event.BENCH_PRESS_REQUEST_CODE+"", 0);
        }
        setResult(RESULT_OK, output);
        finish();
    }
}