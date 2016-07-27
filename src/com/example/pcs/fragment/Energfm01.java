package com.example.pcs.fragment;

import java.math.BigDecimal;
import java.util.List;

import com.example.pcs.PCSMainActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Energfm01 extends Fragment{
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11,tv30;//���õ����ɫ
	public List<PCSInfos> info;
		public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
			View view =  View.inflate(getActivity(), R.layout.energfm1, null);
			info = PCSMainActivity.info;
			initData(view);
		return view;
		};

	// ��id,��������
	private void initData(View view) {
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView)view. findViewById(R.id.tv3);
		tv4 = (TextView)view. findViewById(R.id.tv4);
		tv5 = (TextView) view.findViewById(R.id.tv5);
		tv6 = (TextView) view.findViewById(R.id.tv6);
		tv7 = (TextView) view.findViewById(R.id.tv7);
		tv8 = (TextView) view.findViewById(R.id.tv8);
		tv9 = (TextView)view. findViewById(R.id.tv9);
		tv10 = (TextView)view. findViewById(R.id.tv10);
		tv11 = (TextView)view. findViewById(R.id.tv11);
		tv30 = (TextView) view.findViewById(R.id.tv30);
		setData();
	}
	
	// �������
	private void setData() {
		if (info != null) {
			// �ж�����״̬
			String str = ""+info.get(0).getBatteryoperatingstate();
			if (str.equals("0"))
				tv1.setText(R.string.string36);
			else if (str.equals("1"))
				tv1.setText(R.string.string37);
			else
				tv1.setText(R.string.string38);
			tv2.setText(info.get(0).getInputpowersum());
			//�жϵ�ǰ�������+��ǰ���currentpricetype
			tv3.setText("|"+info.get(0).getCurrentprice());
			tv30.setText(getCurrentPrices(info.get(0).getCurrentpricetype()+""));
			tv4.setText(info.get(0).getInputpowerfee());
			tv5.setText(info.get(0).getInputpower());
			tv6.setText(info.get(0).getOutputpowersum());
			tv7.setText(info.get(0).getOutputpower());
			tv8.setText(info.get(0).getOutputpowerfee());
			tv9.setText(getText(info.get(0).getInverterbatteryV()+""));
			tv10.setText(info.get(0).getProfitsum());
			tv11.setText(info.get(0).getInverterbatteryI()+"");
		}
	}
	/**
	 * ����һλС��
	 */

	private String getText(String str)
	{
		BigDecimal bigDecimal = new BigDecimal(str);
		BigDecimal big = bigDecimal.movePointLeft(1);// ������ƶ��ĸ���
		return big.toString();
	}
	/**
	 * �жϵ�ǰ���״̬ ������ɫ
	 * 
	 */
	public int getCurrentPrices(String str){
		if(str.equals("0")){
			//tv30.setTextColor(Color.RED);
		return R.string.string66;
		}
		else if(str.equals("1")){
			//tv30.setTextColor(Color.YELLOW);
			return R.string.string67;
		}
		else if(str.equals("2")){
			//tv30.setTextColor(Color.GREEN);
			return R.string.string68;
			}
		return R.string.string66;
	}
}
