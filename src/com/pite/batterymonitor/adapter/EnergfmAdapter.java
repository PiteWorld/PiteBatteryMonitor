package com.pite.batterymonitor.adapter;

import java.util.List;

import com.pite.batterymonitor.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EnergfmAdapter extends BaseAdapter {
	private List<String> list;
	private Context context;

	public EnergfmAdapter(List<String> list,Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder  = null ;
		if(convertView == null)
		{
			convertView = View.inflate(context, R.layout.energfmitem, null);
			holder = new ViewHolder();
			holder.energ_item1 = (TextView) convertView.findViewById(R.id.energ_item1);
			holder.energ_item2 = (TextView) convertView.findViewById(R.id.energ_item2);
			holder.energ_item3 = (TextView) convertView.findViewById(R.id.energ_item3);
			holder.energ_item4 = (TextView) convertView.findViewById(R.id.energ_item4);
			holder.energ_item5 = (TextView) convertView.findViewById(R.id.energ_item5);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		holder.energ_item1.setText(list.get(0));
		holder.energ_item2.setText(list.get(0));
		holder.energ_item3.setText(list.get(0));
		holder.energ_item4.setText(list.get(0));
		holder.energ_item5.setText(list.get(0));
		return convertView;
	}
	class ViewHolder{
		private TextView energ_item1,energ_item2,energ_item3,energ_item4,energ_item5;
	}
}
