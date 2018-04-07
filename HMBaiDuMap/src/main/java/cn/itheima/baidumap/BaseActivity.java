package cn.itheima.baidumap;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {
	protected BaiduMap baiduMap;
	protected MapView mapView;
	protected double latitude = 40.050966;// 纬度
	protected double longitude = 116.303128;// 经度
	protected LatLng hmPos = new LatLng(latitude, longitude);// 黑马

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSDK();
		setContentView(R.layout.common);

		init();
	}

	private void initSDK() {
		// 初始化地图引擎 校验KEY
//		SDKInitializer.initialize(getApplicationContext());// 必须传递全局Context
		IntentFilter filter = new IntentFilter();
		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		registerReceiver(new SDKBroadCast(), filter);
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
		MapStatusUpdate mapStatusUpdatePoint = MapStatusUpdateFactory
				.newLatLng(latlng);
		baiduMap.setMapStatus(mapStatusUpdatePoint);

		 mapView.showScaleControl(false);// 设置比例尺是否显示 默认是true
		 mapView.showZoomControls(false);// 设置缩放按钮是否显示 默认是true
	}
	class SDKBroadCast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String result = intent.getAction();
			// 网络错误广播
			if (result
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(getApplicationContext(), "无网络", 0).show();
			}
			// KEY 校验失败
			else if (result
					.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(getApplicationContext(), "校验失败", 0).show();
			}
		}

	}
	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}
}
