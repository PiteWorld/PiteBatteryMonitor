package com.example.pcs.fragment;

import java.util.ArrayList;
import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.adapter.BATAAdapter;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
/**
 *电池数据  趋势数据分析
 * @author Administrator
 *
 */
import android.widget.TextView;

public class BatTrendDataFragment extends Fragment {
	private List<String> list = null;
	private TextView tv_batdate, tv_batV, tv_batI;
	private ListView bat_listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.activity_battrenddata, null);
		tv_batdate = (TextView) view.findViewById(R.id.tv_batDate);
		tv_batV = (TextView) view.findViewById(R.id.tv_batVoltage);
		tv_batI = (TextView) view.findViewById(R.id.tv_batElectricity);
		bat_listview = (ListView) view.findViewById(R.id.bat_listview);
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add(i + "");
		}
		bat_listview.setAdapter(new BATAAdapter(getActivity(), list));
		return view;
	}

}
