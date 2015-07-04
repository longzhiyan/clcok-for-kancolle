package com.zj.clock;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class SelectActivity extends Activity{

	private ListView selectList = null;
	
	private String timePoint = null;
	
	private List<String> lists;
	
	private ListAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_layout);
		
		Bundle extras = getIntent().getExtras(); 
		timePoint = String.valueOf(extras.getLong("No."));
		
		lists = Config.read();
		
		initComponent();
	}
	
	/**
	 * 初始化组件
	 */
	private void initComponent(){
		selectList = (ListView) findViewById(R.id.listview);
		adapter = new ListAdapter(this, lists, timePoint);
		selectList.setOnItemClickListener(listener);
		selectList.setAdapter(adapter);
	}
	
	private OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String number = lists.get(position).split("=")[1];
			StringBuffer name = new StringBuffer();
			name.append("sdcard/Speech/").append(number).append("/").append(timePoint).append(".mp3");
			
			if(!isExists(name.toString()))
				return;
			
			SharePreference.save(SelectActivity.this, timePoint, number);
			
			for(int i = 0; i < selectList.getChildCount(); i++){
				LinearLayout ll = (LinearLayout)selectList.getChildAt(i);
				CheckBox cb = (CheckBox)ll.findViewById(R.id.checkBtn);

				if(i == position){
					boolean isChecked = cb.isChecked();
					if(isChecked)
						SharePreference.remove(SelectActivity.this, timePoint);
					else
						Audio.play(name.toString());
					
					cb.setChecked(!isChecked);
				}
				else
					cb.setChecked(false);
			}
		}
	};
	
	private boolean isExists(String filename){
		File file = new File(filename);
		if(!file.exists()){
			Toast.makeText(SelectActivity.this, "该音效文件不存在！", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	protected void onDestroy() {
		super.onDestroy();
		Audio.stop();
	};
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		Intent intent = new Intent(SelectActivity.this, ClockActivity.class);
		finish();
		startActivity(intent);
	}
}
