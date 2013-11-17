package com.gradugation;

import java.io.Serializable;


public class Character implements Serializable{
        public enum characterType {
            ALBERT, ALBERTA, ENGINEER, ATHLETE
        }
        String Charactertype; //need to enumerate this data type
        String name;
        
        boolean hasGraduated;
        
        int id; //this  will  be  between  1  and  4  to  designate  turn  order
        int credits; //year=credits/30+1.
        
        int coins;
        float x;
        float y;
        
        //Item the_item; //item class not created yet
        //        int bonus_coins;
        //        int bonus_moves;
        //        int bonus_turns;
        //Tile currentTile; //tiles not created yet
        //Tile previousTile;  //(cleared  at  end  of  turn) //tiles not created yet
        //CompassDirection  direction;  /*the  direction  the  player  is  traveling  in*/ //need to enumerate this data type
                 
        public Character(String CharacterType )
        {
        		Charactertype = CharacterType;
                name = "The Real Slim Shady";
                id = 0;
                credits = 0;
                coins = 0;
                x = 0;
                y = 0;
                
        }
        
        public Character()
        {
        		Charactertype = "ALBERT";
                name = "Alberto Gonzalez";
                id = 0;
                credits = 0;
                coins = 0;
                x = 0;
                y = 0;
                
        }
        
        protected void setName(String s){
                name = s;
        }
        public String getName()        {
                return name;
        }
        protected void setId(int id){
                this.id = id;
        }
        public int getId(){
                return id;
        }
        protected void addCredits(int credits){
                this.credits+=credits;
        }
        public int getCredits(){
                return credits;
        }
        protected void addCoins(int coins)        {
                this.coins+=coins;
        }
        public int getCoins()        {
                return coins;
        }
        protected void setX(float x){
                this.x = x;
        }
        protected void setY(float y){
                this.y = y;
        }
        public float getX()        {
                return x;
        }
        public float getY(){
                return y;
        }
        
        protected void setGraduation() {
        	hasGraduated = true;
        }
        
        protected boolean getGraduation() {
        	return hasGraduated;
        }
        
      
}        