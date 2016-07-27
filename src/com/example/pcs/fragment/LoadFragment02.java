package com.example.pcs.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.TrendChartUtils;

import MyLayout.MyLineChartView_LOAD;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;

public class LoadFragment02 extends Fragment {
	private MyLineChartView_LOAD loadfm_U, loadfm_I, loadfm_Load, loadfm_Pa, loadfm_Sa, loadfm_PFa;
	public static TrendChartUtils list = null;
	private List<ChartLineDataUabc> listUabc = null;
	private List<ChartLineDataUabc> listIabc = null;
	private List<ChartLineDataUabc> listLOADabc = null;
	private List<ChartLineDataUabc> listPaabc = null;
	private List<ChartLineDataUabc> listSaabc = null;
	private List<ChartLineDataUabc> listPfaabc = null;
	private ChartLineDataUabc values = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.loadfm02, null);
		initData(view);
		return view;
	}

	private void initData(View view) {
		loadfm_U = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_U);
		loadfm_I = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_I);
		loadfm_Load = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_Load);
		loadfm_Pa = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_Pa);
		loadfm_Sa = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_Sa);
		loadfm_PFa = (MyLineChartView_LOAD) view.findViewById(R.id.loadfm_PFa);
		listUabc = new ArrayList<ChartLineDataUabc>();
		listIabc = new ArrayList<ChartLineDataUabc>();
		listLOADabc = new ArrayList<ChartLineDataUabc>();
		listPaabc = new ArrayList<ChartLineDataUabc>();
		listSaabc = new ArrayList<ChartLineDataUabc>();
		listPfaabc = new ArrayList<ChartLineDataUabc>();
		// 初始化参数
		float[][] f = { { 0f }, { 0f }, { 0f } };
		listUabc.add(new ChartLineDataUabc(f));
		generateDataABC(listUabc, loadfm_U, 1, 1);
		generateDataABC(listUabc, loadfm_I, 1, 1);
		generateDataABC(listUabc, loadfm_Load, 1, 1);
		generateDataABC(listUabc, loadfm_Pa, 1, 1);
		generateDataABC(listUabc, loadfm_Sa, 1, 1);
		generateDataABC(listUabc, loadfm_PFa, 1, 1);
		
		HttpGetData("get_BatteryByPage/939/2/72/1", null);
	}

	/***
	 * 网络请求
	 * 
	 * @param url
	 * @param params
	 */
	private void HttpGetData(String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getStringJson(new String(arg2), TrendChartUtils.class);
						Log.e("tag", "请求数据成功");
						setData(list);
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
	 * 设置趋势图
	 */

	private void generateDataABC(List<ChartLineDataUabc> listvalues, MyLineChartView_LOAD chart, int numberOfLines,
			int point) {
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
			}
			axisValues.add(new AxisValue(0)); // 设置X轴
			ayisValues.add(new AxisValue(0)); // 设置y轴
			line = new Line(values);
			if (i == 0) {
				line.setColor(ChartUtils.COLOR_ORANGE);

			} else if (i == 1) {
				line.setColor(ChartUtils.COLOR_GREEN);
			} else {
				line.setColor(ChartUtils.COLOR_RED);
			}
			line.setStrokeWidth(3);
			line.setPointRadius(0);
			line.setHasPoints(true);
			lines.add(line);
		}
		LineChartData data = new LineChartData(lines);
		data.setValueLabelTextSize(12);
		data.setAxisXBottom(new Axis(axisValues).setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(5));
		data.setAxisYLeft(
				new Axis().setHasLines(false).setLineColor(Color.BLACK).setMaxLabelChars(5).setTextColor(Color.BLACK));
		chart.setLineChartData(data);
	}

	/***
	 * 添加数据
	 */
	private void setData(TrendChartUtils list) {
		if (list == null)
			return;
		listUabc.clear();
		listIabc.clear();
		listLOADabc.clear();
		listPaabc.clear();
		listSaabc.clear();
		listPfaabc.clear();
		float[][] groupUI = new float[3][list.getDataInEveryPage().getBatteryByPage().getRecordCount()];
		for (int k = 0; k < 3; k++) {
			for (int j = 0; j < list.getDataInEveryPage().getBatteryByPage().getRecordCount(); j++) {
				if (k == 0) {
					groupUI[k][j] = Float
							.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(j).getVoltage());
				}
				else if (k == 1) {
					groupUI[k][j] = Float
							.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(j).getVoltage())-10;
				}else{
					groupUI[k][j] = Float
							.parseFloat(list.getDataInEveryPage().getGroupByPage().getData().get(j).getVoltage())-20;
				}
				values = new ChartLineDataUabc(groupUI);
			}
			listUabc.add(values);
			listIabc.add(values);
			listLOADabc.add(values);
			listPaabc.add(values);
			listSaabc.add(values);
			listPfaabc.add(values);
		}
		int point = list.getDataInEveryPage().getBatteryByPage().getRecordCount();
		generateDataABC(listUabc, loadfm_U, 3, point);
		generateDataABC(listIabc, loadfm_I, 3, point);
		generateDataABC(listLOADabc, loadfm_Load, 3, point);
		generateDataABC(listPaabc, loadfm_Pa, 3, point);
		generateDataABC(listSaabc, loadfm_Sa, 3, point);
		generateDataABC(listPfaabc, loadfm_PFa, 3, point);
		loadfm_U.postInvalidate();
		loadfm_I.postInvalidate();
	}

	/**
	 * Uabc 类型数据
	 */
	public class ChartLineDataUabc {
		float[][] values;

		public ChartLineDataUabc(float[][] values) {
			this.values = values;
		}

	}
}
