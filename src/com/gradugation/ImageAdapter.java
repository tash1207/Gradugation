package com.gradugation;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.AssetManager;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListAdapter;

import java.util.Random;

public class ImageAdapter extends BaseAdapter {
	
	
	private final int numSquares = 32;
	public int[][] values = {{0,0,0,0}, {0,0,0,0}, {0,0,0,0}, {0,0,0,0}};
    	
	private int next;
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return numSquares;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView textView;
    	if (convertView == null) {
    		textView = new TextView(mContext);
    		textView.setLayoutParams(new GridView.LayoutParams(125, 125));
    		textView.setGravity(Gravity.CENTER);
    		textView.setTextColor(0xff000000);
    		textView.setPadding(2,  2,  2,  2);
    		textView.setTextSize(50);
//    		Typeface myTypeface = Typeface.createFromAsset(getAssets(),"fonts/Algerian.ttf");
//    		textView.setTypeface(myTypeface);
    	} else {
    		textView = (TextView) convertView;
    	}
    	
    	
    	if (position >= 28){
        	textView.setBackgroundResource(mThumbIds[4]);
        	int colsum = 0;
        	for (int i = 0; i < 4; i++){
        		colsum+=values[i][position%7];
        	}
        	textView.setText(Integer.toString(colsum));
        }
        else if (position == 6 || position == 13 || position == 20 || position == 27){
        	textView.setBackgroundResource(mThumbIds[6]);
        	textView.setText(Integer.toString((position+1)/7));
        }
        else if (position == 5 || position == 12 || position == 19 || position == 26){
        	textView.setBackgroundResource(mThumbIds[5]);
        	textView.setVisibility(0);
        }
        else if (position == 4 || position == 11 || position == 18 || position == 25){
        	textView.setBackgroundResource(mThumbIds[4]);
        	int rowsum = 0;
        	for (int i = 0; i < 4; i++){
        		rowsum+=values[position/7][i];
        	}
        	textView.setText(Integer.toString(rowsum));
        }
        else{
        	Random generator = new Random();
        	next = generator.nextInt();
        	if(next < 0){
        		next*=-1;
        	}
        	next = 1+(next%4);
        	
        	Boolean found = false;
        	for (int i = 0; i < 4; i++)
        	{
        		if (colors[i]==next){
        			found = true;
        			break;
        		}
        	}
        	
        	if(!found){
		        int colorpicker = generator.nextInt();
		       
		        if(colorpicker < 0){
	        		colorpicker*=-1;
	        	}
	        	colorpicker = 1+(colorpicker%4);
		        
	        	while(picked[colorpicker-1]==true){
	        		colorpicker = generator.nextInt();
			        if(colorpicker < 0){
		        		colorpicker*=-1;
		        	}
		        	colorpicker = 1+(colorpicker%4);
	        	}
	        		
	        	picked[colorpicker-1] = true;
	        	colors[colorpicker-1] = next;
	        	
	        	if(values[position/7][position%7] == 0){
	        	textView.setBackgroundResource(mThumbIds[colors[next-1]-1]);
            	
	        	for(int i =0; i<4; i++){
            		if (colors[i]==next){
            			textView.setBackgroundResource(mThumbIds[i]);
            			break;
            		}	
            	}
	        	
	        	textView.setText(Integer.toString(next));
	        	textView.setTextColor(0xFF);
	        	
	        	values[position/7][position%7]=next;}
        	}
        	        	
        	else{
        		if(values[position/7][position%7] == 0){
        		for(int i=0; i<4; i++){
            		if (colors[i]==next){
            			textView.setBackgroundResource(mThumbIds[i]);
            			break;
            		}	
            	}
        		
        		textView.setText(Integer.toString(next));
        		textView.setTextColor(0xFF);
        		values[position/7][position%7]=next;}
        	}
        }
    	return textView;
    }
    

    private AssetManager getAssets() {
		// TODO Auto-generated method stub
		return null;
	}


	// references to our images
    private Integer[] mThumbIds = {
    		R.drawable.red_square, R.drawable.green_square, 
    		R.drawable.blue_square, R.drawable.yellow_square,  
            R.drawable.grey_square, R.drawable.empty_square, 
            R.drawable.orange_square
    };
    
    private Boolean[] picked = {false, false, false, false};
    private int[] colors ={5, 5, 5, 5};

	
}