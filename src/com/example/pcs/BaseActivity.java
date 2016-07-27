package com.example.pcs;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.MenuActivity;
import com.pite.batterymonitor.PacketActivity;
import com.pite.batterymonitor.R;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.utils.PCSInfos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public abstract class  BaseActivity extends Activity{
	private TextView title,time;//每个activity的标题
	private LinearLayout content;//每个activity的布局内容
	private ImageButton back,base_home;
	public static List<PCSInfos> info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baseactivity);
		init();
	}
	public List<PCSInfos> getInfo(){
		return info;
	}
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			time.setText(info.get(0).getTesttime());
		};
	};
	/**
	 * 请求数据的方法
	 */
	//[PCSInfos [id=0, groupid=956, testheadid=168861, testtime=2049-02-05 11:12:13, companyNo=0, devType=0, 
	//devAddress=0, mainsspplyinputUV=235.0, mainsspplyinputVV=234.0, mainsspplyinputWV=83.4, mainsspplyinputFrequency=280.3, bypassspplyinputUV=234.0, bypassspplyinputVV=208.4, bypassspplyinputWV=236.1, bypassspplyinputFrequency=280.3, inverterbatteryV=3238, inverterbatteryI=258.1, inverterbatteryT=3083, inverterbatteryCD=11, systemoutputUV=232.0, systemoutputVV=231.4, systemoutputWV=234.0, systemoutputUI=46.9, systemoutputVI=89.4, systemoutputWI=213.8, systemoutputLoadUP=2596.0, systemoutputLoadVP=1055.0, systemoutputLoadWP=3098.0, systemoutputUYP=9.7, systemoutputVYP=85.3, systemoutputWYP=160.6, systemoutputUSP=10.4, systemoutputVSP=8.5, systemoutputWSP=7.8, systemoutputUPF=3.5, systemoutputVPF=1.0, systemoutputWPF=0.9, systemoutputFrequency=177.9, batterystate=2050, batteryTemperature=0, loadstate=3840, outputstate=5, temperatureinmachine=2333, batteryoperatingstate=1536, currentpricetype=2830, currentprice=0.6700, inputpower=20224385.4, outputpower=71988.7, batteryV=4251.0, batteryI=538.7, inputpowersum=5058.9, inputpowerfee=20211721.1, outputpowersum=21810907.2, outputpowerfee=5049660.2, profitsum=-3532.9, producttype=KU3302 0  , parallelstate=0, inputphasestate=1, bypassstate=1, inverterstate=1, inverterswitchstate=1, inverteroperatingstate=0, UPStemperaturestate=0, batterypolestate=1, fanstate=1, parallelinestate=1, fusestate=1, UPScomstate=1, runState=null, currentPrice=null, totalInputElectricity=null, priceType=null, totalInputElectricityFees=null, inputP=null, outputP=null, 
	//totalOutputElectricity=null, totalOutputElectricityFees=null, batteryU=null, totalProfit=null]]
	private void HttpGetData(String url,RequestParams requestParams){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url,requestParams,new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Log.e("pcs", "请求数据失败");
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if(arg2.length<5)
					return;
				info =getListJson(new String(arg2), PCSInfos.class);
				handler.sendEmptyMessage(1);
				Log.e("info", info.toString());
			}
			});
		Log.e("PCSurl", url);
		}
	public  <T> List<T> getListJson(String json, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for (final JsonElement elem : array) {
				list.add(new Gson().fromJson(elem, cls));
			}
		} catch (Exception e) {
			Log.e("2", "解析 异常：" + e);
		}
		return list;
	}
	
	private void init() {
		back = (ImageButton) findViewById(R.id.back);
		base_home = (ImageButton) findViewById(R.id.base_home);
		title=(TextView) findViewById(R.id.Title);
		content=(LinearLayout) findViewById(R.id.content);
		time=(TextView)findViewById(R.id.ttime);
		base_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//调回主页面
				startActivity(new Intent(BaseActivity.this,MenuActivity.class));
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//添加内容文件
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		content.addView(getcontent(),params);
		HttpGetData(Constant.PSCURL+ PacketActivity.packetgroupid,null);
	}
	public abstract View getcontent() ;
	/**
	 * 改变Activity title 的方法
	 */
	public void setTitie(int str){
		title.setText(str);
	}
	/**
	 * 隐藏返回键的方法
	 * @param string
	 */
	public void setVisibility(int vis){
		back.setVisibility(vis);
	}
	public void showToast(String string){
		Toast.makeText(BaseActivity.this , string, Toast.LENGTH_SHORT).show();
	}
}
