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
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 城市内搜索
 *
 * @author h
 *
 */
public class PoiSearchInCityDemo extends BaseActivity {
	PoiSearch poiSearch ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		poiSearch = PoiSearch.newInstance();// 获取发起poi搜索请求的对象
		poiSearch.setOnGetPoiSearchResultListener(new MyOnGetPoiSearchResultListener());
		search();
	}
	private int currentIndex;
	private void search() {
		PoiCitySearchOption option = new PoiCitySearchOption();
		option.city("北京");// 设置城市
		option.keyword("加油站");// 关键字
		option.pageNum(currentIndex);// 设置显示的页数
		poiSearch.searchInCity(option);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_1){
			currentIndex++;
			search();
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyOnGetPoiSearchResultListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			if (result == null || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			String text = result.getAddress() + ":" + result.getFavoriteNum();
			Toast.makeText(getApplicationContext(), text, 0).show();
		}

		@Override
		public void onGetPoiResult(PoiResult result) {
			if (result == null || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
				Toast.makeText(getApplicationContext(), "未查询到结果", 0).show();
				return;
			}
			String text = "共" + result.getTotalPageNum() + "页，共"
					+ result.getTotalPoiNum() + "条，当前第"
					+ result.getCurrentPageNum() + "页，当前页共"
					+ result.getCurrentPageCapacity() + "条";
			Toast.makeText(getApplicationContext(), text, 0).show();
			baiduMap.clear();// 清楚上一次的数据
			PoiOverlay poiOverlay = new MyPoiOverlay(baiduMap);// 创建覆盖物
			baiduMap.setOnMarkerClickListener(poiOverlay);// 设置数据
			poiOverlay.setData(result);// 只是把当前页的数据显示出来
			poiOverlay.addToMap();// 添加到地图上
			poiOverlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内
		}
	}

	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public boolean onPoiClick(int index) {
			PoiResult poiResult = getPoiResult();
			List<PoiInfo> allPoi = poiResult.getAllPoi();// 获取返回来的所有POI
			PoiInfo poiInfo = allPoi.get(index);
			String text = poiInfo.city + ":" + poiInfo.address;
			//Toast.makeText(getApplicationContext(), text, 0).show();
			PoiDetailSearchOption option = new PoiDetailSearchOption();
			option.poiUid(poiInfo.uid);// 设置poi uid
			poiSearch.searchPoiDetail(option);
			return super.onPoiClick(index);
		}
	}
}
