package com.pite.batterymonitor.adapter;

import java.util.List;

import com.pite.batterymonitor.R;
import com.pite.batterymonitor.utils.StatusUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @param <T>
 *            图表适配器
 */
public class ChartAdapter<T> extends BaseAdapter {
	private Context content;
	private int NumItem;
	private List<String[]> list;
	private LinearLayout ll;
	private LinearLayout chlickll;
	private float tvWight, viewWight;
	private int flags; // 标志
	// 字体颜色
	private List<String[]> listColor = null;

	public ChartAdapter(Context content, List<String[]> list, int NumItem, float tvWight, float viewWight, int flags,
			List<String[]> listColor) {
		this.content = content;
		this.NumItem = NumItem;
		this.list = list;
		this.tvWight = tvWight;
		this.viewWight = viewWight;
		this.flags = flags;
		this.listColor = listColor;
	}

	public ChartAdapter(Context content, List<String[]> list, int NumItem, float tvWight, float viewWight, int flags) {
		this.content = content;
		this.NumItem = NumItem;
		this.list = list;
		this.tvWight = tvWight;
		this.viewWight = viewWight;
		this.flags = flags;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			holder.tex1 = new TextView[NumItem];
			convertView = LayoutInflater.from(content).inflate(R.layout.item, null);
			ll = (LinearLayout) convertView.findViewById(R.id.item_ll);
			for (int i = 0; i < NumItem; i++) {
				holder.tv = new TextView(content);
				holder.tex1[i] = holder.tv;
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, tvWight);
				params.gravity = Gravity.CENTER;
				holder.view = new View(content);
				holder.view.setBackground(content.getResources().getDrawable(R.drawable.line));
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, viewWight);
				chlickll = new LinearLayout(content);
				LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT, 1.0f);
				if (i == NumItem - 1) {
					LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT, 1.0f);
					chlickll.addView(holder.tv, params4);
					ll.addView(chlickll, params3);
				} else {
					chlickll.addView(holder.tv, params);
					chlickll.addView(holder.view, params2);
					ll.addView(chlickll, params3);
				}
				convertView.setTag(holder);
			}
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		new StatusUtils(content); // 传Context
		for (int i = 0; i < holder.tex1.length; i++) {
			if (flags == 0) {
				if (i == 0) {
					holder.tex1[i].setText(list.get(position)[i]);
					holder.tex1[i].setTextColor(Color.BLACK);
				} else if (i > 0 && i < holder.tex1.length - 2) {//判断电池状态和充电状态
					if (listColor != null && listColor.size() > 0) {
						if (!listColor.get(position)[0].equals(" ")) {
						if (i == 1) {
								holder.tex1[i].setTextColor(Color.parseColor("#" + listColor.get(position)[0]));
							} else {
								holder.tex1[i].setTextColor(Color.parseColor("#" + listColor.get(position)[1]));
							}
						}
						holder.tex1[i].setText(list.get(position)[i]);
					} else {
						//String str = StatusUtils.isBatteryChange(list.get(position)[i]);
						holder.tex1[i].setText(list.get(position)[i]);
						/*holder.tex1[i].setTextColor(str.equals(content.getResources().getString(R.string.good))
								? content.getResources().getColor(R.color.lvse)
								: str.equals(content.getResources().getString(R.string.medium)) ? Color.YELLOW
										: Color.RED);*/
					}
				} else {
					String str = StatusUtils.isNewsChange(list.get(position)[i]);
					holder.tex1[i].setText(str);
					holder.tex1[i].setTextColor(str.equals(content.getResources().getString(R.string.checkmark))
							? Color.RED : content.getResources().getColor(R.color.lvse));
				}

			} else if (flags == 1) {
				if (i < 2) {
					holder.tex1[i].setText(list.get(position)[i]);
					holder.tex1[i].setTextColor(Color.BLACK);
				} else if (i >= 2 && i < holder.tex1.length - 2) {
					if (listColor != null && listColor.size() > 0) {
						if (!listColor.get(0).equals(" ")) {
							if (i == 2) {
								holder.tex1[i].setTextColor(Color.parseColor("#" + listColor.get(position)[0]));
							} else
								holder.tex1[i].setTextColor(Color.parseColor("#" + listColor.get(position)[1]));
							    holder.tex1[i].setText(list.get(position)[i]);
						}
					} else {
						//String str = StatusUtils.isBatteryChange(list.get(position)[i]);
						holder.tex1[i].setText(list.get(position)[i]);
					/*	holder.tex1[i].setTextColor(str.equals(content.getResources().getString(R.string.good))
								? content.getResources().getColor(R.color.lvse)
								: str.equals(content.getResources().getString(R.string.medium)) ? Color.YELLOW
										: Color.RED);*/
					}
				} else {
					String str = StatusUtils.isNewsChange(list.get(position)[i]);
					holder.tex1[i].setText(str);
					holder.tex1[i].setTextColor(str.equals(content.getResources().getString(R.string.checkmark))
							? Color.RED : content.getResources().getColor(R.color.lvse));
				}
			} else {
				holder.tex1[i].setText(list.get(position)[i]);
				holder.tex1[i].setTextColor(Color.BLACK);
			}
			holder.tex1[i].setTextSize(12);
			holder.tex1[i].setGravity(Gravity.CENTER);
		}
		return convertView;
	}

	static class MyHolder {
		private TextView tv;
		private View view;
		private TextView[] tex1 = null;
	}

}
