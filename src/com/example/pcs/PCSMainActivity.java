package com.example.pcs;


import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.LoginActivity;
import com.pite.batterymonitor.PacketActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.TestTypeActivity;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.PCSInfos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
	/**
	 * pcs������
	 * @author Administrator
	 *
	 */
public class PCSMainActivity extends BaseActivity {
	// ��ǰ���������
	private float downX, downY;
	// ��¼����ı�׼��Χ,��480*800Ϊ��׼
	private float standardWidth = 800;
	private float standardHight = 480;
	// ���������Ϊx-,x+,y+,y-;
	private double[] BYP = new double[] { 30, 115, 170, 240 };
	private double[] MAIN = new double[] { 30, 115, 270, 340 };

	private double[] ADDC = new double[] { 250, 330, 270, 340 };
	private double[] DCAC = new double[] { 465, 550, 270, 340 };
	private double[] LOAD = new double[] { 685, 765, 270, 340 };

	private double[] BAT = new double[] { 360, 440, 380, 450 };
	private double[] STATE = new double[] { 665, 775, 380, 440 };// ����
	private double[] ENERGY = new double[] { 25, 125, 385, 440 };// ʵʱ������Ϣ
	private float screenWidth, screenHeight;// ��Ļ�ֱ���
	private Handler handler;
	//private EditText name, psd;// �û���������
	//private String username, userpsd;
	//private View view = null; // dialog xml
	private Context content;
	public static List<PCSInfos> info = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		content = PCSMainActivity.this;
		setTitie(R.string.hello_world);
		setVisibility(View.GONE);
	    HttpGetData(Constant.PSCURL+ PacketActivity.packetgroupid,null);
		// ��ȡ��Ļ�ֱ���
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Log.e("TAG", "��Ļ�ֱ���Ϊ" + metrics.widthPixels + "   " + metrics.heightPixels);
		screenWidth = metrics.widthPixels / standardWidth;
		screenHeight = metrics.heightPixels / standardHight;
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					//��·����
					startActivity(new Intent(content, BYPMainActivity.class));
					break;
				case 2:
					//�е�����
					startActivity(new Intent(content, MIANMainActivity.class));
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					//ϵͳ���
					startActivity(new Intent(content, LOADMainActivity.class));
					break;
				case 6:
					//�������
					startActivity(new Intent(content, BATActivity.class));
					break;
				case 7:
					//����״̬
					startActivity(new Intent(content, STATEMainActivity.class));
					break;
				case 8:
					// ����ʵʱ��Ϣ
					startActivity(new Intent(content, StoredEnergyactivity.class));
					break;
				}
			}
		};
	}
	
	/***
	 * ��������
	 * @param url
	 * @param requestParams
	 */
	private void HttpGetData(String url,RequestParams requestParams){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,requestParams,new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Log.e("pcs", "��������ʧ��");
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if(arg2.length<5)
					return;
				info =JsonTools.getListJson(new String(arg2), PCSInfos.class);
				Log.e("info", info.toString());
			}
			});
		}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downX = event.getX();
			downY = event.getY();
			// Log.e("tag", "downX "+downX+" downY "+downY);
			if (BYP[0] * screenWidth < downX && BYP[1] * screenWidth > downX && BYP[2] * screenHeight < downY
					&& downY < BYP[3] * screenHeight) {
				handler.sendEmptyMessage(1);
			} else if (MAIN[0] * screenWidth < downX && MAIN[1] * screenWidth > downX && MAIN[2] * screenHeight < downY
					&& downY < MAIN[3] * screenHeight) {
				handler.sendEmptyMessage(2);
			} else if (ADDC[0] * screenWidth < downX && ADDC[1] * screenWidth > downX && ADDC[2] * screenHeight < downY
					&& downY < ADDC[3] * screenHeight) {
				handler.sendEmptyMessage(3);
			} else if (DCAC[0] * screenWidth < downX && DCAC[1] * screenWidth > downX && DCAC[2] * screenHeight < downY
					&& downY < DCAC[3] * screenHeight) {
				handler.sendEmptyMessage(4);
			} else if (LOAD[0] * screenWidth < downX && LOAD[1] * screenWidth > downX && LOAD[2] * screenHeight < downY
					&& downY < LOAD[3] * screenHeight) {
				handler.sendEmptyMessage(5);
			} else if (BAT[0] * screenWidth < downX && BAT[1] * screenWidth > downX && BAT[2] * screenHeight < downY
					&& downY < BAT[3] * screenHeight) {
				handler.sendEmptyMessage(6);
			} else if (STATE[0] * screenWidth < downX && STATE[1] * screenWidth > downX
					&& STATE[2] * screenHeight < downY && downY < STATE[3] * screenHeight) {
				handler.sendEmptyMessage(7);
			} else if (ENERGY[0] * screenWidth < downX && ENERGY[1] * screenWidth > downX
					&& ENERGY[2] * screenHeight < downY && downY < ENERGY[3] * screenHeight) {
				handler.sendEmptyMessage(8);
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		handler.removeCallbacksAndMessages(null);// ��ֹ�ڴ�й©
		super.onDestroy();
	}

	@Override
	public View getcontent() {
		return View.inflate(PCSMainActivity.this, R.layout.pcsactivity_main, null);
	}
}
