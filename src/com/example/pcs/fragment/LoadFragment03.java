package com.example.pcs.fragment;

import java.util.ArrayList;
import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.adapter.LoadAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LoadFragment03 extends Fragment {
   private List<String> list=null;
	private TextView tv_load_time,tv_loadUa, tv_loadUb, tv_loadUc, tv_loadIa, tv_loadIb, tv_loadIc, tv_loadLa, tv_loadLb, tv_loadLc,
			tv_loadPa, tv_loadPb, tv_loadPc, tv_loadSa, tv_loadSb, tv_loadSc, tv_loadPfa, tv_loadPfb, tv_loadPfc;
	private ListView load_listview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view =View.inflate(getActivity(), R.layout.activity_loadtrenddata, null);
		//initData(view);
		tv_load_time=(TextView) view.findViewById(R.id.load_time);
		tv_loadUa=(TextView) view.findViewById(R.id.load_Ua);
		tv_loadUb=(TextView) view.findViewById(R.id.load_Ub);
		tv_loadUc=(TextView) view.findViewById(R.id.load_Uc);
		
		tv_loadIa=(TextView) view.findViewById(R.id.load_Ia);
		tv_loadIb=(TextView) view.findViewById(R.id.load_Ib);
		tv_loadIc=(TextView) view.findViewById(R.id.load_Ic);
		
		tv_loadLa=(TextView) view.findViewById(R.id.load_La);
		tv_loadLb=(TextView) view.findViewById(R.id.load_Lb);
		tv_loadLc=(TextView) view.findViewById(R.id.load_Lc);
		
		tv_loadPa=(TextView) view.findViewById(R.id.load_Pa);
		tv_loadPb=(TextView) view.findViewById(R.id.load_Pb);
		tv_loadPc=(TextView) view.findViewById(R.id.load_Pc);
	
		tv_loadSa=(TextView) view.findViewById(R.id.load_Sa);
		tv_loadSb=(TextView) view.findViewById(R.id.load_Sb);
		tv_loadSc=(TextView) view.findViewById(R.id.load_Sc);
		
		tv_loadPfa=(TextView) view.findViewById(R.id.load_Pfa);
		tv_loadPfb=(TextView) view.findViewById(R.id.load_Pfb);
		tv_loadPfc=(TextView) view.findViewById(R.id.load_Pfc);
		load_listview=(ListView) view.findViewById(R.id.load_listview);
		
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add(i + "");
		}
		load_listview.setAdapter(new LoadAdapter(getActivity(), list));
		return view;
	}
//		private void initData(View view){
//			tv_load_time=(TextView) view.findViewById(R.id.load_time);
//			tv_loadUa=(TextView) view.findViewById(R.id.load_Ua);
//			tv_loadUb=(TextView) view.findViewById(R.id.load_Ub);
//			tv_loadUc=(TextView) view.findViewById(R.id.load_Uc);
//			
//			tv_loadIa=(TextView) view.findViewById(R.id.load_Ia);
//			tv_loadIb=(TextView) view.findViewById(R.id.load_Ib);
//			tv_loadIc=(TextView) view.findViewById(R.id.load_Ic);
//			
//			tv_loadLa=(TextView) view.findViewById(R.id.load_La);
//			tv_loadLb=(TextView) view.findViewById(R.id.load_Lb);
//			tv_loadLc=(TextView) view.findViewById(R.id.load_Lc);
//			
//			tv_loadPa=(TextView) view.findViewById(R.id.load_Pa);
//			tv_loadPb=(TextView) view.findViewById(R.id.load_Pb);
//			tv_loadPc=(TextView) view.findViewById(R.id.load_Pc);
//		
//			tv_loadSa=(TextView) view.findViewById(R.id.load_Sa);
//			tv_loadSb=(TextView) view.findViewById(R.id.load_Sb);
//			tv_loadSc=(TextView) view.findViewById(R.id.load_Sc);
//			
//			tv_loadPfa=(TextView) view.findViewById(R.id.load_Pfa);
//			tv_loadPfa=(TextView) view.findViewById(R.id.load_Pfb);
//			tv_loadPfa=(TextView) view.findViewById(R.id.load_Pfc);
//			load_listview=(ListView) view.findViewById(R.id.load_listview);
//			
//		
//		}
}
