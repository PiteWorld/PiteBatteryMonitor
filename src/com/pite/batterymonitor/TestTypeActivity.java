package com.pite.batterymonitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.ab.activity.AbActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.BatteryGroupUtils;
import com.pite.batterymonitor.utils.ChartsUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 测试类型
 */
public class TestTypeActivity extends AbActivity {
	private Context context;
	private View noView = null;
	private List<BatteryGroupUtils> list = null; // 电池状态信息
	private int packetgroupid = 0;
	private LinearLayout ll;// 用来添加标题栏的item
	private LinearLayout chlickll;// 用来添加标题栏的item
	private TextView tv;
	private View view;
	private String[] title = null; // 标题
	private ListView listView = null;
	private List<String[]> lists = null;
	//private Button testBtn = null;
	//储能时的字体颜色
	private List<String[]> listColor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(mInflater.inflate(R.layout.activity_test_type, null));
		//PushAgent.getInstance(context).onAppStart();
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		listView = (ListView) this.findViewById(R.id.type_listview);
		ll = (LinearLayout) findViewById(R.id.type_title);
		/*testBtn = (Button) this.findViewById(R.id.test_gao);
		testBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(TestTypeActivity.this, DiagnosActivity.class));
			}
		});*/
		context = TestTypeActivity.this;
		title = new String[] { context.getResources().getString(R.string.ba_type),
				context.getResources().getString(R.string.Battery_statuss),
				context.getResources().getString(R.string.pite_battery_charges), "WIFI", "PLC" };
		// final AbTitleBar mAbTitleBar = this.getTitleBar(); // 标题
		this.setTitleText(R.string.Battery_test_type);
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		addTitle();// 添加标题
		View view = mInflater.inflate(R.layout.home_btn, null);// 标题栏右边
		this.addRightView(view);
		Button btnHome = (Button) this.findViewById(R.id.homeBtn);
		// packetgroupid = getIntent().getExtras().getInt("packetgroupid");
		if (ChartsUtils.isNetworkAvailable(TestTypeActivity.this)) {
			String str = null;
			if (LoginActivity.isChinese == 0) {
				str = "chinese";
			} else {
				str = "english";
			}
			HttpGetData(
					Constant.BATTERY_TEST + PacketActivity.pcketnoid + "/" + PacketActivity.packetgroupid + "/" + str,
					null); // 网络请求
		} else {
			//Toast.makeText(context, R.string.net_no, 0).show();
		}
		String str = null;
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (list.get(position).getTestType().equals("1")) {
					Intent intent = new Intent(TestTypeActivity.this, TimeMonitorActivity.class);
					intent.putExtra("type", "1");
					// intent.putExtra("his",
					// getIntent().getExtras().getInt("packetgroupid"));
					Log.e("2", "测试类型跳转ID：packetgroupid   :" + packetgroupid); 
					startActivity(intent);
				}
				else if (list.get(position).getTestType().equals("4")) {
//					Intent intent = new Intent(TestTypeActivity.this, PostionActivity.class);
//					intent.putExtra("time", position);
					Intent intent = new Intent (TestTypeActivity.this,TimeMonitorActivity.class);
					intent.putExtra("type", "2");
					startActivity(intent);
				}else if(list.get(position).getTestType().equals("5")){
					Intent intent = new Intent (TestTypeActivity.this,WaringActivity.class);
					startActivity(intent);
					
				}else if(list.get(position).getTestType().equals("6")){
					Log.e("tag", list.get(position).getLongitude()+"  "+list.get(position).getLatitude());
					Intent intent = new Intent (TestTypeActivity.this,PostionActivity.class);
					intent.putExtra("longitude", list.get(position).getLongitude());
					intent.putExtra("latitude", list.get(position).getLatitude());
					startActivity(intent);
				}

			}
		});
		/**
		 * 返回主页面
		 */
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, MenuActivity.class));
				TestTypeActivity.this.finish();
			}
		});
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetData(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getListJson(new String(arg2), BatteryGroupUtils.class);
						setLoad(); // 设置 电池组数据
					}else{
						Toast.makeText(context, R.string.resqustFails, 0).show();
					}
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}
		});
	}
	/**
	 * 动态加载电池组数据
	 */
	public void setLoad() {
		if (list == null) {
			return;
		}
		lists = new ArrayList<String[]>();
		//第三个界面数据进行判断
		//字体颜色 list.get(i).getGroupname(),
		listColor = new ArrayList<String[]>();
		String[] data=null;
		for (int i=0; i< list.size();i++) {
			data = new String[] { list.get(i).getTypename(),
					list.get(i).getgSname() + "", list.get(i).getwSName(), list.get(i).getNetStatus() + "",
					list.get(i).getEquipmentStatus() + "" };
			String[] color = new String[] {list.get(i).getgScolor(),list.get(i).getwSColor()};
			if(PacketActivity.hctype.equals("4")){
				data = new String[] { list.get(i).getTypename(), list.get(i).getgSname() + "", list.get(i).getwSName(),
						list.get(i).getNetStatus() + "", list.get(i).getEquipmentStatus() + "" };
			}
			if(list.get(i).getTestType().equals("5")){
				data = new String[] { list.get(i).getTypename(), "--", "--","--","--"};
			}
            if(list.get(i).getTestType().equals("6")){
            	data = new String[] { list.get(i).getTypename(), "--", "--","--","--"};
			}
			
			listColor.add(color);
			lists.add(data);
		}
		listView.setAdapter(new ChartAdapter(TestTypeActivity.this, lists, title.length, 0.01f, 0.99f, 0,listColor));
	}

	/**
	 * 动态添加title选项
	 */
	private void addTitle() {
		for (int i = 0; i < title.length; i++) {
			tv = new TextView(this);
			tv.setText(title[i]);
			tv.setTextSize(12);
			tv.setTextColor(Color.BLACK);
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.01f);
			params.gravity = Gravity.CENTER;
			view = new View(this);
			view.setBackground(TestTypeActivity.this.getResources().getDrawable(R.drawable.line));
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.99f);
			chlickll = new LinearLayout(this);
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 1.0f);

			if (i == title.length - 1) {
				LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				chlickll.addView(tv, params4);
				ll.addView(chlickll, params3);
			} else {
				chlickll.addView(tv, params);
				chlickll.addView(view, params2);
				ll.addView(chlickll, params3);
			}
		}
	}
}
