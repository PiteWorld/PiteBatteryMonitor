package com.pite.batterymonitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.timer.GapsTimer;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.utils.SetTimeGaps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DialogActivity extends Activity {
	private TextView texResrt, texSyn; // 重启 同步
	private Spinner spGaps; // 时间间隔
	// private HistogramFragment hisFragment = null;
	// private HisDataFragment hisdata = null;
	// private TrendFragment trend = null;
	// private TrendDataFragment trenddata = null;
	public GapsTimer timers = null;
	private boolean ISspGaps = false;
	private SetTimeGaps list = null;
	private String str = null; // 中英文
	private ArrayAdapter<String> spGapAdapter = null;
	private List<String> spGapList = null;
	private TimeMonitorActivity tm = null;
	private int TimeGapsPostion = 0; // 选择
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				spGapList = new ArrayList<String>();
				for (int i = 0; i < list.getInterVal().getRecord().size(); i++) {
					spGapList.add("   " + list.getInterVal().getRecord().get(i).getName());
				}
				spGapAdapter = new ArrayAdapter<String>(DialogActivity.this, android.R.layout.simple_spinner_item,
						spGapList);
				spGapAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				spGaps.setAdapter(spGapAdapter);
				spGaps.setSelection(list.getInterVal().getDefault(), true);
			} else if (msg.what == 2) {
				DialogActivity.this.finish();
			}
			else if(msg.what == 3){
				
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);
		texResrt = (TextView) this.findViewById(R.id.dig_resart);
		texSyn = (TextView) this.findViewById(R.id.dig_syn);
		spGaps = (Spinner) this.findViewById(R.id.dig_gaps);
		// hisFragment = JsonId.getHis();
		// hisdata = JsonId.getHisdata() ;
		// trend = JsonId.getTrend() ;
		// trenddata = JsonId.getTrenddata();
		tm = JsonId.getTm();
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		if (ChartsUtils.isNetworkAvailable(DialogActivity.this)) {
			HttpGetDataSp(Constant.BATTERY_TIME_SP + PacketActivity.packetgroupid + "/" + str, null); // 时间选择对话框

		} else {
			//Toast.makeText(DialogActivity.this, R.string.net_no, 0).show();
		}
		texResrt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ChartsUtils.isNetworkAvailable(DialogActivity.this)) {
					new AlertDialog.Builder(DialogActivity.this).setTitle(R.string.Dialog)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							HttpGetDataGaps(Constant.BATTERY_TIME_SP_RESTART + LoginActivity.nodid + "/"
									+ PacketActivity.packetgroupid + "/" + "-1" + "/" + str, null);
							finish();
						}
					})
					.setNegativeButton(R.string.no, null).create().show();
				} else {
					//Toast.makeText(DialogActivity.this, R.string.net_no, 0).show();
				}
			}
		});
		texSyn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("haha", "点击了");
				new AlertDialog.Builder(DialogActivity.this).setTitle(R.string.Dialog)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HttpGetDataGaps(Constant.BATTERY_SYN_TIME + LoginActivity.nodid + "/" + PacketActivity.packetgroupid
								+ "/" + str, null);
						finish();
					}
				})
				.setNegativeButton(R.string.no, null).create().show();
			}
		});
		spGaps.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
				if (ISspGaps) {
					Log.e("haha", "点击了");
					new AlertDialog.Builder(DialogActivity.this).setTitle(R.string.Dialog)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (ChartsUtils.isNetworkAvailable(DialogActivity.this)) {
								TimeGapsPostion = position;
								HttpGetDataGaps(Constant.BATTERY_TIME_SP_RESTART + LoginActivity.nodid + "/"
										+ PacketActivity.packetgroupid + "/"
										+ list.getInterVal().getRecord().get(position).getValue() + "/" + str, null);
								finish();
							} else {
								//Toast.makeText(DialogActivity.this, R.string.net_no, 0).show();
							}
						}
					})
					.setNegativeButton(R.string.no, null).create().show();
					// }
				}
				// JsonId.setGapsPostion(true);

				ISspGaps = true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		// Log.e("2", "时间间隔选择："+JsonId.getTimeGapsPostion());
		// spGaps.setSelection(JsonId.getTimeGapsPostion(), true);
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataSp(String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getStringJson(new String(arg2), SetTimeGaps.class);
						handler.sendEmptyMessage(1);
					} else {
						Toast.makeText(DialogActivity.this, R.string.resqustFails, 0).show();
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
	 * 网络 时间间隔请求
	 * 
	 * @param url{"tempID":"4384","status":"1","text":""命令发送成功!"}
	 * @param params
	 */
	public void HttpGetDataGaps(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// Log.e("2", "时间间隔：" + new String(arg2));
				try {
					if (arg2 != null && arg2.length > 1) {
						String str = new String(arg2);
						JSONObject object = new JSONObject(str);
						String status = object.getString("status");
						String text = object.getString("text");
						String tempID = object.getString("tempID");
						if ("0".equals(status)) { // 设置不成功
							// hisFragment.setRestartHanlder(7, text);
							tm.setcmdHandler(1, text);
						} else {
							if (timers != null) {
								if (timers.isTimerRun) {
									if (timers.timer != null) {
										if (timers.task != null) {
											timers.task.cancel();
										}
									}
								}
							}
							Log.e("2", "tempId:" + tempID);
							timers = new GapsTimer(DialogActivity.this);
							timers.StartTimer(TimeGapsPostion, 10000, tempID);
							tm.setCmdHandler1(1, text);
							// hisFragment.setRestartHanlder(8, text); //
							// 重启命令发送Tex
							// hisdata.setcmdHandler(2, text);
							// trend.setcmdHandler(2, text);
							// trenddata.setcmdHandler(1, text);
							timers.isTimerRun = true;
						}
					}else{
						Toast.makeText(DialogActivity.this, R.string.resqustFails, 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.SET_FAIL, 0).show();
				// 登陆失败
			}
		});
	}

	/**
	 * 网络 重启 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataResart(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				try {
					if (arg2 != null && arg2.length > 1) {
						String str = new String(arg2);
						JSONObject object = new JSONObject(str);
						String status = object.getString("status");
						String text = object.getString("text");
						String tempID = object.getString("tempID");
						if ("0".equals(status)) { // 设置不成功
							// hisFragment.setRestartHanlder(7, text); // 设置不成功
							tm.setcmdHandler(1, text);
						} else {
							if (timers != null) {
								if (timers.isTimerRun) {
									if (timers.timer != null) {
										if (timers.task != null) {
											timers.task.cancel();
										}
									}
								}
							}
							timers = new GapsTimer(DialogActivity.this);
							timers.StartTimer(TimeGapsPostion, 10000, tempID);
							tm.setcmdHandler(1, text);
							timers.isTimerRun = true;
							// hisFragment.setRestartHanlder(8, text); //
							// 重启命令发送Tex
						}
					}else{
						Toast.makeText(DialogActivity.this, R.string.resqustFails, 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Toast.makeText(getActivity(), R.string.SET_FAIL, 0).show();
				// 登陆失败
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setDestroyHandler();
			break;
		case MotionEvent.ACTION_UP:
			setDestroyHandler();
			break;
		}
		return super.onTouchEvent(event);
	}

	public void setDestroyHandler() {
		handler.sendEmptyMessage(2);
	}
		
	@Override
	protected void onDestroy() {
		// if (timers != null) {
		// if (timers.timer != null) {
		// if (timers.task != null) {
		// timers.task.cancel();
		// }
		// }
		// }
		super.onDestroy();
	}
}
