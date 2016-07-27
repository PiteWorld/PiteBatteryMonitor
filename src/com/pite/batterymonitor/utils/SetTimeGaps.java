package com.pite.batterymonitor.utils;

import java.util.List;

/**
 * Ê±¼ä¼ä¸ô
 */
public class SetTimeGaps {
	private InterVal interVal;

	public void setInterVal(InterVal interVal) {
		this.interVal = interVal;
	}

	public InterVal getInterVal() {
		return this.interVal;
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

	}

}
