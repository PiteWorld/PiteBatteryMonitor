package com.example.pcs.fragment;

import java.util.ArrayList;
import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.adapter.EnergfmAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Energfm02 extends Fragment{
	private ListView energfm02_lv;
	private TextView enerfm_year,enerfm_month;
	private List<String> list= null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.energfm02, null);
		enerfm_year = (TextView) view.findViewById(R.id.enerfm_year);
		enerfm_month = (TextView) view.findViewById(R.id.enerfm_month);
		energfm02_lv = (ListView) view.findViewById(R.id.energfm02_lv);
		enerfm_year.setText("2016Äê");
		enerfm_month.setText("12ÔÂ");
		list = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			list.add(i+"");
		}
		energfm02_lv.setAdapter(new EnergfmAdapter(list,getActivity()));
		return view;
	}
}
