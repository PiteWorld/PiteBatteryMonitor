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

public class LoadAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	

	public LoadAdapter(Context context, List<String> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.activity_loadtrenditem, null);
			holder.tv_loadtime = (TextView) convertView.findViewById(R.id.tv_load1);
			
			holder.tv_loadUa = (TextView) convertView.findViewById(R.id.tv_load2);
			holder.tv_loadUb = (TextView) convertView.findViewById(R.id.tv_load3);
			holder.tv_loadUc = (TextView) convertView.findViewById(R.id.tv_load4);
			
			holder.tv_loadIa = (TextView) convertView.findViewById(R.id.tv_load5);
			holder.tv_loadIb = (TextView) convertView.findViewById(R.id.tv_load6);
			holder.tv_loadIc = (TextView) convertView.findViewById(R.id.tv_load7);

			holder.tv_loadLa = (TextView) convertView.findViewById(R.id.tv_load8);
			holder.tv_loadLb = (TextView) convertView.findViewById(R.id.tv_load9);
			holder.tv_loadLc = (TextView) convertView.findViewById(R.id.tv_load10);
			
			holder.tv_loadPa = (TextView) convertView.findViewById(R.id.tv_load11);
			holder.tv_loadPb = (TextView) convertView.findViewById(R.id.tv_load12);
			holder.tv_loadPc = (TextView) convertView.findViewById(R.id.tv_load13);;
			
			holder.tv_loadSa = (TextView) convertView.findViewById(R.id.tv_load14);
			holder.tv_loadSb = (TextView) convertView.findViewById(R.id.tv_load15);
			holder.tv_loadSc = (TextView) convertView.findViewById(R.id.tv_load16);

			holder.tv_loadPfa = (TextView) convertView.findViewById(R.id.tv_load17);
			holder.tv_loadPfb = (TextView) convertView.findViewById(R.id.tv_load18);
			holder.tv_loadPfc = (TextView) convertView.findViewById(R.id.tv_load19);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_loadtime.setText(list.get(0));
		holder.tv_loadUa.setText(list.get(0));
		holder.tv_loadUb.setText(list.get(0));
		holder.tv_loadUc.setText(list.get(0));
		
		holder.tv_loadIa.setText(list.get(0));
		holder.tv_loadIb.setText(list.get(0));
		holder.tv_loadIc.setText(list.get(0));
		
		holder.tv_loadLa.setText(list.get(0));
		holder.tv_loadLb.setText(list.get(0));
		holder.tv_loadLc.setText(list.get(0));
		
		holder.tv_loadPa.setText(list.get(0));
		holder.tv_loadPb.setText(list.get(0));
		holder.tv_loadPc.setText(list.get(0));

		holder.tv_loadSa.setText(list.get(0));
		holder.tv_loadSb.setText(list.get(0));
		holder.tv_loadSc.setText(list.get(0));
		
		holder.tv_loadPfa.setText(list.get(0));
		holder.tv_loadPfb.setText(list.get(0));
		holder.tv_loadPfc.setText(list.get(0));
		return convertView;
	}
 class ViewHolder{
	 private TextView tv_loadtime, tv_loadUa, tv_loadUb, tv_loadUc, tv_loadIa, tv_loadIb, tv_loadIc, tv_loadLa, tv_loadLb, tv_loadLc,
		tv_loadPa, tv_loadPb, tv_loadPc, tv_loadSa, tv_loadSb, tv_loadSc, tv_loadPfa, tv_loadPfb, tv_loadPfc;
	}

}
