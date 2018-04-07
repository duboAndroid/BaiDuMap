package cn.itheima.baidumap;
import android.graphics.Typeface;
import android.os.Bundle;

import com.baidu.mapapi.map.TextOptions;

/**
 * 文字覆盖物
 * @author h
 *
 */
public class TextOptionsDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		draw();
	}

	private void draw() {
		TextOptions textOptions = new TextOptions();
		textOptions.text("DUB")// 设置文字内容
				.fontColor(0x60ff0000)// 设置文字颜色
				.position(hmPos)// 设置位置
				.typeface(Typeface.SERIF)// 设置文字字体
				.fontSize(25);// 设置文字大小
		baiduMap.addOverlay(textOptions);
	}
}
