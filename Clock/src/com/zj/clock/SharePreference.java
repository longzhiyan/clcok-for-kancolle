package com.zj.clock;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreference {
	
	private static String filename = "save.ini";

	public static String load(Context context, String key){
	    SharedPreferences share = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
	    return share.getString(key, "default");
	}
	
	public static void save(Context context, String key, String value){
	    SharedPreferences share = context.getSharedPreferences(filename, Context.MODE_PRIVATE);   
	    SharedPreferences.Editor edit = share.edit(); 
	    edit.putString(key, value);  
	    edit.commit();
	}
	
	public static void remove(Context context, String key){
	    SharedPreferences share = context.getSharedPreferences(filename, Context.MODE_PRIVATE);   
	    SharedPreferences.Editor edit = share.edit(); 
	    edit.remove(key);  
	    edit.commit();
	}
	
	public static void clear(Context context){
	    SharedPreferences share = context.getSharedPreferences(filename, Context.MODE_PRIVATE);   
	    SharedPreferences.Editor edit = share.edit(); 
	    edit.clear();
	    edit.commit();
	}
	
}
