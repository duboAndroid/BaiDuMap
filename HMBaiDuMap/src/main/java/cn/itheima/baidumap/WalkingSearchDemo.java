package cn.itheima.baidumap;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * 步行路线
 * @author h
 *
 */
public class WalkingSearchDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		search();
	}

	private void search() {
		RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
		planSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());

		WalkingRoutePlanOption option = new WalkingRoutePlanOption();
		PlanNode from = PlanNode.withLocation(hmPos);
		PlanNode to = PlanNode.withLocation(new LatLng(40.065796,116.349868));
		option.from(from);// 设置起点
		option.to(to);// 设置终点
		planSearch.walkingSearch(option);
	}
	class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener{

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult arg0) {

		}

		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult result) {
			if (result == null || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			WalkingRouteLine line = result.getRouteLines().get(0);
			overlay.setData(line);// 设置数据
			overlay.addToMap();// 添加到地图上
			overlay.zoomToSpan();// 自动缩放级别
		}
	}
	class MyWalkingRouteOverlay extends WalkingRouteOverlay{

		public MyWalkingRouteOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
		}
		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
		}
	}
}
