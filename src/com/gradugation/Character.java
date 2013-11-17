package com.gradugation;

import java.io.Serializable;

import com.coordinates.MapCoordinate;
import com.coordinates.SpriteCoordinate;


public class Character implements Serializable {
        public enum CHARACTERTYPE {
            ALBERT(""), GRADUGATOR("splash2.png"), ENGINEER("engineer.png"), ATHLETE("athlete.png");
            
            private String imageName;
            
            private CHARACTERTYPE(String imageName) {
            	this.imageName = imageName;
            }
            public String getImageName() {
            	return this.imageName;
        	}
        }

        String name;
        CHARACTERTYPE character;
        
        boolean hasGraduated;
        
        int id, credits, coins;
        SpriteCoordinate location;
        
        public Character(CHARACTERTYPE characterType, String name, SpriteCoordinate location,
        		int charID, int credits, int coins) {
        	this.character = characterType;
        	this.name = name;
        	this.location = location;
        	this.id = charID;
        	this.credits = credits;
        	this.coins = coins;
        }
                 
        /**
         * 
         * @param characterType
         * @param coordinate SpriteCoordinate Where the sprite is in terms of screen size.
         * @param charID
         */
        public Character(CHARACTERTYPE characterType, SpriteCoordinate coordinate, int charID) {
        	this(characterType, "", coordinate, charID, 0, 0);
        }
        
        /**
         * 
         * @param characterType
         * @param coordinate MapCoordinate Where the sprite is in terms of the tile map.
         * @param charID
         */
        public Character(CHARACTERTYPE characterType, MapCoordinate coordinate, int charID) {
        	this(characterType, "", coordinate.mapToSprite(), charID, 0, 0);
        }
        
        /**
         * 
         * @param characterType
         * @param x float Where character is in terms of screen size x
         * @param y float Where character is in terms of screen size y
         * @param charID
         */
        public Character(CHARACTERTYPE characterType, float x, float y, int charID) {
        	this(characterType, "", new SpriteCoordinate(x,y), charID, 0, 0);
        }
        
        /**
         * 
         * @param characterType
         * @param x int Where character is in terms of tile map x
         * @param y int Where character is in terms of tile map y
         * @param charID
         */
        public Character(CHARACTERTYPE characterType, int x, int y, int charID) {
        	this(characterType, "", new MapCoordinate(x,y).mapToSprite(), charID, 0, 0);
        }
        
        public Character() {
            this(CHARACTERTYPE.ALBERT, "", new SpriteCoordinate(), 0, 0, 0);
        }
        
        public void setName(String name){
                this.name = name;
        }

        public String getName()        {
                return name;
        }

        public void setId(int id){
                this.id = id;
        }
        
        public int getId(){
                return id;
        }
        
        public void addCredits(int credits){
                this.credits+=credits;
        }
        
        public int getCredits(){
                return credits;
        }
        
        public void addCoins(int coins)        {
                this.coins+=coins;
        }
        
        public int getCoins()        {
                return coins;
        }
        
        public void setLocation(SpriteCoordinate location){
                this.location = location;
        }

        public SpriteCoordinate getSpriteLocation() {
        	return this.location;
        }
        
        public MapCoordinate getMapLocation() {
        	return this.location.spriteToMap();
        }
        
        public String getCharacterType() {
        	return this.character.getImageName();
        }
}        