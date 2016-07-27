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

public class Energfm03 extends Fragment{
	private ListView energfm03_lv;
	private TextView enerfm03_year;
	private List<String> list= null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.energfm3, null);
		enerfm03_year = (TextView) view.findViewById(R.id.enerfm03_year);
		energfm03_lv = (ListView) view.findViewById(R.id.energfm03_lv);
		enerfm03_year.setText("2016Äê");
		list = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			list.add(i+"");
		}
		energfm03_lv.setAdapter(new EnergfmAdapter(list,getActivity()));
		return view;
	}
}
