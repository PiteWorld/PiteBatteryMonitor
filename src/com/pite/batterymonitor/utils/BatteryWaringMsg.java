package com.pite.batterymonitor.utils;

/**
 * 告警信息
 */
public class BatteryWaringMsg {
	//ID 主键
	private int ID;
	//提示信息
	private String alarmMsg;
	//告警时间
	private String Alarmtime;
	//标称值
	private String normical;
	//处理id
	private String dealID;
	

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAlarmMsg() {
		return alarmMsg;
	}
	public void setAlarmMsg(String alarmMsg) {
		this.alarmMsg = alarmMsg;
	}
	public String getAlarmtime() {
		return Alarmtime;
	}
	public void setAlarmtime(String alarmtime) {
		Alarmtime = alarmtime;
	}
	public String getNormical() {
		return normical;
	}
	public void setNormical(String normical) {
		this.normical = normical;
	}
	public String getDealID() {
		return dealID;
	}
	public void setDealID(String dealID) {
		this.dealID = dealID;
	}
	@Override
	public String toString() {
		return "BatteryWaringMsg [ID=" + ID + ", alarmMsg=" + alarmMsg + ", Alarmtime=" + Alarmtime + ", normical="
				+ normical + ", dealID=" + dealID + "]";
	}
	

	
}
