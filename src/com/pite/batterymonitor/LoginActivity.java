package com.pite.batterymonitor;

import java.io.File;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.batterymonitor.constant.Constant;
import com.pite.batterymonitor.http.HttpReustClient;
import com.pite.batterymonitor.service.LogoServer;
import com.pite.batterymonitor.sharexml.ShareXML;
import com.pite.batterymonitor.utils.ChartsUtils;
import com.pite.batterymonitor.utils.LogoUser;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��½����
 */
public class LoginActivity extends Activity implements OnClickListener {
	private TextView userName, userPwd; // �û��� ����
	private Button loginBtn; // ��½
	private CheckBox savePwd; // ��ס����
	private ShareXML share; // ����
	private Context context;
	private String getUserName, getUserPwd; // ��ȡ���� �û�������
	private boolean loginStatus; // ��ס����״̬
	private TextView piteVersion; // ����汾��
	private Button mBtnLanguage;
	public static int isChinese = 0; // ��Ӣ��ѡ��
	public static String nodid = null; //
	public static String basic_ip;
	public Context content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		content = this;
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		PushAgent mPushAgent = PushAgent.getInstance(context);
		mPushAgent.enable();
		PushAgent.getInstance(context).onAppStart();
		//String device_token = UmengRegistrar.getRegistrationId(context);
		//���ð汾���·���
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//		"application/vnd.android.package-archive");
//		context.startActivity(i);
		HttpGetVersion(Constant.LOGIN_LOGOADSS.concat(Constant.GETVERSION_NAME),null);
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		InputMethodService input = new InputMethodService();
		context = LoginActivity.this;
		share = new ShareXML(context);
		mBtnLanguage = (Button) this.findViewById(R.id.sp_language);
		userName = (TextView) this.findViewById(R.id.login_name);
		userPwd = (TextView) this.findViewById(R.id.login_pwd);
		loginBtn = (Button) this.findViewById(R.id.login_btn);
		savePwd = (CheckBox) this.findViewById(R.id.save_pwd);
		piteVersion = (TextView) this.findViewById(R.id.pite_version);
		loginBtn.setOnClickListener(this);
		// �������Ͳ�����ע��Ļص�����
		try {
			// ���ó���汾��Ϣ
			String version = this.getString(R.string.pite_version);
			piteVersion.setText(version
					+ LoginActivity.this.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		savePwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				loginStatus = isChecked;
				// if (isChecked) {
				// getUserName = userName.getText().toString().trim();
				// getUserPwd = userPwd.getText().toString().trim();
				// } else {
				// share.clearShare();
				// }
			}
		});
		showXML();// ��ʾ�û�����
		mBtnLanguage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context).setTitle(R.string.language_change)
						.setItems(R.array.language, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								Locale locale = getResources().getConfiguration().locale;
								if (which == 0) {
									isChinese = 0;
									mBtnLanguage.setText("����");
									setLang(locale.SIMPLIFIED_CHINESE);
								} else if (which == 1) {
									isChinese = 1;
									mBtnLanguage.setText("English");
									setLang(locale.US);
								}
							}
						}).show();
			}
		});
	}

	public void setLang(Locale locale) {
		// ���res��Դ����
		Resources resources = getResources();
		// ������ö���
		Configuration config = resources.getConfiguration();
		// �����Ļ��������Ҫ�Ƿֱ��ʣ����صȡ�
		DisplayMetrics dm = resources.getDisplayMetrics();
		// ����
		config.locale = locale;
		resources.updateConfiguration(config, dm);
		// ˢ��activity����������Ч
		finish();
		startActivity(new Intent().setClass(LoginActivity.this, LoginActivity.class));
		// LoginActivity.this.finish();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn: // ��½
			if (ChartsUtils.isNetworkAvailable(LoginActivity.this)) {
				// if (!loginStatus) {
				getUserName = userName.getText().toString().trim();
				getUserPwd = userPwd.getText().toString().trim();
				// }
				HttpGetData(Constant.BATTERY_BASIC_ADDRESS_LOGIN + Constant.BATTERY_BASIC_LOGIN + getUserName + "/"
						+ getUserPwd, null);

			} else {
				//Toast.makeText(context, R.string.net_no, Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	/***
	 * ��ȡ�汾�� �жϳ����Ƿ���Ҫ����
	 */
	private void HttpGetVersion(String url, RequestParams params){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if(arg0==200){
					try {
						JSONObject object = new JSONObject(new String(arg2));
						String version = object.optString("version");
						//��ȡ��ǰ�汾�� �жϳ����Ƿ���Ҫ����
						PackageInfo pi = content.getPackageManager().getPackageInfo(content.getPackageName(), 0);
						String versionName = pi.versionName;
						//���µ�����
						String message = object.optString("remark");
						//���µ��ļ���С
						String size = object.optString("fsize");
						//�����Ҫ���µĵ�ַ
						String url = object.optString("apkurl");
						Log.e("tag", "���°汾Ϊ"+version+"  ��ǰ�汾Ϊ"+versionName);
						if(Double.valueOf(version)>Double.valueOf(versionName))
						{
							ShowDialog(url,message);
						}
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
			private void ShowDialog(final String url,String message) {
				Log.e("tag", "������Ҫ����");
				//����dialog�Ƿ���Ҫ����
				String[] mess=message.split("��");
				String content = "";
				for (String string : mess) {
					content+=string+"\n";
				}
				new AlertDialog.Builder(context)
				.setTitle(R.string.update)
				.setMessage(content)
				.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(LoginActivity.this, LogoServer.class);
						//���
						intent.putExtra("flage","1");
						intent.putExtra("apkurl", Constant.LOGIN_LOGO.concat(url));
						startService(intent);
					}
				})
				.setNegativeButton(R.string.no, null)
				.show();
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				
			}
		});
	}
	/**
	 * ����Get ����
	 * 
	 * @param url
	 * @param params
	 * 	// {"ip":"http://203.191.147.81:8011/bms","webappurl":"http://203.191.147.81/BMSystem",
	 * "orgid":"1","indexpage":"home.html","userid":"1011","nodeid":"0","orgname":"��»�����ܼ���豸���޹�˾",
	// "logourl":"bmcp/logopng/logo.png","linkman":"Ī־��","telnum":"0755-26805755"}
	 */
	private void HttpGetData(final String url, final RequestParams params) {
		HttpReustClient.getLogin(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("2", "��½������" + new String(arg2));
				if (arg0 == 200) {
					String str = new String(arg2);
					Log.e("tag", arg0+"�������ݳɹ�");
					try {
						JSONObject object = new JSONObject(str);
						nodid = object.getString("userid");
						basic_ip = object.getString("ip");
						LogoUser(object);
						// Constant.BATTERY_BASIC_ADDRESS = basic_ip;
						initJump(true);
						String imageUri =LogoUser.getInstance().getLogourl();
						//�ж�logo�Ƿ����
						if(!new File(Constant.LOGOIMAGE+"/"+imageUri.substring(imageUri.lastIndexOf("/")+1)).exists())
						{
						Intent intent = new Intent(LoginActivity.this, LogoServer.class);
						//���
						intent.putExtra("flage", "2");
						intent.putExtra("image", Constant.LOGIN_LOGO+object.getString("logourl"));
						startService(intent);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if ("{0}".equals(new String(arg2))) {
					Toast.makeText(context, R.string.FAIL, Toast.LENGTH_SHORT).show();
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// Log.e("2", "��½ʧ�ܣ�" + new String(arg2));
				Toast.makeText(context, R.string.FAIL, Toast.LENGTH_SHORT).show(); // ��½ʧ��
			}
		});
	}

	/**
	 * ��������
	 */
	public void shareXML() {
		share.addString("login", "login");
		share.addString("userName", getUserName);
		share.addString("userPwd", getUserPwd);
		share.addBoolean("loginStatus", loginStatus);
	}
	/*-*
	 * ��ȡ��¼���ص�����Ϣ
	 */
	private void LogoUser(JSONObject object) throws JSONException {
		LogoUser user = LogoUser.getInstance();
		user.setIp(basic_ip);
		user.setUserid(nodid);
		user.setLinkman(object.getString("linkman"));
		user.setLogourl(object.getString("logourl"));
		user.setOrgname(object.getString("orgname"));
		user.setTelnum(object.getString("telnum"));
		user.setWebappurl(object.getString("webappurl"));
		user.setOrgid(object.getString("orgid"));
		user.setIndexpage(object.getString("indexpage"));
		user.setNodeid(object.getString("nodeid"));
	}
	/**
	 * ��ʾ �û��� ����
	 */
	public void showXML() {
		if ("login".equals(share.getShareString("login"))) {
			userName.setText(share.getShareString("userName"));
			userPwd.setText(share.getShareString("userPwd"));
			savePwd.setChecked(share.getShareBoolean("loginStatus"));
		}
	}
	/**
	 * ��ת�Ľ���
	 */
	public void initJump(boolean loginStatus) {
		if (loginStatus) {
			Intent intent = new Intent(context, MenuActivity.class);
			intent.putExtra("nodid", nodid);
			startActivity(intent);
			if (this.loginStatus) {
				shareXML(); // ��ס����
			} else {
				share.clearShare();
			}
			Toast.makeText(context, R.string.OK, Toast.LENGTH_SHORT).show(); // ��½�ɹ�
			LoginActivity.this.finish();
		}
	}

	/**
	 * �ֻ����� ������
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitMenu(context);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * �ֻ����� ������
	 */
	private void ExitMenu(Context context) {
		new AlertDialog.Builder(this).setTitle(R.string.ok_exit)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						LoginActivity.this.finish();
					}
				}).setNegativeButton(R.string.no, null).show();
	}
}
