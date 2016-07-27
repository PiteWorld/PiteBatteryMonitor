package com.pite.batterymonitor.utils;

import java.util.Date;

public class PCSInfos {
	// 表id
	private int id;
	// 组号
	private int groupid;
	// 测试头信息
	private int testheadid;
	// 测试时间
	private String testtime;
	// 公司编号
	private int companyNo;
	// 开发类型
	private int devType;
	// 开发地址
	private int devAddress;
	
	// 市电输入U相电压6000
	private String mainsspplyinputUV;
	// 市电输入V相电压6001
	private String mainsspplyinputVV;
	// 市电输入W相电压6002
	private String mainsspplyinputWV;
	// 市电输入频率6003
	private String mainsspplyinputFrequency;
	
	// 旁路输入U相电压6004
	private String bypassspplyinputUV;
	// 旁路输入V相电压6005
	private String bypassspplyinputVV;
	// 旁路输入W相电压6006
	private String bypassspplyinputWV;
	// 旁路输入频率6007
	private String bypassspplyinputFrequency;
	
	// 逆变器电池电压
	private int inverterbatteryV;
	// 逆变器电池电流
	private String inverterbatteryI;
	// 逆变器电池温度
	private int inverterbatteryT;
	// 电池充放电状态
	private int inverterbatteryCD;
	
	
	// 系统输出U相电压1
	private String systemoutputUV;
	// 系统输出v相电压2
	private String systemoutputVV;
	// 系统输出W相电压3
	private String systemoutputWV;
	// 系统输出U相电流4
	private String systemoutputUI;
	// 系统输出V相电流5
	private String systemoutputVI;
	// 系统输出W相电流6
	private String systemoutputWI;
	// 系统输出U相负载百分比7
	private String systemoutputLoadUP;
	// 系统输出V相负载百分比8
	private String systemoutputLoadVP;
	// 系统输出W相负载百分比9
	private String systemoutputLoadWP;
	// 系统输出U相有功功率10
	private String systemoutputUYP;
	// 系统输出V相有功功率11
	private String systemoutputVYP;
	// 系统输出W相有功功率12
	private String systemoutputWYP;
	// 系统输出U相视在功率13
	private String systemoutputUSP;
	// 系统输出V相视在功率14
	private String systemoutputVSP;
	// 系统输出W相视在功率15
	private String systemoutputWSP;
	// 系统输出U相PF值16
	private String systemoutputUPF;
	// 系统输出V相PF值17
	private String systemoutputVPF;
	// 系统输出W相PF值18
	private String systemoutputWPF;
	// 系统输出频率19
	private String systemoutputFrequency;
	
	
	// 电池状态
	private int batterystate;
	// 电池状态
	private String batteryTemperature;
	// 负载状态
	private int loadstate;
	// 输出状态
	private int outputstate;
	// 机内温度
	private int temperatureinmachine;
	
	
	// 储能运行状态
	private int batteryoperatingstate;
	// 当前电价类型
	private int currentpricetype;
	// 当前电价
	private String currentprice;
	// 输入功率（低字在前
	private String inputpower;
	// 输出功率（低字在前）
	private String outputpower;
	// 电池电压
	private String batteryV;
	// 电池电流
	private String batteryI;
	// 6044 累计输入总电量（低字在前）
	private String inputpowersum;
	// '累计输入总电费（低字在前'
	private String inputpowerfee;
	// 累计输出总电量（低字在前）
	private String outputpowersum;
	// 累计输出总电费（低字在前）
	private String outputpowerfee;
	// 累计收益（低字在前）
	private String profitsum;
	
	// 产品型号
	private String producttype;
	
