package com.gradugation;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
//import android.media.AudioManager;

public class BackgroundMusicService extends Service implements MediaPlayer.OnPreparedListener {
    public static final String ACTION_PLAY = "com.gradugation.action.PLAY";
    MediaPlayer backgroundMusic = null;

    public int onStartCommand(Intent intent, int flags, int startId) {
    	super.onStartCommand(intent, flags, startId);
        if (intent.getAction().equals(ACTION_PLAY)) {
        	// need to worry about audio focus at some point
            backgroundMusic = new MediaPlayer();
            backgroundMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
				backgroundMusic.setDataSource(getApplicationContext(), Uri.parse("android.resource://com.gradugation/raw/gator_fight_song"));
				backgroundMusic.setOnPreparedListener(this);
	            backgroundMusic.prepareAsync(); // prepare async to not block main thread
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        return START_STICKY;
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
    	// TODO: when we save user settings, will need to check settings to see if sound is turned off
    	player.start();
    	player.setLooping(true);
    }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		backgroundMusic.stop();
		backgroundMusic.release();
	}
}