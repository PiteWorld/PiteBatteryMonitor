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
 * ���� ���ݷ���
 */
public class TrendDataFragment extends Fragment {
	public Spinner trenData_sp;   //����ʱ�� ����ͺ�
	// private TextView btStation, btGroup, btType, btMinR, btMaxR, btMinU,
	// btGroupU, btGroupI, btStatus, btCharge, texCmd; // ���״̬��Ϣ
	private TextView trenData_name, trenData_Battery_type, trenData_manufacturers, trenData_Battery_group,
			trenData_Internal_standard, trenData_total_voltage, trenData_Battery_num, trenData_total_current,
			trenData_Battery_status, trenData_charge,trend_sydl;
	private String[] titles = null;
	private Context context;
	private TrendChartUtils lists = null; // ���״̬��Ϣ
	private Histogramf titleList = null;
	private List<BatteryInformation> spList = null;// spinner ��غ�
	private List<String> spListData = null; // spinner���ݼ���
	private ArrayAdapter<String> spAdapter = null; // spinner adapter
	private TimeMonitorActivity tm = null;
	private ArrayAdapter<String> spGapAdapter = null;
	private List<String> spGapList = null;
	private int poistion = 0;
	private int upData = 72;
	private int selection;
	// �ж��Ƿ������һҳ
	private boolean islast;
	// �ж��Ƿ�Ϊ���һҳ
	private int RecordCount;
	// ��¼ԭʼ���ݳ���
	private int numcount;
	private boolean firstResult;
	// ��ҳ���صı��
	private int num = 1;
	private LinearLayout ll;// ������ӱ�������item
	private LinearLayout chlickll;// ������ӱ�������item
	private TextView tv;
	private View view;
	private RefreshListView listView = null;
	private List<String[]> listadapter;
	private ChartAdapter<String[]> adapter = null;
	private PopupWindow popuwindow;
	
	//�������ݷ��� �������ж��ǵ�ѹ��� ����������  1������ ��1��Ϊ��ѹ���
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
	 * ���ñ�ͷ��ͨ������
	 **/
	private void setTitle(Histogramf list) {
		if (list == null || list.getTitleData().size() == 0)
			return;
		trenData_name.setText(list.getTitleData().get(0).getNodename());
		trenData_Battery_type.setText(list.getTitleData().get(0).getBatterytypeName());
		trenData_manufacturers.setText(list.getTitleData().get(0).getAutomation());
		trenData_Battery_group.setText(list.getTitleData().get(0).getGroupname());
		trenData_Internal_standard.setText(list.getTitleData().get(0).getNominalR() + "m��");
		trenData_total_voltage.setText(list.getTitleData().get(0).getU_Str() + "V");
		trenData_Battery_num.setText(list.getTitleData().get(0).getGcount() + "");
		trenData_total_current.setText(list.getTitleData().get(0).getI() + "A");
		// ���״̬�ͳ��״̬
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
		// (1)��������
		if (type.equals("1"))
			titles = new String[] { "Time", "GU(V)", "GI(A)", "U(V)", "R1(m��)", "T(��)", "C(%)", "R2(m��)", "C2(F)" };
		
		else{
			trend_sydl.setText(R.string.sydl);//ʣ�����
			titles = new String[] { "Time", "GU(V)", "GI(A)", "U(V)", "T(��)", "SOC(%)" };
		}
		addTitle();
		// listView.setMode(Mode.PULL_FROM_END);
		listView.setScrollContainer(true);
		// tableAdapter.getItem(0).setVisibility(View.INVISIBLE);
	   //���listview��Ŀ
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
		 * Log.e("tag", "���ڼ��ظ�������-----"); } else { StringBuffer str = new
		 * StringBuffer();
		 * str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.
		 * packetgroupid).append("/")
		 * .append(String.valueOf(++num)).append("/").append(upData).append("/")
		 * .append(poistion + 1); HttpGetDataTrend(str.toString(), null); //���һҳ
		 * ���´β�ˢ�� islast = true; Log.e("tag", "�Ѿ������һҳ"); } }
		 * listView.onRefreshComplete(); } });
		 */
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
			}
			// ����ˢ��
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
							Log.e("tag", "���ڼ��ظ�������---������ --");
						} else {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 1);
							// ���һҳ ���´β�ˢ��
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
							Log.e("tag", "���ڼ��ظ�������---��ѹ��� --");
						} else {
							StringBuffer str = new StringBuffer();
							str.append(Constant.BATTERY_MONITOR_TREND2).append(PacketActivity.packetgroupid).append("/")
									.append(String.valueOf(++num)).append("/").append(upData).append("/")
									.append(poistion + 1);
							HttpGetDataTrend(str.toString(), null, 2);
							// ���һҳ ���´β�ˢ��
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
		setLoad(); // ���� ���������
		setBatteryNum();// ���õ�غ�����
		trenData_sp.setSelection(JsonId.getNumPostion(), true);
	}
	 
	public TrendDataFragment(TimeMonitorActivity tm) {
		this.tm = tm;
	}

	/**
	 * ����Get ���� ͼ������
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
						// �жϵ�һ�η��ص����ݳ��ȣ�����Fragment��תԽ��
						if (firstResult) {
							numcount = lists.getDataInEveryPage().getGroupByPage().getRecordCount();
							firstResult = false;
						}
						// �жϸ�������
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
				// // ��½ʧ��
			}
		});
		// listView.onRefreshComplete(true);
	}

	/**
	 * ��̬���ص��������
	 */
	public void setLoad() {
		if (lists == null) {
			return;
		}
		// listadapter = new ArrayList<String[]>();
		selection = listadapter.size();
		Log.e("2", "listˢ��ǰ��С��" + listadapter.size());

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
		Log.e("tag", listadapter.size() + "  �������ݵ��ܳ���");
		listView.setAdapter(adapter = new ChartAdapter(getActivity(), listadapter, titles.length, 0.04f, 0.96f, 2));
		handler.sendEmptyMessage(1);
		
	}

	/**
	 * ��������
	 */
	public void setLoadqita() {
		if (lists == null) {
			return;
		}
		// listadapter = new ArrayList<String[]>();
		selection = listadapter.size();
		Log.e("2", "listˢ��ǰ��С��" + listadapter.size());

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
		Log.e("tag", listadapter.size() + "  �������ݵ��ܳ���");
		listView.setAdapter(adapter = new ChartAdapter(getActivity(), listadapter, titles.length, 0.04f, 0.96f, 2));
		handler.sendEmptyMessage(1);
	}

	/**
	 * ��̬���titleѡ��
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
	 * ���õ�غ�����
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
	 * ��ID
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
	 * ��ȡָ�� ��ʽ��ʱ�� ����ʱ��
	 * 
	 * @param time
	 * @return
	 */
	public String setTimeSublist(String time) {
		String times = time.substring(5, 16);
		return times;
	}
}
