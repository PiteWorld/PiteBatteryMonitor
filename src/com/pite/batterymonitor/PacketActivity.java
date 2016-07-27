package com.pite.batterymonitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.ab.activity.AbActivity;
import com.example.pcs.PCSMainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.BatteryGroupUtils;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.umeng.message.PushAgent;

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

/**
 * 电池组信息
 */
public class PacketActivity extends AbActivity {
	private Context context;
	// 表格的Adapter
	private View noView = null;
	private List<BatteryGroupUtils> list = null; // 电池状态信息
	public static int packetgroupid = 0;
	public static int pcketnoid = 0;
	private LinearLayout ll, menu_ll, menu_titles;// 用来添加标题栏的item
	private LinearLayout chlickll;// 用来添加标题栏的item
	private TextView tv;
	private View view;
	private ListView listView = null;
	private List<String[]> lists = null;
	private String[] title = null; // 标题
	//储能时的字体颜色
	private List<String[]> listColor;
	//判断当前是否为储能电站
	public static String hctype=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = PacketActivity.this;
		setAbContentView(mInflater.inflate(R.layout.activity_menu, null));
		PushAgent.getInstance(context).onAppStart();
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		menu_ll = (LinearLayout) findViewById(R.id.menu_ll);
		menu_ll.setVisibility(View.GONE);
		menu_titles = (LinearLayout) findViewById(R.id.menu_titles);
		menu_titles.setVisibility(View.GONE);
		listView = (ListView) this.findViewById(R.id.menu_listview);
		ll = (LinearLayout) findViewById(R.id.menu_title);
		title = new String[] { context.getResources().getString(R.string.ba_station),
				context.getResources().getString(R.string.ba_group),
				context.getResources().getString(R.string.Battery_statuss),
				context.getResources().getString(R.string.pite_battery_charges), "WIFI", "PLC" };
		this.setTitleText(R.string.Battery_pack_Information);
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		addTitle(); // 标题
		View view = mInflater.inflate(R.layout.home_btn, null);// 标题栏右边
		this.addRightView(view);
		Button btnHome = (Button) this.findViewById(R.id.homeBtn);
		// (11)设置Adapter
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				packetgroupid = list.get(position).getGroupID();
				if (list.get(position).getNodeType() == 0) {
					if (list.get(position).getDevtype().equals("kh_pcs")) {
						startActivity(new Intent(PacketActivity.this, PCSMainActivity.class));
					}else {
						Intent intent = new Intent(PacketActivity.this, TestTypeActivity.class);
						pcketnoid = list.get(position).getNodeid();
						Log.e("2", "电池分组packetgroupid:  " + list.get(position).getGroupID());
						Log.e("2", "电池分组pcketnoid:" + list.get(position).getNodeid());
						startActivity(intent);
				 }
				} else {
					return;
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
				PacketActivity.this.finish();
			}
		});
		//检查网络是否可用
		if (ChartsUtils.isNetworkAvailable(PacketActivity.this)) {
			String str = null;
			if (LoginActivity.isChinese == 0) {
				str = "chinese";
			} else {
				str = "english";
			}
			HttpGetData(Constant.BATTERY_PACKET + "/" + MenuActivity.nodeid + "/" + str, null); // 网络请求
		} else {
			// Toast.makeText(context, R.string.net_no, 0).show();
		}
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
						sePackettLoad(); // 设置 电池组数据
					} else {
						Toast.makeText(context, R.string.resqustFails, Toast.LENGTH_SHORT).show();
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
	public void sePackettLoad() {
		if (list == null) {
			return;
		}
		lists = new ArrayList<String[]>();
		listColor = new ArrayList<String[]>();
		String[] data =null;
		for (int i = 0; i < list.size(); i++) {
			hctype = list.get(i).gethctype();
			/*String[] data = new String[] { list.get(i).getNodename(), list.get(i).getGroupname(),
					list.get(i).getGroupStatus() + "", list.get(i).getWorkStatus(), list.get(i).getNetwork() + "",
					list.get(i).getDevice() + "" };
			//判断当前是不是储能电站类型  改变取值添加颜色
			if(hctype.equals("3")){*/
				data = new String[] { list.get(i).getNodename(), list.get(i).getGroupname(),
						list.get(i).getgSname() + "", list.get(i).getwSName(), list.get(i).getNetwork() + "",
						list.get(i).getDevice() + "" };
				String[] color = new String[] {list.get(i).getgScolor(),list.get(i).getwSColor()};
				/*{"nodeid":10035,"groupID":944,"nodename":"储能电站","groupname":"PCS11-6","device":0,
				 * "network":0,"groupStatus":0,"workStatus":"0","gSname":"良好",
				 * "gScolor":"000000","wSName":"115%","wSColor":"000000","nodeType":0,
				 * "devtype":"pite_3923c"}*/
				if(hctype.equals("4")){
					data = new String[] { list.get(i).getNodename(), list.get(i).getGroupname(),
							 "--", "--", list.get(i).getNetwork() + "", "--" };
				}
				listColor.add(color);
				
			if (list.get(i).getNodeType() == 1) {
				data = new String[] { list.get(i).getNodename(), list.get(i).getGroupname(), "--", "--",
						list.get(i).getNetwork() + "", "--" };
				lists.add(data);
			} else
				lists.add(data);
		}
		listView.setAdapter(new ChartAdapter(PacketActivity.this, lists, title.length, 0.03f, 0.97f, 1,listColor));
	}

	/**
	 * 动态添加title选项
	 */
	private void addTitle() {
		for (int i = 0; i < title.length; i++) {
			tv = new TextView(this);
			tv.setText(title[i]);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(12);
			tv.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.03f);
			params.gravity = Gravity.CENTER;
			view = new View(this);
			view.setBackground(PacketActivity.this.getResources().getDrawable(R.drawable.line));
			LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, 0.97f);
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
