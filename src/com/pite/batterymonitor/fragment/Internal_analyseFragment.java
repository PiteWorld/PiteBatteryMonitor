package com.pite.batterymonitor.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.Histogramf;
import com.pite.batterymonitor.utils.Internal_analyse;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.SPTimeUtils;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 电池 内阻分析
 * @author Administrator
 *
 */
public class Internal_analyseFragment extends Fragment {
	private TextView internal_name, internal_Battery_type, internal_manufacturers, internal_Battery_group,
			internal_Internal_standard, internal_total_voltage, internal_Battery_num,
			internal_total_current, internal_R_average, internal_R_mean, tv,internal_Battery_status,Internal_battery_charge;
	private LinearLayout internal_analysis_title;
	private ListView internal_analysis_lv;
	public Spinner Internal_sp;
	//sp数据
	private ArrayAdapter<String> spAdapter = null;
	private Internal_analyse list;
	private  List<String> spList = null;
	private SPTimeUtils spUtils = null;
	private Histogramf listdate=null;
	private String str = null;
	private List<String[]> dataList;
	private View view;
	private ChartAdapter<String[]> adapter;
	private LinearLayout chlickll;
	private String[] titles = null;
	private TimeMonitorActivity tm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.internal_deviation, null);
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		listdate = HistogramFragment.list;
		spUtils = HistogramFragment.spUtils;
		spList = HistogramFragment.spList;
		init(view);
		//sp添加adapter
		setSpinner();
		titles = new String[] { getActivity().getResources().getString(R.string.number), "R1(mΩ)",
				getActivity().getResources().getString(R.string.AVB), "U(V)", "T(℃)", "R2(mΩ)", "C2(F)" };
		addTitle();
		dataList = new ArrayList<String[]>();
		
		Internal_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				HttpGetDataTrend(Constant.GET_RESISTANCEANALYSISAD + spUtils.getRang().getData().get(position).getTestID() + "/" + str, null);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		return view;
	}
	public Internal_analyseFragment(TimeMonitorActivity tm){
		this.tm = tm;
	}
	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataTrend(String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						dataList.clear();
						list = JsonTools.getStringJson(new String(arg2), Internal_analyse.class);
						setData(list);
						for (int i = 0; i < list.getHisData().size(); i++) {
							String[] str = new String[] { list.getHisData().get(i).getBatterynum() + "",
									list.getHisData().get(i).getR1(), list.getHisData().get(i).getAD(),
									list.getHisData().get(i).getU(), list.getHisData().get(i).getT(),
									list.getHisData().get(i).getR2(), list.getHisData().get(i).getC2() };
							dataList.add(str);
						}
						adapter = new ChartAdapter<String[]>(getActivity(), dataList, titles.length, 0.03f, 0.97f, 2);
						internal_analysis_lv.setAdapter(adapter);
					}else{
						Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
					}
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}
		});
	}

	/**
	 * 添加title数据
	 */
	
	private void setData(Internal_analyse list) {
		/*
		 * TextView Data_name, Data_Battery_type, Data_manufacturers,
		 * Data_Battery_group, Data_Internal_standard, Data_total_voltage,
		 * Data_Battery_time1, Data_Battery_num, Data_total_current,internal_Battery_status,Internal_battery_charge
		
		 */
		if (list == null && list.getHisData().size() < 1)
			return;
		internal_R_average.setText(list.getTitleData().get(0).getAVG() + "mΩ");
		internal_R_mean.setText(list.getTitleData().get(0).getNormalAVG());
	}

	/**
	 * 动态添加title选项
	 */
	private void addTitle() {
		for (int i = 0; i < titles.length; i++) {
			tv = new TextView(getActivity());
			tv.setText(titles[i]);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(12);
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.03f);
			params.gravity = Gravity.CENTER;
			view = new View(getActivity());
			view.setBackground(getActivity().getResources().getDrawable(R.drawable.line));
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.97f);
			chlickll = new LinearLayout(getActivity());
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1.0f);

			if (i == titles.length - 1) {
				LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				chlickll.addView(tv, params4);
				internal_analysis_title.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				internal_analysis_title.addView(chlickll, params3);
			}
		}
	}
	
	/**
	 * id绑定
	 * 
	 * @param view
	 */
	private void init(View view) {
		internal_name = (TextView) view.findViewById(R.id.internal_name);
		internal_Battery_type = (TextView) view.findViewById(R.id.internal_Battery_type);
		internal_manufacturers = (TextView) view.findViewById(R.id.internal_manufacturers);
		internal_Battery_group = (TextView) view.findViewById(R.id.internal_Battery_group);
		internal_Internal_standard = (TextView) view.findViewById(R.id.internal_Internal_standard);
		internal_total_voltage = (TextView) view.findViewById(R.id.internal_total_voltage);
		Internal_sp = (Spinner) view.findViewById(R.id.internal_sp);
		internal_Battery_num = (TextView) view.findViewById(R.id.internal_Battery_num);
		internal_total_current = (TextView) view.findViewById(R.id.internal_total_current);
		internal_R_average = (TextView) view.findViewById(R.id.internal_R_average);
		internal_R_mean = (TextView) view.findViewById(R.id.internal_R_mean);
		internal_analysis_title = (LinearLayout) view.findViewById(R.id.internal_analysis_title);
		internal_analysis_lv = (ListView) view.findViewById(R.id.internal_analysis_lv);
		//增加电池状态和充电状态
		internal_Battery_status = (TextView) view.findViewById(R.id.internal_Battery_status);
		Internal_battery_charge = (TextView) view.findViewById(R.id.internal_battery_charge);
		setTitle(listdate);
		internal_analysis_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				JsonId.setNumPostion(Integer.parseInt(dataList.get(position)[0])-1);
				JsonId.setFlage(2);
				tm.showFragment(6);
			}
		});
	}
	/***
	 * 设置表头数据
	 * @param listdate2
	 */
	private void setTitle(Histogramf listdate) {
		if(listdate == null||listdate.getTitleData().size()==0)
			return;
		internal_name.setText(listdate.getTitleData().get(0).getNodename());
		internal_Battery_type.setText(listdate.getTitleData().get(0).getBatterytypeName());
		internal_manufacturers.setText(listdate.getTitleData().get(0).getAutomation());
		internal_Battery_group.setText(listdate.getTitleData().get(0).getGroupname());
		internal_Internal_standard.setText(listdate.getTitleData().get(0).getNominalR()+"mΩ");
		internal_total_voltage.setText(listdate.getTitleData().get(0).getU_Str()+"V");
		internal_Battery_num.setText(listdate.getTitleData().get(0).getGcount() + "");
		internal_total_current.setText(listdate.getTitleData().get(0).getI()+"A");
		internal_Battery_status.setText(listdate.getTitleData().get(0).getStatus());
		internal_Battery_status.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getGScolor()));
		Internal_battery_charge.setText(listdate.getTitleData().get(0).getWorkStatus());
		Internal_battery_charge.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getWScolor()));
	}

	/**
	 * 设置Spinner
	 */
	public void setSpinner() {
		if (spList == null||spList.size()==0) {
			return;
		}
		spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				setTimeSublist(spList));
		spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Internal_sp.setAdapter(spAdapter);
	}
	/**
	 * 截取指定 格式的时间 月日时分
	 * 
	 * @param time
	 * @return
	 */
	public List<String> setTimeSublist(List<String> time) {
		List<String> lists = new ArrayList<String>();
		String times = null;
		for (int i = 0; i < time.size(); i++) {
			times = time.get(i).substring(5, 16);
			lists.add(times);
		}

		return lists;
	}

}
