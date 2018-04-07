package cn.itheima.baidumap;

import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.mapapi.map.BaiduMap;

/**
 * 地图图层（底图、交通图、卫星图）
 * @author h
 *
 */
public class LayerDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				// 底图
				baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				baiduMap.setTrafficEnabled(false);
				break;
			case KeyEvent.KEYCODE_2:
				// 交通图
				baiduMap.setTrafficEnabled(true);
				break;
			case KeyEvent.KEYCODE_3:
				// 卫星图
				baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				baiduMap.setTrafficEnabled(false);
				break;

			default:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
