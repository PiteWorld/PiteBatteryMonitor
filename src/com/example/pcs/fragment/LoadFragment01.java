package com.example.pcs.fragment;

import java.util.List;

import com.example.pcs.PCSMainActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadFragment01 extends Fragment {
	private TextView load_tv, load_tv2, load_tv3, load_tv4, load_tv5, load_tv6, load_tv7, load_tv8, load_tv9, load_tv10,
	load_tv11, load_tv12, load_tv13, load_tv14, load_tv15, load_tv16, load_tv17, load_tv18, load_tv19;
	private List<PCSInfos> info=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.loadfm01, null);
		initData(view);
		info = PCSMainActivity.info;
		return view;
		}

	private void initData(View view ) {
		load_tv = (TextView)  view.findViewById(R.id.load_tv);
		load_tv2 = (TextView) view.findViewById(R.id.load_tv2);
		load_tv3 = (TextView) view.findViewById(R.id.load_tv3);
		load_tv4 = (TextView) view.findViewById(R.id.load_tv4);
		load_tv5 = (TextView) view.findViewById(R.id.load_tv5);
		load_tv6 = (TextView) view.findViewById(R.id.load_tv6);
		load_tv7 = (TextView) view.findViewById(R.id.load_tv7);
		load_tv8 = (TextView) view.findViewById(R.id.load_tv8);
		load_tv9 = (TextView) view.findViewById(R.id.load_tv9);
		load_tv10 = (TextView) view.findViewById(R.id.load_tv10);
		load_tv11 = (TextView) view.findViewById(R.id.load_tv11);
		load_tv12 = (TextView) view. findViewById(R.id.load_tv12);
		load_tv13 = (TextView) view.findViewById(R.id.load_tv13);
		load_tv14 = (TextView) view.findViewById(R.id.load_tv14);
		load_tv15 = (TextView) view.findViewById(R.id.load_tv15);
		load_tv16 = (TextView) view.findViewById(R.id.load_tv16);
		load_tv17 = (TextView) view.findViewById(R.id.load_tv17);
		load_tv18 = (TextView) view.findViewById(R.id.load_tv18);
		load_tv19 = (TextView) view.findViewById(R.id.load_tv19);
		// 获取请求结果
		SetValue();
		
	}
	private void SetValue(){
		if (info != null) {
			load_tv.setText(info.get(0).getSystemoutputUV());
			load_tv2.setText(info.get(0).getSystemoutputUI());
			load_tv3.setText(info.get(0).getSystemoutputLoadUP());
			load_tv4.setText(info.get(0).getSystemoutputUYP());
			load_tv5.setText(info.get(0).getSystemoutputUSP());
			// PF值
			load_tv6.setText(info.get(0).getSystemoutputUPF());

			load_tv7.setText(info.get(0).getSystemoutputVV());
			load_tv8.setText(info.get(0).getSystemoutputVI());
			load_tv9.setText(info.get(0).getSystemoutputLoadVP());
			load_tv10.setText(info.get(0).getSystemoutputVYP());
			load_tv11.setText(info.get(0).getSystemoutputVSP());
			// PF值
			load_tv12.setText(info.get(0).getSystemoutputVPF());
			load_tv13.setText(info.get(0).getSystemoutputFrequency());

			load_tv14.setText(info.get(0).getSystemoutputWV());
			load_tv15.setText(info.get(0).getSystemoutputWI());
			load_tv16.setText(info.get(0).getSystemoutputLoadWP());
			load_tv17.setText(info.get(0).getSystemoutputWYP());
			load_tv18.setText(info.get(0).getSystemoutputWSP());
			// PF值
			load_tv19.setText(info.get(0).getSystemoutputWPF());
			Log.e("tag", "%%%%%%%%%%%%%%%%%%%%%%%%");
		}
		
		
	}

}
