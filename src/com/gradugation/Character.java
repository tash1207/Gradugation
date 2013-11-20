package com.gradugation;

import java.io.Serializable;

import com.coordinates.MapCoordinate;
import com.coordinates.SpriteCoordinate;


public class Character implements Serializable {

        public enum CHARACTERTYPE {
            PREMED("med_student.png"), GRADUGATOR("splash2.png"), ENGINEER("engineer.png"), ATHLETE("athlete.png");
            
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
        
        boolean hasGraduated = false;
        
        int charId, credits, coins, gameId;
        SpriteCoordinate location;
        
        /**
         * 
         * @param characterType
         * @param name
         * @param location
         * @param charID
         * @param gameID
         * @param credits
         * @param coins
         */
        public Character(String characterType, String name, SpriteCoordinate location,
        		int charID, int gameID, int credits, int coins) {
        	this.character = CHARACTERTYPE.valueOf(characterType);
        	this.name = name;
        	this.location = location;
        	this.charId = charID;
        	this.gameId = gameID;
        	this.credits = credits;
        	this.coins = coins;
        }
                 
        /**
         * 
         * @param characterType
         * @param coordinate SpriteCoordinate Where the sprite is in terms of screen size.
         * @param charID
         * @param gameID
         */
        public Character(String characterType, SpriteCoordinate coordinate, int charID, int gameID) {
        	this(characterType, "", coordinate, charID, gameID, 0, 0);
        	
        }
        
        /**
         * 
         * @param characterType
         * @param coordinate MapCoordinate Where the sprite is in terms of the tile map.
         * @param charID
         * @param gameID
         */
        public Character(String characterType, MapCoordinate coordinate, int charID, int gameID) {
        	this(characterType, "", coordinate.mapToSprite(), charID, gameID, 0, 0);
        }
        
        /**
         * 
         * @param characterType
         * @param x float Where character is in terms of screen size x
         * @param y float Where character is in terms of screen size y
         * @param charID
         * @param gameID
         */
        public Character(String characterType, float x, float y, int charID, int gameID) {
        	this(characterType, "", new SpriteCoordinate(x,y), charID, gameID, 0, 0);
        }
        
        /**
         * 
         * @param characterType
         * @param x int Where character is in terms of tile map x
         * @param y int Where character is in terms of tile map y
         * @param charID
         * @param gameID
         */
        public Character(String characterType, int x, int y, int charID, int gameID) {
        	this(characterType, "", new MapCoordinate(x,y).mapToSprite(), charID, gameID, 0, 0);
        }
        
        /**
         * Sets everything to 0, and no string for name.
         */
        public Character() {
            this("", "", new SpriteCoordinate(), 0, 0, 0, 0);
        }
        
        public void setName(String name) {
                this.name = name;
        }

        public String getName() {
                return name;
        }
        
        public String getType() {
        	return name;
        }

        public void setId(int id) {
                this.charId = id;
        }
        
        public int getId() {
                return charId;
        }
        
        public int getGameId() {
        	return gameId;
        }
        
        public void addCredits(int credits) {
                this.credits+=credits;
        }
        
        public void setCredits(int credits) {
        	this.credits = credits;
        }
        
        public int getCredits() {
                return credits;
        }
        
        public void addCoins(int coins) {
                this.coins+=coins;
        }
        
        public void setCoins(int coins) {
        	this.coins = coins;
        }
        
        public int getCoins() {
                return coins;
        }
        
        public void setGraduated(boolean hasGraduated) {
        	this.hasGraduated = hasGraduated;
        }
        
        public boolean getGraduated() {
        	return hasGraduated;
        }
        
        /**
         * This is used for setting x,y in reference to screen size.
         * @param x
         * @param y
         */
        public void setLocation(float x, float y) {
        	this.location = new SpriteCoordinate(x,y);
        }

        public void setLocation(SpriteCoordinate location) {
                this.location = location;
        }
        
        public void setLocation(MapCoordinate location) {
        	this.location = location.mapToSprite();
        }

        public SpriteCoordinate getSpriteLocation() {
        	return this.location;
        }
        
        public MapCoordinate getMapLocation() {
            return this.location.spriteToMap();
    }
        
        public String getCharacterImage() {
        	return this.character.getImageName();
        }
}        
