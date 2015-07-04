package com.zj.clock;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Audio {

	private static MediaPlayer mMediaPlayer;
	
	public static void play(String name){
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
		}

		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mMediaPlayer.release();
					mMediaPlayer = null;
				}
			});
		} catch (Exception e) {
		}
	}
	
	public static void stop(){
		if(mMediaPlayer==null)
			return;
		
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.stop();
		mMediaPlayer.release();
		mMediaPlayer = null;
	}
	
}
