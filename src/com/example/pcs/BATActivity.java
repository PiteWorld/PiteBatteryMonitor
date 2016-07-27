package com.example.pcs;

import com.example.pcs.fragment.BatRealTimeFragment;
import com.example.pcs.fragment.BatTrendDataFragment;
import com.example.pcs.fragment.BatTrendFragment;
import com.example.pcs.fragment.Energfm01;
import com.example.pcs.fragment.Energfm02;
import com.example.pcs.fragment.Energfm03;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.JsonId;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * �������
 * @author Administrator
 */
public class BATActivity extends BaseActivity implements OnClickListener {
	private FragmentManager manager = getFragmentManager();
	//private int[] titles=new int[]{R.string.Real_time_Info,R.string.Thrend_btn,R.string.Trend_data};
	private Button Real_btn,Thrend_btn,trend_data;
	private BatRealTimeFragment batRealTimeFragment=null; //ʵʱ��Ϣ
	private BatTrendFragment batTrendFragment=null; //����ͼ
	private BatTrendDataFragment batTrendDataFragment=null;//��������
	
//	private TextView bata_tv, bata_tv2, bata_tv3, bata_tv4, bata_tv5, bata_tv6, bata_tv7, bata_tv8, bata_tv9,
//			bata_current;//��ʾΪ����ŵ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitie(R.string.string21);
		initData();
	}

	private void initData() {
		Real_btn=(Button) findViewById(R.id.Real_btn);
		Thrend_btn=(Button) findViewById(R.id.Thrend_btn);
		trend_data=(Button) findViewById(R.id.trend_data_btn);
		
		Real_btn.setOnClickListener(this);
		Thrend_btn.setOnClickListener(this);
		trend_data.setOnClickListener(this);
		showFragment(1);
		Real_btn.setBackgroundResource(R.drawable.content_cell);
	  /*  bata_tv = (TextView) findViewById(R.id.bata_tv);
		bata_tv2 = (TextView) findViewById(R.id.bata_tv2);
		bata_tv3 = (TextView) findViewById(R.id.bata_tv3);
		bata_tv4 = (TextView) findViewById(R.id.bata_tv4);
		bata_current = (TextView) findViewById(R.id.bata_current);
		if (getInfo() != null) {
			 bata_tv.setText(getText(getInfo().get(0).getInverterbatteryV()+""));
			 Log.e("1","*************"+getInfo());
			// �жϵ�ǰΪ��ŵ����
			if (Double.valueOf(getInfo().get(0).getInverterbatteryI()+"".trim()) > 0)
				bata_current.setText(R.string.string23);
			else
				bata_current.setText(R.string.string78);
				bata_tv2.setText(getInfo().get(0).getInverterbatteryI()+"");
				//�¶�
			bata_tv3.setText(getInfo().get(0).getInverterbatteryT() + "��");
			// �жϵ�ǰ״̬
			if ((getInfo().get(0).getInverterbatteryCD()+"").equals("0"))
			bata_tv4.setText(R.string.string37);
			else
			bata_tv4.setText(R.string.string38);
			
		}*/
	}
	/**
	 * ����һλС��
	 */

