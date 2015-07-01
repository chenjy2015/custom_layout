package com.example.custom;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class SwipeRefreshLayoutActivity extends Activity implements OnRefreshListener,OnItemClickListener{
	
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	private ArrayList<String> list = new ArrayList<String>();
	private int mCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe_layout);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeRefreshLayout.setOnRefreshListener(this);
//		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//				android.R.color.holo_green_light,
//				android.R.color.holo_orange_light,
//				android.R.color.holo_red_light);
		mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		mCount = 10;
		initData(mCount);
		mListView = (ListView) findViewById(R.id.autolinelistview);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list));
		mListView.setOnItemClickListener(this);
		
		
	}
	
	private ArrayList<String> initData(int count) {
		for(int i=0;i<count;i++){
			list.add("item"+i);
		}
		return list;
	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(false);
				mCount += 10;
				ArrayList<String> tempList = initData(mCount);;
				list.addAll(tempList);
				handler.sendEmptyMessage(0);
			}
		}, 2000);
	}
	
	public Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			mListView.setAdapter(new ArrayAdapter<String>(SwipeRefreshLayoutActivity.this,
					android.R.layout.simple_list_item_1, list));
		};
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), list.get(position).toString(), 0).show();
	}

}
