package com.example.pcs;

import com.example.pcs.fragment.Energfm01;
import com.example.pcs.fragment.Energfm02;
import com.example.pcs.fragment.Energfm03;
import com.pite.batterymonitor.R;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 储能实时信息
 * 
 * @author Administrator
 *
 */
public class StoredEnergyactivity extends BaseActivity implements OnClickListener {
	private Button eneng_01, eneng_02, eneng_03;
	private FragmentManager fm = getFragmentManager();
	private Energfm01 energfm01 = null;
	private Energfm02 energfm02 = null;
	private Energfm03 energfm03 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.StoredEnergy);
		initData();
	}

	// 绑定id,加载数据
	private void initData() {
		eneng_01 = (Button) findViewById(R.id.eneng_01);
		eneng_02 = (Button) findViewById(R.id.eneng_02);
		eneng_03 = (Button) findViewById(R.id.eneng_03);
		eneng_01.setOnClickListener(this);
		eneng_02.setOnClickListener(this);
		eneng_03.setOnClickListener(this);
		showFragment(1);
		eneng_01.setBackgroundResource(R.drawable.content_cell);
	}

	private void showFragment(int index) {
		FragmentTransaction ft = fm.beginTransaction();
		hideFrament(ft);
		switch (index) {
		case 1:
			if (energfm01 == null) {
				energfm01 = new Energfm01();
				ft.add(R.id.energfm, energfm01);
			} else
				ft.show(energfm01);
			break;
		case 2:
			if (energfm02 == null){
				energfm02 = new Energfm02();
				ft.add(R.id.energfm, energfm02);
				}
			else
				ft.show(energfm02);
			break;
		case 3:
			if (energfm03 == null){
				energfm03 = new Energfm03();
				ft.add(R.id.energfm, energfm03);
			}
			else
				ft.show(energfm03);
			break;
		}
		ft.commit();
	}

	private void hideFrament(FragmentTransaction ft) {
		if (energfm01 != null)
			ft.hide(energfm01);
		if (energfm02 != null)
			ft.hide(energfm02);
		if (energfm03 != null)
			ft.hide(energfm03);
	}

	/***
	 * 改变Button颜色 表示当前选中
	 */
	private void setButtonBack(int index) {
		eneng_01.setBackgroundResource(R.drawable.title_cell);
		eneng_02.setBackgroundResource(R.drawable.title_cell);
		eneng_03.setBackgroundResource(R.drawable.title_cell);
		switch (index) {
		case 1:
			eneng_01.setBackgroundResource(R.drawable.content_cell);
			break;

		case 2:
			eneng_02.setBackgroundResource(R.drawable.content_cell);
			break;
		case 3:
			eneng_03.setBackgroundResource(R.drawable.content_cell);
			break;
		}
	}
	@Override
	public View getcontent() {
		return View.inflate(StoredEnergyactivity.this, R.layout.stored_energyactivity_main, null);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.eneng_01:
			setButtonBack(1);
			showFragment(1);
			break;
		case R.id.eneng_02:
			setButtonBack(2);
			showFragment(2);
			break;
		case R.id.eneng_03:
			setButtonBack(3);
			showFragment(3);
			break;
		}
	}
}
