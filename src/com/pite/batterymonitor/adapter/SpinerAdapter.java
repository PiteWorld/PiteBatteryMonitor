package com.pite.batterymonitor.adapter;

import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.SPTimeUtils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SpinerAdapter extends BaseAdapter {
	
	public static interface IOnItemSelectListener{
        public void onItemClick(int pos);
    };
	//获取测试时间
	private List<SPTimeUtils> mdata_Time;
	private LayoutInflater mInflater;
	private Context context;

	public SpinerAdapter(List<SPTimeUtils> data_Time, Context context) {
		super();
		this.mdata_Time = mdata_Time;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	 public void refreshData(List<SPTimeUtils> dataTime, int selIndex){
		 mdata_Time = dataTime;
	        if (selIndex < 0){
	            selIndex = 0;
	        }
	        if (selIndex >= mdata_Time.size()){
	            selIndex = mdata_Time.size() - 1;
	        }
	    }

//	//使用for循环取出测试时间
//	private void ininTieme(){
//		for (int i = 0; i < data_Time.size(); i++) {
//			data_Time.add(SPTimeUtils.Data);
//		}
//	}
	
	@Override
	public int getCount() {
		return mdata_Time.size();
	}

	@Override
	public Object getItem(int position) {
		return mdata_Time.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder viewHolder;
	   convertView =LayoutInflater.from(context).inflate(
				  R.layout.spitem, null);
		
	        if (convertView == null) {

	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        return convertView;
	}
	private static  class ViewHolder{
		
	}
	
}