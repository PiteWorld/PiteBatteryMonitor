package com.pite.batterymonitor.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.utils.Data_analysis;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.PacketActivity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 直方  数据分析
 * 
 * @author Administrator
 *
 */
public class Data_analysisFragment extends Fragment {
	// 站名、电池类型、电池厂家、组名、内阻标准、总电压、测试时间、电池数量、总电流,電池狀態,充電状态
	private TextView Data_name, Data_Battery_type, Data_manufacturers, Data_Battery_group, Data_Internal_standard,
			Data_total_voltage,Data_Battery_num, Data_total_current, Data_R_max1, Data_R_max2,
			Data_R_max3, Data_R_max4, Data_R_max5, Data_R_max6, Data_U_high1, Data_U_high2, Data_U_high3, Data_U_low1,
			Data_U_low2, Data_U_low3, Data_conclusion, tv,Data_Battery_status,data_harged_state,Data_SOCmax;
	private LinearLayout data_analysis_title;
	public Spinner Data_Battery_time1;
	private ListView data_analysis_lv;
	private Data_analysis list;
	private List<String[]> dataList;
	private View view;
	private ChartAdapter<String[]> adapter;
	private LinearLayout chlickll;
	private String[] titles = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.data_analysis, null);
		init(view);
		titles = new String[] {"No.", "U(V)", "R1(mΩ)", "T(℃)",
				"C(％)", "R2(mΩ)", "C2(F)" };
		/**
		 * 添加标题
		 */
		String str = null;
		dataList = new ArrayList<String[]>();
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		HttpGetDataTrend(Constant.GET_DATAANALYSIS + PacketActivity.packetgroupid + "/" + str, null);
		addTitle();
		dataList = new ArrayList<String[]>();
		return view;
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataTrend(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						dataList.clear();
						Type type = new TypeToken<Data_analysis>() {
						}.getType();
						Gson gson = new Gson();
						list = gson.fromJson(new String(arg2), type);
						// list = JsonTools.getStringJson(new
						// String(arg2),Data_analysis.class);
						setData(list);
						for (int i = 0; i < list.getHisData().size(); i++) {
							String[] str = new String[] { list.getHisData().get(i).getBatterynum() + "",
									list.getHisData().get(i).getU(), list.getHisData().get(i).getR1(),
									list.getHisData().get(i).getT(), list.getHisData().get(i).getC(),
									list.getHisData().get(i).getR2(), list.getHisData().get(i).getC2() };
							dataList.add(str);
						}
						adapter = new ChartAdapter<String[]>(getActivity(), dataList, titles.length, 0.03f, 0.97f, 2);
						data_analysis_lv.setAdapter(adapter);
					} else {
						Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.net_error, 0).show();
			}
		});
	}

	/**
	 * 添加title数据
	 */

	private void setData(Data_analysis list) {
		if (list == null && list.getHisData().size() < 1)
			return;
		/*
		 * TextView Data_name, Data_Battery_type, Data_manufacturers,
		 * Data_Battery_group, Data_Internal_standard, Data_total_voltage,
		 * Data_Battery_time1, Data_Battery_num, Data_total_current,
		 * Data_R_max1, Data_R_max2, Data_R_max3, Data_R_max4, Data_R_max5,
		 * Data_R_max6, Data_U_high1, Data_U_high2, Data_U_high3, Data_U_low1,
		 * Data_U_low2, Data_U_low3, Data_conclusion,
		 */
		Data_name.setText(list.getTitleData().get(0).getNodename());
		Data_Battery_type.setText(list.getTitleData().get(0).getBatterytypeName());
		Data_manufacturers.setText(list.getTitleData().get(0).getAutomation());
		Data_Battery_group.setText(list.getTitleData().get(0).getGroupname());
		Data_Internal_standard.setText(list.getTitleData().get(0).getNominalR());
		Data_total_voltage.setText(list.getTitleData().get(0).getU_Str());
		Data_Battery_num.setText(list.getTitleData().get(0).getGcount() + "");
		Data_total_current.setText(list.getTitleData().get(0).getI());
		Data_R_max1.setText(list.getTitleData().get(0).getRMax().get(0).getRH0());
		Data_R_max2.setText(list.getTitleData().get(0).getRMax().get(0).getRH1());
		Data_R_max3.setText(list.getTitleData().get(0).getRMax().get(0).getRH2());
		Data_R_max4.setText(list.getTitleData().get(0).getRMax().get(0).getRH3());
		Data_R_max5.setText(list.getTitleData().get(0).getRMax().get(0).getRH4());
		Data_R_max6.setText(list.getTitleData().get(0).getRMax().get(0).getRH5());
		Data_U_high1.setText(list.getTitleData().get(0).getVMax().get(0).getVH0());
		Data_U_high2.setText(list.getTitleData().get(0).getVMax().get(0).getVH1());
		Data_U_high3.setText(list.getTitleData().get(0).getVMax().get(0).getVH2());
		Data_U_low1.setText(list.getTitleData().get(0).getVMin().get(0).getVL0());
		Data_U_low2.setText(list.getTitleData().get(0).getVMin().get(0).getVL1());
		Data_U_low3.setText(list.getTitleData().get(0).getVMin().get(0).getVL2());
		//電池狀態 和 充電狀態
		//Data_Battery_status
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
	 * 初始化控件
	 * 
	 * @param view
	 */
	private void init(View view) {
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
		Data_Battery_time1 = (Spinner) view.findViewById(R.id.Data_Battery_time1);
		//SOC偏大六节
		Data_SOCmax =(TextView) view.findViewById(R.id.Data_SOCmax);
	}
}
