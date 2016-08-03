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
import com.pite.batterymonitor.utils.BatteryInformation;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.Histogramf;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.RefreshListView;
import com.pite.batterymonitor.utils.RefreshListView.OnRefreshListener;
import com.pite.batterymonitor.utils.TrendChartUtils;
import com.pite.batterymonitor.PacketActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 趋势 数据分析
 */
public class TrendDataFragment extends Fragment {
	public Spinner trenData_sp;   //测试时间 电池型号
	// private TextView btStation, btGroup, btType, btMinR, btMaxR, btMinU,
	// btGroupU, btGroupI, btStatus, btCharge, texCmd; // 电池状态信息
	private TextView trenData_name, trenData_Battery_type, trenData_manufacturers, trenData_Battery_group,
			trenData_Internal_standard, trenData_total_voltage, trenData_Battery_num, trenData_total_current,
			trenData_Battery_status, trenData_charge,trend_sydl;
	private String[] titles = null;
	private Context context;
	private TrendChartUtils lists = null; // 电池状态信息
	private Histogramf titleList = null;
	private List<BatteryInformation> spList = null;// spinner 电池号
	private List<String> spListData = null; // spinner数据集合
	private ArrayAdapter<String> spAdapter = null; // spinner adapter
	private TimeMonitorActivity tm = null;
	private ArrayAdapter<String> spGapAdapter = null;
	private List<String> spGapList = null;
	private int poistion = 0;
	private int upData = 72;
	private int selection;
	// 判断是否是最后一页
	private boolean islast;
	// 判断是否为最后一页
	private int RecordCount;
	// 记录原始数据长度
	private int numcount;
	private boolean firstResult;
	// 分页加载的标记
	private int num = 1;
	private LinearLayout ll;// 用来添加标题栏的item
	private LinearLayout chlickll;// 用来添加标题栏的item
	private TextView tv;
	private View view;
	private RefreshListView listView = null;
	private List<String[]> listadapter;
	private ChartAdapter<String[]> adapter = null;
	private PopupWindow popuwindow;
	
