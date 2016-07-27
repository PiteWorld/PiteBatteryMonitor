package com.pite.batterymonitor;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.adapter.ChartAdapter;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.jsontool.JsonTools;
import com.pite.batterymonitor.utils.BatteryGroupUtils;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.LogoUser;
import com.umeng.message.PushAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 站信息
 */
public class MenuActivity extends Activity {
	private Context context;
	private LinearLayout ll;// 用来添加标题栏的item
	private LinearLayout chlickll;// 用来添加标题栏的item
	private TextView tv,menu_corporate_name,menu_telnum;
	private View view;
	private View noView = null;
	private List<BatteryGroupUtils> list = null;
	private String[] title = null; // 标题
	public static int nodeid = 0;
	private ListView listView = null;
	private List<String[]> lists = null;
	private String url=null;
	private String logoimange = null;
    //判断当前是否为充电
	public static String type=null;
	//储能时的字体颜色
   private List<String[]> listColor;
	private ImageView menu_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		//setAbContentView(mInflater.inflate(R.layout.activity_menu, null));
		context = MenuActivity.this;
		PushAgent.getInstance(context).onAppStart();
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		listView = (ListView) this.findViewById(R.id.menu_listview);
		ll = (LinearLayout) findViewById(R.id.menu_title);
		menu_corporate_name = (TextView) findViewById(R.id.menu_corporate_name);
		menu_telnum = (TextView) findViewById(R.id.menu_telnum);
		menu_image = (ImageView) findViewById(R.id.menu_image);
		//设置公司名称和电话
		menu_corporate_name.setText(LogoUser.getInstance().getOrgname());
		menu_telnum.setText(LogoUser.getInstance().getTelnum());
		
		 logoimange = LogoUser.getInstance().getLogourl();
		File file = new File(Constant.LOGOIMAGE+"/"+logoimange.substring(logoimange.lastIndexOf("/")+1));
		if(file.exists()){
			//BitmapFactory.Options options = new BitmapFactory.Options();
			//options.inJustDecodeBounds = false;
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
			menu_image.setImageBitmap(bitmap);
			//Drawable drawable =new BitmapDrawable(bitmap);
			//this.setLogoLine(drawable);
		}
		else{
			url = "http://203.191.147.81:8001/bmcp/logopng/"+logoimange.substring(logoimange.lastIndexOf("/")+1);
			Log.e("tag","Thread1.............");
			new Thread1().start();
			
		}
		
		title = new String[] { context.getResources().getString(R.string.ba_station),
				context.getResources().getString(R.string.Battery_statuss),
				context.getResources().getString(R.string.pite_battery_charges), "GPRS", "PLC" };
		//this.setTitleText(R.string.Battery_Information);
		//this.set;
		//this.setTitleLayoutBackground(R.drawable.top_bg);
		//this.setTitleTextMargin(20, 0, 0, 0);
		//this.setLogo(R.drawable.button_selector_back);
		addTitle();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MenuActivity.this, PacketActivity.class);
				// intent.putExtra("group", list.get(position -
				// 1).getNodeid());
				// Log.e("2", "电池组跳转group:"+ list.get(position -
				// 1).getNodeid());
				//保存返回的登录地址
				Constant.BATTERY_BASIC_ADDRESS_LOGINs =list.get(position).getRooturl();
				nodeid = list.get(position).getNodeid();
				startActivity(intent);
			}
		});
		if (ChartsUtils.isNetworkAvailable(MenuActivity.this)) {
			String str = null;
			if (LoginActivity.isChinese == 0) {
				str = "chinese";
			} else {
				str = "english";
			}
			HttpGetData(LoginActivity.basic_ip+"/"+"rest"+"/"+Constant.BATTERY_GOURP + LoginActivity.nodid + "/" + str, null); // 网络请求
		} else {
			//Toast.makeText(context, R.string.net_no, 0).show();
		}
	}
	 /** 
     * 获取网落图片资源  
     * @param url 
     * @return 
     */  
	class Thread1 extends Thread{
		
		@Override
		public void run() {
			final Bitmap bit =getHttpBitmap(url);
			runOnUiThread(new Runnable() {
				public void run() {
					menu_image.setImageBitmap(bit);
				}
			});
		}
	}
    public Bitmap getHttpBitmap(String url){  
        URL myFileURL;  
        Bitmap bitmap=null;  
		Log.e("tag", url+" -----");
        try{  
            myFileURL = new URL(url);  
            //获得连接  
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制  
            conn.setConnectTimeout(6000);  
            //连接设置获得数据流  
            conn.setDoInput(true);  
            //不使用缓存  
            conn.setUseCaches(false);  
            //这句可有可无，没有影响  
            //conn.connect();  
            Log.e("tag", conn.getContentLength()+"---");
            //得到数据流  
            InputStream is = conn.getInputStream();  
            //解析得到图片  
            bitmap = BitmapFactory.decodeStream(is);  
            //关闭数据流  
            is.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return bitmap;  
          
    }  
	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetData(final String url, final RequestParams params) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("页面一返回的url", url);
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getListJson(new String(arg2), BatteryGroupUtils.class);
						setLoad(); // 设置 电池组数据
					} else {
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
	 * 加载数据
	 */
	private void setLoad() {
		if (list == null) {
			return;
		}
		lists = new ArrayList<String[]>();
		listColor= new ArrayList<String[]>();
		for (int i = 0; i < list.size(); i++) {
			
			type = list.get(i).gethctype();
			String[] data=null ;
				data = new String[] { list.get(i).getNodename(),
						list.get(i).getgSname() + "", list.get(i).getwSName(), list.get(i).getNetwork() + "",
						list.get(i).getDevice() + "" };
				String[] color = new String[] {list.get(i).getgScolor(),list.get(i).getwSColor()};
				listColor.add(color);
				lists.add(data);
		}
		listView.setAdapter(new ChartAdapter(MenuActivity.this, lists, title.length, 0.03f, 0.97f, 0,listColor));
	}
//		
//		for (BatteryGroupUtils map : list) {
//			String[] data = new String[] { map.getNodename(), map.getGroupStatus() + "", map.getWorkStatus(),
//					map.getNetwork() + "", map.getDevice() + "" };
//			lists.add(data);
//		}
//		listView.setAdapter(new ChartAdapter(MenuActivity.this, lists, title.length, 0.01f, 0.99f, 0));
//
//	}

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
					LayoutParams.MATCH_PARENT, 0.01f);
			params.gravity = Gravity.CENTER;
			view = new View(this);
			view.setBackground(MenuActivity.this.getResources().getDrawable(R.drawable.line));
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

	/**
	 * 手机返回 键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitMenu(MenuActivity.this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 手机返回 键监听
	 */
	private void ExitMenu(Context context) {
		new AlertDialog.Builder(this).setTitle(R.string.ok_exit)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MenuActivity.this.finish();
					}
				}).setNegativeButton(R.string.no, null).show();
	}
}
