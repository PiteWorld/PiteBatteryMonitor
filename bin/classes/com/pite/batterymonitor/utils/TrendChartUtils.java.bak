package com.pite.batterymonitor.utils;

import java.util.List;

public class TrendChartUtils {
	public DataInEveryPage DataInEveryPage;

	public void setDataInEveryPage(DataInEveryPage DataInEveryPage) {
		this.DataInEveryPage = DataInEveryPage;
	}

	public DataInEveryPage getDataInEveryPage() {
		return this.DataInEveryPage;
	}

	public class GroupByPage {
		public List<Data> Data;

		public int RecordCount;

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

		public class Data {
			public String Time;

			public String Voltage;

			public String Ampere;

			public void setTime(String Time) {
				this.Time = Time;
			}

			public String getTime() {
				return this.Time;
			}

			public void setVoltage(String Voltage) {
				this.Voltage = Voltage;
			}

			public String getVoltage() {
				return this.Voltage;
			}

			public void setAmpere(String Ampere) {
				this.Ampere = Ampere;
			}

			public String getAmpere() {
				return this.Ampere;
			}

		}

	}

	public class DataInEveryPage {
		public GroupByPage GroupByPage;

		public BatteryByPage BatteryByPage;

		public void setGroupByPage(GroupByPage GroupByPage) {
			this.GroupByPage = GroupByPage;
		}

		public GroupByPage getGroupByPage() {
			return this.GroupByPage;
		}

		public void setBatteryByPage(BatteryByPage BatteryByPage) {
			this.BatteryByPage = BatteryByPage;
		}

		public BatteryByPage getBatteryByPage() {
			return this.BatteryByPage;
		}

	}

	public class BatteryByPage {
		public List<Data> Data;
		//
		public int RecordCount;

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

		public class Data {
			public String TestId;
			public String Time;

			public List<Events> Events;
			
			public void setTestId(String TestId) {
				this.TestId = TestId;
			}

			public String getTestId() {
				return this.TestId;
			}
			public void setTime(String Time) {
				this.Time = Time;
			}

			public String getTime() {
				return this.Time;
			}

			public void setEvents(List<Events> Events) {
				this.Events = Events;
			}

			public List<Events> getEvents() {
				return this.Events;
			}
			//
		}

		public class Events {
			public int Num;

			private String SOC;

			private String SOH;

			private String Voltage;

			private String Resistance;

			private String R2;

			private String Capacity;

			private String C2;

			private String Temperature;

			public void setNum(int Num) {
				this.Num = Num;
			}

			public int getNum() {
				return this.Num;
			}

			public void setSOC(String SOC) {
				this.SOC = SOC;
			}

			public String getSOC() {
				return this.SOC;
			}

			public void setSOH(String SOH) {
				this.SOH = SOH;
			}

			public String getSOH() {
				return this.SOH;
			}

			public void setVoltage(String Voltage) {
				this.Voltage = Voltage;
			}

			public String getVoltage() {
				return this.Voltage;
			}

			public void setResistance(String Resistance) {
				this.Resistance = Resistance;
			}

			public String getResistance() {
				return this.Resistance;
			}

			public void setR2(String R2) {
				this.R2 = R2;
			}

			public String getR2() {
				return this.R2;
			}

			public void setCapacity(String Capacity) {
				this.Capacity = Capacity;
			}

			public String getCapacity() {
				return this.Capacity;
			}

			public void setC2(String C2) {
				this.C2 = C2;
			}

			public String getC2() {
				return this.C2;
			}

			public void setTemperature(String Temperature) {
				this.Temperature = Temperature;
			}

			public String getTemperature() {
				return this.Temperature;
			}
		}
	}
}