	//趋势数据分析 ：用来判断是电压检测 还是内阻检测  1内阻检测 非1则为电压检测
	private String type;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter.notifyDataSetChanged();
				listView.setSelection(selection);
			} else {
				adapter.notifyDataSetChanged();
				listView.setSelection(selection);
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.trend_data, null);
		type = getArguments().getString("type");
		setID(view);
		titleList = HistogramFragment.list;
		listadapter = new ArrayList<String[]>();
		setTitle(titleList);
		return view;
	}

	/**
	 * 设置表头的通用数据
	 **/
	private void setTitle(Histogramf list) {
		if (list == null || list.getTitleData().size() == 0)
			return;
		trenData_name.setText(list.getTitleData().get(0).getNodename());
		trenData_Battery_type.setText(list.getTitleData().get(0).getBatterytypeName());
		trenData_manufacturers.setText(list.getTitleData().get(0).getAutomation());
		trenData_Battery_group.setText(list.getTitleData().get(0).getGroupname());
		trenData_Internal_standard.setText(list.getTitleData().get(0).getNominalR() + "mΩ");
		trenData_total_voltage.setText(list.getTitleData().get(0).getU_Str() + "V");
		trenData_Battery_num.setText(list.getTitleData().get(0).getGcount() + "");
		trenData_total_current.setText(list.getTitleData().get(0).getI() + "A");
		// 电池状态和充电状态
		trenData_Battery_status.setText(list.getTitleData().get(0).getStatus());
		trenData_Battery_status.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getGScolor()));
		trenData_charge.setText(list.getTitleData().get(0).getWorkStatus());
		trenData_charge.setTextColor(Color.parseColor("#" + list.getTitleData().get(0).getWScolor()));
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void initView() {
		// (1)标题配置
		if (type.equals("1"))
			titles = new String[] { "Time", "GU(V)", "GI(A)", "U(V)", "R1(mΩ)", "T(℃)", "C(%)", "R2(mΩ)", "C2(F)" };
		
		else{
			trend_sydl.setText(R.string.sydl);//剩余电量
			titles = new String[] { "Time", "GU(V)", "GI(A)", "U(V)", "T(℃)", "SOC(%)" };
		}
		addTitle();
		// listView.setMode(Mode.PULL_FROM_END);
		listView.setScrollContainer(true);
		// tableAdapter.getItem(0).setVisibility(View.INVISIBLE);
	   //点击listview条目
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// if (position != 0) {
				if (position >= numcount)
					return;
				JsonId.setTimePostion(position);
				switch (JsonId.flage) {
				case 0:
					JsonId.setNumPostion(position);
					tm.showFragment(3);
					break;
				case 1:
					tm.showFragment(4);
					break;
				case 2:
					tm.showFragment(5);
					break;
				}
			}
			// }
		});
		/*
		 * listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
		 * 
		 * @Override public void onRefresh(PullToRefreshBase<ListView>
		 * refreshView) { if (!islast) { if (RecordCount == 99) { StringBuffer
		 * str = new StringBuffer();
		 * str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.
		 * packetgroupid).append("/")
		 * .append(String.valueOf(++num)).append("/").append(upData).append("/")
		 * .append(poistion + 1); HttpGetDataTrend(str.toString(), null);
		 * Log.e("tag", "正在加载更多数据-----"); } else { StringBuffer str = new
		 * StringBuffer();
		 * str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.
		 * packetgroupid).append("/")
		 * .append(String.valueOf(++num)).append("/").append(upData).append("/")
		 * .append(poistion + 1); HttpGetDataTrend(str.toString(), null); //最后一页
		 * ，下次不刷新 islast = true; Log.e("tag", "已经是最后一页"); } }
		 * listView.onRefreshComplete(); } });
		 */
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
			}
			// 下拉刷新
			@SuppressWarnings("static-access")
			@Override
			public void onLoadMore() {
				if (type.equals("1")) {
					if (!islast) {
						if (RecordCount>0) {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 1);
							Log.e("tag", "正在加载更多数据---内阻检测 --");
						} else {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 1);
							// 最后一页 ，下次不刷新
							islast = true;
						}
					} else {
						listView.onRefreshComplete(true);
					}
				} else {
					if (!islast) {
						if (RecordCount >0) {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 2);
							Log.e("tag", "正在加载更多数据---电压检测 --");
						} else {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 2);
							// 最后一页 ，下次不刷新
							islast = true;
						}
					}
				}
			}
		});
		
		
		trenData_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// if (spStatus) {
				poistion = position;
				listadapter.clear();
				// JsonId.setNumPostion(position);
				if (ChartsUtils.isNetworkAvailable(getActivity())) {
					StringBuffer str = new StringBuffer();
					if (type.equals("1")) {
						str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/")
								.append("1").append("/").append("72").append("/").append(position + 1);
						firstResult = true;
						HttpGetDataTrend(str.toString(), null, 1);
					} else {
						str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/")
								.append("1").append("/").append("72").append("/").append(position + 1);
						firstResult = true;
						HttpGetDataTrend(str.toString(), null, 2);
					}
				} else {
					//Toast.makeText(getActivity(), R.string.net_no, 0).show();
				}
	            trenData_sp.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch(event.getAction()){
						case MotionEvent.ACTION_MOVE:
							Log.e("tag", "trenData_sp.........");
							break;
						}
						return false;
					}
					
				});

			
				// }
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		setLoad(); // 设置 电池组数据
		setBatteryNum();// 设置电池号数据
		trenData_sp.setSelection(JsonId.getNumPostion(), true);
	}
	 
	public TrendDataFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	/**
	 * 网络Get 请求 图形数据
	 * 
	 * @param url
	 * @param params
	 * @throws InterruptedException
	 */

	public void HttpGetDataTrend(String url, final RequestParams params, final int falge) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						lists = JsonTools.getStringJson(new String(arg2), TrendChartUtils.class);
						RecordCount = lists.getDataInEveryPage().getGroupByPage().getRecordCount();
						// 判断第一次返回的数据长度，避免Fragment跳转越界
						if (firstResult) {
							numcount = lists.getDataInEveryPage().getGroupByPage().getRecordCount();
							firstResult = false;
						}
						// 判断更新类型
						if (falge == 1)
							setLoad();
						else
							setLoadqita();

					} else {
						// Toast.makeText(getActivity(), R.string.resqustFails,
						// 0).show();
					}
				}
				listView.onRefreshComplete(true);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.net_error, 0).show();
				// // 登陆失败
			}
		});
		// listView.onRefreshComplete(true);
	}

	/**
	 * 动态加载电池组数据
	 */
	public void setLoad() {
		if (lists == null) {
			return;
		}
		// listadapter = new ArrayList<String[]>();
		selection = listadapter.size();
		Log.e("2", "list刷新前大小：" + listadapter.size());

		// lists.getDataInEveryPage().getBatteryByPage().getRecordCount());
		for (int i = lists.getDataInEveryPage().getBatteryByPage().getRecordCount() - 1; i >= 0; i--) {
			String[] data = new String[] {
					setTimeSublist(lists.getDataInEveryPage().getGroupByPage().getData().get(i).getTime()),
					lists.getDataInEveryPage().getGroupByPage().getData().get(i).getVoltage() + "",
					lists.getDataInEveryPage().getGroupByPage().getData().get(i).getAmpere() + "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getVoltage() + "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getResistance()
							+ "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getTemperature()
							+ "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getCapacity()
							+ "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getR2() + "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getC2() + "" };
			listadapter.add(data);
		}
		Log.e("tag", listadapter.size() + "  加载数据的总长度");
		listView.setAdapter(adapter = new ChartAdapter(getActivity(), listadapter, titles.length, 0.04f, 0.96f, 2));
		handler.sendEmptyMessage(1);
		
	}

	/**
	 * 其他数据
	 */
	public void setLoadqita() {
		if (lists == null) {
			return;
		}
		// listadapter = new ArrayList<String[]>();
		selection = listadapter.size();
		Log.e("2", "list刷新前大小：" + listadapter.size());

		// lists.getDataInEveryPage().getBatteryByPage().getRecordCount());
		for (int i = lists.getDataInEveryPage().getBatteryByPage().getRecordCount() - 1; i >= 0; i--) {
			String[] data = new String[] {
					setTimeSublist(lists.getDataInEveryPage().getGroupByPage().getData().get(i).getTime()),
					lists.getDataInEveryPage().getGroupByPage().getData().get(i).getVoltage() + "",
					lists.getDataInEveryPage().getGroupByPage().getData().get(i).getAmpere() + "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getVoltage() + "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getTemperature()
							+ "",
					lists.getDataInEveryPage().getBatteryByPage().getData().get(i).getEvents().get(0).getCapacity()
							+ "", };
			listadapter.add(data);
		}
		Log.e("tag", listadapter.size() + "  加载数据的总长度");
		listView.setAdapter(adapter = new ChartAdapter(getActivity(), listadapter, titles.length, 0.04f, 0.96f, 2));
		handler.sendEmptyMessage(1);
	}

	/**
	 * 动态添加title选项
	 */
	private void addTitle() {
		for (int i = 0; i < titles.length; i++) {
			tv = new TextView(getActivity());
			tv.setText(titles[i]);
			tv.setMaxLines(1);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(10);
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.04f);
			params.gravity = Gravity.CENTER;
			view = new View(getActivity());
			view.setBackground(getActivity().getResources().getDrawable(R.drawable.line));
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.96f);
			chlickll = new LinearLayout(getActivity());
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1.0f);

			if (i == titles.length - 1) {
				LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				chlickll.addView(tv, params4);
				ll.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				ll.addView(chlickll, params3);
			}
		}
	}

	/**
	 * 设置电池号数据
	 */
	public void setBatteryNum() {
		if (titleList == null || titleList.getHisData().size() == 0) {
			return;
		}
		spListData = new ArrayList<String>();
		for (int i = 0; i < titleList.getHisData().size(); i++) {
			spListData.add("      " + titleList.getHisData().get(i).getBatterynum() + "#");
		}
		spAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spListData);
		spAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		trenData_sp.setAdapter(spAdapter);
	}

	/*
	 * 绑定ID
	 */
	public void setID(View view) {
		listView = (RefreshListView) view.findViewById(R.id.trend_listview);
		ll = (LinearLayout) view.findViewById(R.id.trenddata_title);
		// btStation = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_station_name);
		// btGroup = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_group_name);
		// btType = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_type_name);
		// btMinR = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_min_R_name);
		// btMaxR = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_max_R_name);
		// btMinU = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_min_U_name);
		// btGroupU = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_group_u_name);
		// btGroupI = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_group_cur_name);
		// btStatus = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_status_name);
		// spTrendData = (Spinner)
		// getActivity().findViewById(R.id.trenddata_battery_sp_time);
		// spGaps = (Spinner)
		// getActivity().findViewById(R.id.trenddata_battery_sp_gaps);
		// btCharge = (TextView)
		// getActivity().findViewById(R.id.trenddata_battery_charge);
		// texCmd = (TextView)
		// getActivity().findViewById(R.id.trenddata_gaps_cmd);
		trenData_name = (TextView) view.findViewById(R.id.trenData_name);
		trenData_Battery_type = (TextView) view.findViewById(R.id.trenData_Battery_type);
		trenData_manufacturers = (TextView) view.findViewById(R.id.trenData_manufacturers);
		trenData_Battery_group = (TextView) view.findViewById(R.id.trenData_Battery_group);
		trenData_Internal_standard = (TextView) view.findViewById(R.id.trenData_Internal_standard);
		trenData_total_voltage = (TextView) view.findViewById(R.id.trenData_total_voltage);
		trenData_Battery_num = (TextView) view.findViewById(R.id.trenData_Battery_num);
		trenData_total_current = (TextView) view.findViewById(R.id.trenData_total_current);
		trenData_Battery_status = (TextView) view.findViewById(R.id.trenData_Battery_status);
		trenData_charge = (TextView) view.findViewById(R.id.trenData_charge);
		trenData_sp = (Spinner) view.findViewById(R.id.trenData_sp);
		trend_sydl = (TextView) view.findViewById(R.id.trend_sydl);
	}

	/**
	 * 截取指定 格式的时间 月日时分
	 * 
	 * @param time
	 * @return
	 */
	public String setTimeSublist(String time) {
		String times = time.substring(5, 16);
		return times;
	}
}
