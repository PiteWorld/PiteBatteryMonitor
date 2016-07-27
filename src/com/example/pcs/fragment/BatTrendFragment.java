package com.example.pcs.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.example.pcs.fragment.LoadFragment02.ChartLineDataUabc;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.PCSInfos;
import com.pite.batterymonitor.utils.TrendChartUtils;

import MyLayout.BatLineChartView;
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

/***
 * 趋势图
 * @author Administrator
 *
 */
public class BatTrendFragment extends Fragment {
	private BatLineChartView bat_UV,bat_IA;
	public static TrendChartUtils list = null;
	private List<ChartLineDataUI> listUV = null;
	private List<ChartLineDataUI> listIA = null;
	private ChartLineDataUI values = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=View.inflate(getActivity(),R.layout.activity_battrendfragment, null);
		initData(view);
		return view;
	}
	private void initData(View view){
		bat_UV=(BatLineChartView) view.findViewById(R.id.bat_UV);
		bat_IA=(BatLineChartView) view.findViewById(R.id.bat_IA);
		listUV=new ArrayList<ChartLineDataUI>();
		listIA=new ArrayList<ChartLineDataUI>();
		// 初始化参数
		float[][] f = { { 0f }, { 0f } ,{ 0f}};
		listUV.add(new ChartLineDataUI(f));
		GenerateData(listUV, bat_UV, 1, 1);
		GenerateData(listIA, bat_IA, 1, 1);//??????????
		HttpGetData("get_BatteryByPage/932/1/72/1", null);
	}
	/***
	 * 1网络请求
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
	 * 2设置趋势图
	 */

	private void GenerateData(List<ChartLineDataUI> listvalues, BatLineChartView chart, int numberOfLines,
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
				values.add(new PointValue(j, listvalues.get(i).values[i][j])); //??????????
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
		data.setAxisXBottom(
				new Axis(axisValues).setHasLines(false).setTextColor(Color.BLACK).setMaxLabelChars(5));
		data.setAxisYLeft(
				new Axis().setHasLines(false).setLineColor(Color.BLACK).setMaxLabelChars(5).setTextColor(Color.BLACK));
		chart.setLineChartData(data);
	}
	
	
	/***
	 * 3添加数据
	 */
	private void setData(TrendChartUtils list) {
		if (list == null)
			return;
		listUV.clear();
		listIA.clear();
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
				values = new ChartLineDataUI(groupUI);
			}
			listUV.add(values);
			listIA.add(values);
			
		}
		int point = list.getDataInEveryPage().getBatteryByPage().getRecordCount();
		GenerateData(listUV, bat_UV, 1, point);
		GenerateData(listIA, bat_IA, 1, point);
		bat_UV.postInvalidate();
		bat_IA.postInvalidate();
	}

	/**
	 *4 UI 类型数据  U 电压
	 *            I 电流
	 */
	public class ChartLineDataUI {
		float[][] values;
		public ChartLineDataUI(float[][] values) {
			this.values = values;
		}

	}
	

}
