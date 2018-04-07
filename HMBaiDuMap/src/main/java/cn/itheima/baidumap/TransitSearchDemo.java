package cn.itheima.baidumap;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRoutePlanOption.TransitPolicy;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * 攻击换乘路线
 * @author h
 *
 */
public class TransitSearchDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		search();
	}

	private void search() {
		RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
		planSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());

		TransitRoutePlanOption option = new TransitRoutePlanOption();
		PlanNode from = PlanNode.withLocation(hmPos);
		PlanNode to = PlanNode.withLocation(new LatLng(40.065796,116.349868));
		option.city("北京");// 设置城市
		option.from(from);
		option.to(to);
		option.policy(TransitPolicy.EBUS_WALK_FIRST);// 设置策略
		planSearch.transitSearch(option);
	}
	class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener{

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult result) {
			if (result == null || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			TransitRouteLine line = result.getRouteLines().get(0);
			overlay.setData(line);// 设置数据
			overlay.addToMap();// 添加到地图上
			overlay.zoomToSpan();// 自动缩放级别
		}

		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult result) {}

	}
	class MyTransitRouteOverlay extends TransitRouteOverlay{

		public MyTransitRouteOverlay(BaiduMap arg0) {
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
