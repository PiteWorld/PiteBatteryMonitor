package com.pite.batterymonitor.utils;

import java.util.List;

public class jsonOut {
	public static class TDataInEveryPage{

		public static class TBatteryByPage{

			public static class TData{

				public static class TEvents{

					private	Double	resistance;	/*55.5*/
					private	Integer	num;	/*1*/
					private	Double	r2;	/*135.3*/
					private	Integer	sOC;	/*98*/
					private	Integer	voltage;	/*37*/
					private	Integer	temperature;	/*31*/
					private	Double	c2;	/*0.7*/
					private	Integer	capacity;	/*85*/
					private	Integer	sOH;	/*0*/

					public void setResistance(Double value){
						this.resistance = value;
					}
					public Double getResistance(){
						return this.resistance;
					}

					public void setNum(Integer value){
						this.num = value;
					}
					public Integer getNum(){
						return this.num;
					}

					public void setR2(Double value){
						this.r2 = value;
					}
					public Double getR2(){
						return this.r2;
					}

					public void setSOC(Integer value){
						this.sOC = value;
					}
					public Integer getSOC(){
						return this.sOC;
					}

					public void setVoltage(Integer value){
						this.voltage = value;
					}
					public Integer getVoltage(){
						return this.voltage;
					}

					public void setTemperature(Integer value){
						this.temperature = value;
					}
					public Integer getTemperature(){
						return this.temperature;
					}

					public void setC2(Double value){
						this.c2 = value;
					}
					public Double getC2(){
						return this.c2;
					}

					public void setCapacity(Integer value){
						this.capacity = value;
					}
					public Integer getCapacity(){
						return this.capacity;
					}

					public void setSOH(Integer value){
						this.sOH = value;
					}
					public Integer getSOH(){
						return this.sOH;
					}

				}
				private	List<TEvents>	events;	/*List<TEvents>*/
				public void setEvents(List<TEvents> value){
					this.events = value;
				}
				public List<TEvents> getEvents(){
					return this.events;
				}

				private	String	time;	/*2015-08-30 21:48:21*/

				public void setTime(String value){
					this.time = value;
				}
				public String getTime(){
					return this.time;
				}

			}
			private	List<TData>	data;	/*List<TData>*/
			public void setData(List<TData> value){
				this.data = value;
			}
			public List<TData> getData(){
				return this.data;
			}

			private	Integer	recordCount;	/*12*/

			public void setRecordCount(Integer value){
				this.recordCount = value;
			}
			public Integer getRecordCount(){
				return this.recordCount;
			}

		}
		public static class TGroupByPage{

			public static class TData{

				private	String	time;	/*2015-08-30 21:48:21*/
				private	Double	voltage;	/*220.6*/
				private	Double	ampere;	/*0.3*/

				public void setTime(String value){
					this.time = value;
				}
				public String getTime(){
					return this.time;
				}

				public void setVoltage(Double value){
					this.voltage = value;
				}
				public Double getVoltage(){
					return this.voltage;
				}

				public void setAmpere(Double value){
					this.ampere = value;
				}
				public Double getAmpere(){
					return this.ampere;
				}

			}
			private	List<TData>	data;	/*List<TData>*/
			public void setData(List<TData> value){
				this.data = value;
			}
			public List<TData> getData(){
				return this.data;
			}

			private	Integer	recordCount;	/*12*/

			public void setRecordCount(Integer value){
				this.recordCount = value;
			}
			public Integer getRecordCount(){
				return this.recordCount;
			}

		}
		private	TBatteryByPage	batteryByPage;	/*TBatteryByPage*/
		private	TGroupByPage	groupByPage;	/*TGroupByPage*/

		public void setBatteryByPage(TBatteryByPage value){
			this.batteryByPage = value;
		}
		public TBatteryByPage getBatteryByPage(){
			return this.batteryByPage;
		}

		public void setGroupByPage(TGroupByPage value){
			this.groupByPage = value;
		}
		public TGroupByPage getGroupByPage(){
			return this.groupByPage;
		}

	}
	private	TDataInEveryPage	dataInEveryPage;	/*TDataInEveryPage*/

	public void setDataInEveryPage(TDataInEveryPage value){
		this.dataInEveryPage = value;
	}
	public TDataInEveryPage getDataInEveryPage(){
		return this.dataInEveryPage;
	}
}