	// 单并机状态
	private int parallelstate;
	// 输入相序
	private int inputphasestate;
	// 旁路状态
	private int bypassstate;
	// 整流器状态
	private int inverterstate;
	// 逆变器开关机状态
	private int inverterswitchstate;
	// 逆变器运行状态
	private int inverteroperatingstate;
	// UPS温度状态
	private int UPStemperaturestate;
	// 电池极性状态
	private int batterypolestate;
	// 风机状态
	private int fanstate;
	// 并机线状态
	private int parallelinestate;
	// 保险丝状态
	private int fusestate;
	// UPS通讯故障
	private int UPScomstate;
	// 储能运行状态
	private String runState;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getTestheadid() {
		return testheadid;
	}
	public void setTestheadid(int testheadid) {
		this.testheadid = testheadid;
	}
	public String getTesttime() {
		return testtime;
	}
	public void setTesttime(String testtime) {
		this.testtime = testtime;
	}
	public int getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(int companyNo) {
		this.companyNo = companyNo;
	}
	public int getDevType() {
		return devType;
	}
	public void setDevType(int devType) {
		this.devType = devType;
	}
	public int getDevAddress() {
		return devAddress;
	}
	public void setDevAddress(int devAddress) {
		this.devAddress = devAddress;
	}
	public String getMainsspplyinputUV() {
		return mainsspplyinputUV;
	}
	public void setMainsspplyinputUV(String mainsspplyinputUV) {
		this.mainsspplyinputUV = mainsspplyinputUV;
	}
	public String getMainsspplyinputVV() {
		return mainsspplyinputVV;
	}
	public void setMainsspplyinputVV(String mainsspplyinputVV) {
		this.mainsspplyinputVV = mainsspplyinputVV;
	}
	public String getMainsspplyinputWV() {
		return mainsspplyinputWV;
	}
	public void setMainsspplyinputWV(String mainsspplyinputWV) {
		this.mainsspplyinputWV = mainsspplyinputWV;
	}
	public String getMainsspplyinputFrequency() {
		return mainsspplyinputFrequency;
	}
	public void setMainsspplyinputFrequency(String mainsspplyinputFrequency) {
		this.mainsspplyinputFrequency = mainsspplyinputFrequency;
	}
	public String getBypassspplyinputUV() {
		return bypassspplyinputUV;
	}
	public void setBypassspplyinputUV(String bypassspplyinputUV) {
		this.bypassspplyinputUV = bypassspplyinputUV;
	}
	public String getBypassspplyinputVV() {
		return bypassspplyinputVV;
	}
	public void setBypassspplyinputVV(String bypassspplyinputVV) {
		this.bypassspplyinputVV = bypassspplyinputVV;
	}
	public String getBypassspplyinputWV() {
		return bypassspplyinputWV;
	}
	public void setBypassspplyinputWV(String bypassspplyinputWV) {
		this.bypassspplyinputWV = bypassspplyinputWV;
	}
	public String getBypassspplyinputFrequency() {
		return bypassspplyinputFrequency;
	}
	public void setBypassspplyinputFrequency(String bypassspplyinputFrequency) {
		this.bypassspplyinputFrequency = bypassspplyinputFrequency;
	}
	public int getInverterbatteryV() {
		return inverterbatteryV;
	}
	public void setInverterbatteryV(int inverterbatteryV) {
		this.inverterbatteryV = inverterbatteryV;
	}
	public String getInverterbatteryI() {
		return inverterbatteryI;
	}
	public void setInverterbatteryI(String inverterbatteryI) {
		this.inverterbatteryI = inverterbatteryI;
	}
	public int getInverterbatteryT() {
		return inverterbatteryT;
	}
	public void setInverterbatteryT(int inverterbatteryT) {
		this.inverterbatteryT = inverterbatteryT;
	}
	public int getInverterbatteryCD() {
		return inverterbatteryCD;
	}
	public void setInverterbatteryCD(int inverterbatteryCD) {
		this.inverterbatteryCD = inverterbatteryCD;
	}
	public String getSystemoutputUV() {
		return systemoutputUV;
	}
	public void setSystemoutputUV(String systemoutputUV) {
		this.systemoutputUV = systemoutputUV;
	}
	public String getSystemoutputVV() {
		return systemoutputVV;
	}
	public void setSystemoutputVV(String systemoutputVV) {
		this.systemoutputVV = systemoutputVV;
	}
	public String getSystemoutputWV() {
		return systemoutputWV;
	}
	public void setSystemoutputWV(String systemoutputWV) {
		this.systemoutputWV = systemoutputWV;
	}
	public String getSystemoutputUI() {
		return systemoutputUI;
	}
	public void setSystemoutputUI(String systemoutputUI) {
		this.systemoutputUI = systemoutputUI;
	}
	public String getSystemoutputVI() {
		return systemoutputVI;
	}
	public void setSystemoutputVI(String systemoutputVI) {
		this.systemoutputVI = systemoutputVI;
	}
	public String getSystemoutputWI() {
		return systemoutputWI;
	}
	public void setSystemoutputWI(String systemoutputWI) {
		this.systemoutputWI = systemoutputWI;
	}
	public String getSystemoutputLoadUP() {
		return systemoutputLoadUP;
	}
	public void setSystemoutputLoadUP(String systemoutputLoadUP) {
		this.systemoutputLoadUP = systemoutputLoadUP;
	}
	public String getSystemoutputLoadVP() {
		return systemoutputLoadVP;
	}
	public void setSystemoutputLoadVP(String systemoutputLoadVP) {
		this.systemoutputLoadVP = systemoutputLoadVP;
	}
	public String getSystemoutputLoadWP() {
		return systemoutputLoadWP;
	}
	public void setSystemoutputLoadWP(String systemoutputLoadWP) {
		this.systemoutputLoadWP = systemoutputLoadWP;
	}
	public String getSystemoutputUYP() {
		return systemoutputUYP;
	}
	public void setSystemoutputUYP(String systemoutputUYP) {
		this.systemoutputUYP = systemoutputUYP;
	}
	public String getSystemoutputVYP() {
		return systemoutputVYP;
	}
	public void setSystemoutputVYP(String systemoutputVYP) {
		this.systemoutputVYP = systemoutputVYP;
	}
	public String getSystemoutputWYP() {
		return systemoutputWYP;
	}
	public void setSystemoutputWYP(String systemoutputWYP) {
		this.systemoutputWYP = systemoutputWYP;
	}
	public String getSystemoutputUSP() {
		return systemoutputUSP;
	}
	public void setSystemoutputUSP(String systemoutputUSP) {
		this.systemoutputUSP = systemoutputUSP;
	}
	public String getSystemoutputVSP() {
		return systemoutputVSP;
	}
	public void setSystemoutputVSP(String systemoutputVSP) {
		this.systemoutputVSP = systemoutputVSP;
	}
	public String getSystemoutputWSP() {
		return systemoutputWSP;
	}
	public void setSystemoutputWSP(String systemoutputWSP) {
		this.systemoutputWSP = systemoutputWSP;
	}
	public String getSystemoutputUPF() {
		return systemoutputUPF;
	}
	public void setSystemoutputUPF(String systemoutputUPF) {
		this.systemoutputUPF = systemoutputUPF;
	}
	public String getSystemoutputVPF() {
		return systemoutputVPF;
	}
	public void setSystemoutputVPF(String systemoutputVPF) {
		this.systemoutputVPF = systemoutputVPF;
	}
	public String getSystemoutputWPF() {
		return systemoutputWPF;
	}
	public void setSystemoutputWPF(String systemoutputWPF) {
		this.systemoutputWPF = systemoutputWPF;
	}
	public String getSystemoutputFrequency() {
		return systemoutputFrequency;
	}
	public void setSystemoutputFrequency(String systemoutputFrequency) {
		this.systemoutputFrequency = systemoutputFrequency;
	}
	public int getBatterystate() {
		return batterystate;
	}
	public void setBatterystate(int batterystate) {
		this.batterystate = batterystate;
	}
	public String getBatteryTemperature() {
		return batteryTemperature;
	}
	public void setBatteryTemperature(String batteryTemperature) {
		this.batteryTemperature = batteryTemperature;
	}
	public int getLoadstate() {
		return loadstate;
	}
	public void setLoadstate(int loadstate) {
		this.loadstate = loadstate;
	}
	public int getOutputstate() {
		return outputstate;
	}
	public void setOutputstate(int outputstate) {
		this.outputstate = outputstate;
	}
	public int getTemperatureinmachine() {
		return temperatureinmachine;
	}
	public void setTemperatureinmachine(int temperatureinmachine) {
		this.temperatureinmachine = temperatureinmachine;
	}
	public int getBatteryoperatingstate() {
		return batteryoperatingstate;
	}
	public void setBatteryoperatingstate(int batteryoperatingstate) {
		this.batteryoperatingstate = batteryoperatingstate;
	}
	public int getCurrentpricetype() {
		return currentpricetype;
	}
	public void setCurrentpricetype(int currentpricetype) {
		this.currentpricetype = currentpricetype;
	}
	public String getCurrentprice() {
		return currentprice;
	}
	public void setCurrentprice(String currentprice) {
		this.currentprice = currentprice;
	}
	public String getInputpower() {
		return inputpower;
	}
	public void setInputpower(String inputpower) {
		this.inputpower = inputpower;
	}
	public String getOutputpower() {
		return outputpower;
	}
	public void setOutputpower(String outputpower) {
		this.outputpower = outputpower;
	}
	public String getBatteryV() {
		return batteryV;
	}
	public void setBatteryV(String batteryV) {
		this.batteryV = batteryV;
	}
	public String getBatteryI() {
		return batteryI;
	}
	public void setBatteryI(String batteryI) {
		this.batteryI = batteryI;
	}
	public String getInputpowersum() {
		return inputpowersum;
	}
	public void setInputpowersum(String inputpowersum) {
		this.inputpowersum = inputpowersum;
	}
	public String getInputpowerfee() {
		return inputpowerfee;
	}
	public void setInputpowerfee(String inputpowerfee) {
		this.inputpowerfee = inputpowerfee;
	}
	public String getOutputpowersum() {
		return outputpowersum;
	}
	public void setOutputpowersum(String outputpowersum) {
		this.outputpowersum = outputpowersum;
	}
	public String getOutputpowerfee() {
		return outputpowerfee;
	}
	public void setOutputpowerfee(String outputpowerfee) {
		this.outputpowerfee = outputpowerfee;
	}
	public String getProfitsum() {
		return profitsum;
	}
	public void setProfitsum(String profitsum) {
		this.profitsum = profitsum;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	public int getParallelstate() {
		return parallelstate;
	}
	public void setParallelstate(int parallelstate) {
		this.parallelstate = parallelstate;
	}
	public int getInputphasestate() {
		return inputphasestate;
	}
	public void setInputphasestate(int inputphasestate) {
		this.inputphasestate = inputphasestate;
	}
	public int getBypassstate() {
		return bypassstate;
	}
	public void setBypassstate(int bypassstate) {
		this.bypassstate = bypassstate;
	}
	public int getInverterstate() {
		return inverterstate;
	}
	public void setInverterstate(int inverterstate) {
		this.inverterstate = inverterstate;
	}
	public int getInverterswitchstate() {
		return inverterswitchstate;
	}
	public void setInverterswitchstate(int inverterswitchstate) {
		this.inverterswitchstate = inverterswitchstate;
	}
	public int getInverteroperatingstate() {
		return inverteroperatingstate;
	}
	public void setInverteroperatingstate(int inverteroperatingstate) {
		this.inverteroperatingstate = inverteroperatingstate;
	}
	public int getUPStemperaturestate() {
		return UPStemperaturestate;
	}
	public void setUPStemperaturestate(int uPStemperaturestate) {
		UPStemperaturestate = uPStemperaturestate;
	}
	public int getBatterypolestate() {
		return batterypolestate;
	}
	public void setBatterypolestate(int batterypolestate) {
		this.batterypolestate = batterypolestate;
	}
	public int getFanstate() {
		return fanstate;
	}
	public void setFanstate(int fanstate) {
		this.fanstate = fanstate;
	}
	public int getParallelinestate() {
		return parallelinestate;
	}
	public void setParallelinestate(int parallelinestate) {
		this.parallelinestate = parallelinestate;
	}
	public int getFusestate() {
		return fusestate;
	}
	public void setFusestate(int fusestate) {
		this.fusestate = fusestate;
	}
	public int getUPScomstate() {
		return UPScomstate;
	}
	public void setUPScomstate(int uPScomstate) {
		UPScomstate = uPScomstate;
	}
	public String getRunState() {
		return runState;
	}
	public void setRunState(String runState) {
		this.runState = runState;
	}
	@Override
	public String toString() {
		return "PCSInfos [id=" + id + ", groupid=" + groupid + ", testheadid=" + testheadid + ", testtime=" + testtime
				+ ", companyNo=" + companyNo + ", devType=" + devType + ", devAddress=" + devAddress
				+ ", mainsspplyinputUV=" + mainsspplyinputUV + ", mainsspplyinputVV=" + mainsspplyinputVV
				+ ", mainsspplyinputWV=" + mainsspplyinputWV + ", mainsspplyinputFrequency=" + mainsspplyinputFrequency
				+ ", bypassspplyinputUV=" + bypassspplyinputUV + ", bypassspplyinputVV=" + bypassspplyinputVV
				+ ", bypassspplyinputWV=" + bypassspplyinputWV + ", bypassspplyinputFrequency="
				+ bypassspplyinputFrequency + ", inverterbatteryV=" + inverterbatteryV + ", inverterbatteryI="
				+ inverterbatteryI + ", inverterbatteryT=" + inverterbatteryT + ", inverterbatteryCD="
				+ inverterbatteryCD + ", systemoutputUV=" + systemoutputUV + ", systemoutputVV=" + systemoutputVV
				+ ", systemoutputWV=" + systemoutputWV + ", systemoutputUI=" + systemoutputUI + ", systemoutputVI="
				+ systemoutputVI + ", systemoutputWI=" + systemoutputWI + ", systemoutputLoadUP=" + systemoutputLoadUP
				+ ", systemoutputLoadVP=" + systemoutputLoadVP + ", systemoutputLoadWP=" + systemoutputLoadWP
				+ ", systemoutputUYP=" + systemoutputUYP + ", systemoutputVYP=" + systemoutputVYP + ", systemoutputWYP="
				+ systemoutputWYP + ", systemoutputUSP=" + systemoutputUSP + ", systemoutputVSP=" + systemoutputVSP
				+ ", systemoutputWSP=" + systemoutputWSP + ", systemoutputUPF=" + systemoutputUPF + ", systemoutputVPF="
				+ systemoutputVPF + ", systemoutputWPF=" + systemoutputWPF + ", systemoutputFrequency="
				+ systemoutputFrequency + ", batterystate=" + batterystate + ", batteryTemperature="
				+ batteryTemperature + ", loadstate=" + loadstate + ", outputstate=" + outputstate
				+ ", temperatureinmachine=" + temperatureinmachine + ", batteryoperatingstate=" + batteryoperatingstate
				+ ", currentpricetype=" + currentpricetype + ", currentprice=" + currentprice + ", inputpower="
				+ inputpower + ", outputpower=" + outputpower + ", batteryV=" + batteryV + ", batteryI=" + batteryI
				+ ", inputpowersum=" + inputpowersum + ", inputpowerfee=" + inputpowerfee + ", outputpowersum="
				+ outputpowersum + ", outputpowerfee=" + outputpowerfee + ", profitsum=" + profitsum + ", producttype="
				+ producttype + ", parallelstate=" + parallelstate + ", inputphasestate=" + inputphasestate
				+ ", bypassstate=" + bypassstate + ", inverterstate=" + inverterstate + ", inverterswitchstate="
				+ inverterswitchstate + ", inverteroperatingstate=" + inverteroperatingstate + ", UPStemperaturestate="
				+ UPStemperaturestate + ", batterypolestate=" + batterypolestate + ", fanstate=" + fanstate
				+ ", parallelinestate=" + parallelinestate + ", fusestate=" + fusestate + ", UPScomstate=" + UPScomstate
				+ ", runState=" + runState + "]";
	}
	

}
