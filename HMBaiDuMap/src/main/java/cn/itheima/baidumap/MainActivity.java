package cn.itheima.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;

public class MainActivity extends Activity {
	private static String[] objects = new String[] { "hello world", "图层",
			"圆形覆盖物", "展示文字", "marker覆盖物", "矩形范围内搜索", "圆形区域", "全城搜索", "驾车路线",
			"步行路线", "公交换乘", "我的位置" };
	private static Class[] clazzs = new Class[] { HelloWorld.class,
			LayerDemo.class, CircleOptionsDemo.class, TextOptionsDemo.class,
			MarkerOptionsDemo.class, PoiSearchInBoundDemo.class,
			PoiSearchNearByDemo.class, PoiSearchInCityDemo.class,
			DrivingSearchDemo.class, WalkingSearchDemo.class,
			TransitSearchDemo.class, MyLocationDemo.class };
	private ListView list;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.item, objects);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(getApplicationContext(),clazzs[position]);
				startActivity(intent);
			}
		});
		SDKInitializer.initialize(getApplicationContext());// 必须传递全局Context
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
