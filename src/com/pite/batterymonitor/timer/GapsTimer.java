package com.pite.batterymonitor.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.utils.JsonId;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.TimeMonitorActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class GapsTimer {
	private Context context;
	public Timer timer = new Timer();
	private int postion;
	private String SEND_UPDATA = "com.set.sucess"; // 时间间隔广播
	private String SEND_UPDATA_RESTART = "com.set.restart"; // 重启广播
	private String SEND_UPDATA_SYN = "com.set.syn"; // 同步参数广播
	private int time = 0;
	private int index = 0;
	public static boolean setLongTime = false; // 设置超时
	private boolean isRestart = false; // 重启
	private boolean isTimeGaps = false;// 时间间隔
	private boolean isSyn = false; // 同步参数
	public static boolean isTimerRun = false;
	private String tmpID = null;
	private TimeMonitorActivity tm = null;
	// private HistogramFragment hisfragment;

	public GapsTimer(Context context) {
		this.context = context;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				String str = null;
				index += time;
				Log.e("2", "计时时间：" + index);
				if (index > 240000) {
					setLongTime = true;
					time = 0;
					timer.cancel();
				}
				String langues = null;
				if (LoginActivity.isChinese == 0) {
					langues = "chinese";
				} else {
					langues = "english";
				}
				str = Constant.BATTERY_RESTART_SUEC + tmpID + "/" + langues;
				HttpGetTimerGaps(str, null);
			}
		};
	};
	public TimerTask task = new TimerTask() {
		@Override
		public void run() {
			handler.sendEmptyMessage(1);
		}
	};

	/**
	 * 
	 * @param flags
	 *            时间间隔，重启设置 ，同步参数功能选择
	 * @param times
	 *            定时时间
	 * @param tmp
	 *            返回的值
	 */
	public void StartTimer(int pos, int times, String tmp) {
		time = times;
		tmpID = tmp;
		postion = pos;
		tm = JsonId.getTm();
		timer.schedule(task, 0, time);
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetTimerGaps(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("2", "返回参数：" + new String(arg2));
				try {
					String str = new String(arg2);
					JSONObject object = new JSONObject(str);
					String status = object.getString("status");
					String text = object.getString("text");
					Intent intent = null;
					if ("-1".equals(status)) {
						tm.setcmdHandler(1, text);
						timer.cancel();
					}
					if ("1".equals(status)) {
						JsonId.setTimeGapsPostion(postion);
						tm.setcmdHandler(1, text);
						timer.cancel();
					}
					if ("2".equals(status)) {
						tm.setCmdHandler1(1, text);
					}
					if ("3".equals(status)) {
						tm.setCmdHandler1(1, text);
					}
					if (setLongTime) {
						setLongTime = false;
						tm.setcmdHandler(1, String.valueOf(R.string.SEND_LONG_TIME));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}
		});
	}

}
