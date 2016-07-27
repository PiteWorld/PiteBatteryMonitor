package com.example.pcs.fragment;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 系统输出的实时信息
 * @author Administrator
 *
 */
public class LOADMainFragment extends Fragment {
	private TextView load_tv, load_tv2, load_tv3, load_tv4, load_tv5, load_tv6, load_tv7, load_tv8, load_tv9, load_tv10,
	load_tv11, load_tv12, load_tv13, load_tv14, load_tv15, load_tv16, load_tv17, load_tv18, load_tv19;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.activity_loadmain, null);
		load_tv=(TextView) view.findViewById(R.id.load_tv);
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
		load_tv11= (TextView) view.findViewById(R.id.load_tv11);
		load_tv12 = (TextView) view.findViewById(R.id.load_tv12);
		load_tv13 = (TextView) view.findViewById(R.id.load_tv13);
		load_tv14 = (TextView) view.findViewById(R.id.load_tv14);
		load_tv15 = (TextView) view.findViewById(R.id.load_tv15);
		load_tv16 = (TextView) view.findViewById(R.id.load_tv16);
		load_tv17 = (TextView) view.findViewById(R.id.load_tv17);
		load_tv18 = (TextView) view.findViewById(R.id.load_tv18);
		load_tv19= (TextView) view.findViewById(R.id.load_tv19);
		return view;
	}
	/**
	 * 赋值
	 */
	private void setValue(){
		
	}
	/**
	 * 隐藏 fragment
	 */
	public void hideFragment(FragmentTransaction ft) {
	

	}
	@Override
	public void onAttach(Activity activity) {
		
		
		
		super.onAttach(activity);
	}
	

	
	
	
	
	
	
	

}
