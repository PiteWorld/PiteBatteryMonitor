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
import com.pite.batterymonitor.utils.BatteryInformation;
import com.pite.batterymonitor.utils.ChartParamers;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.Histogramf;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.RefreshListView;
import com.pite.batterymonitor.utils.TrendChartUtils;
import com.pite.batterymonitor.PacketActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

import MyLayout.MyLineChartView;
import android.app.Fragment;
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
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;

/**
 * fragment 趋势图
 */
public class TrendFragment extends Fragment {
	public Spinner trend_sp; // 测试时间， 时间间隔
	private MyLineChartView layoutU, layoutR, layoutC, layoutC2, layoutR2, layoutT, layoutUI, trend_uqita, trend_iqita,
			trend_cqita; // 组电压及
	private TextView texUI, texU, texR, texR2, texC2, texT, texC, texTitleUI, texTitleU, texTitleR, texTitleR2,
			texTitleC, texTitleC2, texTitleT, texTitleUIS;
	private TextView trend_name, trend_Battery_type, trend_manufacturers, trend_Battery_group, trend_Internal_standard,
			trend_total_voltage, trend_Battery_num, trend_total_current, trend_Battery_status, trend_charge;
	// 图形的显示与隐藏
	private TextView tex_title_uqita, trend_tex_uqita, tex_title_iqita, trend_tex_iqita, tex_title_cqita,
			trend_tex_Cqita,thr_sydl;
	private LinearLayout trend_ll_ua, trend_ll_u, trend_ll_i, trend_ll_cqita, trend_ll_c,trend_ll_r,trend_ll_c2,trend_ll_r2;
	private TimeMonitorActivity tm; // 组电流
	private int columnIndex; // 直方图点击的 列数
	// private TextView btStation, btGroup, btType, btMinR, btMaxR, btMinU,
	// btGroupU, btGroupI, btStatus; // 电池状态信息
	private ArrayAdapter<String> spAdapter = null;
	public static TrendChartUtils list = null;
	private List<String> spListData = null; // spinner数据集合
	private int num = 2;
	private boolean ISspGaps = false;
	private List<BatteryInformation> listNum = null;
	private ArrayAdapter<String> spGapAdapter = null;
	private List<String> spGapList = null;
	private List<ChartLineDataUI> listGroupUI = new ArrayList<ChartLineDataUI>(); // 趋势具体数据
	private List<ChartLineData> listGroupV = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupR = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupT = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupCap = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupR2 = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupC2 = new ArrayList<ChartLineData>();
	// 总电压 总电流和c
	private List<ChartLineData> listGroupuqita = new ArrayList<ChartLineData>();
	private List<ChartLineData> listGroupiqita = new ArrayList<ChartLineData>();
	private String strCmd;
	private Histogramf listdate;
	private String type;
//	private RefreshListView listView = null;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				setChart();
				// setXTime(); // 设置X轴时间
				if (type.equals("1"))
					setVisbli();
				else
					setGone();
			} else if (msg.what == 2) {
				if (strCmd != null) {
				}
			} else if (msg.what == 3) {
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		type = getArguments().getString("type");
		View view = View.inflate(getActivity(), R.layout.fragment_trend, null);
		setID(view); // 绑定ID.
		listdate = HistogramFragment.list;
		setTitle(listdate);
		setTitle();// 设置趋势图标题
		return view;
	}

	/***
	 * 
	 * 表格上面的通用数据
	 * 
	 * @param listdate2
	 */
	private void setTitle(Histogramf listdate) {
		if (listdate == null || listdate.getTitleData().size() == 0)
			return;
		trend_name.setText(listdate.getTitleData().get(0).getNodename());
		trend_Battery_type.setText(listdate.getTitleData().get(0).getBatterytypeName());
		trend_manufacturers.setText(listdate.getTitleData().get(0).getAutomation());
		trend_Battery_group.setText(listdate.getTitleData().get(0).getGroupname());
		trend_Internal_standard.setText(listdate.getTitleData().get(0).getNominalR() + "mΩ");
		trend_total_voltage.setText(listdate.getTitleData().get(0).getU_Str() + "V");
		trend_Battery_num.setText(listdate.getTitleData().get(0).getGcount() + "");
		trend_total_current.setText(listdate.getTitleData().get(0).getI() + "A");
		// 电池状态和充电状态
		trend_Battery_status.setText(listdate.getTitleData().get(0).getStatus());
		trend_Battery_status.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getGScolor()));
		trend_charge.setText(listdate.getTitleData().get(0).getWorkStatus());
		trend_charge.setTextColor(Color.parseColor("#" + listdate.getTitleData().get(0).getWScolor()));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void initView() {
		columnIndex = ChartParamers.getColumnIndex() + 1;
		StringBuffer str = new StringBuffer();
		float[][] f = { { 0f }, { 0f } };
		listGroupUI.add(new ChartLineDataUI(f));
		listGroupV.add(new ChartLineData(0f));
		if (type.equals("1")) {
			generateDataUI(listGroupUI, layoutUI, 1, 1);
			generateData(listGroupV, layoutU, 1, 1);
			generateData(listGroupV, layoutR, 1, 1);
			generateData(listGroupV, layoutT, 1, 1);
			generateData(listGroupV, layoutC, 1, 1);
			generateData(listGroupV, layoutR2, 1, 1);
			generateData(listGroupV, layoutC2, 1, 1);
			trend_ll_u.setVisibility(View.GONE);
			trend_ll_i.setVisibility(View.GONE);
			trend_ll_cqita.setVisibility(View.GONE);
		} else {
			generateData(listGroupV, layoutU, 1, 1);
			generateData(listGroupV, layoutT, 1, 1);
			generateData(listGroupV, trend_cqita, 1, 1);
			generateData(listGroupV, trend_iqita, 1, 1);
			generateData(listGroupV, trend_uqita, 1, 1);
			trend_ll_u.setVisibility(View.VISIBLE);
			trend_ll_i.setVisibility(View.VISIBLE);
			trend_ll_cqita.setVisibility(View.VISIBLE);
			trend_ll_ua.setVisibility(View.GONE);
			trend_ll_c.setVisibility(View.GONE);
			trend_ll_r.setVisibility(View.GONE);
			trend_ll_c2.setVisibility(View.GONE);
			trend_ll_r2.setVisibility(View.GONE);
			
		}
		/*if (ChartsUtils.isNetworkAvailable(getActivity())) {
			if (type.equals("1")) {
				str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/").append("1")
						.append("/").append("100").append("/").append(columnIndex);
			} else {
				str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/").append("1")
						.append("/").append("100").append("/").append(columnIndex);
			}
			HttpGetData(str.toString(), null);

		} else {
			Toast.makeText(getActivity(), R.string.net_no, 0).show();
		}*/
		trend_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				JsonId.setNumPostion(position);
				if (ChartsUtils.isNetworkAvailable(getActivity())) {
					StringBuffer str = new StringBuffer();
					if (type.equals("1")) {
						str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/")
								.append("1").append("/").append("72").append("/").append(position + 1);
					} else {
						str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/")
								.append("1").append("/").append("72").append("/").append(position + 1);
					}
					HttpGetData(str.toString(), null);

				} else {
					//Toast.makeText(getActivity(), "请稍等.............", 0).show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		setBatteryNum();// 设置电池号
		trend_sp.setSelection(JsonId.getNumPostion(), true);
		// spGaps.setSelection(JsonId.getTimeGapsPostion(), true);
		
	}

	public TrendFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	/**
	 * 隐藏与显示
	 */
	private void setVisbli() {
		// trend_ll_u,trend_ll_i,trend_ll_cqita
		trend_ll_u.setVisibility(View.GONE);
		trend_ll_i.setVisibility(View.GONE);
		trend_ll_cqita.setVisibility(View.GONE);
		int point = list.getDataInEveryPage().getBatteryByPage().getRecordCount();
		generateDataUI(listGroupUI, layoutUI, 2, point);
		generateData(listGroupV, layoutU, 1, point);
		generateData(listGroupR, layoutR, 1, point);
		generateData(listGroupT, layoutT, 1, point);
		generateData(listGroupCap, layoutC, 1, point);
		generateData(listGroupR2, layoutR2, 1, point);
		generateData(listGroupC2, layoutC2, 1, point);
		layoutUI.postInvalidate();
		layoutU.postInvalidate();
		layoutC.postInvalidate();
		layoutT.postInvalidate();
		layoutR.postInvalidate();
		layoutC2.postInvalidate();
		layoutR2.postInvalidate();
		// texR, texR2, texC2
	}

	private void setGone() {
		thr_sydl.setText(R.string.sydl);
		trend_ll_u.setVisibility(View.VISIBLE);
		trend_ll_i.setVisibility(View.VISIBLE);
		trend_ll_cqita.setVisibility(View.VISIBLE);
		trend_ll_ua.setVisibility(View.GONE);
		trend_ll_c.setVisibility(View.GONE);
		trend_ll_r.setVisibility(View.GONE);
		trend_ll_c2.setVisibility(View.GONE);
		trend_ll_r2.setVisibility(View.GONE);
		int point = list.getDataInEveryPage().getBatteryByPage().getRecordCount();
		generateData(listGroupV, layoutU, 1, point);
		generateData(listGroupT, layoutT, 1, point);
		generateData(listGroupuqita, trend_uqita, 1, point);
		generateData(listGroupiqita, trend_iqita, 1, point);
		generateData(listGroupCap, trend_cqita, 1, point);

		layoutU.postInvalidate();
		layoutT.postInvalidate();
		trend_uqita.postInvalidate();
		trend_iqita.postInvalidate();
		trend_cqita.postInvalidate();
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataGaps(final String url, final RequestParams params) {
		HttpReustClient.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				if (new String(arg2).equals("null(1)")) {
					Toast.makeText(getActivity(), R.string.SET_OK, 0).show();
				} else {
					Toast.makeText(getActivity(), R.string.SET_FAIL, 0).show();
				}
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 设置X轴时间
	 */
	TextView[] xTime;

	public void setXTime() {
		if (type.equals("1"))
			xTime = new TextView[] { texUI, texU, texR, texT, texC, texR2, texC2 };
		else
			xTime = new TextView[] { trend_tex_uqita,trend_tex_iqita,trend_tex_Cqita,texU, texT};

		for (int i = 0; i < xTime.length; i++) {
			xTime[i].setText(setSulist(list.getDataInEveryPage().getBatteryByPage().getData().get(0).getTime()) + "  "
					+ setSulist(list.getDataInEveryPage().getBatteryByPage().getData()
							.get(list.getDataInEveryPage().getBatteryByPage().getRecordCount() / 2).getTime())
					+ "  " + setSulist(list.getDataInEveryPage().getBatteryByPage().getData()
							.get(list.getDataInEveryPage().getBatteryByPage().getRecordCount() - 1).getTime()));
		}
	}

	/**
	 * 截取指定格式数据
	 */
	public String setSulist(String str) {
		String string = null;
		for (int i = 0; i < str.length(); i++) {
			string = str.substring(5, 16);
		}
		return string;
	}

	/**
	 * 设置趋势图标题
	 */
	public void setTitle() {
		texTitleUI.setText(R.string.pite_battery_UI);
		texTitleUI.setTextColor(Color.BLUE);
		texTitleUIS.setText(R.string.pite_battery_UIS);
		texTitleUIS.setTextColor(Color.GREEN);
		texTitleU.setText(R.string.pite_battery_V);
		texTitleU.setTextColor(Color.GREEN);
		texTitleR.setText(R.string.pite_battery_R);
		texTitleR.setTextColor(Color.GREEN);
		texTitleT.setText(R.string.pite_battery_T);
		texTitleT.setTextColor(Color.GREEN);
		texTitleC.setText(R.string.pite_battery_C);
		texTitleC.setTextColor(Color.GREEN);
		texTitleR2.setText(R.string.pite_battery_R2);
		texTitleR2.setTextColor(Color.GREEN);
		texTitleC2.setText(R.string.pite_battery_F);
		texTitleC2.setTextColor(Color.GREEN);
		//extView tex_title_uqita, trend_tex_Uqita, tex_title_iqita, trend_tex_iqita, tex_title_cqita,
		//trend_tex_Cqita;
		tex_title_uqita.setText(R.string.pite_battery_UI2);
		tex_title_uqita.setTextColor(Color.GREEN);
		tex_title_iqita.setText(R.string.pite_battery_UIS);
		tex_title_iqita.setTextColor(Color.GREEN);
		tex_title_cqita.setText(R.string.SOC);
		tex_title_cqita.setTextColor(Color.GREEN);
	}

	/***
	 * 趋势数据
	 */
	private void setChart() {
		listGroupUI = new ArrayList<ChartLineDataUI>();
		listGroupV = new ArrayList<ChartLineData>();
		listGroupR = new ArrayList<ChartLineData>();
		listGroupT = new ArrayList<ChartLineData>();
		listGroupCap = new ArrayList<ChartLineData>();
		listGroupR2 = new ArrayList<ChartLineData>();
		listGroupC2 = new ArrayList<ChartLineData>();
		listGroupuqita = new ArrayList<ChartLineData>();
		listGroupiqita = new ArrayList<ChartLineData>();
		ChartLineData tmp = null;
		ChartLineDataUI tmpUI = null;
		float[][] groupUI = new float[num][list.getDataInEveryPage().getBatteryByPage().getRecordCount()];
		for (int k = 0; k < num; k++) {
			for (int j = 0; j < list.getDataInEveryPage().getBatteryByPage().getRecordCount(); j++) {
				if (k == 0) {
					groupUI[k][j] = Float
							.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(j).getVoltage());
				}
				if (k == 1) {
					groupUI[k][j] = Float
							.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(j).getAmpere());
				}
				tmpUI = new ChartLineDataUI(groupUI);
			}
			listGroupUI.add(tmpUI);
		}
		if (type.equals("1")) {
			for (int i = 0; i < list.getDataInEveryPage().getBatteryByPage().getRecordCount(); i++) {
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getVoltage()));
				listGroupV.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(list.getDataInEveryPage().getBatteryByPage().getData().get(i)
						.getEvents().get(0).getResistance()));
				listGroupR.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(list.getDataInEveryPage().getBatteryByPage().getData().get(i)
						.getEvents().get(0).getTemperature() + ""));
				listGroupT.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getCapacity()
								+ ""));
				listGroupCap.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getR2()));
				listGroupR2.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getC2()));
				listGroupC2.add(tmp);
			}
		} else {
			for (int i = 0; i < list.getDataInEveryPage().getBatteryByPage().getRecordCount(); i++) {
				// 获取U和I
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getGroupByPage().getData().get(i).getVoltage()));
				listGroupuqita.add(tmp);
				
				tmp = new ChartLineData(
						Float.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(i).getAmpere() + ""));
				listGroupiqita.add(tmp);

				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getVoltage()));
				listGroupV.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(list.getDataInEveryPage().getBatteryByPage().getData().get(i)
						.getEvents().get(0).getTemperature() + ""));
				listGroupT.add(tmp);
				tmp = new ChartLineData(Float.parseFloat(
						list.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getCapacity()
								+ ""));
				listGroupCap.add(tmp);
			}
		}
		Log.e("2", "数据更换成功");
	}
	/**
	 * 设置趋势图
	 */
	private void generateData(List<ChartLineData> listvalues, MyLineChartView chart, int numberOfLines, int point) {
		float[] f = new float[point];
		int max = 0;
		List<Line> lines = new ArrayList<Line>();
		List<AxisValue> axisValues = new ArrayList<AxisValue>();
		List<AxisValue> ayisValues = new ArrayList<AxisValue>();
		List<PointValue> values = null;
		Line line = null;
		for (int i = 0; i < numberOfLines; ++i) {
			values = new ArrayList<PointValue>();
			int j = 0;
			values.add(new PointValue(0, 0));
			for (j = 0; j < point; j++) {
				values.add(new PointValue(j, listvalues.get(j).values));
				f[j] = listvalues.get(j).values;
			}
			max = getMax(f);
			axisValues.add(new AxisValue(0)); // 设置X轴
			ayisValues.add(new AxisValue(max * 2));
			line = new Line(values);
			line.setColor(ChartUtils.COLOR_GREEN);
			line.setHasPoints(true);
			line.setStrokeWidth(3);
			line.setPointRadius(2);
			lines.add(line);
		}
		for (int i = 0; i < 1; ++i) {
			values = new ArrayList<PointValue>();
			int j = 0;
			values.add(new PointValue(0, 0));
			values.add(new PointValue(0, max * 2));
			axisValues.add(new AxisValue(0)); // 设置X轴
			line = new Line(values);
			line.setColor(Color.BLACK);
			line.setHasPoints(false);
			line.setStrokeWidth(3);
			line.setPointRadius(0);
			lines.add(line);
		}
		LineChartData data = new LineChartData(lines);
		data.setAxisXBottom(new Axis(axisValues).setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(5));
		data.setAxisYLeft(
				new Axis().setHasLines(false).setLineColor(Color.BLACK).setMaxLabelChars(5).setTextColor(Color.BLACK));
		chart.setLineChartData(data);
	}
	/**
	 * 设置趋势图
	 */
	int a = 0;

	private void generateDataUI(List<ChartLineDataUI> listvalues, MyLineChartView chart, int numberOfLines, int point) {
		float[] f = new float[point];
		int max = 0;
		List<Line> lines = new ArrayList<Line>();
		List<AxisValue> axisValues = new ArrayList<AxisValue>();
		List<AxisValue> ayisValues = new ArrayList<AxisValue>();
		List<PointValue> values = null;
		Line line = null;
		// if (a == 0) {
		for (int i = 0; i < numberOfLines; ++i) {
			values = new ArrayList<PointValue>();
			for (int j = 0; j < point; j++) {
				values.add(new PointValue(j, listvalues.get(i).values[i][j]));
				// a = 1;
			}
			// max = getMax(f);
			axisValues.add(new AxisValue(0)); // 设置X轴
			ayisValues.add(new AxisValue(0)); // 设置y轴
			line = new Line(values);
			if (i == 0) {
				line.setColor(ChartUtils.COLOR_BLUE);

			} else {
				line.setColor(ChartUtils.COLOR_GREEN);
			}
			line.setStrokeWidth(3);
			line.setPointRadius(0);
			line.setHasPoints(true);
			lines.add(line);
		}
		// int height = chart.getHeight();
		LineChartData data = new LineChartData(lines);
		data.setValueLabelTextSize(12);
		data.setAxisXBottom(new Axis(axisValues).setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(5));
		data.setAxisYLeft(
				new Axis().setHasLines(false).setLineColor(Color.BLACK).setMaxLabelChars(5).setTextColor(Color.BLACK));
		chart.setLineChartData(data);
		// generateDataUI(listGroupUI, layoutUI, num, point);
		// }
		// if (a == 1) {
		// for (int i = 0; i < numberOfLines; ++i) {
		// values = new ArrayList<PointValue>();
		// for (int j = 0; j < point; j++) {
		// values.add(new PointValue(j,
		// listvalues.get(0).values[1][j]));
		// Log.e("2",
		// "组电流："+getActivity().getIntent().getExtras().getInt("his")+"
		// "+listvalues.get(0).values[1][j]+"");
		// }
		// }
		// line = new Line(values);
		// line.setColor(ChartUtils.COLOR_RED);
		// line.setStrokeWidth(3);
		// line.setPointRadius(0);
		// line.setHasPoints(true);
		// lines.add(line);
		// a=0;
		// int height = chart.getHeight();
		// LineChartData data = new LineChartData(lines);
		// data.setValueLabelTextSize(12);
		// data.setAxisXBottom(new
		// Axis(axisValues).setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(5));
		// data.setAxisYRight(new
		// Axis().setHasLines(false).setTextColor(Color.BLACK)
		// .setFormatter(new HeightValueFormatter(height / 3.5f, 0,
		// 0)));
		// chart.setLineChartData(data);
		// }
		// }

	}

	// for (int i = 0; i < 1; ++i) {
	// values = new ArrayList<PointValue>();
	// int j = 0;
	// values.add(new PointValue(0, 0));
	// values.add(new PointValue(0, max * 2));
	// axisValues.add(new AxisValue(0)); // 设置X轴
	// line = new Line(values);
	// line.setColor(Color.BLACK);
	// line.setHasPoints(false);
	// line.setStrokeWidth(3);
	// line.setPointRadius(0);
	// lines.add(line);
	// }

	// Viewport v = chart.getMaximumViewport();
	// v.set(v.left, height, v.right, 0);
	// chart.setMaximumViewport(v);
	// chart.setCurrentViewport(v);

	// for (int i = 0; i < 1; ++i) {
	// values = new ArrayList<PointValue>();
	// int j = 0;
	// values.add(new PointValue(0, 0));
	// values.add(new PointValue(0, max * 2));
	// axisValues.add(new AxisValue(0)); // 设置X轴
	// line = new Line(values);
	// line.setColor(Color.BLACK);
	// line.setHasPoints(false);
	// line.setStrokeWidth(3);
	// line.setPointRadius(0);
	// lines.add(line);
	// }

	/**
	 * 
	 */
	private static class HeightValueFormatter extends SimpleAxisValueFormatter {

		private float scale;
		private float sub;
		private int decimalDigits;

		public HeightValueFormatter(float scale, float sub, int decimalDigits) {
			this.scale = scale;
			this.sub = sub;
			this.decimalDigits = decimalDigits;
		}

		@Override
		public int formatValueForAutoGeneratedAxis(char[] formattedValue, float value, int autoDecimalDigits) {
			float scaledValue = (value + sub) / scale;
			return super.formatValueForAutoGeneratedAxis(formattedValue, scaledValue, this.decimalDigits);
		}
	}

	/**
	 * 趋势数据类
	 */
	public class ChartLineData {
		float values;

		public ChartLineData(float values) {
			this.values = values;
		}
	}

	/**
	 * 趋势组电压 组电流
	 */
	public class ChartLineDataUI {
		float[][] values;

		public ChartLineDataUI(float[][] values) {
			this.values = values;
		}

	}

	/**
	 * 设置电池号数据
	 */
	public void setBatteryNum() {
		if (listdate == null || listdate.getHisData().size() == 0) {
			return;
		}
		spListData = new ArrayList<String>();
		for (int i = 0; i < listdate.getHisData().size(); i++) {
			spListData.add("      " + listdate.getHisData().get(i).getBatterynum() + "#");
		}
		spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spListData);
		spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		trend_sp.setAdapter(spAdapter);
		//trend_sp.addView(listView);
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */

	public void HttpGetData(String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getStringJson(new String(arg2), TrendChartUtils.class);
						handler.sendEmptyMessage(1);
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
	 * 解析文本数据
	 */

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

	/**
	 * 绑定ID
	 */
	public void setID(View view) {
		texTitleUIS = (TextView) view.findViewById(R.id.tex_title_uis);
		texUI = (TextView) view.findViewById(R.id.trend_tex_UI);
		texU = (TextView) view.findViewById(R.id.trend_tex_U);
		texC = (TextView) view.findViewById(R.id.trend_tex_C);
		texC2 = (TextView) view.findViewById(R.id.trend_tex_C2);
		texR = (TextView) view.findViewById(R.id.trend_tex_R);
		texR2 = (TextView) view.findViewById(R.id.trend_tex_R2);
		texT = (TextView) view.findViewById(R.id.trend_tex_T);
		texTitleUI = (TextView) view.findViewById(R.id.tex_title_ui);
		texTitleU = (TextView) view.findViewById(R.id.tex_title_u);
		texTitleC = (TextView) view.findViewById(R.id.tex_title_c);
		texTitleC2 = (TextView) view.findViewById(R.id.tex_title_c2);
		texTitleR = (TextView) view.findViewById(R.id.tex_title_r);
		texTitleR2 = (TextView) view.findViewById(R.id.tex_title_r2);
		texTitleT = (TextView) view.findViewById(R.id.tex_title_t);
		layoutU = (MyLineChartView) view.findViewById(R.id.trend_u);
		layoutC = (MyLineChartView) view.findViewById(R.id.trend_c);
		layoutC2 = (MyLineChartView) view.findViewById(R.id.trend_c2);
		layoutR = (MyLineChartView) view.findViewById(R.id.trend_r);
		layoutR2 = (MyLineChartView) view.findViewById(R.id.trend_r2);
		layoutT = (MyLineChartView) view.findViewById(R.id.trend_t);
		layoutUI = (MyLineChartView) view.findViewById(R.id.trend_gUI);

		trend_name = (TextView) view.findViewById(R.id.trend_name);
		trend_Battery_type = (TextView) view.findViewById(R.id.trend_Battery_type);
		trend_manufacturers = (TextView) view.findViewById(R.id.trend_manufacturers);
		trend_Battery_group = (TextView) view.findViewById(R.id.trend_Battery_group);
		trend_Internal_standard = (TextView) view.findViewById(R.id.trend_Internal_standard);
		trend_total_voltage = (TextView) view.findViewById(R.id.trend_total_voltage);
		trend_Battery_num = (TextView) view.findViewById(R.id.trend_Battery_num);
		trend_total_current = (TextView) view.findViewById(R.id.trend_total_current);
		trend_Battery_status = (TextView) view.findViewById(R.id.trend_Battery_status);
		trend_charge = (TextView) view.findViewById(R.id.trend_charge);
		trend_sp = (Spinner) view.findViewById(R.id.trend_sp);
		// trend_uqita,trend_iqita,trend_cqita; // 组电压及
		trend_uqita = (MyLineChartView) view.findViewById(R.id.trend_uqita);
		trend_iqita = (MyLineChartView) view.findViewById(R.id.trend_iqita);
		trend_cqita = (MyLineChartView) view.findViewById(R.id.trend_cqita);
		// tex_title_uqita,trend_tex_Uqita,tex_title_iqita,trend_tex_iqita,tex_title_cqita,trend_tex_Cqita;
		tex_title_uqita = (TextView) view.findViewById(R.id.tex_title_uqita);
		trend_tex_uqita = (TextView) view.findViewById(R.id.trend_tex_Uqita);
		tex_title_iqita = (TextView) view.findViewById(R.id.tex_title_iqita);
		trend_tex_iqita = (TextView) view.findViewById(R.id.trend_tex_iqita);
		tex_title_cqita = (TextView) view.findViewById(R.id.tex_title_cqita);
		trend_tex_Cqita = (TextView) view.findViewById(R.id.trend_tex_Cqita);
		// trend_ll_ua,trend_ll_u,trend_ll_i,trend_ll_cqita,trend_ll_c,tex_title_r,trend_ll_c2;
		trend_ll_ua = (LinearLayout) view.findViewById(R.id.trend_ll_ua);
		trend_ll_u = (LinearLayout) view.findViewById(R.id.trend_ll_u);
		trend_ll_i = (LinearLayout) view.findViewById(R.id.trend_ll_i);
		trend_ll_cqita = (LinearLayout) view.findViewById(R.id.trend_ll_cqita);
		
		trend_ll_c = (LinearLayout) view.findViewById(R.id.trend_ll_c);
		trend_ll_r = (LinearLayout) view.findViewById(R.id.trend_ll_r);
		trend_ll_c2 = (LinearLayout) view.findViewById(R.id.trend_ll_c2);
		trend_ll_r2 = (LinearLayout) view.findViewById(R.id.trend_ll_r2);
		thr_sydl = (TextView) view .findViewById(R.id.thr_sydl);
	}

	public void setcmdHandler(int index, String title) {
		this.strCmd = title;
		handler.sendEmptyMessage(index);
		handler.sendEmptyMessageDelayed(3, 5000);
	}

	/**
	 * 获取Y轴最大值
	 */
	public int getMax(float[] d) {
		int max = 0;
		for (int i = 0; i < d.length; i++) {
			if (d[i] > max) {
				max = (int) d[i];
			}
		}
		return max;
	}
}
