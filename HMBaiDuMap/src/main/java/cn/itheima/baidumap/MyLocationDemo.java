package cn.itheima.baidumap;

import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

/**
 * 定位
 *
 * @author h
 *
 */
public class MyLocationDemo extends BaseActivity {
	public LocationClient mLocationClient;
	private BitmapDescriptor icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locate();
	}

	private void locate() {
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(new MyLocationListener());

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		baiduMap.setMyLocationEnabled(true);// 打开定位图层

		icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
		MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, icon);
		baiduMap.setMyLocationConfigeration(config);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_1:
				// 普通
				MyLocationConfiguration nomal = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, icon);
				baiduMap.setMyLocationConfigeration(nomal);
				break;
			case KeyEvent.KEYCODE_2:
				// 跟随
				MyLocationConfiguration follow = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, icon);
				baiduMap.setMyLocationConfigeration(follow);
				break;
			case KeyEvent.KEYCODE_3:
				// 罗盘
				MyLocationConfiguration compass = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, icon);
				baiduMap.setMyLocationConfigeration(compass);
				break;
			default:break;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation result) {
			MyLocationData dada = new MyLocationData.Builder()
					.latitude(result.getLatitude())// 设置纬度
					.longitude(result.getLongitude())// 设置经度
					.build();
			baiduMap.setMyLocationData(dada);// 显示定位信息 只有打开定位图层 才有效果
		}
	}

	@Override
	protected void onStart() {
		mLocationClient.start();// 开始定位
		super.onStart();
	}

	@Override
	protected void onPause() {
		mLocationClient.stop();// 停止定位
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocationClient.unRegisterLocationListener(new MyLocationListener());
	}
}
