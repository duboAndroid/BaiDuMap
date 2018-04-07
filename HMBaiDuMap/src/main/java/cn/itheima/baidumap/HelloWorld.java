package cn.itheima.baidumap;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

/**
 * 百度地图入门
 *
 * @author h
 *
 */
public class HelloWorld extends BaseActivity {
	private static final String TAG = "HelloWorld";
	private BaiduMap baiduMap;
	private MapView mapView;
	double latitude = 40.050966;// 纬度
	double longitude = 116.303128;// 经度
	LatLng hmPos = new LatLng(latitude, longitude);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		// 设置缩放地图 （范围是V2.X 3~19 V1.X 3~18）
		// V2.X与V1.X的主要区别
		// ①修改了地图文件的格式 优化了数据 （北京市 110M 15M）
		// ②增加了一个级别，（增加了3D效果 18 19）

		// BaiduMap 管理具体的某一个MapView 操作：旋转、缩放、移动
		mapView = (MapView) findViewById(R.id.mapview);
		baiduMap = mapView.getMap();
		// 改变当前地图的状态
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15);// 设置缩放到15级，默认是12级
		baiduMap.setMapStatus(mapStatusUpdate);
		// 设置中心点 默认是天安门
		LatLng latlng = hmPos;// 位置？经纬度
		MapStatusUpdate mapStatusUpdatePoint = MapStatusUpdateFactory.newLatLng(latlng);
		baiduMap.setMapStatus(mapStatusUpdatePoint);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 缩放，旋转，移动
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				// 放大 一个级别
				MapStatusUpdate mapStatusZoomIn = MapStatusUpdateFactory.zoomIn();
				baiduMap.setMapStatus(mapStatusZoomIn);
				break;
			case KeyEvent.KEYCODE_2:
				// 缩小 一个级别
				MapStatusUpdate mapStatusZoomOut = MapStatusUpdateFactory.zoomOut();
				baiduMap.setMapStatus(mapStatusZoomOut);
				break;
			case KeyEvent.KEYCODE_3:
				// 以一个点为中心 旋转
				MapStatus mapStatus = baiduMap.getMapStatus();// 获取地图的状态
				float rotate = mapStatus.rotate;// 地图的旋转角度
				Log.d(TAG, "rotate:" + rotate);// 0~360
				MapStatus rotateStatu = new MapStatus.Builder().rotate(rotate + 30).build();
				MapStatusUpdate rotateMapstatu = MapStatusUpdateFactory.newMapStatus(rotateStatu);
				baiduMap.setMapStatus(rotateMapstatu);
				break;
			case KeyEvent.KEYCODE_4:
				// 以一条直线为轴旋转 俯角（OverLooking）
				MapStatus mapStatusOver = baiduMap.getMapStatus();// 获取地图的状态
				float overlook = mapStatusOver.overlook;
				Log.d(TAG, "overlook:" + overlook);// 0~-45
				MapStatus overStatu = new MapStatus.Builder().overlook(overlook - 5).build();
				MapStatusUpdate overMapstatu = MapStatusUpdateFactory.newMapStatus(overStatu);
				baiduMap.setMapStatus(overMapstatu);
				break;
			case KeyEvent.KEYCODE_5:
				// 移动
				MapStatusUpdate moveMapStatu = MapStatusUpdateFactory.newLatLng(new LatLng(40.065796, 116.349868));
				baiduMap.animateMapStatus(moveMapStatu);
				break;
			default:
				break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
