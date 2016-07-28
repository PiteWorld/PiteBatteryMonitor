package com.pite.batterymonitor;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class LanguesActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(LanguesActivity.this, LoginActivity.class);
		if (isZh()) {
			intent.putExtra("lage", 0);
		} else {
			intent.putExtra("lage", 1);
		}
		startActivity(intent);
	}

	private boolean isZh() {
		Locale locale = getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh")) {
			setLang(locale.SIMPLIFIED_CHINESE);
			return true;
		} else {
			setLang(locale.US);
			return false;
		}
	}

	public void setLang(Locale locale) {
		// 获得res资源对象
		Resources resources = getResources();
		// 获得设置对象
		Configuration config = resources.getConfiguration();
		// 获得屏幕参数：主要是分辨率，像素等。
		DisplayMetrics dm = resources.getDisplayMetrics();
		// 语言
		config.locale = locale;
		resources.updateConfiguration(config, dm);
		// 刷新activity才能马上奏效
		finish();
		// startActivity(new Intent().setClass(LanguesActivity.this,
		// LanguesActivity.class));
		// LoginActivity.this.finish();
	}
}
