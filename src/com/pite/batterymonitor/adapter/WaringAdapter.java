package com.pite.batterymonitor.adapter;

import java.util.HashMap;
import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.BatteryWaringMsg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class WaringAdapter extends BaseAdapter {

	private Context context;
	private List<BatteryWaringMsg> list;
	public static HashMap<Integer, Boolean> hash = null;
	private String dealID;

	public WaringAdapter(Context context, List<BatteryWaringMsg> list) {
		this.context = context;
		this.list = list;
		hash = new HashMap<Integer, Boolean>();
		initData();
	}

	// 初始化hash
	private void initData() {
		for (int i = 0; i < list.size(); i++) {
			hash.put(i, false);
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.war_item, null);
			holder.ck_choose = (CheckBox) convertView.findViewById(R.id.war_item_check);
			holder.tv_time = (TextView) convertView.findViewById(R.id.war_item_time);
			holder.tv_event = (TextView) convertView.findViewById(R.id.war_item_event);

			// holder.tv_caozuo=(Button)convertView.findViewById(R.id.war_item_caozuo);
			// 操作按钮
			// holder.tv_caozuo.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// getHash().put(position, true);
			// new AlertDialog.Builder(context).setTitle(R.string.string104)
			// .setMessage(list.get(position).getAlarmMsg())
			// .setPositiveButton(R.string.yes, new
			// DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// Toast.makeText(context, "你选择了是", 0).show();
			//// holder.tv_caozuo.setText("yichu");
			//
			//
			// }
			// }).setNeutralButton(R.string.no, new
			// DialogInterface.OnClickListener() {
			//
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// getHash().put(position, false);
			// }
			// }).create().show();
			// }
			// });
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// 获取告警时间 告警信息
		if (list.get(position).getDealID().equals("0")) {
			holder.tv_time.setText(list.get(position).getAlarmtime());
			holder.tv_event.setText(list.get(position).getAlarmMsg());
			holder.ck_choose.setChecked(hash.get(position));
		} else {
			holder.tv_time.setText(list.get(position).getAlarmtime());
			holder.tv_event.setText(list.get(position).getAlarmMsg());
			holder.ck_choose.setChecked(false);
			holder.ck_choose.setClickable(false);
		}
		return convertView;
	}

	public static class ViewHolder {
		public CheckBox ck_choose;
		public TextView tv_time, tv_event;
	}

	public static HashMap<Integer, Boolean> getHash() {
		return hash;
	}

	public static void setHash(HashMap<Integer, Boolean> hash) {
		WaringAdapter.hash = hash;
	}
}
