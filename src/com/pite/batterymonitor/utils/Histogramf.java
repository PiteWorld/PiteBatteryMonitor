package com.pite.batterymonitor.utils;

import java.util.List;

public class Histogramf {
	private InterVal interVal;

	private List<TitleData> TitleData;

	private List<HisData> HisData;

	private int TestID;

	public void setInterVal(InterVal interVal) {
		this.interVal = interVal;
	}

	public InterVal getInterVal() {
		return this.interVal;
	}

	public void setTitleData(List<TitleData> TitleData) {
		this.TitleData = TitleData;
	}

	public List<TitleData> getTitleData() {
		return this.TitleData;
	}

	public void setHisData(List<HisData> HisData) {
		this.HisData = HisData;
	}

	public List<HisData> getHisData() {
		return this.HisData;
	}

	public void setTestID(int TestID) {
		this.TestID = TestID;
	}

	public int getTestID() {
		return this.TestID;
	}

	public class InterVal {
		private List<Record> Record;

		private int Default;

		public void setRecord(List<Record> Record) {
			this.Record = Record;
		}

		public List<Record> getRecord() {
			return this.Record;
		}

		public void setDefault(int Default) {
			this.Default = Default;
		}

		public int getDefault() {
			return this.Default;
		}

		public class Record {
			private String Name;

			private int Value;

			public void setName(String Name) {
				this.Name = Name;
			}

			public String getName() {
				return this.Name;
			}

			public void setValue(int Value) {
				this.Value = Value;
			}

			public int getValue() {
				return this.Value;
			}
		}
	}

	public class TitleData {
		private int groupid;

		private String nodename;

		private String groupname;

		private String batterytypeName;

		private String nominalR;

		private String U_Str;

		private String U_StrColor;

		private String Gcount;

		private String I;

		private String status;

		private String gScolor;

		private String workStatus;

		private String wScolor;

		private String Automation;

		public void setGroupid(int groupid) {
			this.groupid = groupid;
		}

		public int getGroupid() {
			return this.groupid;
		}

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

		public void setNominalR(String nominalR) {
			this.nominalR = nominalR;
		}

		public String getNominalR() {
			return this.nominalR;
		}

		public void setU_Str(String U_Str) {
			this.U_Str = U_Str;
		}

		public String getU_Str() {
			return this.U_Str;
		}

		public void setU_StrColor(String U_StrColor) {
			this.U_StrColor = U_StrColor;
		}

		public String getU_StrColor() {
			return this.U_StrColor;
		}

		public void setGcount(String Gcount) {
			this.Gcount = Gcount;
		}

		public String getGcount() {
			return this.Gcount;
		}

		public void setI(String I) {
			this.I = I;
		}

		public String getI() {
			return this.I;
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

		public void setWorkStatus(String workStatus) {
			this.workStatus = workStatus;
		}

		public String getWorkStatus() {
			return this.workStatus;
		}

		public void setWScolor(String wScolor) {
			this.wScolor = wScolor;
		}

		public String getWScolor() {
			return this.wScolor;
		}

		public void setAutomation(String Automation) {
			this.Automation = Automation;
		}

		public String getAutomation() {
			return this.Automation;
		}

	}

	public class HisData {
		private int groupid;

		private int batteryID;

		private int batterynum;

		private String R;

		private String RColor;

		private String C;

		private String CColor;

		private String U;

		private String UColor;

		private String R2;

		private String R2Color;

		private String C2;

		private String C2Color;

		private String T;

		private String TColor;

		public void setGroupid(int groupid) {
			this.groupid = groupid;
		}

		public int getGroupid() {
			return this.groupid;
		}

		public void setBatteryID(int batteryID) {
			this.batteryID = batteryID;
		}

		public int getBatteryID() {
			return this.batteryID;
		}

		public void setBatterynum(int batterynum) {
			this.batterynum = batterynum;
		}

		public int getBatterynum() {
			return this.batterynum;
		}

		public void setR(String R) {
			this.R = R;
		}

		public String getR() {
			return this.R;
		}

		public void setRColor(String RColor) {
			this.RColor = RColor;
		}

		public String getRColor() {
			return this.RColor;
		}

		public void setC(String C) {
			this.C = C;
		}

		public String getC() {
			return this.C;
		}

		public void setCColor(String CColor) {
			this.CColor = CColor;
		}

		public String getCColor() {
			return this.CColor;
		}

		public void setU(String U) {
			this.U = U;
		}

		public String getU() {
			return this.U;
		}

		public void setUColor(String UColor) {
			this.UColor = UColor;
		}

		public String getUColor() {
			return this.UColor;
		}

		public void setR2(String R2) {
			this.R2 = R2;
		}

		public String getR2() {
			return this.R2;
		}

		public void setR2Color(String R2Color) {
			this.R2Color = R2Color;
		}

		public String getR2Color() {
			return this.R2Color;
		}

		public void setC2(String C2) {
			this.C2 = C2;
		}

		public String getC2() {
			return this.C2;
		}

		public void setC2Color(String C2Color) {
			this.C2Color = C2Color;
		}

		public String getC2Color() {
			return this.C2Color;
		}

		public void setT(String T) {
			this.T = T;
		}

		public String getT() {
			return this.T;
		}

		public void setTColor(String TColor) {
			this.TColor = TColor;
		}

		public String getTColor() {
			return this.TColor;
		}
	}
}