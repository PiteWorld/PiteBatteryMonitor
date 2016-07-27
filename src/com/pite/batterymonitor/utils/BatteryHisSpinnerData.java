package com.pite.batterymonitor.utils;

import java.util.List;

public class BatteryHisSpinnerData {
	private DataByHead DataByHead;

	public void setDataByHead(DataByHead DataByHead) {
		this.DataByHead = DataByHead;
	}

	public DataByHead getDataByHead() {
		return this.DataByHead;
	}

	public class DataByHead {
		private Group Group;

		private Battery Battery;

		public void setGroup(Group Group) {
			this.Group = Group;
		}

		public Group getGroup() {
			return this.Group;
		}

		public void setBattery(Battery Battery) {
			this.Battery = Battery;
		}

		public Battery getBattery() {
			return this.Battery;
		}

	}

	public class Battery {
		private List<Data> Data;

		private int RecordCount;

		public void setData(List<Data> Data) {
			this.Data = Data;
		}

		public List<Data> getData() {
			return this.Data;
		}

		public void setRecordCount(int RecordCount) {
			this.RecordCount = RecordCount;
		}

		public int getRecordCount() {
			return this.RecordCount;
		}

	}

	public class Data {
		private int batterynum;

		private int soc;

		private String socColor;

		private int soh;

		private String sohColor;

		private int testCapacity;

		private String cColor;

		private double testvoltage;

		private String vColor;

		private double testresistance;

		private String rColor;

		private double testr2;

		private String r2Color;

		private int testc2;

		private String c2Color;

		private int temperature;

		private String tColor;

		public void setBatterynum(int batterynum) {
			this.batterynum = batterynum;
		}

		public int getBatterynum() {
			return this.batterynum;
		}

		public void setSoc(int soc) {
			this.soc = soc;
		}

		public int getSoc() {
			return this.soc;
		}

		public void setSocColor(String socColor) {
			this.socColor = socColor;
		}

		public String getSocColor() {
			return this.socColor;
		}

		public void setSoh(int soh) {
			this.soh = soh;
		}

		public int getSoh() {
			return this.soh;
		}

		public void setSohColor(String sohColor) {
			this.sohColor = sohColor;
		}

		public String getSohColor() {
			return this.sohColor;
		}

		public void setTestCapacity(int testCapacity) {
			this.testCapacity = testCapacity;
		}

		public int getTestCapacity() {
			return this.testCapacity;
		}

		public void setCColor(String cColor) {
			this.cColor = cColor;
		}

		public String getCColor() {
			return this.cColor;
		}

		public void setTestvoltage(double testvoltage) {
			this.testvoltage = testvoltage;
		}

		public double getTestvoltage() {
			return this.testvoltage;
		}

		public void setVColor(String vColor) {
			this.vColor = vColor;
		}

		public String getVColor() {
			return this.vColor;
		}

		public void setTestresistance(double testresistance) {
			this.testresistance = testresistance;
		}

		public double getTestresistance() {
			return this.testresistance;
		}

		public void setRColor(String rColor) {
			this.rColor = rColor;
		}

		public String getRColor() {
			return this.rColor;
		}

		public void setTestr2(double testr2) {
			this.testr2 = testr2;
		}

		public double getTestr2() {
			return this.testr2;
		}

		public void setR2Color(String r2Color) {
			this.r2Color = r2Color;
		}

		public String getR2Color() {
			return this.r2Color;
		}

		public void setTestc2(int testc2) {
			this.testc2 = testc2;
		}

		public int getTestc2() {
			return this.testc2;
		}

		public void setC2Color(String c2Color) {
			this.c2Color = c2Color;
		}

		public String getC2Color() {
			return this.c2Color;
		}

		public void setTemperature(int temperature) {
			this.temperature = temperature;
		}

		public int getTemperature() {
			return this.temperature;
		}

		public void setTColor(String tColor) {
			this.tColor = tColor;
		}

		public String getTColor() {
			return this.tColor;
		}

	}

	public class Group {
		private String nodename;

		private String groupname;

		private String batterytypeName;

		private int nominalR;

		private double vMin;

		private String VminColor;

		private int vnum;

		private double rMax;

		private String RmaxColor;

		private int rnum;

		private double testgroupvoltage;

		private String GroupVoltageColor;

		private double testgroupampere;

		private String status;

		private String gScolor;

		public void setNodename(String nodename) {
			this.nodename = nodename;
		}

		public String getNodename() {
			return this.nodename;
		}

		public void setGroupname(String groupname) {
			this.groupname = groupname;
		}

		public String getGroupname() {
			return this.groupname;
		}

		public void setBatterytypeName(String batterytypeName) {
			this.batterytypeName = batterytypeName;
		}

		public String getBatterytypeName() {
			return this.batterytypeName;
		}

		public void setNominalR(int nominalR) {
			this.nominalR = nominalR;
		}

		public int getNominalR() {
			return this.nominalR;
		}

		public void setVMin(double vMin) {
			this.vMin = vMin;
		}

		public double getVMin() {
			return this.vMin;
		}

		public void setVminColor(String VminColor) {
			this.VminColor = VminColor;
		}

		public String getVminColor() {
			return this.VminColor;
		}

		public void setVnum(int vnum) {
			this.vnum = vnum;
		}

		public int getVnum() {
			return this.vnum;
		}

		public void setRMax(double rMax) {
			this.rMax = rMax;
		}

		public double getRMax() {
			return this.rMax;
		}

		public void setRmaxColor(String RmaxColor) {
			this.RmaxColor = RmaxColor;
		}

		public String getRmaxColor() {
			return this.RmaxColor;
		}

		public void setRnum(int rnum) {
			this.rnum = rnum;
		}

		public int getRnum() {
			return this.rnum;
		}

		public void setTestgroupvoltage(double testgroupvoltage) {
			this.testgroupvoltage = testgroupvoltage;
		}

		public double getTestgroupvoltage() {
			return this.testgroupvoltage;
		}

		public void setGroupVoltageColor(String GroupVoltageColor) {
			this.GroupVoltageColor = GroupVoltageColor;
		}

		public String getGroupVoltageColor() {
			return this.GroupVoltageColor;
		}

		public void setTestgroupampere(double testgroupampere) {
			this.testgroupampere = testgroupampere;
		}

		public double getTestgroupampere() {
			return this.testgroupampere;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}

		public void setGScolor(String gScolor) {
			this.gScolor = gScolor;
		}

		public String getGScolor() {
			return this.gScolor;
		}

	}
}
