package cn.itheima.baidumap;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingPolicy;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import android.os.Bundle;
import android.widget.Toast;

/**
 * 驾车路线
 * @author h
 *
 */
public class DrivingSearchDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		search();
	}

	private void search() {
		RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
		planSearch.setOnGetRoutePlanResultListener(new MyOnGetRoutePlanResultListener());

		DrivingRoutePlanOption option = new DrivingRoutePlanOption();
		PlanNode from = PlanNode.withLocation(hmPos);
		PlanNode to = PlanNode.withLocation(new LatLng(40.065796,116.349868));
		option.policy(DrivingPolicy.ECAR_TIME_FIRST);// 设置策略
		option.from(from);// 设置起点
		option.to(to);// 设置终点
		List<PlanNode> nodes = new ArrayList<PlanNode>();
		nodes.add(PlanNode.withCityNameAndPlaceName("北京", "天安门"));
		option.passBy(nodes);
		planSearch.drivingSearch(option);
	}
	class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener{

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			if (result == null
					|| SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			DrivingRouteLine line = result.getRouteLines().get(0);
			overlay.setData(line);
			overlay.addToMap();
			overlay.zoomToSpan();
		}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult arg0) {

		}

		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult arg0) {

		}

	}
	class MyDrivingRouteOverlay extends DrivingRouteOverlay{

		public MyDrivingRouteOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);//super.getStartMarker();
		}
		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);//super.getTerminalMarker();
		}

	}
}
