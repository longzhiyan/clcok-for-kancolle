package com.zj.clock;

import android.util.Log;

public class CLog {

	private static String TAG = "CLOCK";
	
	public static void d(String message){
		Log.d(TAG, message);
	}
	
	public static void d(int message){
		Log.d(TAG, String.valueOf(message));
	}
	
	public static void d(long message){
		Log.d(TAG, String.valueOf(message));
	}
}
