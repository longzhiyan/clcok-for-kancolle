//package com.zj.clock;
//
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.Context;
//import android.content.Intent;
//

/**
 * 桌面插件
 */

//public class ClockWidget extends AppWidgetProvider{
//	
//	// 每添加一个小插件调用一次，跟onDeleted对应
//	@Override
//	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//		CLog.d("onUpdate");
//		Intent intent = new Intent(context, ClockService.class);
//		context.startService(intent);
//	}
//	
//	// 第一个小插件添加时调用，跟onDisabled对应
//	@Override
//	public void onEnabled(Context context) {
//		super.onEnabled(context);
//		CLog.d("onEnabled");
//	}
//
//	// 最后一个小插件删除时会调用
//	@Override
//	public void onDisabled(Context context) {
//		super.onDisabled(context);
//
//		CLog.d("onDisabled");
//		Intent intent = new Intent(context, ClockService.class);
//		context.stopService(intent);
//	}
//	
//	// 任何添加删除操作都会调用
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		super.onReceive(context, intent);
//		CLog.d("onReceive");
//	}
//	
//	// 小插件每删除一个调用一次
//	@Override
//	public void onDeleted(Context context, int[] appWidgetIds) {
//		super.onDeleted(context, appWidgetIds);
//		CLog.d("onDeleted");
//	}
//}
