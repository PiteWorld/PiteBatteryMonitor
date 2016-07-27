package com.pite.batterymonitor.utils;

public class HfJsonTime {
	public class DateStart {
		private int year;

		private int month;

		private int day;

		public void setYear(int year) {
			this.year = year;
		}

		public int getYear() {
			return this.year;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getMonth() {
			return this.month;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getDay() {
			return this.day;
		}

	}

	public class DateEnd {
		private int year;

		private int month;

		private int day;

		public void setYear(int year) {
			this.year = year;
		}

		public int getYear() {
			return this.year;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getMonth() {
			return this.month;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getDay() {
			return this.day;
		}

	}

	public class Root {
		private DateStart dateStart;

		private DateEnd dateEnd;

		private String timeInit;

		public void setDateStart(DateStart dateStart) {
			this.dateStart = dateStart;
		}

		public DateStart getDateStart() {
			return this.dateStart;
		}

		public void setDateEnd(DateEnd dateEnd) {
			this.dateEnd = dateEnd;
		}

		public DateEnd getDateEnd() {
			return this.dateEnd;
		}

		public void setTimeInit(String timeInit) {
			this.timeInit = timeInit;
		}

		public String getTimeInit() {
			return this.timeInit;
		}

	}
}
