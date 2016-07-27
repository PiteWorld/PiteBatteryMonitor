package com.pite.batterymonitor.utils;

import com.pite.batterymonitor.R;

import android.content.Context;

/**
 * ��� ͨѶ״̬ �任��
 */
public class StatusUtils {
	private static Context context;

	public StatusUtils() {

	}

	public StatusUtils(Context context) {
		this.context = context;
	}

	/**
	 * ͨѶ ״̬�任
	 */
	public static String isNewsChange(String data) {
		// String change = data.equals("0") ?
		// context.getResources().getString(R.drawable.cross) :
		// context.getResources().getString(R.drawable.checkmark);
		String change = data.equals("0") ? context.getResources().getString(R.string.cross)
				:data.equals("--")?context.getResources().getString(R.string.not)
				: context.getResources().getString(R.string.checkmark);
		return change;
	}

	/**
	 * ���״̬�任
	 */
	public static String isBatteryChange(String data) {// �쳣 ���� �е� ����
		// String change = data.equals("0") ?
		// context.getResources().getString(R.drawable.expction)
		// : data.equals("4") ?
		// context.getResources().getString(R.drawable.change)
		// : data.equals("6") ?
		// context.getResources().getString(R.drawable.mindu) :
		// context.getResources().getString(R.drawable.good);
		// String change = data.equals("0") ?
		// context.getResources().getString(R.drawable.good)
		// : data.equals("1") ?
		// context.getResources().getString(R.drawable.mindu)
		// : data.equals("2") ?
		// context.getResources().getString(R.drawable.expction)
		// : data.equals("3") ?
		// context.getResources().getString(R.drawable.change)
		// : data.equals("6") ?
		// context.getResources().getString(R.drawable.expction)
		// : context.getResources().getString(R.drawable.expction);
		String change = data
				.equals("0")
						? context.getResources().getString(R.string.good)
						: data.equals("1") ? context.getResources().getString(R.string.medium)
								: data.equals("2") ? context.getResources().getString(R.string.Exception)
										: data.equals("3") ? context.getResources().getString(R.string.Change)
												: data.equals("6")
														? context.getResources().getString(R.string.Exception)
														:data.equals("--")
															?context.getResources().getString(R.string.not)
																	:context.getResources().getString(R.string.Exception);
		return change;
	}
}