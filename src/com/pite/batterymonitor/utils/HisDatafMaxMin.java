package com.pite.batterymonitor.utils;

import java.util.List;

public class HisDatafMaxMin {
	private List<TitleData> TitleData;

	public void setTitleData(List<TitleData> TitleData) {
		this.TitleData = TitleData;
	}

	public List<TitleData> getTitleData() {
		return this.TitleData;
	}

	public class TitleData {
		private List<RMax> RMax;

		private List<VMax> VMax;

		private List<VMin> VMin;

		public void setRMax(List<RMax> RMax) {
			this.RMax = RMax;
		}

		public List<RMax> getRMax() {
			return this.RMax;
		}

		public void setVMax(List<VMax> VMax) {
			this.VMax = VMax;
		}

		public List<VMax> getVMax() {
			return this.VMax;
		}

		public void setVMin(List<VMin> VMin) {
			this.VMin = VMin;
		}

		public List<VMin> getVMin() {
			return this.VMin;
		}

		public class RMax {
			private String RH0;

			private String RH1;

			private String RH2;

			private String RH3;

			private String RH4;

			private String RH5;

			public void setRH0(String RH0) {
				this.RH0 = RH0;
			}

			public String getRH0() {
				return this.RH0;
			}

			public void setRH1(String RH1) {
				this.RH1 = RH1;
			}

			public String getRH1() {
				return this.RH1;
			}

			public void setRH2(String RH2) {
				this.RH2 = RH2;
			}

			public String getRH2() {
				return this.RH2;
			}

			public void setRH3(String RH3) {
				this.RH3 = RH3;
			}

			public String getRH3() {
				return this.RH3;
			}

			public void setRH4(String RH4) {
				this.RH4 = RH4;
			}

			public String getRH4() {
				return this.RH4;
			}

			public void setRH5(String RH5) {
				this.RH5 = RH5;
			}

			public String getRH5() {
				return this.RH5;
			}

		}

		public class VMax {
			private String VH0;

			private String VH1;

			private String VH2;

			public void setVH0(String VH0) {
				this.VH0 = VH0;
			}

			public String getVH0() {
				return this.VH0;
			}

			public void setVH1(String VH1) {
				this.VH1 = VH1;
			}

			public String getVH1() {
				return this.VH1;
			}

			public void setVH2(String VH2) {
				this.VH2 = VH2;
			}

			public String getVH2() {
				return this.VH2;
			}

		}

		public class VMin {
			private String VL0;

			private String VL1;

			private String VL2;

			public void setVL0(String VL0) {
				this.VL0 = VL0;
			}

			public String getVL0() {
				return this.VL0;
			}

			public void setVL1(String VL1) {
				this.VL1 = VL1;
			}

			public String getVL1() {
				return this.VL1;
			}

			public void setVL2(String VL2) {
				this.VL2 = VL2;
			}

			public String getVL2() {
				return this.VL2;
			}

		}
	}
}