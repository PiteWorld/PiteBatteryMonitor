package com.pite.batterymonitor.adapter;

import java.util.List;

import com.ab.adapter.AbImageShowAdapter.ViewHolder;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.PCSInfos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class BATAAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	

	public BATAAdapter(Context context, List<String> list) {
		super();
		this.context = context;
		this.list = list;
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
		ViewHolder holder=null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_battrenditem, null);
			holder.tv_batdate = (TextView) convertView.findViewById(R.id.batDate);
			holder.tv_batV = (TextView) convertView.findViewById(R.id.batVoltage);
			holder.tv_batI = (TextView) convertView.findViewById(R.id.batelEctricity);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_batdate.setText(list.get(0));
		holder.tv_batV.setText(list.get(0));
		holder.tv_batI.setText(list.get(0));
		return convertView;
	}
 class ViewHolder{
		private TextView tv_batdate,tv_batV,tv_batI;
	}

}
