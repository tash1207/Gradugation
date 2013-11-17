package com.gradugation;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coordinates.MapCoordinate;
import com.coordinates.SpriteCoordinate;

public class ChooseCharacterActivity extends BaseActivity {
		private DbHelper dbhelper;
		private Event event;
		
        public static final String THE_PLAYERS = "com.gradugation.the_players";
        private RadioGroup radioGroup;
        private RadioButton radioGroup1Button;
        
        private ImageView characterImage;
        private TextView characterAttributes;
    	private final MapCoordinate centerMap = new MapCoordinate(7,7);
    	private int numPlayers;

        private static MapCoordinate[] defaultLocation;
        

        ArrayList<Character> thePlayers = new ArrayList<Character>();
        int playersChosen = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_choose_character);
                
                Intent intent = getIntent();
                numPlayers = intent.getIntExtra(NewGameActivity.NUMBER_OF_PLAYERS, 1);
                defaultLocation = new MapCoordinate[numPlayers];
                
                for (int i = 0; i < numPlayers; i++) {
                	MapCoordinate offset = new MapCoordinate();
        			if (i == 2 || i == 3) {
        				offset.setY(1);
        			}
        			if (i % 2 == 1) {
        				offset.setX(1);
        			}
        			defaultLocation[i] = offset.add(centerMap);
                }
                
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
        	
            
            
            if (playersChosen > numPlayers-1) {                                        
                    startGame();
            }
            else if (playersChosen == numPlayers-1) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    
                    //find the radiobutton by returned id
                    radioGroup1Button = (RadioButton) findViewById(selectedId);
                    
                    String type = (String)radioGroup1Button.getText();
                    // need to grab game id from database when we are saving this
                    int gameID = 0;
                    Character thePlayer = new Character(type.toUpperCase(), type, defaultLocation[playersChosen].mapToSprite(), playersChosen, gameID, 0, 0);
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
                    
                    String type = (String)radioGroup1Button.getText();
                    // need to grab game id from database when we are saving this
                    int gameID = 0;
                    Character thePlayer = new Character(type.toUpperCase(), type, defaultLocation[playersChosen].mapToSprite(), playersChosen, gameID, 0, 0);

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
            String[] game = {"0","0",Integer.toString(playersChosen),"0"};
        	dbhelper.insertRow(1, game);
        	
        	//Item Table
            String[] item = {"0",null,"0",null,null,"0",null};
        	dbhelper.insertRow(3, item);
        	
        	//For loop assigning each minigame ID to each minigame row
        	//Minigame Table
        	event = new Event();
	        String[] minigame1 = {"0",Integer.toString(event.BENCH_PRESS_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame2 = {"0",Integer.toString(event.WIRES_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame3 = {"0",Integer.toString(event.WAIT_IN_LINE_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame4 = {"0",Integer.toString(event.WHACK_AFLYER_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame5 = {"0",Integer.toString(event.COLOR_REQUEST_CODE),"0","0","0","0"};
	        String[] minigame6 = {"0",Integer.toString(event.GRADUATION_REQUEST_CODE),"0","0","0","0"};
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