package com.pite.batterymonitor.service;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.http.HttpReustClient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PiteService extends Service {
	private MyBinder binder;
	private boolean isGetStatus = false; // 数据获取状态

	@Override
	public void onCreate() {
		super.onCreate();
		binder = new MyBinder();
	}

	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 获取 Service
	 * 
	 * @return
	 */
	public class MyBinder extends Binder {
		// public PiteService getService() {
		// return PiteService.this;
		// }
		public boolean HttpGetData(final String url, final RequestParams params) {
			HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					if (arg0 == 200) {
						Log.e("2", "byte ; " + arg2);
						Log.e("2", "解析数据" + new String(arg2));
						isGetStatus = true;
					} else {
						isGetStatus = false;
					}
				}
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Log.e("2", "arg0 ; " + arg0);
					Log.e("2", "Header ; " + arg1);
					Log.e("2", "byte ; " + arg2);
					Log.e("2", "Throwable ; " + arg3);
				}
			});
			return isGetStatus;
		}
	}

	@Override
	public void onDestroy() {
		isGetStatus = false;
		super.onDestroy();
	}
}
