package com.gradugation;

import android.content.Context;
import android.content.Intent;

public class SongPlayer {
	private static SongPlayer instance;
	private Intent backgroundMusicServiceIntent;
	private Context context;
	private boolean isPlaying;
	
	public SongPlayer(Context context) {
		backgroundMusicServiceIntent = new Intent(context, BackgroundMusicService.class);
        backgroundMusicServiceIntent.setAction(BackgroundMusicService.ACTION_PLAY);
        this.context = context;
        isPlaying = false;
	}
	
	public static void initializePlayer(Context context) {
		instance = new SongPlayer(context);
	}
	
	public static void playSong() {
		instance.play();
	}
	
	public void play() {
		if (!isPlaying) {
			context.startService(backgroundMusicServiceIntent);
			isPlaying = true;
		}
	}
	
	public static void stopSong() {
		instance.stop();
	}
	
	public void stop() {
		if (isPlaying) {
			context.stopService(backgroundMusicServiceIntent);
			isPlaying = false;
		}
	}
}
