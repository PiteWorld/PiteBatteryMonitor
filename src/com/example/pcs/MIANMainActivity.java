package com.example.pcs;
import com.pite.batterymonitor.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * �е�����
 * 
 * @author Administrator
 *
 */
public class MIANMainActivity extends BaseActivity {
	private TextView mian_tv, mian_tv2,mian_tv3, mian_tv4, mian_tv5, mian_tv6, mian_tv7, mian_tv8, mian_tv9, mian_tv10, mian_tv11,
			mian_tv12, mian_tv13, mian_tv14, mian_tv15, mian_tv16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.mian_title);
		initData();
	}

	private void initData() {
		mian_tv = (TextView) findViewById(R.id.mian_tv);
		mian_tv2 = (TextView) findViewById(R.id.mian_tv2);

		mian_tv6 = (TextView) findViewById(R.id.mian_tv6);
		mian_tv7 = (TextView) findViewById(R.id.mian_tv7);
		
		mian_tv12= (TextView) findViewById(R.id.mian_tv12);
		mian_tv13= (TextView) findViewById(R.id.mian_tv13);
	
		if (getInfo() != null) {
			//�е�����U���ѹ
			mian_tv.setText(getInfo().get(0).getMainsspplyinputUV());
			//�е�����V���ѹ
			mian_tv6.setText(getInfo().get(0).getMainsspplyinputVV());
			//�е�����Ƶ��
			mian_tv7.setText(getInfo().get(0).getMainsspplyinputFrequency());
			//�е�����W���ѹ
			mian_tv12.setText(getInfo().get(0).getMainsspplyinputWV());
			
		}
	}

	@Override
	public View getcontent() {
		return View.inflate(MIANMainActivity.this, R.layout.activity_mianmain, null);
	}

}
