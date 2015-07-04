package com.zj.clock;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;  
	
	private List<String> list;
	
	private Context context;
	
	private String timePoint;
	
	public ListAdapter(Context context, List<String> stl, String timePoint) {
		super();  
        this.mInflater = LayoutInflater.from(context);  
        this.list = stl;
        this.context = context;
        this.timePoint = timePoint;
	}
	
    public final class ViewHolder {  
        public TextView name;  
        public CheckBox checkbox;
    }  

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;  
        if (convertView == null) {  
            viewHolder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.select_item, parent, false);  
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);  
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBtn);
            convertView.setTag(viewHolder);
        } else {  
            viewHolder = (ViewHolder) convertView.getTag();  
        }
        
        String text = list.get(position).split("=")[0];
        viewHolder.name.setText(text);
        
        String value = SharePreference.load(context, timePoint);
        String id = list.get(position).split("=")[1];
        if(id.equals(value))
        	viewHolder.checkbox.setChecked(true);
        
        return convertView;  
	}
}