//	private String getText(String str)
//	{
//		BigDecimal bigDecimal = new BigDecimal(str);
//		BigDecimal big = bigDecimal.movePointLeft(1);// ������ƶ��ĸ���
//		return big.toString();
//	}
	
	@Override
	public View getcontent() {
		return View.inflate(BATActivity.this, R.layout.bat_batactivity_main, null);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.Real_btn:
			setButtonBack(1);
			showFragment(1);
			break;
		case R.id.Thrend_btn:
			setButtonBack(2);
			showFragment(2);
			break;
		case R.id.trend_data_btn:
			setButtonBack(3);
			showFragment(3);
			break;
		}
	}
	
	private void showFragment(int index) {
		FragmentTransaction ft = manager.beginTransaction();
		hideFragment(ft);
		switch (index) {
		case 1:
			if (batRealTimeFragment == null) {
				batRealTimeFragment = new BatRealTimeFragment();
				ft.add(R.id.batInfo, batRealTimeFragment);
			} else
				ft.show(batRealTimeFragment);
			break;
		case 2:
			if (batTrendFragment == null){
				batTrendFragment = new BatTrendFragment();
				ft.add(R.id.batInfo, batTrendFragment);
				}
			else
				ft.show(batTrendFragment);
			break;
		case 3:
			if (batTrendDataFragment == null){
				batTrendDataFragment = new BatTrendDataFragment();
				ft.add(R.id.batInfo, batTrendDataFragment);
			}
			else
				ft.show(batTrendDataFragment);
			break;
		}
		ft.commit();
	}

	/**
	 * �ж���ʾ�Ľ���
	 *   
	 */
	/*public void showFragment(int index) {
		FragmentTransaction ft = manager.beginTransaction();
		hideFragment(ft);
		switch (index) {
		case 1:// ʵʱ��Ϣ
			if (batRealTimeFragment!= null) {
				ft.show(batRealTimeFragment);
			} else {
				batRealTimeFragment = new BatRealTimeFragment();
				// ��type����framnet
				Bundle bundle = new Bundle();
				bundle.putStringArray("type", new String[]{});
				batRealTimeFragment.setArguments(bundle);
				ft.add(R.id.ll_batcontext, batRealTimeFragment);
			}
			setBackGroupBtn(Real_btn); // ������ɫ
			break;

		case 2:// ����ͼ
			if (batTrendFragment != null) {
				ft.show(batTrendFragment);
			} else {
				batTrendFragment = new BatTrendFragment();
				// ��type����framnet
				Bundle bundle = new Bundle();
				bundle.putStringArray("type", new String[]{});
				 batTrendFragment.setArguments(bundle);
				ft.add(R.id.content, batTrendFragment);
			}
			setBackGroupBtn(Thrend_btn);
			break;
		case 3:// ��������
			if (batTrendDataFragment != null) {
				ft.show(batTrendDataFragment);
			} else {
				batTrendDataFragment = new BatTrendDataFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArray("value", new String[]{});
				batTrendDataFragment.setArguments(bundle);
				ft.add(R.id.content, batTrendDataFragment);
			}

			setBackGroupBtn(trend_data);
			break;
		}

		ft.commit();
	}*/
	
	/**
	 * ���� fragment
	 */
	public void hideFragment(FragmentTransaction ft) {
		if (batRealTimeFragment != null) {
			ft.hide(batRealTimeFragment);
		}
		if (batTrendFragment!= null) {
			ft.hide(batTrendFragment);
		}
		if (batTrendDataFragment != null) {
			ft.hide(batTrendDataFragment);
		}
	}
	
	/***
	 * �ı�Button��ɫ ��ʾ��ǰѡ��
	 */
	private void setButtonBack(int index) {
	
		Real_btn.setBackgroundResource(R.drawable.title_cell);
		Thrend_btn.setBackgroundResource(R.drawable.title_cell);
		trend_data.setBackgroundResource(R.drawable.title_cell);
		switch (index) {
		case 1:
			Real_btn.setBackgroundResource(R.drawable.content_cell);
			break;

		case 2:
			Thrend_btn.setBackgroundResource(R.drawable.content_cell);
			break;
		case 3:
			trend_data.setBackgroundResource(R.drawable.content_cell);
			break;
		}
	}
	/**
	 * fragment �л���ť
	 */
//	public void setBackGroupBtn(Button btn) {
//		Real_btn.setBackground(getResources().getDrawable(R.drawable.title_cell));
//		Thrend_btn.setBackground(getResources().getDrawable(R.drawable.title_cell));
//		trend_data.setBackground(getResources().getDrawable(R.drawable.title_cell));
//		
//	}

}
