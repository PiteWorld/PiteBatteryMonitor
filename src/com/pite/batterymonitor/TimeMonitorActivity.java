package com.pite.batterymonitor;

import java.util.List;

import com.ab.activity.AbActivity;
import com.pite.batterymonitor.fragment.HisDataFragment;
import com.pite.batterymonitor.fragment.HistogramFragment;
import com.pite.batterymonitor.fragment.Internal_analyseFragment;
import com.pite.batterymonitor.fragment.TrendDataFragment;
import com.pite.batterymonitor.fragment.TrendFragment;
import com.pite.batterymonitor.fragment.Voltage_deviationFragment;
import com.pite.batterymonitor.utils.JsonId;
import com.umeng.message.PushAgent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 图表
 */
public class TimeMonitorActivity extends AbActivity implements OnClickListener {
	private Button btnHis, btnTrend, btnHisData, btnTrendData, voltageAnalysisAD_bt, resistanceAnalysisAD_bt;
	private FragmentManager manager; // fragment 管理器
	private HistogramFragment hisFragment;// 直方图
	private TrendFragment trendFragment; // 趋势图
	private TrendDataFragment trendData; // 趋势 数据
	public  HisDataFragment HisData = null;// 直方 数据分析
	private Internal_analyseFragment Internal_analyse = null;// 电池内阻分析
	private Voltage_deviationFragment Voltage_deviation = null;// 电压均差分析
	public static int columnIndex = 0;
	private int position;
	private boolean status = false;
	private TextView texCmd;
	private String strCmd;
	private Button btnHome, titleSet;
	// 判断测试类型
	public String type = "";
	private ArrayAdapter<String> spGapAdapter = null;
	private List<String> spGapList = null;
	private TimeMonitorActivity tm = null;
	private int TimeGapsPostion = 0; // 选择
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (strCmd != null) {
					texCmd.setVisibility(View.VISIBLE);
					texCmd.setText(strCmd);
					texCmd.setTextColor(Color.RED);
					texCmd.setTextSize(10);
				}
			} else if (msg.what == 2) {
				texCmd.setVisibility(View.GONE);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(mInflater.inflate(R.layout.activity_time_monitor, null));
		PushAgent.getInstance(TimeMonitorActivity.this).onAppStart();
		// mAbTitleBar = this.getTitleBar(); // 标题
		type = getIntent().getStringExtra("type");
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(0, 0, 0, 0);
		btnHis = (Button) this.findViewById(R.id.histogram_btn);
		btnTrend = (Button) this.findViewById(R.id.trend_btn);
		btnHisData = (Button) this.findViewById(R.id.his_data_btn);
		btnTrendData = (Button) this.findViewById(R.id.trend_data_btn);
		resistanceAnalysisAD_bt = (Button) this.findViewById(R.id.resistanceAnalysisAD_bt);
		voltageAnalysisAD_bt = (Button) this.findViewById(R.id.voltageAnalysisAD_bt);
		View view = mInflater.inflate(R.layout.titleset, null);// 标题栏右边
		if (!type.equals("1")){
			resistanceAnalysisAD_bt.setVisibility(View.GONE);
			this.setTitleText(R.string.xunjian);
		}
		else{
			resistanceAnalysisAD_bt.setVisibility(View.VISIBLE);
			this.setTitleText(R.string.title_activity_time_monitor);
		}
		this.addRightView(view);
		btnHome = (Button) this.findViewById(R.id.homeBtn);
		titleSet = (Button) this.findViewById(R.id.setTitle);
		texCmd = (TextView) this.findViewById(R.id.titdata_gaps_cmd);
		// dia = new DialogActivity();
		// Button chongqi = (Button) this.findViewById(R.id.chongqiBtn);
		manager = getFragmentManager();
		btnHis.setOnClickListener(this);
		btnTrend.setOnClickListener(this);
		btnHisData.setOnClickListener(this);
		btnTrendData.setOnClickListener(this);
		resistanceAnalysisAD_bt.setOnClickListener(this);
		voltageAnalysisAD_bt.setOnClickListener(this);
		showFragment(1); // 设置默认fragment
		/**
		 * 返回主页面
		 */
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(TimeMonitorActivity.this, MenuActivity.class));
				TimeMonitorActivity.this.finish();
			}
		});
		titleSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showPopupWindow();
				 Intent intent = new Intent(TimeMonitorActivity.this,DialogActivity.class);
				// DialogActivity.class);
				// JsonId.setHis(hisFragment);
				// JsonId.setHisdata(dataFragment);
				// JsonId.setTrend(trendFragment);
				// JsonId.setTrenddata(trendData);
				 JsonId.setTm(TimeMonitorActivity.this);
				 startActivity(intent);
			}
		});
	}


	public TimeMonitorActivity() {

	}

	public void setCmdHandler1(int index, String title) {
		this.strCmd = title;
		handler.sendEmptyMessage(index);
	}

	public void setcmdHandler(int index, String title) {
		this.strCmd = title;
		handler.sendEmptyMessage(index);
		handler.sendEmptyMessageDelayed(2, 5000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.histogram_btn:
			showFragment(1);
			break;
		case R.id.trend_btn:
			showFragment(2);
			break;
		case R.id.his_data_btn:
			showFragment(3);
			break;
		case R.id.voltageAnalysisAD_bt:
			showFragment(4);
			break;
		case R.id.resistanceAnalysisAD_bt:
			showFragment(5);
			break;
		case R.id.trend_data_btn:
			showFragment(6);
			break;
		}
	}

	/**
	 * 判断显示的界面
	 *   
	 */
	public void showFragment(int index)   {
		FragmentTransaction ft = manager.beginTransaction();
		hideFragment(ft);
		JsonId.setGapsPostion(false);
		switch (index) {
		case 1://直方图
			if (hisFragment != null) {
				ft.show(hisFragment);
			} else {
				hisFragment = new HistogramFragment(this);
				// 把type传给framnet
				Bundle bundle = new Bundle();
				bundle.putString("type", type);
				hisFragment.setArguments(bundle);
				ft.add(R.id.content, hisFragment);
			}
			if (hisFragment.Histogram_sp != null) {
				hisFragment.Histogram_sp.setSelection(JsonId.timePostion);
			}
			setBackGroupBtn(btnHis); // 设置颜色
			break;

		case 2://趋势图
			if (trendFragment != null) {
				ft.show(trendFragment);
			} else {
				trendFragment = new TrendFragment(this);
				// 把type传给framnet
				Bundle bundle = new Bundle();
				bundle.putString("type", type);
				trendFragment.setArguments(bundle);
				ft.add(R.id.content, trendFragment);
			}
			if (trendFragment.trend_sp != null) {
				trendFragment.trend_sp.setSelection(JsonId.numPostion);
			}
			setBackGroupBtn(btnTrend);
			break;
		case 3://直方数据分析
			if (HisData != null) {
				ft.show(HisData);
			} else {
				HisData = new HisDataFragment(TimeMonitorActivity.this);
				Bundle bundle = new Bundle();
				bundle.putString("type", type);
				HisData.setArguments(bundle);
				ft.add(R.id.content, HisData);
			}
			if (HisData.Data_Battery_time1 != null) {
				HisData.Data_Battery_time1.setSelection(JsonId.timePostion);
			}
			setBackGroupBtn(btnHisData);
			break;

		case 4://电压均差分析
			if (Voltage_deviation != null) {
				ft.show(Voltage_deviation);
			} else {
				Voltage_deviation = new Voltage_deviationFragment(TimeMonitorActivity.this);
				Bundle bundle = new Bundle();
				bundle.putString("type", type);
				Voltage_deviation.setArguments(bundle);
				ft.add(R.id.content, Voltage_deviation);
			}
			if (Voltage_deviation.voltage_sp != null) {
				Voltage_deviation.voltage_sp.setSelection(JsonId.timePostion);
			}
			setBackGroupBtn(voltageAnalysisAD_bt);
			break;
		case 5://电池内阻分析
			if (Internal_analyse != null) {
				ft.show(Internal_analyse);
			} else {
				Internal_analyse = new Internal_analyseFragment(TimeMonitorActivity.this);
				ft.add(R.id.content, Internal_analyse);
			}
			if (Internal_analyse.Internal_sp != null) {
				Internal_analyse.Internal_sp.setSelection(JsonId.timePostion);
			}
			setBackGroupBtn(resistanceAnalysisAD_bt);
			break;
		case 6://趋势数据分析
			if (trendData != null) {
				ft.show(trendData);
			} else {
				trendData = new TrendDataFragment(this);
				Bundle bundle = new Bundle();
				bundle.putString("type", type);
				trendData.setArguments(bundle);
				ft.add(R.id.content, trendData);
			}
			if (trendData.trenData_sp != null) {
				trendData.trenData_sp.setSelection(JsonId.numPostion);
			}
			setBackGroupBtn(btnTrendData);
			break;
		}
		ft.commit();
	}

	/**
	 * fragment 切换按钮
	 */
	public void setBackGroupBtn(Button btn) {
		btnHis.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btnHisData.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btnTrend.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btnTrendData.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btnTrend.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btnTrendData.setBackground(getResources().getDrawable(R.drawable.title_cell));
		voltageAnalysisAD_bt.setBackground(getResources().getDrawable(R.drawable.title_cell));
		resistanceAnalysisAD_bt.setBackground(getResources().getDrawable(R.drawable.title_cell));
		btn.setBackground(getResources().getDrawable(R.drawable.content_cell));
	}

	/**
	 * 隐藏 fragment
	 */
	public void hideFragment(FragmentTransaction ft) {
		if (hisFragment != null) {
			ft.hide(hisFragment);
		}
		if (trendFragment != null) {
			ft.hide(trendFragment);
		}
		if (HisData != null) {
			ft.hide(HisData);
		}
		if (trendData != null) {
			ft.hide(trendData);
		}
		if (Voltage_deviation != null) {
			ft.hide(Voltage_deviation);
		}
		if (Internal_analyse != null) {
			ft.hide(Internal_analyse);
		}
	}
	/**
	 * fragment传递参数
	 */
	public void setAragmets(int postion) {
		this.position = postion;
	}

	/**
	 * 获取参数
	 */
	public int getAragmets() {
		return position;
	}

	@Override
	protected void onDestroy() {
		JsonId.timePostion = 0;
		JsonId.numPostion = 0;
		JsonId.timeGapsPostion = 0;
		super.onDestroy();
	}
}
