package com.example.pcs;


import com.pite.batterymonitor.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
	/**
	 * 旁路输入
	 * @author Administrator
	 *
	 */
public class BYPMainActivity extends BaseActivity {
	private TextView byp_tv,byp_tv3,byp_tv4,byp_tv5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.byp_title);
		initData();
	}
	private void initData() {
		// TODO Auto-generated method stub
		byp_tv = (TextView) findViewById(R.id.byp_tv);
		byp_tv3 = (TextView) findViewById(R.id.byp_tv3);
		byp_tv4 = (TextView) findViewById(R.id.byp_tv4);
		byp_tv5 = (TextView) findViewById(R.id.byp_tv5);
		//加载数据
		if(getInfo()!=null)
		byp_tv.setText(getInfo().get(0).getBypassspplyinputUV());
		byp_tv3.setText(getInfo().get(0).getBypassspplyinputVV());
		byp_tv4.setText(getInfo().get(0).getBypassspplyinputFrequency());
		byp_tv5.setText(getInfo().get(0).getBypassspplyinputWV());
	}
	@Override
	public View getcontent() {
		// TODO Auto-generated method stub
		return View.inflate(BYPMainActivity.this, R.layout.activity_bypmain, null);
	}
}
