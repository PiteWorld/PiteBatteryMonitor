package com.pite.batterymonitor.constant;

import com.pite.batterymonitor.LoginActivity;

import android.os.Environment;

/***
 * ���ʵ�ַ����
 */
public class Constant {// 192.168.1.138:83
	public static String BATTERY_BASIC_ADDRESS = LoginActivity.basic_ip; //
	
	public static String BATTERY_BASIC_ADDRESS_LOGINs = ""; // �ڶ�ҳ��ʼ������ַ
	public static final String BATTERY_BASIC_ADDRESS_LOGIN = "http://203.191.147.81:8080/bms/rest/"; // ��¼������ַ
	//public static final String BATTERY_BASIC_LOGIN = "getCheckUserLoginIP/";
	public static final String BATTERY_BASIC_LOGIN = "getLoginValidateInfo/";
	public static final String BATTERY_GOURP = "getFirstPageData/";// �����
	public static final String BATTERY_PACKET = "getGroupPageData/";// ��ط���
	public static final String BATTERY_TEST = "getNodeGroupPageData/"; // ��������
	// public static final String BATTERY_MONITOR = "getChartHeadPageData/1"; //
	// ��ʱ���
	public static final String BATTERY_POSTION = ""; // λ�ü��
	public static final String BATTERY_WARING = ""; // ������Ϣ
	public static final String BATTERY_MONITOR_TREND = "get_BatteryByPage/"; // ����ͼ
	public static final String BATTERY_MONITOR_TREND2 = "get_BatteryByPageBC/"; // ��ŵ�����ͼ
	public static final String BATTERY_HIS_TIME = "get_BatteryByHeadsetAdriod/G/";// ֱ��ͼ
	public static final String BATTERY_HIS_TIME2 = "get_BatteryByHeadsetAdriodBC/G/";// ��ŵ�ֱ��ͼ
	public static final String BATTERY_HIS_TIMESP = "get_BatteryByHeadsetAdriod/H/";//TestID/chinese"; ֱ��ͼspinner
	public static final String BATTERY_HIS_TIMESP2 = "get_BatteryByHeadsetAdriodBC/H/";//TestID/chinese"; ��ŵ�ֱ��ͼspinner
	public static final String BATTERY_RESTART_SUEC = "getReceiptStatus/"; // �����Ƿ�ɹ�
	public static final String BATTERY_TIME_SP = "get_Interval/";// {_Gid}/{_lanGuage}
	public static final String BATTERY_SP_TIME = "time_setJson/";//ʱ����ѡ���
	public static final String BATTERY_SP_TIME2 = "time_setJsonBC/";//��ŵ�ʱ����ѡ���
	public static final String BATTERY_TIME_SP_RESTART = "setMoveCmdCode/"; // ����
	public static final String BATTERY_SYN_TIME = "setSyncParams/";//ͬ������{userid}/{groupid}/{_lanGuage}
	public static final String GET_DATAANALYSIS = "get_DataAnalysis/*/";//���ݷ�������
	public static final String GET_DATAANALYSIS2 = "get_DataAnalysisBC/*/";//��ŵ����ݷ�������
	public static final String get_voltageAnalysisAD = "get_voltageAnalysisAD/*/";//��ѹ��������
	public static final String get_voltageAnalysisAD2 = "get_voltageAnalysisADBC/*/";//��ŵ��ѹ��������
	public static final String GET_RESISTANCEANALYSISAD = "get_resistanceAnalysisAD/*/";//�����������
	public static final String LOGIN_LOGO = "http://203.191.147.81:8001/";//��ҵlogo��ַ+��¼���صĵ�ַ
	public static final String LOGIN_LOGOADSS = "http://203.191.147.81:8011/bms/rest/"; // ������°汾�ĵ�ַ
	public static final String GETVERSION_NAME = "getTestInitUpgradeInfo/0";//�汾�ŵĻ�ȡ
	public static final String LOGOFILE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pite_Battery";
	public static final String LOGOIMAGE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pite_Battery/loge";//logo�����ַ
	public static final String PSCURL = "http://203.191.147.81:8011/bms/rest/getKeHuaData/"; //pcs��Ϣ
	public static final String WARINGURL = "get_AlarmMalfInfo/"; //�澯��Ϣ
	public static final String WARINGURL_HEAD = "get_AlarmMalfHeadInfo/"; //�澯��Ϣ
	public static final String WARINGURL_UPDATE="updateAlarmDealInfo/";//�澯״̬

	

}
