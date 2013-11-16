package com.gradugation;

// Store game information

public class SavedGame {
	private String gameNumber;
	private int iconID;
	//time and date of last save
	private String timeDate;
	
	public SavedGame(String gameNumber) {
		super();
		this.gameNumber = gameNumber;
	}
	
	public SavedGame(String gameNumber, int iconID) {
		super();
		this.gameNumber = gameNumber;
		this.iconID = iconID;
	}

	public SavedGame(String gameNumber, int iconID, String timeDate) {
		super();
		this.gameNumber = gameNumber;
		this.iconID = iconID;
		this.timeDate = timeDate;
	}

	public String getGameNumber() {
		return gameNumber;
	}
	public int getIconID() {
		return iconID;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}
}
