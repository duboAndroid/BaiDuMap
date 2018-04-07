package cn.itheima.baidumap;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * marker覆盖物
 *
 * @author h
 *
 */
public class MarkerOptionsDemo extends BaseActivity {

	private View pop;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPop();
		draw();
	}

	private void initPop() {
		// 预加载pop 设置为隐藏
		pop = View.inflate(getApplicationContext(), R.layout.pop, null);
		LayoutParams params = new MapViewLayoutParams.Builder()
				.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 设置为经纬度模式
				.position(hmPos)// 设置位置 不能null
				.width(MapViewLayoutParams.WRAP_CONTENT)
				.height(MapViewLayoutParams.WRAP_CONTENT)
				.build();
		mapView.addView(pop, params);
		pop.setVisibility(View.INVISIBLE);// 设置为隐藏
		title = (TextView) pop.findViewById(R.id.title);
	}

	// 点击覆盖物时 弹出泡泡
	private void draw() {
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.eat_icon);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(hmPos)// 设置位置
				.icon(bitmapDescriptor)// 设置显示的图片
				.draggable(true)// 设置可以拖拽
				.title("DUB");// 设置标题
		baiduMap.addOverlay(markerOptions);
		markerOptions = new MarkerOptions().title("向北").position(new LatLng(latitude + 0.001, longitude)).icon(bitmapDescriptor);
		baiduMap.addOverlay(markerOptions);
		markerOptions = new MarkerOptions().title("向东").position(new LatLng(latitude, longitude + 0.001)).icon(bitmapDescriptor);
		baiduMap.addOverlay(markerOptions);
		ArrayList<BitmapDescriptor> bitmaps = new ArrayList<BitmapDescriptor>();
		bitmaps.add(bitmapDescriptor);
		bitmaps.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo));
		markerOptions = new MarkerOptions().title("向西南")
				.position(new LatLng(latitude - 0.001, longitude - 0.001))
				//.icon(bitmapDescriptor);
				.icons(bitmaps);
		baiduMap.addOverlay(markerOptions);
		baiduMap.setOnMarkerClickListener(new MyOnMarkerClickListener());//点击pop
	}
	class MyOnMarkerClickListener implements OnMarkerClickListener{

		@Override
		public boolean onMarkerClick(Marker marker) {
			// 当点击时 更新位置 设置为显示
			LayoutParams params = new MapViewLayoutParams.Builder()
					.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 设置为经纬度模式
					.position(marker.getPosition())// 设置位置 不能null
					.width(MapViewLayoutParams.WRAP_CONTENT)
					.height(MapViewLayoutParams.WRAP_CONTENT)
					.yOffset(-5)// 设置y轴偏移量 向上是负数 向下是正数
					.build();
			mapView.updateViewLayout(pop, params);
			pop.setVisibility(View.VISIBLE);
			title.setText(marker.getTitle());
			return true;
		}

	}
}
