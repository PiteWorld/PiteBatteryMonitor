package com.pite.batterymonitor.utils;

import java.util.List;

public class SPTimeUtils {
	private Rang Rang;

	private Init Init;

	public void setRang(Rang Rang) {
		this.Rang = Rang;
	}

	public Rang getRang() {
		return this.Rang;
	}

	public void setInit(Init Init) {
		this.Init = Init;
	}

	public Init getInit() {
		return this.Init;
	}

	public class Rang {
		private List<Data> Data;

		private int Length;

		public void setData(List<Data> Data) {
			this.Data = Data;
		}

		public List<Data> getData() {
			return this.Data;
		}

		public void setLength(int Length) {
			this.Length = Length;
		}

		public int getLength() {
			return this.Length;
		}

	}

	public class Data {
		private String Time;

		private int TestID;

		public void setTime(String Time) {
			this.Time = Time;
		}

		public String getTime() {
			return this.Time;
		}

		public void setTestID(int TestID) {
			this.TestID = TestID;
		}

		public int getTestID() {
			return this.TestID;
		}

	}

	public class Init {
		private String Time;

		private int TestID;

		public void setTime(String Time) {
			this.Time = Time;
		}

		public String getTime() {
			return this.Time;
		}

		public void setTestID(int TestID) {
			this.TestID = TestID;
		}

		public int getTestID() {
			return this.TestID;
		}

	}

}
