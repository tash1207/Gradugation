package com.gradugation;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseCharacterActivity extends BaseActivity {
		private DbHelper dbhelper;
		private Event event;
		
        public static final String THE_PLAYERS = "com.gradugation.the_players";
        private RadioGroup radioGroup;
        private RadioButton radioGroup1Button;
        
        private ImageView characterImage;
        private TextView characterAttributes;
        
        ArrayList<Character> thePlayers = new ArrayList<Character>();
        int playersChosen = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_choose_character);
                
                radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
                characterImage = (ImageView) findViewById(R.id.character_image);
                characterAttributes = (TextView) findViewById(R.id.character_attributes);
                
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radio0) {
							characterImage.setImageResource(R.drawable.athlete_big);
							characterAttributes.setText(getString(R.string.attributes_athlete));
						}
						else if (checkedId == R.id.radio1) {
							characterImage.setImageResource(R.drawable.engineer_big);
							characterAttributes.setText(getString(R.string.attributes_engineer));
						}
						else if (checkedId == R.id.radio2) {
							characterImage.setImageResource(R.drawable.gradugator_big);
							characterAttributes.setText(getString(R.string.attributes_gradugator));
						}
						
					}
				});
        }
        
        public void btnBackClicked(View view) {
        	super.onBackPressed();
        }
        
        public void btnContinueClicked(View view) {
        	Intent intent = getIntent();
            int numPlayers = intent.getIntExtra(NewGameActivity.NUMBER_OF_PLAYERS, 1);
            if (playersChosen > numPlayers-1) {                                        
                    startGame();
            }
            else if (playersChosen == numPlayers-1) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    
                    //find the radiobutton by returned id
                    radioGroup1Button = (RadioButton) findViewById(selectedId);
                    
                    Character thePlayer = new Character((String)radioGroup1Button.getText());
                    thePlayer.setName((String)radioGroup1Button.getText());
                    thePlayers.add(thePlayer);
                    
                    
                    Toast.makeText(ChooseCharacterActivity.this,  
                    		"Player " + Integer.toString(playersChosen+1) + " is " + 
                    		thePlayers.get(playersChosen).getName(), Toast.LENGTH_SHORT).show();
                    playersChosen++;
                    startGame();
            }
            else {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    
                    //find the radiobutton by returned id
                    radioGroup1Button = (RadioButton) findViewById(selectedId);
                    
                    Character thePlayer = new Character((String)radioGroup1Button.getText());
                    thePlayer.setName((String)radioGroup1Button.getText());
                    thePlayers.add(thePlayer);
                    
                    Toast.makeText(ChooseCharacterActivity.this,  
                    		"Player " + Integer.toString(playersChosen+1) + " is " + 
                    		thePlayers.get(playersChosen).getName(), Toast.LENGTH_SHORT).show();
                    playersChosen++;
            }
        }
        
public void startGame() {
        	
        	// need to have as many character objects as characters to pass to main game screen
        	// character needs: SpriteCoordiate location, credits, coins, id for use in db, id for use in game
        	// game id, characterNames, characterTypes, function to get the image for the character based on their names
        	// characterLocation is default position
        	// need credits/coints = 0 for each player
        	// need to generate gameId for use in db
        	
        	dbhelper = new DbHelper(this);
            SQLiteDatabase db = dbhelper.openDB();
            
            //Game Table
            String[] game = {Integer.toString(dbhelper.getGameCount()),"0",Integer.toString(playersChosen),"0"};
        	dbhelper.insertRow(1, game);
        	
        	//Item Table
            String[] item = {"0",null,"0",null,null,"0",null};
        	dbhelper.insertRow(3, item);
        	
        	//For loop assigning each minigame ID to each minigame row
        	//Minigame Table
        	event = new Event();
	        String[] minigame1 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.BENCH_PRESS_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame2 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.WIRES_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame3 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.WAIT_IN_LINE_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame4 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.WHACK_AFLYER_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame5 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.COLOR_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame6 = {Integer.toString(dbhelper.getGameCount()),Integer.toString(event.GRADUATION_REQUEST_CODE),"0","0","0","0"};
	        dbhelper.insertRow(4, minigame1);
	        dbhelper.insertRow(4, minigame2);
	        dbhelper.insertRow(4, minigame3);
	        dbhelper.insertRow(4, minigame4);
	        dbhelper.insertRow(4, minigame5);
	        dbhelper.insertRow(4, minigame6);
   
	        //Character Table
            for (int i = 0; i < playersChosen; i++){
            	//Convert variable i to string
	            String[] character = {Integer.toString(i),null,null,"0","0","0","0",Integer.toString(i+1)};	
            	dbhelper.insertRow(2, character);
            }
            Log.d("# of Games", Integer.toString(dbhelper.getGameCount()));
            
        	dbhelper.close();
        	
            Intent intent = new Intent(this, MainGameScreen.class);
            intent.putExtra(THE_PLAYERS, (Serializable)thePlayers);
            startActivity(intent);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.choose_character, menu);
                return true;
        }

}