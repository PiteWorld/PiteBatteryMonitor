package com.pite.batterymonitor.sharexml;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * SharedPreferences 保存
 */
public class ShareXML {
	private SharedPreferences shared;
	private Context context;

	public ShareXML(Context context) {
		this.context = context;
		shared = context.getSharedPreferences("setting", context.MODE_PRIVATE);
	}
	/**
	 * 添加 String
	 */
	public void addString(String name,String value){
		Editor editor = shared.edit();
		editor.putString(name, value);
		editor.commit();
	}
	/**
	 * 添加 Int
	 */
	public void addInt(String name,int value){
		Editor editor = shared.edit() ;
		editor.putInt(name, value);
		editor.commit();
	}
	/**
	 * 添加 boolean
	 */
	public void addBoolean(String name,boolean status){
		Editor editor = shared.edit() ;
		editor.putBoolean(name, status);
		editor.commit() ;
	}
	/**
	 * 获取 boolean
	 */
	public boolean getShareBoolean(String name){
		return shared.getBoolean(name,false);
	}
	/**
	 * 获取  String
	 * @param name
	 * @return
	 */
	public String getShareString(String name){
		return shared.getString(name,null);
	}
	/**
	 * 获取 int 
	 * @param name
	 * @return
	 */
	public int getShareInt(String name){
		return shared.getInt(name, 0);
	}
	/**
	 * 清空 SharedPreferences 数据
	 * @return
	 */
	public boolean clearShare(){
		Editor editor = shared.edit() ;
		editor.clear() ;
		return editor.commit();
	}
}
