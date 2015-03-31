package com.example.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.custom.listview.RoundCornerListView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

/**
 * 自定义下拉刷新测试类
 * @author chenjy
 * 2015.3.18
 */
public class RoundListViewDemo extends Activity {

	private RoundCornerListView mListView;
	private SimpleAdapter adapter;
	private List<Map<String, String>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_list);
		mListView = (RoundCornerListView) findViewById(R.id.round_listview);
		initData();
		adapter = new SimpleAdapter(getApplicationContext(), data,
				R.layout.item_round_list, new String[] { "text" },
				new int[] { R.id.tv_system_title });
		mListView.setAdapter(adapter);
	}

	public void initData() {
		data = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "关于本机");
		data.add(map);

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("text", "其他产品");
		data.add(map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("text", "开发团队");
		data.add(map2);

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("text", "投诉建议");
		data.add(map3);

		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("text", "联系我们");
		data.add(map4);

	}

}
