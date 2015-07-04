package com.zj.clock;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClockBroadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
			intent = new Intent(context, ClockService.class);
			context.startService(intent);
		}
		
		//获取时间
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);

		CLog.d("hour:" + hour + " ,min:" + min);
		
		if(min == 0){
			String value = SharePreference.load(context, String.valueOf(hour));
			if(value.equals("default"))
				return;
			StringBuffer name = new StringBuffer();
			name.append("sdcard/Speech/").append(value).append("/").append(hour).append(".mp3");
			Audio.play(name.toString());
		}
	}

}
