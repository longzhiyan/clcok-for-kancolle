package com.zj.clock;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;

public class ClockService extends Service{

	static RemoteViews views;
	static AppWidgetManager appWidgetManager;
	static ComponentName componentName;
	private BroadcastReceiver receiver;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		receiver = new ClockBroadcast();
		IntentFilter filter=new IntentFilter(Intent.ACTION_TIME_TICK);
		filter.addAction(Intent.ACTION_BOOT_COMPLETED);
		registerReceiver(receiver, filter);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

}
