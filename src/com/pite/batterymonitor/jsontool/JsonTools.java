package com.pite.batterymonitor.jsontool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.pite.batterymonitor.utils.BatteryGroupUtils;

import android.util.Log;
/**
 * Json Gson数据解析
 */
public class JsonTools {
	public JsonTools() {
	}

	/**
	 * 
	 * @param json
	 *            String 数据 格式解析
	 * @param cls
	 * @return
	 */
	public static <T> T getStringJson(String json, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			Log.e("2", "数据解析异常：" + e);
		}
		return t;
	}

	/**
	 * 
	 * @param json
	 *            list<T> 数据格式 解析
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getListJson(String json, Class<T> cls) {
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

	public static <T> List<T> getlist(String json, Class<T> cls) {
		List<T> t = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			t = gson.fromJson(json, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			Log.e("2", "解析 异常：" + e);
		}
		return t;
	}

	/**
	 * list<Map<String,object>> 数据转换
	 * 
	 * @param json
	 * @return
	 */
	public static <T> List<Map<String, BatteryGroupUtils>> getListMapJson(String json) {
		List<Map<String, BatteryGroupUtils>> list = new ArrayList<Map<String, BatteryGroupUtils>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}

}
