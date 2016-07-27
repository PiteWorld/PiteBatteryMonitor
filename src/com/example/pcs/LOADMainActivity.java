package com.example.pcs;

import com.example.pcs.fragment.LoadFragment01;
import com.example.pcs.fragment.LoadFragment02;
import com.example.pcs.fragment.LoadFragment03;
import com.pite.batterymonitor.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 系统输出
 * 
 * @author Administrator
 *
 */
public class LOADMainActivity extends BaseActivity implements OnClickListener {
	private Button load_bt01, load_bt02, load_bt03;
	private FragmentManager fm = getFragmentManager();
	private LoadFragment01 loadfm01 = null;
	private LoadFragment02 loadfm02 = null;
	private LoadFragment03 loadfm03 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.load_title);
		initData();
	}

	private void initData() {
		load_bt01 = (Button) findViewById(R.id.load_bt01);
		load_bt02 = (Button) findViewById(R.id.load_bt02);
		load_bt03 = (Button) findViewById(R.id.load_bt03);
		load_bt01.setOnClickListener(this);
		load_bt02.setOnClickListener(this);
		load_bt03.setOnClickListener(this);
		showFragment(1);
		load_bt01.setBackgroundResource(R.drawable.content_cell);
	}

	@Override
	public View getcontent() {
		return View.inflate(LOADMainActivity.this, R.layout.activity_loadmain, null);
	}

	// 避免Fragment重叠
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
	}

	private void showFragment(int index) {
		FragmentTransaction ft = fm.beginTransaction();
		hideFrament(ft);
		switch (index) {
		case 1:
			if (loadfm01 == null) {
				loadfm01 = new LoadFragment01();
				ft.add(R.id.loadfm, loadfm01);
				Log.e("tag", "loadfm01"+".");
			} else
				ft.show(loadfm01);
			break;
		case 2:
			if (loadfm02 == null) {
				loadfm02 = new LoadFragment02();
				ft.add(R.id.loadfm, loadfm02);
				Log.e("tag", "loadfm02"+"..");
			} else
				ft.show(loadfm02);
			break;
		case 3:
			if (loadfm03 == null) {
				loadfm03 = new LoadFragment03();
				ft.add(R.id.loadfm, loadfm03);
				Log.e("tag", "loadfm03"+"...");
			} else
				ft.show(loadfm03);
			break;
		}
		ft.commit();
	}

	private void hideFrament(FragmentTransaction ft) {
		if (loadfm01 != null)
			ft.hide(loadfm01);
		if (loadfm02 != null)
			ft.hide(loadfm02);
		if (loadfm03 != null)
			ft.hide(loadfm03);
		 
	}

	/***
	 * 改变Button颜色 表示当前选中
	 */
	private void setButtonBack(int index) {
		load_bt01.setBackgroundResource(R.drawable.title_cell);
		load_bt02.setBackgroundResource(R.drawable.title_cell);
		load_bt03.setBackgroundResource(R.drawable.title_cell);
		switch (index) {
		case 1:
			load_bt01.setBackgroundResource(R.drawable.content_cell);
			break;
		case 2:
			load_bt02.setBackgroundResource(R.drawable.content_cell);
			break;
		case 3:
			load_bt03.setBackgroundResource(R.drawable.content_cell);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.load_bt01:
			setButtonBack(1);
			showFragment(1);
			break;
		case R.id.load_bt02:
			setButtonBack(2);
			showFragment(2);
			break;
		case R.id.load_bt03:
			setButtonBack(3);
			showFragment(3);
			break;
		}
	}
}
