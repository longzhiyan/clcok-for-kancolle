package com.zj.clock;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ClockActivity extends Activity{
	
	public final static String filePath = "sdcard/Speech";
	
	private ListView showTimePonit = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		
		initComponent();
		
		//deleteFile();
		
		createFile();
	}
	
	/**
	 * 初始化组件
	 */
	private void initComponent(){
		Button open = (Button)super.findViewById(R.id.openBtn);
		Button close = (Button)super.findViewById(R.id.clostBtn);
		Button cancel = (Button)super.findViewById(R.id.cancelBtn);
		
		open.setOnClickListener(btnListener);
		close.setOnClickListener(btnListener);
		cancel.setOnClickListener(btnListener);
		
		//listview
		showTimePonit = (ListView) findViewById(R.id.listview);
		showTimePonit.setOnItemClickListener(itemListner);
		SimpleAdapter adapter = new SimpleAdapter(this, initData(), R.layout.item1, new String[]{"time","hint"}, new int[]{R.id.time, R.id.hint});  
		showTimePonit.setAdapter(adapter);
	}
	
	private List<HashMap<String,Object>> initData(){
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();  
		
		String[] ex = new String[24];
		List<String> lists = Config.read();
		int count = 0;
		for(int i = 0; i < 24; i++){
			String a = SharePreference.load(this, String.valueOf(i));
			
			count = 0;
			for(int j = 0; j < lists.size(); j++){
				String s[] = lists.get(j).split("=");
				String m = s[0];
				String n = s[1];
				if(n.equals(a)){
					ex[i] = m;
					break;
				}
				count++;
			}
			
			if(count == lists.size())
				ex[i] = "";
			
			HashMap<String,Object>map = new HashMap<String,Object>();
			map.put("time", tiems(i));
			map.put("hint", ex[i]);
			data.add(map); 
		}
		return data;
	}
	
	private String tiems(int index){
		StringBuffer sb = new StringBuffer();
		if(index < 10)
			return sb.append("0").append(index).append(":00").toString();
		else
			return sb.append(index).append(":00").toString();
	}
	
	private Intent intent = null;
	private OnClickListener btnListener = new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.openBtn:
					intent = new Intent(ClockActivity.this, ClockService.class);
					ClockActivity.this.startService(intent);
					
					Toast.makeText(ClockActivity.this, "您开启了报时服务！", Toast.LENGTH_SHORT).show();
					break;
				case R.id.clostBtn:
					intent = new Intent(ClockActivity.this, ClockService.class);
					ClockActivity.this.stopService(intent);
					
					Toast.makeText(ClockActivity.this, "您关闭了报时服务！", Toast.LENGTH_SHORT).show();
					break;
				case R.id.cancelBtn:
					SharePreference.clear(ClockActivity.this);
					
					intent = new Intent(ClockActivity.this, ClockService.class);
					ClockActivity.this.stopService(intent);
					
					int len = showTimePonit.getChildCount();
					for(int i = 0; i < len; i++){
						LinearLayout ll = (LinearLayout)showTimePonit.getChildAt(i);
						TextView tv = (TextView)ll.findViewById(R.id.hint);
						tv.setText("");
					}
					
					Toast.makeText(ClockActivity.this, "您关闭了报时服务！", Toast.LENGTH_SHORT).show();
					break;
				default:break;
			}
		}
	};
	
	private OnItemClickListener itemListner = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(ClockActivity.this, SelectActivity.class);
			intent.putExtra("No.", id);
			finish();
			startActivity(intent);
		}
	};
	
	public void createFile(){
		File file = new File(filePath);
		boolean isExist = file.exists();
		if(!isExist){
			CLog.d("creating file!!!");
			file.mkdirs();
			
			copyResToSdcard(filePath);
			
			Config.create();
		}
    }
	
	public void copyResToSdcard(String name){
		Field[] raw = R.raw.class.getFields(); 
	    for (Field r : raw) { 
	    	try {
	    		int id = getResources().getIdentifier(r.getName(), "raw", getPackageName());
	    		
	    		String folder = r.getName().substring(0, 2).equals("jg") ? "001" : "002";
	    		StringBuffer filename = new StringBuffer();
	    		filename.append(filePath).append("/").append(folder);
	    		File file = new File(filename.toString());
	    		if(!file.exists())
	    			file.mkdir();
	    		
	    		String nickname = r.getName().substring(2);
	    		StringBuffer path = new StringBuffer();
	    		path.append(name).append("/").append(folder).append("/").append(nickname).append(".mp3");
    			BufferedOutputStream bufEcrivain = new BufferedOutputStream((new FileOutputStream(new File(path.toString())))); 
    			BufferedInputStream VideoReader = new BufferedInputStream(getResources().openRawResource(id)); 
    			byte[] buff = new byte[1024]; 
    			int len; 
    			while( (len = VideoReader.read(buff)) > 0 ){ 
    				bufEcrivain.write(buff,0,len); 
    			} 
    			bufEcrivain.flush();
    			bufEcrivain.close();
    			VideoReader.close();
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	     }
	}
	
	protected void onDestroy() {
		super.onDestroy();
		Audio.stop();
	};
	
	/**
	 * 测试用
	 */
	public void deleteFile(){
		Field[] raw = R.raw.class.getFields(); 
		String name = "";
		for (Field r : raw) { 
	    	try { 
	    		CLog.d("R.raw." + r.getName()); 
	    		name = filePath + "/001/" + r.getName().substring(2) + ".mp3";
	    		File file = new File(name);
	    		if(file.exists()){
	    			file.delete();
	    			CLog.d("delete");
	    		}
	    		
	    		name = filePath + "/002/" + r.getName().substring(2) + ".mp3";
	    		file = new File(name);
	    		if(file.exists()){
	    			file.delete();
	    			CLog.d("delete");
	    		}
	    		
	         } catch (Exception e) { 
	             e.printStackTrace(); 
	         } 
		}
		
		name = filePath + "/001";
		File file = new File(name);
		file.delete();
		CLog.d("delete");
		
		name = filePath + "/002";
		file = new File(name);
		file.delete();
		CLog.d("delete");
		
		name = filePath + "/config.ini";
		file = new File(name);
		file.delete();
		CLog.d("delete");
		
		name = filePath;
		file = new File(name);
		file.delete();
		CLog.d("delete");
	}
}
