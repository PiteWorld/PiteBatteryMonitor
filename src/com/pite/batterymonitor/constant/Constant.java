package com.pite.batterymonitor.constant;

import com.pite.batterymonitor.LoginActivity;

import android.os.Environment;

/***
 * 访问地址常量
 */
public class Constant {// 192.168.1.138:83
	public static String BATTERY_BASIC_ADDRESS = LoginActivity.basic_ip; //
	
	public static String BATTERY_BASIC_ADDRESS_LOGINs = ""; // 第二页开始基础地址
	public static final String BATTERY_BASIC_ADDRESS_LOGIN = "http://203.191.147.81:8080/bms/rest/"; // 登录基础地址
	//public static final String BATTERY_BASIC_LOGIN = "getCheckUserLoginIP/";
	public static final String BATTERY_BASIC_LOGIN = "getLoginValidateInfo/";
	public static final String BATTERY_GOURP = "getFirstPageData/";// 电池组
	public static final String BATTERY_PACKET = "getGroupPageData/";// 电池分组
	public static final String BATTERY_TEST = "getNodeGroupPageData/"; // 测试类型
	// public static final String BATTERY_MONITOR = "getChartHeadPageData/1"; //
	// 定时监测
	public static final String BATTERY_POSTION = ""; // 位置监测
	public static final String BATTERY_WARING = ""; // 警告信息
	public static final String BATTERY_MONITOR_TREND = "get_BatteryByPage/"; // 趋势图
	public static final String BATTERY_MONITOR_TREND2 = "get_BatteryByPageBC/"; // 充放电趋势图
	public static final String BATTERY_HIS_TIME = "get_BatteryByHeadsetAdriod/G/";// 直方图
	public static final String BATTERY_HIS_TIME2 = "get_BatteryByHeadsetAdriodBC/G/";// 充放电直方图
	public static final String BATTERY_HIS_TIMESP = "get_BatteryByHeadsetAdriod/H/";//TestID/chinese"; 直方图spinner
	public static final String BATTERY_HIS_TIMESP2 = "get_BatteryByHeadsetAdriodBC/H/";//TestID/chinese"; 充放电直方图spinner
	public static final String BATTERY_RESTART_SUEC = "getReceiptStatus/"; // 重启是否成功
	public static final String BATTERY_TIME_SP = "get_Interval/";// {_Gid}/{_lanGuage}
	public static final String BATTERY_SP_TIME = "time_setJson/";//时间间隔选择框
	public static final String BATTERY_SP_TIME2 = "time_setJsonBC/";//充放电时间间隔选择框
	public static final String BATTERY_TIME_SP_RESTART = "setMoveCmdCode/"; // 重启
	public static final String BATTERY_SYN_TIME = "setSyncParams/";//同步参数{userid}/{groupid}/{_lanGuage}
	public static final String GET_DATAANALYSIS = "get_DataAnalysis/*/";//数据分析报告
	public static final String GET_DATAANALYSIS2 = "get_DataAnalysisBC/*/";//充放电数据分析报告
	public static final String get_voltageAnalysisAD = "get_voltageAnalysisAD/*/";//电压分析报告
	public static final String get_voltageAnalysisAD2 = "get_voltageAnalysisADBC/*/";//充放电电压分析报告
	public static final String GET_RESISTANCEANALYSISAD = "get_resistanceAnalysisAD/*/";//内阻分析报告
	public static final String LOGIN_LOGO = "http://203.191.147.81:8001/";//企业logo地址+登录返回的地址
	public static final String LOGIN_LOGOADSS = "http://203.191.147.81:8011/bms/rest/"; // 获得最新版本的地址
	public static final String GETVERSION_NAME = "getTestInitUpgradeInfo/0";//版本号的获取
	public static final String LOGOFILE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pite_Battery";
	public static final String LOGOIMAGE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pite_Battery/loge";//logo保存地址
	public static final String PSCURL = "http://203.191.147.81:8011/bms/rest/getKeHuaData/"; //pcs信息
	public static final String WARINGURL = "get_AlarmMalfInfo/"; //告警信息
	public static final String WARINGURL_HEAD = "get_AlarmMalfHeadInfo/"; //告警信息
	public static final String WARINGURL_UPDATE="updateAlarmDealInfo/";//告警状态

	

}
