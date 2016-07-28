package com.pite.batterymonitor.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.TimeMonitorActivity;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.adapter.SpinerAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.HisDatafMaxMin;
import com.pite.batterymonitor.utils.Histogramf;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.SPTimeUtils;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 直方 数据分析
 */
public class HisDataFragment extends Fragment {
	public Spinner Data_Battery_time1; // 测试时间， 时间间隔
	// 站名、电池类型、电池厂家、组名、内阻标准、总电压、测试时间、电池数量、总电流,電池狀態,充電狀態
	private TextView Data_name, Data_Battery_type, Data_manufacturers, Data_Battery_group, Data_Internal_standard,
			Data_total_voltage, Data_Battery_num, Data_total_current, Data_R_max1, Data_R_max2, Data_R_max3,
			Data_R_max4, Data_R_max5, Data_R_max6, Data_U_high1, Data_U_high2, Data_U_high3, Data_U_low1, Data_U_low2,
			Data_U_low3, Data_conclusion, Data_Battery_status, data_harged_state,data_SOCMax,His_sydl;//剩余电量
	// 电池状态信息
	// private BatteryRecord titleList = null;// 直方图表头
	private Histogramf list = null;
	private HisDatafMaxMin titleList = null;
	private TimeMonitorActivity tm;
	// private SPTimeUtils hisDataList = null; // 下拉列表数据
	private ArrayAdapter<String> spGapAdapter = null;
	private ArrayAdapter<String> spAdapter = null;
	private ListView list_testTime;//用于显示测试时间列表
	private List<String> spList = null;
	private SPTimeUtils spUtils = null;
	private LinearLayout data_analysis_title;// 用来添加标题栏的item
	private LinearLayout chlickll;// 用来添加标题栏的item
	private TextView tv;
	private View view;
	private String[] titles = null; // 标题
	private ListView data_analysis_lv = null;
	private List<String[]> lists = null;
	private String str = null;
	private boolean two;
	private Context context;
	private PopupWindow popupWindow;
	//判断直方数据 分析 1 内阻检测  非1电压检测
	private String type;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setLoad(); // 设置 电池组数据
				break;
			case 2:
				setHisList(); //设置表头
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_data, null);
		setID(view);
		type = getArguments().getString("type");
		// (1)标题配置
		if (type.equals("1")) {
			titles = new String[] { "No.", "U(V)", "R1(mΩ)", "T(℃)", "C(%)", "R2(mΩ)", "C2(F)" };
			setVisbli();
		} else {
			titles = new String[] { "No.", "U(V)", "T(℃)", "SOC(%)" };
			setGone();
		}
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		list = HistogramFragment.list;
		spList = HistogramFragment.spList;
		spUtils = HistogramFragment.spUtils;
		setTitle(list);
		addTitle(); // 设置标题
		return view;
	}
	/**
	 * 
	 */
	private void setVisbli() {
		data_SOCMax.setText(R.string.R_max);
	}

	private void setGone() {
		data_SOCMax.setText(R.string.R_max2);
		His_sydl.setText(R.string.sydl);
	}

	/***
	 * 添加表头数据
	 * 
	 * @param list2
	 */
	private void setTitle(Histogramf list) {
		if (list == null || list.getHisData().size() == 0)
			return;
		Data_name.setText(list.getTitleData().get(0).getNodename());
		Data_Battery_type.setText(list.getTitleData().get(0).getBatterytypeName());
		Data_manufacturers.setText(list.getTitleData().get(0).getAutomation());
		Data_Battery_group.setText(list.getTitleData().get(0).getGroupname());
		Data_Internal_standard.setText(list.getTitleData().get(0).getNominalR() + "mΩ");
		Data_total_voltage.setText(list.getTitleData().get(0).getU_Str() + "V");
		Data_Battery_num.setText(list.getTitleData().get(0).getGcount() + "");
		Data_total_current.setText(list.getTitleData().get(0).getI() + "A");
		// 电池状态和充电状态
		Data_Battery_status.setText(list.getTitleData().get(0).getStatus());
		Data_Battery_status.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getGScolor()));
		data_harged_state.setText(list.getTitleData().get(0).getWorkStatus());
		data_harged_state.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getWScolor()));
		setSpinner(); // 设置Spinner
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		setLoad();
	}

	public void initView() {
		data_analysis_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JsonId.setNumPostion(position);
				JsonId.setFlage(0);
				tm.showFragment(6);
			}
		});
		if(JsonId.getNumPostion()!=-1){
			Log.e("tag", JsonId.getNumPostion()+"JsonId.getNumPostion()");
		Data_Battery_time1.setSelection(JsonId.getNumPostion());
		  getDataTwo(JsonId.getNumPostion());
		}
	
		Data_Battery_time1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// JsonId.setTimePostion(position);
				if (ChartsUtils.isNetworkAvailable(getActivity())) {
					if (type.equals("1")) {
					// 请求最大值和最小值
					HttpGetData((Constant.GET_DATAANALYSIS + spUtils.getRang().getData().get(position).getTestID() + "/"
							+ str), null);
					}
					else{
						// 请求最大值和最小值
						HttpGetData((Constant.GET_DATAANALYSIS2+ spUtils.getRang().getData().get(position).getTestID() + "/"
								+ str), null);
					}
					// 第一次不请求数据,第二次请求，地址改变
					if (two) {
						getDataTwo(position);
					}
					two = true;
				} 
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
	public HisDataFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	private void getDataTwo(int position) {
		if (type.equals("1")) {
			HttpGetDataTwo(Constant.BATTERY_HIS_TIMESP
					+ spUtils.getRang().getData().get(position).getTestID() + "/" + str, null);
		} else {
			HttpGetDataTwo(Constant.BATTERY_HIS_TIMESP2
					+ spUtils.getRang().getData().get(position).getTestID() + "/" + str, null);
		}
	}
	/**
	 * 设置Spinner 测试时间
	 */
	public void setSpinner() {
		if (spList == null || spList.size() == 0) {
			return;
		}
	    popupWindow=new PopupWindow(50, 60);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		popupWindow.update();
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		
		spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				setTimeSublist(spList));
		
	    spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		Data_Battery_time1.setAdapter(spAdapter);
		
		
		//list_testTime.setAdapter(spAdapter);

	   //spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
	   //setTimeSublist(spList));
       //spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	   //Data_Battery_time1.setAdapter(adapter);
	}
	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 *            请求最大值个最小值
	 */
	public void HttpGetData(String url, RequestParams params) {

		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						Log.e("tag", "请求最大和最小值");
						Log.e("tag", new String(arg2).toString()+"--");
						titleList = JsonTools.getStringJson(new String(arg2), HisDatafMaxMin.class);
						handler.sendEmptyMessage(2);
					}
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
			}
		});
	}
	/***
	 * 第二次点击请求
	 */
	public void HttpGetDataTwo(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getStringJson(new String(arg2), Histogramf.class);
						handler.sendEmptyMessage(1);
					}
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
			}
		});
	}
	/*
	 * public void HttpGetData(final String url, final RequestParams params) {
	 * HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
	 * 
	 * @Override public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
	 * if (arg0 == 200) { if (arg2 != null && arg2.length > 1) { // titleList =
	 * JsonTools.getStringJson(new String(arg2), BatteryRecord.class); // list =
	 * JsonTools.getListJson(new String(arg2), //handler.sendEmptyMessage(1);
	 * 
	 * } else { Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
	 * } } }
	 * 
	 * @Override public void onFailure(int arg0, Header[] arg1, byte[] arg2,
	 * Throwable arg3) { // Toast.makeText(getActivity(), R.string.net_error,
	 * 0).show(); } }); }
	 */

	/**
	 * 解析表头文本数据,设置表头数据值 TextView Data_name, Data_Battery_type,
	 * Data_manufacturers, Data_Battery_group, Data_Internal_standard,
	 * Data_total_voltage,Data_Battery_num, Data_total_current, Data_R_max1,
	 * Data_R_max2, Data_R_max3, Data_R_max4, Data_R_max5, Data_R_max6,
	 * Data_U_high1, Data_U_high2, Data_U_high3, Data_U_low1, Data_U_low2,
	 * Data_U_low3, Data_conclusion,Data_Battery_status,data_harged_state;
	 */
	public void setHisList() {
		if (titleList == null) {
			return;
		}
		if(type.equals("1")){
			Data_R_max1.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH0() + "mΩ");
			Data_R_max2.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH1() + "mΩ");
			Data_R_max3.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH2() + "mΩ");
			Data_R_max4.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH3() + "mΩ");
			Data_R_max5.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH4() + "mΩ");
			Data_R_max6.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH5() + "mΩ");
		}
		else{
			
			Data_R_max1.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH0() + "%");
			Data_R_max2.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH1() + "%");
			Data_R_max3.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH2() + "%");
			Data_R_max4.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH3() + "%");
			Data_R_max5.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH4() + "%");
			Data_R_max6.setText(titleList.getTitleData().get(0).getRMax().get(0).getRH5() + "%");
		}
			Data_U_high1.setText(titleList.getTitleData().get(0).getVMax().get(0).getVH0() + "V");
			Data_U_high2.setText(titleList.getTitleData().get(0).getVMax().get(0).getVH1() + "V");
			Data_U_high3.setText(titleList.getTitleData().get(0).getVMax().get(0).getVH2() + "V");
			Data_U_low1.setText(titleList.getTitleData().get(0).getVMin().get(0).getVL0() + "V");
			Data_U_low2.setText(titleList.getTitleData().get(0).getVMin().get(0).getVL1() + "V");
			Data_U_low3.setText(titleList.getTitleData().get(0).getVMin().get(0).getVL2() + "V");
	}

	/**
	 * 动态加载电池组数据
	 */
	int a = 0;

	public void setLoad() {
		if (list == null) {
			return;
		}
		lists = new ArrayList<String[]>();
		if (type.equals("1")) {
			for (int i = 0; i < list.getHisData().size(); i++) {
				String[] data = new String[] { ++a + "", list.getHisData().get(i).getU(),
						list.getHisData().get(i).getR(), list.getHisData().get(i).getT() + "",
						list.getHisData().get(i).getC() + "", list.getHisData().get(i).getR2() + "",
						getDoubleFormatString(Double.valueOf(list.getHisData().get(i).getC2())) };
				if (data[1].equals("0.000") && data[2].equals("0.00")) {
					for (int j = 0; j < data.length; j++) {
						if (j > 0)
							data[j] = "- -";
					}
				} else {
					if (data[1].equals("0.000"))
						data[1] = "- -";
					if (data[2].equals("0.00"))
						data[2] = "- -";
				}
				lists.add(data);
			}
		} else {
			for (int i = 0; i < list.getHisData().size(); i++) {
				String[] data = new String[] { ++a + "", list.getHisData().get(i).getU(),
						list.getHisData().get(i).getT() + "", list.getHisData().get(i).getC() + "" };
				if (data[1].equals("0.000") && data[2].equals("0.00")) {
					for (int j = 0; j < data.length; j++) {
						if (j > 0)
							data[j] = "- -";
					}
				} else {
					if (data[1].equals("0.000"))
						data[1] = "- -";
					if (data[2].equals("0.00"))
						data[2] = "- -";
				}
				lists.add(data);
			}
		}
		a = 0;
		Log.e("tag", lists.get(0)[3]);
		data_analysis_lv.setAdapter(new ChartAdapter(getActivity(), lists, titles.length, 0.03f, 0.97f, 2));
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
				data_analysis_title.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				data_analysis_title.addView(chlickll, params3);
			}
		}
	}

	/**
	 * 将double数据保存小数点后面两位数字
	 */
	public String getDoubleFormatString(double value) {
		DecimalFormat df;
		if (value >= 100.0) {
			df = new DecimalFormat("0");
		} else if (value >= 10.0) {
			df = new DecimalFormat("0.0");
		} else {
			df = new DecimalFormat("0.00");
		}
		return df.format(value);
	}

	/**
	 * 绑定ID
	 */
	/*
	 * private TextView btStation, btGroup, btType, btMinR, btMaxR, btMinU,
	 * btGroupU, btGroupI, btStatus, btCharge,texCmd,Data_R_max1, Data_R_max2,
	 * Data_R_max3, Data_R_max4, Data_R_max5, Data_R_max6, Data_U_high1,
	 * Data_U_high2, Data_U_high3, Data_U_low1, Data_U_low2, Data_U_low3,
	 * Data_conclusion;
	 */
	public void setID(View view) {
		Data_name = (TextView) view.findViewById(R.id.Data_name);
		Data_total_voltage = (TextView) view.findViewById(R.id.Data_total_voltage);
		Data_manufacturers = (TextView) view.findViewById(R.id.Data_manufacturers);
		Data_Battery_group = (TextView) view.findViewById(R.id.Data_Battery_group);
		Data_Battery_type = (TextView) view.findViewById(R.id.Data_Battery_type);
		Data_Internal_standard = (TextView) view.findViewById(R.id.Data_Internal_standard);
		Data_Battery_time1 = (Spinner) view.findViewById(R.id.Data_Battery_time1);
		Data_Battery_num = (TextView) view.findViewById(R.id.Data_Battery_num);
		Data_total_current = (TextView) view.findViewById(R.id.Data_total_current);
		Data_R_max1 = (TextView) view.findViewById(R.id.Data_R_max1);
		Data_R_max2 = (TextView) view.findViewById(R.id.Data_R_max2);
		Data_R_max3 = (TextView) view.findViewById(R.id.Data_R_max3);
		Data_R_max4 = (TextView) view.findViewById(R.id.Data_R_max4);
		Data_R_max5 = (TextView) view.findViewById(R.id.Data_R_max5);
		Data_R_max6 = (TextView) view.findViewById(R.id.Data_R_max6);
		Data_U_high1 = (TextView) view.findViewById(R.id.Data_U_high1);
		Data_U_high2 = (TextView) view.findViewById(R.id.Data_U_high2);
		Data_U_high3 = (TextView) view.findViewById(R.id.Data_U_high3);
		Data_U_low1 = (TextView) view.findViewById(R.id.Data_U_low1);
		Data_U_low2 = (TextView) view.findViewById(R.id.Data_U_low2);
		Data_U_low3 = (TextView) view.findViewById(R.id.Data_U_low3);
		Data_conclusion = (TextView) view.findViewById(R.id.Data_conclusion);
		data_analysis_title = (LinearLayout) view.findViewById(R.id.data_analysis_title);
		data_analysis_lv = (ListView) view.findViewById(R.id.data_analysis_lv);
		Data_Battery_status = (TextView) view.findViewById(R.id.Data_Battery_status);
		data_harged_state = (TextView) view.findViewById(R.id.data_harged_state);
		// SOC偏大六节
		data_SOCMax = (TextView) view.findViewById(R.id.data_SOCMax);
		His_sydl = (TextView) view.findViewById(R.id.His_sydl);
		//list_testTime=(ListView) view.findViewById(R.id.list_testTime);
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


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
