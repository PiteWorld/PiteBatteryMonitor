package com.pite.batterymonitor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.adapter.WaringAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.BatteryWaringMsg;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.WarHeadJson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WaringActivity extends Activity implements OnClickListener, OnItemClickListener {
	private Context context;
	private ListView war_listview;
	private ImageButton war_back, war_home;
	private List<BatteryWaringMsg> list = null;
	private LinearLayout quanxuan;
	// 保存选中状态
	private List<String> listData;
	private WarHeadJson warheadjson = null;
	private WaringAdapter wadapter;
	private TextView war_station, war_type, war_u_top_limit, war_temper_top_limit, war_r_relpace, war_group_name,
			war_min_r, war_u_limit, war_temper_limit, war_r_waring;
	private CheckBox war_check_all;
	private Button war_btn_deal;
	private TextView war_tv_deal;
	private String str = null;
	private boolean falg = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waring);
		context = this;
		listData = new ArrayList<String>();
		if (ChartsUtils.isNetworkAvailable(WaringActivity.this)) {
			if (LoginActivity.isChinese == 0) {
				str = "chinese";
			} else {
				str = "english";
			}
		}
		initData();
	}

	/**
	 * id綁定
	 */
	private void initData() {
		war_btn_deal = (Button) findViewById(R.id.war_btn_deal);
		war_check_all = (CheckBox) findViewById(R.id.war_check_all);
		war_tv_deal = (TextView) findViewById(R.id.war_tv_deal);
		war_listview = (ListView) findViewById(R.id.war_listview);
		war_back = (ImageButton) findViewById(R.id.war_back);
		war_home = (ImageButton) findViewById(R.id.war_home);
		war_station = (TextView) findViewById(R.id.war_station);
		war_type = (TextView) findViewById(R.id.war_type);
		war_u_top_limit = (TextView) findViewById(R.id.war_u_top_limit);
		war_temper_top_limit = (TextView) findViewById(R.id.war_temper_top_limit);
		war_r_relpace = (TextView) findViewById(R.id.war_r_relpace);
		war_group_name = (TextView) findViewById(R.id.war_group_name);
		war_min_r = (TextView) findViewById(R.id.war_min_r);
		war_u_limit = (TextView) findViewById(R.id.war_u_limit);
		war_temper_limit = (TextView) findViewById(R.id.war_temper_limit);
		war_r_waring = (TextView) findViewById(R.id.war_r_waring);
		quanxuan = (LinearLayout) findViewById(R.id.quanxuan);
		quanxuan.setOnClickListener(this);
		war_back.setOnClickListener(this);
		war_home.setOnClickListener(this);
		war_listview.setOnItemClickListener(this);

		// war_check_all.setOnCheckedChangeListener(this);
		war_btn_deal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context).setTitle(R.string.string104)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						SavaChooseState();
						String json = JsonUpload();
						RequestParams params = new RequestParams();
						params.put("DealData", json);
						Log.e("tag1", "********" + json);
						Log.e("tag1", "********" + params);
						HttpUpdateData(Constant.WARINGURL_UPDATE ,params);
						if (listData.size() == 0) {
							Toast.makeText(WaringActivity.this, R.string.string107, 0).show();
							return;
						} //else {
							//for (int i = 0; i < listData.size(); i++) {
								//HttpUpdateData(Constant.WARINGURL_UPDATE , );
							//}
						//}
					}
				}).setNeutralButton(R.string.no, null).create().show();

				// SavaChooseState();
				/*
				 * String json = JsonUpload(); RequestParams params = new
				 * RequestParams(); params.put("DealData", json); Log.e("tag1",
				 * "1111111111" + json);
				 */
				// if (listData.size() == 0) {
				// Toast.makeText(WaringActivity.this, "请至少选择一个处理", 0).show();
				// return;
				// }
				// else{
				// for (int i = 0; i < listData.size(); i++) {
				// HttpUpdateData(Constant.WARINGURL_UPDATE + listData.get(i),
				// null);
				// }
				// }
			}
		});
		HttpGetDataTitle(Constant.WARINGURL_HEAD + PacketActivity.packetgroupid + "/" + str, null);
		HttpGetData(Constant.WARINGURL + PacketActivity.packetgroupid + "/" + str, null); // 网络请求

	}

	/**
	 * 拼接json [ {"ID": "1"}, {"ID": "2"}, ]String
	 * s="[{\"ID\":11},{\"ID\":12},...]";
	 */
	// private String JsonUpload() {
	// String json = "";
	// String head = "{\"ID\":\"";
	// String foot = "\"}";
	// if (listData.size() == 0)
	// return null;
	// for (int i = 0; i < listData.size() - 1; i++) {
	// json += head + listData.get(i) + foot + ",";
	// }
	// json = "[" + json + head + listData.get(listData.size() - 1) + foot +
	// "]";
	// Log.e("tag", json + " -------");
	// return json;
	// }
	
	/**
	 * 拼接json [ {"ID": "1"}, {"ID": "2"}, ]String
	 * s="[{\"ID\":11},{\"ID\":12},...]";
	 */
	private String JsonUpload() {
		String json = "(";
		if (listData.size() == 0)
			return null;
		for (int i = 0; i < listData.size() - 1; i++) {
			json += listData.get(i) + ",";
		}
		json = json  + listData.get(listData.size() - 1)+")";
		Log.e("tag", json + " -------");
		return json;
	}

	/**
	 * 赋值的方法
	 * 
	 * @param v
	 */
	private void setText(WarHeadJson warheadjson) {
		if (warheadjson != null) {
			war_station.setText(warheadjson.getNodeName());
			war_type.setText(warheadjson.getTypename());
			war_u_top_limit.setText(getText(warheadjson.getUH()) + "V");
			war_temper_top_limit.setText(warheadjson.getTH() + "℃");
			war_r_relpace.setText(getText(warheadjson.getRH()) + "mΩ");
			war_group_name.setText(warheadjson.getGroupName());
			war_min_r.setText(getText(warheadjson.getNominalr()) + "mΩ");
			war_u_limit.setText(getText(warheadjson.getUL()) + "V");
			war_temper_limit.setText(warheadjson.getTL() + "℃");
			war_r_waring.setText(getText(warheadjson.getRL()) + "mΩ");
		}
	}

	/**
	 * 保留两位小数
	 * 
	 * @param str
	 * @return
	 */
	private String getText(String str) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(Double.valueOf(str));
	}

	boolean ischeck = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.war_back:
			finish();
			break;
		case R.id.war_home:
			startActivity(new Intent(WaringActivity.this, MenuActivity.class));
			break;
		case R.id.quanxuan:
			if (list == null || list.size() == 0)
				return;
			if (ischeck) {
				for (int i = 0; i < list.size(); i++) {
					WaringAdapter.getHash().put(i, true);
					war_check_all.setBackgroundResource(R.drawable.check);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					WaringAdapter.getHash().put(i, false);
					war_check_all.setBackgroundResource(R.drawable.check2);
				}
			}
			ischeck = !ischeck;
			wadapter.notifyDataSetChanged();
			break;
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
						list = JsonTools.getListJson(new String(arg2), BatteryWaringMsg.class);
						wadapter = new WaringAdapter(context, list);
						war_listview.setAdapter(wadapter);
						Log.e("2", list.toString());
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(context, R.string.resqustFails, Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 网络Get 请求 请求Title
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetDataTitle(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						warheadjson = JsonTools.getStringJson(new String(arg2), WarHeadJson.class);
						setText(warheadjson);
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
	 * 网络Get 请求 请求Title
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpUpdateData(final String url, final RequestParams params) {
		HttpReustClient.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (new String(arg2).trim().equals("1")) {
					Log.e("tag", "处理成功");
//					for (int i = 0; i < arg2.length; i++) {
//						WaringAdapter.getHash().put(i, false);
//						Log.e("tag", "处理成功------------");
//					}
					HttpGetData(Constant.WARINGURL + PacketActivity.packetgroupid + "/" + str, null); // 网络请求
				} else
					Log.e("tag", "处理失败");
				war_check_all.setBackgroundResource(R.drawable.check2);
				wadapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Log.e("tag", "处理失败");
			}
		});

		/*
		 * HttpReustClient.post(url, params, new AsyncHttpResponseHandler() {
		 * 
		 * @Override public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
		 * { if (arg0 == 200) { if (arg2 != null && arg2.length > 1) {
		 * Log.e("tag", " @@@@@@@@@ "+new String(arg2)); } else {
		 * Toast.makeText(context, R.string.resqustFails,
		 * Toast.LENGTH_SHORT).show(); }
		 * 
		 * } }
		 * 
		 * @Override public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		 * Throwable arg3) { // TODO Auto-generated method stub
		 * 
		 * } });
		 */
	}

	/*
	 * // 全选事件监听
	 * 
	 * @Override public void onCheckedChanged(CompoundButton buttonView, boolean
	 * isChecked) { if(list==null||list.size()==0) return; if (isChecked) { for
	 * (int i = 0; i < list.size(); i++) { WaringAdapter.getHash().put(i, true);
	 * } } else { for (int i = 0; i < list.size(); i++) {
	 * WaringAdapter.getHash().put(i, false); } }
	 * wadapter.notifyDataSetChanged(); }
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (WaringAdapter.getHash().get(position)) {
			WaringAdapter.getHash().put(position, false);
		} else {
			WaringAdapter.getHash().put(position, true);
		}
		wadapter.notifyDataSetChanged();
	}

	// 保存选中的状态
	public void SavaChooseState() {
		listData.clear();
		for (int i = 0; i < WaringAdapter.getHash().size(); i++) {
			if (WaringAdapter.getHash().get(i)) {
				listData.add(list.get(i).getID() + "");
			}
		}
		Log.e("tag", listData.size() + "*---");
	}
}
