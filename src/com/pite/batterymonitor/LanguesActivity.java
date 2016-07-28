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
		// ���res��Դ����
		Resources resources = getResources();
		// ������ö���
		Configuration config = resources.getConfiguration();
		// �����Ļ��������Ҫ�Ƿֱ��ʣ����صȡ�
		DisplayMetrics dm = resources.getDisplayMetrics();
		// ����
		config.locale = locale;
		resources.updateConfiguration(config, dm);
		// ˢ��activity����������Ч
		finish();
		// startActivity(new Intent().setClass(LanguesActivity.this,
		// LanguesActivity.class));
		// LoginActivity.this.finish();
	}
}
