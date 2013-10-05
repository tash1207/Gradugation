package com.gradugation;

import android.app.Application;

public class Character extends Application {
	
	//CharacterType  type; //need to enumerate this data type
	String name;
	//Image picture; //unsure of how pictures will be associated with characters
	int id; //this  will  be  between  1  and  4  to  designate  turn  order
	int credits; //year=credits/30+1.
	int coins;
	int moves;
	int turns;
	
	//Item the_item; //item class not created yet
	int bonus_coins;
	int bonus_moves;
	int bonus_turns;
	//Tile currentTile; //tiles not created yet
	//Tile previousTile;  //(cleared  at  end  of  turn) //tiles not created yet
	//CompassDirection  direction;  /*the  direction  the  player  is  travelling  in*/ //need to enumerate this data type
	
	public void addCredits(int credits)
	{
		this.credits+=credits;
	}
	
	public void addCoins(int coins)
	{
		this.coins+=coins;
	}
}

