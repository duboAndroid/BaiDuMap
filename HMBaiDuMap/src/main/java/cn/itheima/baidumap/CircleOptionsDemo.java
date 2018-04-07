package cn.itheima.baidumap;

import android.os.Bundle;

import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Stroke;

/**
 * 圆形覆盖物
 * @author h
 *
 */
public class CircleOptionsDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		draw();
	}

	private void draw() {
		// 绘制圆
		// 定义
		// 圆心+半径
		// 样式
		// 是否填充+颜色+如果不填充 圆边框粗细
		// 1、创建自己
		CircleOptions circleOptions = new CircleOptions();
		// 2、设置数据
		circleOptions.center(hmPos)// 设置圆心
				.radius(1000)// 设置半径 以米为单位
				.fillColor(0x00000000)// 设置颜色 16进制 透明度 红 绿 蓝
				.stroke(new Stroke(5, 0x600000ff));
		// 3、添加到地图中
		baiduMap.addOverlay(circleOptions);
	}
}
