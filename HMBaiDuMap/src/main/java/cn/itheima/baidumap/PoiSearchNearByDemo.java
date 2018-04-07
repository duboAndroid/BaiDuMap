package cn.itheima.baidumap;

import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.os.Bundle;
import android.widget.Toast;

/**
 * 周边搜索 只搜索一个
 * @author h
 *
 */
public class PoiSearchNearByDemo extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		search();
	}

	private void search() {
		PoiSearch poiSearch = PoiSearch.newInstance();// 获取发起poi搜索请求的对象
		poiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener());

		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.location(hmPos);// 设置中心
		option.radius(1000);// 单位是米
		option.keyword("加油站");// 设置搜索关键字
		poiSearch.searchNearby(option);
	}
	class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if(result==null||SearchResult.ERRORNO.RESULT_NOT_FOUND==result.error){
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			// 创建覆盖物
			PoiOverlay poiOverlay = new MyPoiOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(poiOverlay);
			// 设置数据
			poiOverlay.setData(result);
			// 添加到地图上
			poiOverlay.addToMap();
			poiOverlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内
		}

	}

	class MyPoiOverlay extends PoiOverlay{

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}
		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			List<PoiInfo> allPoi = poiResult.getAllPoi();// 获取返回来的所有POI
			PoiInfo poiInfo = allPoi.get(index);
			String text = poiInfo.city + ":" + poiInfo.address;
			Toast.makeText(getApplicationContext(), text, 0).show();
			return super.onPoiClick(index);
		}
	}
}
