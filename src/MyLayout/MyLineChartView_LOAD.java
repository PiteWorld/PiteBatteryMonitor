package MyLayout;

import com.example.pcs.fragment.LoadFragment02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import lecho.lib.hellocharts.view.LineChartView;

public class MyLineChartView_LOAD extends LineChartView {

	public MyLineChartView_LOAD(Context context) {
		super(context);
	}

	public MyLineChartView_LOAD(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLineChartView_LOAD(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas arg0) {
		super.onDraw(arg0);
		int h = arg0.getHeight();
		int w = arg0.getWidth();
		float xStart = w * (0.05f);
		xStart += 10;
		float yBottom = (h - h * (0.15f));
		Paint pa = new Paint();
		pa.setColor(Color.BLACK);
		pa.setStrokeWidth(1);
		pa.setAntiAlias(true);
		pa.setTextSize(20);
		Paint pa1 = new Paint();
		pa1.setColor(Color.BLACK);
		pa1.setTextSize(20);
		pa1.setStrokeWidth(1.5f);
		pa1.setAntiAlias(true);
		for (float yline = 0; yline < yBottom; yline += (yBottom / 3)) { // X轴平行线
			if (yline == 0)
				continue;
			arg0.drawLine(xStart, yline, w, yline, pa);
		}
		int index = 0;
		int gap = 0;
		if (LoadFragment02.list != null) {
			index = gap = LoadFragment02.list.getDataInEveryPage().getBatteryByPage().getData().size() / 5;
		}
		for (float xline = 0; xline < w; xline += (w / 5)) { // Y轴平行线
			if (xline == 0) {
				continue;
			}
			arg0.drawLine(xline, 0, xline, yBottom, pa);
			if (LoadFragment02.list != null) {
				arg0.drawText(setSulist(
						LoadFragment02.list.getDataInEveryPage().getBatteryByPage().getData().get(index).getTime()),
						xline - 50, h, pa1);
			}
			index += gap;
		}
	}

	/**
	 * 截取指定格式数据
	 */
	public String setSulist(String str) {
		String string = null;
		for (int i = 0; i < str.length(); i++) {
			string = str.substring(5, 16);
		}
		return string;
	}

	/**
	 * 获取Y轴最大值
	 */
	public int getMax(double[] d) {
		int max = 0;
		for (int i = 0; i < d.length; i++) {
			if (d[i] > max) {
				max = (int) d[i];
			}
		}
		return max;
	}
}