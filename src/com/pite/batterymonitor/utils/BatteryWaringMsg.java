package com.pite.batterymonitor.utils;

/**
 * �澯��Ϣ
 */
public class BatteryWaringMsg {
	//ID ����
	private int ID;
	//��ʾ��Ϣ
	private String alarmMsg;
	//�澯ʱ��
	private String Alarmtime;
	//���ֵ
	private String normical;
	//����id
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
