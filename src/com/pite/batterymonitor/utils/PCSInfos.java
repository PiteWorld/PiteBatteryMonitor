package com.pite.batterymonitor.utils;

import java.util.Date;

public class PCSInfos {
	// ��id
	private int id;
	// ���
	private int groupid;
	// ����ͷ��Ϣ
	private int testheadid;
	// ����ʱ��
	private String testtime;
	// ��˾���
	private int companyNo;
	// ��������
	private int devType;
	// ������ַ
	private int devAddress;
	
	// �е�����U���ѹ6000
	private String mainsspplyinputUV;
	// �е�����V���ѹ6001
	private String mainsspplyinputVV;
	// �е�����W���ѹ6002
	private String mainsspplyinputWV;
	// �е�����Ƶ��6003
	private String mainsspplyinputFrequency;
	
	// ��·����U���ѹ6004
	private String bypassspplyinputUV;
	// ��·����V���ѹ6005
	private String bypassspplyinputVV;
	// ��·����W���ѹ6006
	private String bypassspplyinputWV;
	// ��·����Ƶ��6007
	private String bypassspplyinputFrequency;
	
	// �������ص�ѹ
	private int inverterbatteryV;
	// �������ص���
	private String inverterbatteryI;
	// ���������¶�
	private int inverterbatteryT;
	// ��س�ŵ�״̬
	private int inverterbatteryCD;
	
	
	// ϵͳ���U���ѹ1
	private String systemoutputUV;
	// ϵͳ���v���ѹ2
	private String systemoutputVV;
	// ϵͳ���W���ѹ3
	private String systemoutputWV;
	// ϵͳ���U�����4
	private String systemoutputUI;
	// ϵͳ���V�����5
	private String systemoutputVI;
	// ϵͳ���W�����6
	private String systemoutputWI;
	// ϵͳ���U�ฺ�ذٷֱ�7
	private String systemoutputLoadUP;
	// ϵͳ���V�ฺ�ذٷֱ�8
	private String systemoutputLoadVP;
	// ϵͳ���W�ฺ�ذٷֱ�9
	private String systemoutputLoadWP;
	// ϵͳ���U���й�����10
	private String systemoutputUYP;
	// ϵͳ���V���й�����11
	private String systemoutputVYP;
	// ϵͳ���W���й�����12
	private String systemoutputWYP;
	// ϵͳ���U�����ڹ���13
	private String systemoutputUSP;
	// ϵͳ���V�����ڹ���14
	private String systemoutputVSP;
	// ϵͳ���W�����ڹ���15
	private String systemoutputWSP;
	// ϵͳ���U��PFֵ16
	private String systemoutputUPF;
	// ϵͳ���V��PFֵ17
	private String systemoutputVPF;
	// ϵͳ���W��PFֵ18
	private String systemoutputWPF;
	// ϵͳ���Ƶ��19
	private String systemoutputFrequency;
	
	
	// ���״̬
	private int batterystate;
	// ���״̬
	private String batteryTemperature;
	// ����״̬
	private int loadstate;
	// ���״̬
	private int outputstate;
	// �����¶�
	private int temperatureinmachine;
	
	
	// ��������״̬
	private int batteryoperatingstate;
	// ��ǰ�������
	private int currentpricetype;
	// ��ǰ���
	private String currentprice;
	// ���빦�ʣ�������ǰ
	private String inputpower;
	// ������ʣ�������ǰ��
	private String outputpower;
	// ��ص�ѹ
	private String batteryV;
	// ��ص���
	private String batteryI;
	// 6044 �ۼ������ܵ�����������ǰ��
	private String inputpowersum;
	// '�ۼ������ܵ�ѣ�������ǰ'
	private String inputpowerfee;
	// �ۼ�����ܵ�����������ǰ��
	private String outputpowersum;
	// �ۼ�����ܵ�ѣ�������ǰ��
	private String outputpowerfee;
	// �ۼ����棨������ǰ��
	private String profitsum;
	
	// ��Ʒ�ͺ�
	private String producttype;
	
	// ������״̬
	private int parallelstate;
	// ��������
	private int inputphasestate;
	// ��·״̬
	private int bypassstate;
	// ������״̬
	private int inverterstate;
	// ��������ػ�״̬
	private int inverterswitchstate;
	// ���������״̬
	private int inverteroperatingstate;
	// UPS�¶�״̬
	private int UPStemperaturestate;
	// ��ؼ���״̬
	private int batterypolestate;
	// ���״̬
	private int fanstate;
	// ������״̬
	private int parallelinestate;
	// ����˿״̬
	private int fusestate;
	// UPSͨѶ����
	private int UPScomstate;
	// ��������״̬
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
