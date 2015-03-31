package com.example.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.custom.listview.AutoLineListView;
import com.example.custom.listview.AutoLineListView.OnLoadListenner;
import com.example.custom.listview.AutoLineListView.OnRefreshListenner;
import com.example.custom.listview.AutoListView;
import com.example.custom.listview.adapter.ListViewAdapter;

public class AutoLinePrefreshListViewDemo extends Activity implements OnRefreshListenner,OnLoadListenner{

	private AutoLineListView mListView;
	private ListViewAdapter adapter;
	private List<String> data = new ArrayList<String>();;
	private static int maxPullHeight = 120;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_line_listview);

		mListView = (AutoLineListView) findViewById(R.id.lv);
		adapter = new ListViewAdapter(this, data);
		mListView.setAdapter(adapter);
		mListView.setMaxPullHeight(maxPullHeight);//设置下拉高度
		mListView.setImageViewWidth(12);
		mListView.setOnRefreshListenner(this);//设置刷新回调
		mListView.setOnLoadListenner(this);//设置加载回调
		
		
		initData();
	}

	private void initData() {
		loadData(AutoListView.REFRESH);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			@SuppressWarnings("unchecked")
			List<String> result = (List<String>) msg.obj;
			switch (msg.what) {
			case AutoListView.REFRESH:
				mListView.onRefreshComplete();
				data.clear();
				data.addAll(result);
				break;
			case AutoListView.LOAD:
				mListView.onLoadComplete();
				data.addAll(result);
				break;
			}
			adapter.notifyDataSetChanged();
		};
	};


	// 测试数据
	public List<String> getData() {
		List<String> result = new ArrayList<String>();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			long l = random.nextInt(10000);
			result.add("当前条目的ID：" + l);
		}
		return result;
	}

	private void loadData(final int what) {
		// 这里模拟从服务器获取数据
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				msg.obj = getData();
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**加载回调*/
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		loadData(AutoListView.LOAD);
	}

	/**刷新回调*/
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadData(AutoListView.REFRESH);
	}
}
