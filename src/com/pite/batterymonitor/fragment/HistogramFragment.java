package com.pite.batterymonitor.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.timer.GapsTimer;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.Histogramf;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.SPTimeUtils;
import com.pite.batterymonitor.utils.TrendChartUtils;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.PacketActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * fragment 直方图
 */
public class HistogramFragment extends Fragment {
	public Spinner Histogram_sp; // 测试时间， 时间间隔
	/*
	 * private TextView btStation, btGroup, btType, btMinR, btMaxR, btMinU,
	 * btGroupU, btGroupI, btStatus, texCmd, texCharge; // 电池状态信息
	 */
	private TextView Histogram_name, Histogram_Battery_type, Histogram_manufacturers, Histogram_Battery_group,
			Histogram_Internal_standard, Histogram_total_voltage, Histogram_Battery_num, Histogram_total_current,
			Histogram_Battery_status, Histogram_charge, texCmd, texCharge, Hista_sydl;
	private ColumnChartView chartU, chartR, chartT, chartC, chartR2, chartC2, chartCqita;
	private ColumnChartData data;
	private TextView titleU, titleR, titleT, titleC, titleCqita, titleR2, titleC2;
	public static String TestId;
	public static Histogramf list = null; // json数据集合
	public static List<String> spList = null;
	public static SPTimeUtils spUtils = null; // 下拉列表数据
	// 测试开始范围，最后测试时间
	// private List<JsonId> listspTimae = new ArrayList<JsonId>();// spTime数据
	private List<chartObject> listU = new ArrayList<chartObject>();
	private List<chartObject> listR = new ArrayList<chartObject>();
	private List<chartObject> listT = new ArrayList<chartObject>();
	private List<chartObject> listC = new ArrayList<chartObject>();
	private List<chartObject> listR2 = new ArrayList<chartObject>();
	private List<chartObject> listC2 = new ArrayList<chartObject>();
	private TimeMonitorActivity tm = null;
	private ArrayAdapter<String> spAdapter = null;
	private String SEND_UPDATA = "com.set.sucess"; // 广播
	private String SEND_UPDATA_RESTART = "com.set.restart"; // 重启广播
	private String SEND_UPDATA_SYN = "com.set.syn"; // 同步参数广播
	public static TrendChartUtils listTrend = null;
	private String showText = null;
	public hisBordcast cast;
	// 判断c的位置
	private LinearLayout qita, dingjian;
	private String type;
	// 判断中英文
	private String str = null;
	private boolean isresult = false;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				setLoad();
				setChart(); // 设置表头文本
				// 获取activity传过来的数据，判断类型
				Log.e("type", type + "  当前点击的类型");
				if (type.equals("1")) {
					setVisbli();
				} else {
					setGone();
				}
				break;
			case 2:
				if (spUtils == null)
					return;
				spList = new ArrayList<String>();
				for (int i = 0; i < spUtils.getRang().getLength(); i++) {
					spList.add(spUtils.getRang().getData().get(i).getTime());
				}
				spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
						setTimeSublist(spList));
				spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				Histogram_sp.setAdapter(spAdapter);
				break;
			case 3: // 时间间隔
				if (showText != null) {
					texCmd.setVisibility(View.VISIBLE);
					if (GapsTimer.setLongTime) {
						GapsTimer.setLongTime = false;
						texCmd.setText(R.string.pite_gaps_cmd_fails);
					} else {
						// texCmd.setText(R.string.pite_gaps_cmd_suec);
						texCmd.setText(showText);
					}
					texCmd.setTextColor(Color.RED);
				}
				break;
			case 4: // 重启
				if (showText != null) {
					texCmd.setVisibility(View.VISIBLE);
					if (GapsTimer.setLongTime) {
						GapsTimer.setLongTime = false;
						texCmd.setText(R.string.pite_gaps_cmd_fails);
					} else {
						texCmd.setText(showText);
						// texCmd.setText(R.string.pite_gaps_cmd_suec);
					}
					texCmd.setTextColor(Color.RED);
				}
				break;
			case 5: // 同步
				if (showText != null) {
					texCmd.setVisibility(View.VISIBLE);
					if (GapsTimer.setLongTime) {
						GapsTimer.setLongTime = false;
						texCmd.setText(R.string.pite_gaps_cmd_fails);
					} else {
						// texCmd.setText(R.string.pite_tongbu_cmd);
						texCmd.setText(showText);
					}
					texCmd.setTextColor(Color.RED);
				}
				break;
			case 6:
				texCmd.setVisibility(View.GONE);
				break;
			case 7: // 失败
				if (showText != null) {
					texCmd.setVisibility(View.VISIBLE);
					texCmd.setTextColor(Color.RED);
					// texCmd.setText(R.string.pite_gaps_cmd_fails);
					// texCmd.setText(showText);
				}
				break;
			case 8: // 重启命令
				if (showText != null) {
					texCmd.setVisibility(View.VISIBLE);
					texCmd.setTextColor(Color.RED);
					// texCmd.setText(R.string.pite_chongqi_cmd);
					texCmd.setText(showText);
				}
				break;
			}
		}
	};

	public HistogramFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_histogram, null);
		type = getArguments().getString("type");
		str = null;
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		setID(view);
		initView();
		setChartTitle(); // ChartTitle 设置
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	public void initView() {
		/*
		 * if (ChartsUtils.isNetworkAvailable(getActivity())) { StringBuffer str
		 * = new StringBuffer();
		 * str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.
		 * packetgroupid).append("/").append("1")
		 * .append("/").append("100").append("/").append(1);
		 * HttpGetDataTrend(str.toString(), null); } else {
		 * Toast.makeText(getActivity(), R.string.net_no, 0).show(); }
		 */

		listU.add(new chartObject(1, 0xFFFFFFFF));
		chartU.setOnValueTouchListener(new ValueTouchListener());
		chartC.setOnValueTouchListener(new ValueTouchListener());
		chartCqita.setOnValueTouchListener(new ValueTouchListener());
		chartR.setOnValueTouchListener(new ValueTouchListener());
		chartC2.setOnValueTouchListener(new ValueTouchListener());
		chartR2.setOnValueTouchListener(new ValueTouchListener());
		chartT.setOnValueTouchListener(new ValueTouchListener());
		// 初始化
		generateData(listU, chartU, listU.size());
		generateData(listU, chartR, listU.size());
		generateData(listU, chartT, listU.size());
		generateData(listU, chartC, listU.size());
		generateData(listU, chartCqita, listU.size());
		generateData(listU, chartR2, listU.size());
		generateData(listU, chartC2, listU.size());
		// 请求spinner数据
		if (ChartsUtils.isNetworkAvailable(getActivity())) {
			// 请求直方图数据
			if (type.equals("1")) {
				HttpGetData(Constant.BATTERY_HIS_TIME + PacketActivity.packetgroupid + "/" + str, null);
				HttpGetDataSP(
						Constant.BATTERY_SP_TIME + PacketActivity.packetgroupid + "/" + "1" + "/" + "100" + "/" + "1",
						null);
				setVisbli();
			} else {
				HttpGetData(Constant.BATTERY_HIS_TIME2 + PacketActivity.packetgroupid + "/" + str, null);
				HttpGetDataSP(
						Constant.BATTERY_SP_TIME2 + PacketActivity.packetgroupid + "/" + "1" + "/" + "100" + "/" + "1",
						null);
				setGone();
			}
		}
		Histogram_sp.setSelection(0, false);
		Histogram_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				JsonId.setTimePostion(position);
				JsonId.setNumPostion(position);
				if (ChartsUtils.isNetworkAvailable(getActivity())) {
						// 第一次不请求，点击后请求
						if (isresult) {
							if (type.equals("1")) {
								HttpGetData(
										Constant.BATTERY_HIS_TIMESP
												+ spUtils.getRang().getData().get(position).getTestID() + "/" + str,
										null);

							} else {
								HttpGetData(
										Constant.BATTERY_HIS_TIMESP2
												+ spUtils.getRang().getData().get(position).getTestID() + "/" + str,
										null);
							}
						}
					}
					isresult = true;
				//} 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		cast = new hisBordcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SEND_UPDATA);
		filter.addAction(SEND_UPDATA_RESTART);
		filter.addAction(SEND_UPDATA_SYN);
		getActivity().registerReceiver(cast, filter);
		Histogram_sp.setSelection(JsonId.getTimePostion(), true);
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
						listTrend = JsonTools.getStringJson(new String(arg2), TrendChartUtils.class);
					} else {
						Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.net_error, 0).show();
				// // 登陆失败
			}
		});
	}

	/**
	 * 网络Get 请求 第二次直方图数据
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetData(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getStringJson(new String(arg2), Histogramf.class);
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
	 * 网络Get 时间段 Spinner 获取数据
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataSP(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						spUtils = JsonTools.getStringJson(new String(arg2), SPTimeUtils.class);
						handler.sendEmptyMessage(2);
					} else {
						Toast.makeText(getActivity(), R.string.resqustFails, 0).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.net_error, 0).show();
				// //
				// 登陆失败
			}
		});
	}

	/**
	 * 设置更新广播
	 */
	private class hisBordcast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(SEND_UPDATA)) {
				handler.sendEmptyMessage(3);
				handler.sendEmptyMessageDelayed(6, 10000);
			}
			if (intent.getAction().equals(SEND_UPDATA_RESTART)) {
				handler.sendEmptyMessage(4);
				handler.sendEmptyMessageDelayed(6, 10000);
			}
			if (intent.getAction().equals(SEND_UPDATA_SYN)) {
				handler.sendEmptyMessage(5);
				handler.sendEmptyMessageDelayed(6, 10000);
			}
		}
	}

	/**
	 * 重启显示数字
	 * 
	 * @param index
	 * 
	 *            String tex 更新内容
	 */
	public void setRestartHanlder(int index, String text) {
		this.showText = text;
		handler.sendEmptyMessage(index);
	}

	/**
	 * 图表 类
	 */
	private class chartObject {
		float values;
		int color;

		public chartObject(float values, int color) {
			this.values = values;
			this.color = color;
		}
	}

	/**
	 * 设置图表数据
	 */
	public void setChart() {
		listU = new ArrayList<chartObject>();
		listR = new ArrayList<chartObject>();
		listT = new ArrayList<chartObject>();
		listC = new ArrayList<chartObject>();
		listR2 = new ArrayList<chartObject>();
		listC2 = new ArrayList<chartObject>();
		if (list.getHisData().size() == 0 || list.getHisData() == null) {
			Log.e("tag", "获得的数据长度为0");
			return;
		}
		chartObject tmp = null;
		for (int i = 0; i < list.getHisData().size(); i++) {
			if (type.equals("1")) {
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getU()),
						Color.parseColor("#" + list.getHisData().get(i).getUColor()));
				listU.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getC()),
						Color.parseColor("#" + list.getHisData().get(i).getCColor()));
				listC.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getR()),
						Color.parseColor("#" + list.getHisData().get(i).getRColor()));
				listR.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getC2()),
						Color.parseColor("#" + list.getHisData().get(i).getC2Color()));
				listC2.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getR2()),
						Color.parseColor("#" + list.getHisData().get(i).getR2Color()));
				listR2.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getT()),
						Color.parseColor("#" + list.getHisData().get(i).getTColor()));
				listT.add(tmp);
			} else {
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getU()),
						Color.parseColor("#" + list.getHisData().get(i).getUColor()));
				listU.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getC()),
						Color.parseColor("#" + list.getHisData().get(i).getCColor()));
				listC.add(tmp);
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getR()),
						Color.parseColor("#" + list.getHisData().get(i).getRColor()));
				tmp = new chartObject(Float.parseFloat(list.getHisData().get(i).getT()),
						Color.parseColor("#" + list.getHisData().get(i).getTColor()));
				listT.add(tmp);
			}
		}
	}

	/**
	 * 解析文本数据 Histogram_name, Histogram_Battery_type, Histogram_manufacturers,
	 * Histogram_Battery_group, Histogram_Internal_standard,
	 * Histogram_total_voltage, Histogram_Battery_num,
	 * Histogram_total_current,Histogram_Battery_status,Histogram_charge,;
	 */
	public void setLoad() {
		if (list == null) {
			return;
		}
		Histogram_name.setText(list.getTitleData().get(0).getNodename());
		Histogram_Battery_type.setText(list.getTitleData().get(0).getBatterytypeName());
		Histogram_manufacturers.setText(list.getTitleData().get(0).getAutomation());
		Histogram_Battery_group.setText(list.getTitleData().get(0).getGroupname());
		Histogram_Internal_standard.setText(list.getTitleData().get(0).getNominalR() + "mΩ");
		Histogram_total_voltage.setText(list.getTitleData().get(0).getU_Str() + "V");
		Histogram_Battery_num.setText(list.getTitleData().get(0).getGcount() + "");
		Histogram_total_current.setText(list.getTitleData().get(0).getI() + "A");
		// 电池状态和充电状态
		Histogram_Battery_status.setText(list.getTitleData().get(0).getStatus());
		Histogram_Battery_status.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getGScolor()));
		Histogram_charge.setText(list.getTitleData().get(0).getWorkStatus());
		Histogram_charge.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getWScolor()));
		// 时间接口需要的id
		TestId = String.valueOf(list.getTestID());
		/*
		 * for (int i = 0; i < list.getTitleData().size(); i++) {
		 * texCharge.setText(list.getTitleData().get(i).getWorkStatus());
		 * btStatus.setText(list.getTitleData().get(i).getStatus());
		 * 
		 * btStatus.setTextColor(Color.parseColor("#" +
		 * list.getTitleData().get(i).getgScolor()));
		 * texCharge.setTextColor(Color.parseColor("#" +
		 * list.getTitleData().get(i).getwScolor()));
		 * 
		 * btStation.setText(list.getTitleData().get(i).getNodename());
		 * btGroup.setText(list.getTitleData().get(i).getGroupname());
		 * btType.setText(list.getTitleData().get(i).getBatterytypeName());
		 * btMinR.setText(list.getTitleData().get(i).getNominalR() + "mΩ");
		 * btMinU.setText(list.getTitleData().get(i).getVMinN() + "#" +
		 * list.getTitleData().get(i).getVMin() + "V");
		 * btMaxR.setText(list.getTitleData().get(i).getRMaxN() + "#" +
		 * list.getTitleData().get(i).getRMax() + "mΩ");
		 * btGroupU.setText(list.getTitleData().get(i).getTestgroupvoltage() +
		 * "V");
		 * btGroupI.setText(list.getTitleData().get(i).getTestgroupampere() +
		 * "A"); }
		 */
	}

	/**
	 * 生成图表数据
	 */
	public void generateData(List<chartObject> listValue, ColumnChartView chart, int numColumns) {
		if (chart == null || listValue == null || listValue.size() < 1) {
			return;
		}
		int numSubcolumns = 1; // 柱形显示列数
		List<Column> columns = new ArrayList<Column>();
		List<SubcolumnValue> values = null;
		List<AxisValue> axisValues = new ArrayList<AxisValue>();
		int Xnum[] = new int[listU.size()]; // X轴坐标
		for (int a = 1; a < listU.size() + 1; a++) {
			Xnum[a - 1] = a;
		}
		for (int i = 0; i < listU.size(); i++) {
			values = new ArrayList<SubcolumnValue>();
			for (int j = 0; j < numSubcolumns; ++j) {
				values.add(new SubcolumnValue(listValue.get(i).values, listValue.get(i).color)
						.setLabel(listValue.get(i).values + "")); // 设置数据及颜色值
			}
			axisValues.add(new AxisValue(i).setLabel(String.valueOf(Xnum[i]))); // 设置X轴
			columns.add(new Column(values).setHasLabelsOnlyForSelected(false).setHasLabels(false));// 添加柱
		}

		data = new ColumnChartData(columns);
		data.setValueLabelsTextColor(Color.BLACK);
		data.setValueLabelTextSize(12);
		data.setAxisXBottom(new Axis(axisValues).setHasLines(true).setTextColor(Color.BLACK).setTextSize(20)); // X轴
		data.setAxisYLeft(new Axis().setHasLines(true).setTextColor(Color.BLACK).setMaxLabelChars(3).setTextSize(10)); // Y轴
		data.setStacked(true);
		// 设置行为属性，支持缩放、滑动以及平移
		chart.setInteractive(true);
		chart.setZoomType(ZoomType.HORIZONTAL);
		// chart.setContainerScrollEnabled(false,
		// ContainerScrollType.HORIZONTAL);
		chart.setValueSelectionEnabled(true);
		chart.setColumnChartData(data);
	}

	/**
	 * 点击事件监听
	 */
	private class ValueTouchListener implements ColumnChartOnValueSelectListener {
		@Override
		public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
			TimeMonitorActivity ta = (TimeMonitorActivity) getActivity();
			JsonId.setNumPostion(columnIndex);
			ta.showFragment(2);
		}

		@Override
		public void onValueDeselected() {

		}

	}

	/**
	 * Title 标题
	 */
	public void setChartTitle() {
		titleU.setText("U(V)");
		titleU.setTextSize(20);
		titleU.setTextColor(Color.BLACK);
		titleR.setText("R1(mΩ)");
		titleR.setTextSize(20);
		titleR.setTextColor(Color.BLACK);
		titleC.setText("C(%)");
		titleC.setTextSize(20);
		titleC.setTextColor(Color.BLACK);
		titleCqita.setText(R.string.SOCC);
		titleCqita.setTextSize(20);
		titleCqita.setTextColor(Color.BLACK);
		titleT.setText("T(°C)");
		titleT.setTextSize(20);
		titleT.setTextColor(Color.BLACK);
		titleC2.setText("C2(F)");
		titleC2.setTextSize(20);
		titleC2.setTextColor(Color.BLACK);
		titleR2.setText("R2(mΩ)");
		titleR2.setTextSize(20);
		titleR2.setTextColor(Color.BLACK);
	}

	/**
	 * 绑定ID
	 */
	public void setID(View view) {
		/*
		 * btStation = (TextView)
		 * getActivity().findViewById(R.id.battery_station_name); btGroup =
		 * (TextView) getActivity().findViewById(R.id.battery_group_name);
		 * btType = (TextView)
		 * getActivity().findViewById(R.id.battery_type_name); btMinR =
		 * (TextView) getActivity().findViewById(R.id.battery_min_R_name);
		 * btMaxR = (TextView)
		 * getActivity().findViewById(R.id.battery_max_R_name); btMinU =
		 * (TextView) getActivity().findViewById(R.id.battery_min_U_name);
		 * btGroupU = (TextView)
		 * getActivity().findViewById(R.id.battery_group_u_name); btGroupI =
		 * (TextView) getActivity().findViewById(R.id.battery_group_cur_name);
		 * btStatus = (TextView)
		 * getActivity().findViewById(R.id.battery_status_name);
		 */
		// spTime = (Spinner) getActivity().findViewById(R.id.battery_sp_times);
		Histogram_name = (TextView) view.findViewById(R.id.Histogram_name);
		Histogram_Battery_type = (TextView) view.findViewById(R.id.Histogram_Battery_type);
		Histogram_manufacturers = (TextView) view.findViewById(R.id.Histogram_manufacturers);
		Histogram_Battery_group = (TextView) view.findViewById(R.id.Histogram_Battery_group);
		Histogram_Internal_standard = (TextView) view.findViewById(R.id.Histogram_Internal_standard);
		Histogram_total_voltage = (TextView) view.findViewById(R.id.Histogram_total_voltage);
		Histogram_Battery_num = (TextView) view.findViewById(R.id.Histogram_Battery_num);
		Histogram_total_current = (TextView) view.findViewById(R.id.Histogram_total_current);
		Histogram_Battery_status = (TextView) view.findViewById(R.id.Histogram_Battery_status);
		Histogram_charge = (TextView) view.findViewById(R.id.Histogram_charge);
		Histogram_sp = (Spinner) view.findViewById(R.id.Histogram_sp);

		chartU = (ColumnChartView) view.findViewById(R.id.his_line_U);
		chartR = (ColumnChartView) view.findViewById(R.id.his_line_R1);
		chartT = (ColumnChartView) view.findViewById(R.id.his_line_T);
		chartC = (ColumnChartView) view.findViewById(R.id.his_line_C);
		chartCqita = (ColumnChartView) view.findViewById(R.id.his_line_Cqita);
		chartR2 = (ColumnChartView) view.findViewById(R.id.his_line_R2);
		chartC2 = (ColumnChartView) view.findViewById(R.id.his_line_C2);
		titleU = (TextView) view.findViewById(R.id.his_u_title);
		titleC = (TextView) view.findViewById(R.id.his_c_title);
		titleR = (TextView) view.findViewById(R.id.his_r1_title);
		titleT = (TextView) view.findViewById(R.id.his_t_title);
		titleR2 = (TextView) view.findViewById(R.id.his_r2_title);
		titleC2 = (TextView) view.findViewById(R.id.his_c2_title);
		texCmd = (TextView) view.findViewById(R.id.his_gaps_cmd);
		texCharge = (TextView) view.findViewById(R.id.battery_charge);

		qita = (LinearLayout) view.findViewById(R.id.qita);
		dingjian = (LinearLayout) view.findViewById(R.id.dingjian);
		titleCqita = (TextView) view.findViewById(R.id.his_c_titleqita);
		Hista_sydl = (TextView) view.findViewById(R.id.Hista_sydl);
	}

	/**
	 * 隐藏和显示R1，R2，c2
	 */
	private void setGone() {
		chartR.setVisibility(View.GONE);
		chartR2.setVisibility(View.GONE);
		chartC2.setVisibility(View.GONE);

		titleR.setVisibility(View.GONE);
		titleR2.setVisibility(View.GONE);
		titleC2.setVisibility(View.GONE);
		titleC.setText(R.string.SOCC);

		dingjian.setVisibility(View.GONE);
		qita.setVisibility(View.VISIBLE);
		Hista_sydl.setText(R.string.sydl);
		generateData(listU, chartU, listU.size());
		generateData(listT, chartT, listU.size());
		generateData(listC, chartC, listU.size());
		generateData(listC, chartCqita, listU.size());
	}

	private void setVisbli() {

		chartR.setVisibility(View.VISIBLE);
		chartR2.setVisibility(View.VISIBLE);
		chartC2.setVisibility(View.VISIBLE);

		titleR.setVisibility(View.VISIBLE);
		titleR2.setVisibility(View.VISIBLE);
		titleC2.setVisibility(View.VISIBLE);

		dingjian.setVisibility(View.VISIBLE);
		qita.setVisibility(View.GONE);
		generateData(listU, chartU, listU.size());
		generateData(listR, chartR, listU.size());
		generateData(listT, chartT, listU.size());
		generateData(listC, chartC, listU.size());
		generateData(listC, chartCqita, listU.size());
		generateData(listR2, chartR2, listU.size());
		generateData(listC2, chartC2, listU.size());
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

	public HistogramFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	@Override
	public void onDestroyView() {
		list = null;
		chartU = null;
		chartR = null;
		chartR2 = null;
		chartC = null;
		chartCqita = null;
		chartC2 = null;
		chartT = null;
		getActivity().unregisterReceiver(cast);
		super.onDestroyView();
	}
}
