package com.pite.batterymonitor.utils;

import java.text.DecimalFormat;
import java.text.Format;

import android.util.Log;

/**
 * 直方图 信息
 */
public class BatteryInformation {
	private String groupid; // 组ID
	private String batteryID; // 电池ID
	private String batterynum; // 电池号
	private String socColor; // soc 颜色
	private String sohColor; // soh颜色

	private String testvoltage; // 电压

	private String vColor; // 电压 颜色

	private String testCapacity; // C

	private String cColor; // C颜色

	private String testresistance; // R

	private String rColor; // R颜色

	private String testr2; // R2

	private String r2Color; // R2颜色

	private float testc2; // C2

	private String c2Color; // C2颜色

	private String temperature; // T

	private String tColor; // T颜色

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getGroupid() {
		return this.groupid;
	}

	public void setBatteryID(String batteryID) {
		this.batteryID = batteryID;
	}

	public String getBatteryID() {
		return this.batteryID;
	}

	public void setBatterynum(String batterynum) {
		this.batterynum = batterynum;
	}

	public String getBatterynum() {
		return this.batterynum;
	}

	public void setSocColor(String socColor) {
		this.socColor = socColor;
	}

	public String getSocColor() {
		return this.socColor;
	}

	public void setSohColor(String sohColor) {
		this.sohColor = sohColor;
	}

	public String getSohColor() {
		return this.sohColor;
	}

	public void setTestvoltage(String testvoltage) {
		this.testvoltage = testvoltage;
	}

	public String getTestvoltage() {
		return this.testvoltage;
	}

	public void setVColor(String vColor) {
		this.vColor = vColor;
	}

	public String getVColor() {
		return this.vColor;
	}

	public void setTestCapacity(String testCapacity) {
		this.testCapacity = testCapacity;
	}

	public String getTestCapacity() {
		return this.testCapacity;
	}

	public void setCColor(String cColor) {
		this.cColor = cColor;
	}

	public String getCColor() {
		return this.cColor;
	}

	public void setTestresistance(String testresistance) {
		this.testresistance = testresistance;
	}

	public String getTestresistance() {
		return this.testresistance;
	}

	public void setRColor(String rColor) {
		this.rColor = rColor;
	}

	public String getRColor() {
		return this.rColor;
	}

	public void setTestr2(String testr2) {
		this.testr2 = testr2;
	}

	public String getTestr2() {
		return this.testr2;
	}

	public void setR2Color(String r2Color) {
		this.r2Color = r2Color;
	}

	public String getR2Color() {
		return this.r2Color;
	}

	public void setTestc2(float testc2) {
		this.testc2 = testc2;
	}

	public float getTestc2() {
		return this.testc2;
	}

	public void setC2Color(String c2Color) {
		this.c2Color = c2Color;
	}

	public String getC2Color() {
		return this.c2Color;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setTColor(String tColor) {
		this.tColor = tColor;
	}

	public String getTColor() {
		return this.tColor;
	}
}
