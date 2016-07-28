package com.example.pcs.fragment;

import java.math.BigDecimal;
import java.util.List;

import com.example.pcs.PCSMainActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * ������ݵ�ʵʱ��Ϣ
 * @author Administrator
 *
 */
public class BatRealTimeFragment extends Fragment {
	private TextView bata_tv, bata_tv2, bata_tv3, bata_tv4, bata_tv5, bata_tv6, bata_tv7, bata_tv8, bata_tv9,bata_current;//��ʾΪ����ŵ����	
	private FragmentManager manager; // fragment������
	private List<PCSInfos> info;
	/*
	 * �жϵ�ǰ���״̬
	 */
	private int Status;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.batactivity, null);
		info = PCSMainActivity.info;
		initData(view);
		return view;
	}
	
	private void initData(View view){
		bata_tv=(TextView) view.findViewById(R.id.bata_tv);
		bata_tv2=(TextView) view.findViewById(R.id.bata_tv2);
		bata_tv3=(TextView) view.findViewById(R.id.bata_tv3);
		bata_tv4=(TextView) view.findViewById(R.id.bata_tv4);
		bata_current = (TextView)view.findViewById(R.id.bata_current);
		setValue();
	}
	/**
	 * ��ֵ
	 */
	private void setValue() {
		if (info != null) {
			bata_tv.setText(getText(info.get(0).getInverterbatteryV() + ""));
			if (Double.valueOf(info.get(0).getInverterbatteryI() + "".trim()) > 0)
				bata_current.setText(R.string.string23);
			else {
				bata_current.setText(R.string.string78);
				bata_tv2.setText(info.get(0).getInverterbatteryI() + "");
			}
			bata_tv3.setText(info.get(0).getInverterbatteryT() + "��");
			if ((info.get(0).getInverterbatteryCD() + "").equals("0"))
				bata_tv4.setText(R.string.string37);
			else {
				bata_tv4.setText(R.string.string38);
			}
		}
	}

	/**
	 * ����һλС��
	 * @param str
	 * @return
	 */
	private String getText(String str)
	{
		BigDecimal bigDecimal = new BigDecimal(str);
		BigDecimal big = bigDecimal.movePointLeft(1);// ������ƶ��ĸ���
		return big.toString();
	}
	/**
	 * �жϵ��״̬
	 */
	private int batteryStatus(String str) {
		if (str.equals("0"))
			Status = R.string.string57;
		else if (str.equals("1"))
			Status = R.string.string58;
		else if (str.equals("2"))
			Status = R.string.string59;
		else if (str.equals("3"))
			Status = R.string.string60;
		return Status;
	}
	/* * �ж�����״̬
	 */
	private int otherStatus(String str) {
		if (str.equals("0"))
			Status = R.string.string57;
		else if (str.equals("1"))
			Status = R.string.string58;
		return Status;
	}
}
