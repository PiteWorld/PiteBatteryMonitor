package com.pite.batterymonitor.utils;

import com.pite.batterymonitor.fragment.HisDataFragment;
import com.pite.batterymonitor.fragment.HistogramFragment;
import com.pite.batterymonitor.fragment.TrendDataFragment;
import com.pite.batterymonitor.fragment.TrendFragment;
import com.pite.batterymonitor.TimeMonitorActivity;

public class JsonId {
	public static String Time;
	public static String TestID;
	public static int numPostion = -1;
	public static int timePostion = 0;
	public static int timeGapsPostion = 0;
	public static boolean isGapsPostion = false;
	public static HistogramFragment his = null;
	public static HisDataFragment hisdata = null;
	public static TrendFragment trend = null;
	public static TrendDataFragment trenddata = null;
	public static TimeMonitorActivity tm = null;
	//���ӱ��
	public static int flage = 0;
	public static int getFlage() {
		return flage;
	}

	public static void setFlage(int flage) {
		JsonId.flage = flage;
	}

	public static TimeMonitorActivity getTm() {
		return tm;
	}

	public static void setTm(TimeMonitorActivity tm) {
		JsonId.tm = tm;
	}

	public static HisDataFragment getHisdata() {
		return hisdata;
	}

	public static void setHisdata(HisDataFragment hisdata) {
		JsonId.hisdata = hisdata;
	}

	public static TrendFragment getTrend() {
		return trend;
	}

	public static void setTrend(TrendFragment trend) {
		JsonId.trend = trend;
	}

	public static TrendDataFragment getTrenddata() {
		return trenddata;
	}

	public static void setTrenddata(TrendDataFragment trenddata) {
		JsonId.trenddata = trenddata;
	}

	public static HistogramFragment getHis() {
		return his;
	}

	public static void setHis(HistogramFragment his) {
		JsonId.his = his;
	}

	public static boolean isGapsPostion() {
		return isGapsPostion;
	}

	public static void setGapsPostion(boolean isGapsPostion) {
		JsonId.isGapsPostion = isGapsPostion;
	}

	public static int getTimeGapsPostion() {
		return timeGapsPostion;
	}

	public static void setTimeGapsPostion(int timeGapsPostion) {
		JsonId.timeGapsPostion = timeGapsPostion;
	}

	public static int getNumPostion() {
		return numPostion;
	}

	public static void setNumPostion(int numPostion) {
		JsonId.numPostion = numPostion;
	}

	public static int getTimePostion() {
		return timePostion;
	}

	public static void setTimePostion(int timePostion) {
		JsonId.timePostion = timePostion;
	}

	public int getPostion() {
		return numPostion;
	}

	public void setPostion(int postion) {
		this.numPostion = postion;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getTestID() {
		return TestID;
	}

	public void setTestID(String testID) {
		TestID = testID;
	}

}
