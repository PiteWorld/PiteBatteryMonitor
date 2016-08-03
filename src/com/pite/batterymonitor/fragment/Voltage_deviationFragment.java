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
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.SPTimeUtils;
import com.pite.batterymonitor.utils.Voltage_deviation;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
 * 电压  均差分析
 * @author Administrator
 *
 */
public class Voltage_deviationFragment extends Fragment {
	private TextView voltagr_name, voltagr_Battery_type, voltagr_manufacturers, voltagr_Battery_group,
			voltagr_Internal_standard, voltagr_total_voltage, voltagr_Battery_num, voltagr_total_current,
			voltagr_U_average, voltagr_U_mean, tv, voltagr_Battery_status, voltage_charge,voltage_sydl;
	private ListView voltage_deviation_lv;
	private LinearLayout voltage_deviation_title;
	private List<String[]> dataList;
	public Spinner voltage_sp;
	private Voltage_deviation list;
	private View view;
	private ChartAdapter<String[]> adapter;
	private LinearLayout chlickll;
	private String[] titles = null;
	private List<String> spList = null;
	private SPTimeUtils spUtils = null;
	private Histogramf listdate = null;
	private String str = null;
	private TimeMonitorActivity tm;
	// sp数据
	private ArrayAdapter<String> spAdapter = null;
	private String type;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				setData(list);
				dataList.clear();
				if (type.equals("1")) {
					for (int i = 0; i < list.getHisData().size(); i++) {
						String[] str = new String[] { list.getHisData().get(i).getBatterynum() + "",
								list.getHisData().get(i).getU(), list.getHisData().get(i).getAD(),
								list.getHisData().get(i).getR1(), list.getHisData().get(i).getT(),
								list.getHisData().get(i).getR2(), list.getHisData().get(i).getC2() };
						dataList.add(str);
					}
				} else {
					for (int i = 0; i < list.getHisData().size(); i++) {
						String[] str = new String[] { list.getHisData().get(i).getBatterynum() + "",
								list.getHisData().get(i).getU(), list.getHisData().get(i).getAD(),
								list.getHisData().get(i).getC(),
								list.getHisData().get(i).getT()};
						dataList.add(str);
					}
				}
				adapter = new ChartAdapter<String[]>(getActivity(), dataList, titles.length, 0.03f, 0.97f, 2);
				voltage_deviation_lv.setAdapter(adapter);
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.voltage_deviation, null);
		type = getArguments().getString("type");
		listdate = HistogramFragment.list;
		spUtils = HistogramFragment.spUtils;
		spList = HistogramFragment.spList;
		init(view);
		setTitle(listdate);
		if (type.equals("1")){
			titles = new String[] { "No.", "U(V)", getActivity().getResources().getString(R.string.AV), "R1(mΩ)",
					"T(℃)", "R2(mΩ)", "C2(F)" };
			}
		else{
			titles = new String[] { "No.", "U(V)", getActivity().getResources().getString(R.string.AV), "SOC(%)","T(℃)" };
			voltage_sydl.setText(R.string.sydl);
		}
		addTitle();

		dataList = new ArrayList<String[]>();
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}

		voltage_sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (type.equals("1")) {
					HttpGetDataTrend(Constant.get_voltageAnalysisAD
							+ spUtils.getRang().getData().get(position).getTestID() + "/" + str, null);
				} else {
					HttpGetDataTrend(Constant.get_voltageAnalysisAD2
							+ spUtils.getRang().getData().get(position).getTestID() + "/" + str, null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
		return view;
	}

	public Voltage_deviationFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	/***
	 * 图标上面的数据
	 * 
	 * @param listdate2
	 */
	private void setTitle(Histogramf listdate) {
		if (listdate == null || listdate.getTitleData().size() == 0)
			return;
		voltagr_name.setText(listdate.getTitleData().get(0).getNodename());
		voltagr_Battery_type.setText(listdate.getTitleData().get(0).getBatterytypeName());
		voltagr_manufacturers.setText(listdate.getTitleData().get(0).getAutomation());
		voltagr_Battery_group.setText(listdate.getTitleData().get(0).getGroupname());
		voltagr_Internal_standard.setText(listdate.getTitleData().get(0).getNominalR() + "mΩ");
		voltagr_total_voltage.setText(listdate.getTitleData().get(0).getU_Str() + "V");
		// voltagr_Battery_time1.setText(list.getTitleData().get(0).getTestTime());
		voltagr_Battery_status.setText(listdate.getTitleData().get(0).getStatus());
		voltagr_Battery_status.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getGScolor()));
		voltage_charge.setText(listdate.getTitleData().get(0).getWorkStatus());
		voltage_charge.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getWScolor()));
		voltagr_Battery_num.setText(listdate.getTitleData().get(0).getGcount() + "");
		voltagr_total_current.setText(listdate.getTitleData().get(0).getI() + "A");
		setSpinner();
	}

	/**
	 * id绑定
	 * 
	 * @param view
	 */
	private void init(View view) {
		voltagr_name = (TextView) view.findViewById(R.id.voltagr_name);
		voltagr_Battery_type = (TextView) view.findViewById(R.id.voltagr_Battery_type);
		voltagr_manufacturers = (TextView) view.findViewById(R.id.voltagr_manufacturers);
		voltagr_Battery_group = (TextView) view.findViewById(R.id.voltagr_Battery_group);
		voltagr_Internal_standard = (TextView) view.findViewById(R.id.voltagr_Internal_standard);
		voltagr_total_voltage = (TextView) view.findViewById(R.id.voltagr_total_voltage);
		voltagr_Battery_num = (TextView) view.findViewById(R.id.voltagr_Battery_num);
		voltagr_total_current = (TextView) view.findViewById(R.id.voltagr_total_current);
		voltagr_U_average = (TextView) view.findViewById(R.id.voltagr_U_average);
		voltagr_U_mean = (TextView) view.findViewById(R.id.voltagr_U_mean);
		voltage_deviation_lv = (ListView) view.findViewById(R.id.voltage_deviation_lv);
		voltage_deviation_title = (LinearLayout) view.findViewById(R.id.voltage_deviation_title);
		voltagr_Battery_status = (TextView) view.findViewById(R.id.voltagr_Battery_status);
		voltage_charge = (TextView) view.findViewById(R.id.voltage_charge);
		voltage_sp = (Spinner) view.findViewById(R.id.voltage_sp);
		voltage_sydl = (TextView) view . findViewById(R.id.voltage_sydl);
		voltage_deviation_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JsonId.setNumPostion(Integer.parseInt(dataList.get(position)[0]) - 1);
				JsonId.setFlage(1);
				tm.showFragment(6);
			}
		});
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
						list = JsonTools.getStringJson(new String(arg2), Voltage_deviation.class);
						handler.sendEmptyMessage(1);
					} else {
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
	 * 添加头数据
	 */
	private void setData(Voltage_deviation list) {
		// voltagr_name,voltagr_Battery_type,voltagr_manufacturers,voltagr_Battery_group,
		// voltagr_Internal_standard,voltagr_total_voltage,voltagr_Battery_time1,voltagr_Battery_num,
		// voltagr_total_current,voltagr_U_average,voltagr_U_mean
		if (list == null && list.getHisData().size() < 1)
			return;
		voltagr_U_average.setText(list.getTitleData().get(0).getAVG() + "V");
		voltagr_U_mean.setText(list.getTitleData().get(0).getNormalAVG() + "mV");
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
				voltage_deviation_title.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				voltage_deviation_title.addView(chlickll, params3);
			}
		}
	}

	/**
	 * 设置Spinner
	 */
	public void setSpinner() {
		if (spList == null || spList.size() == 0) {
			return;
		}
		spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				setTimeSublist(spList));
		spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		voltage_sp.setAdapter(spAdapter);
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
