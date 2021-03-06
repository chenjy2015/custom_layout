/*
 * Created by Storm Zhang, Mar 31, 2014.
 */

package com.example.custom;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;

public class MainActivity extends Activity implements
		SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	private SwipeRefreshLayout mSwipeLayout;
	private ListView mListView;
	private ArrayList<String> list = new ArrayList<String>();

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getData()));
		mListView.setOnItemClickListener(this);

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		mSwipeLayout.setOnRefreshListener(this);
//		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//				android.R.color.holo_green_light,
//				android.R.color.holo_orange_light,
//				android.R.color.holo_red_light);
		mSwipeLayout.setScrollBarSize(50);
		mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
	}

	private ArrayList<String> getData() {
		list.add("AutoLinePrefreshListViewDemo");
		list.add("RoundListViewDemo");
		list.add("CustomLayoutActivity");
		list.add("RotatePicBrowserActivity");
		list.add("ExpendLayoutActivity");
		list.add("DragGridActivity");
		list.add("SwipeRefreshLayoutActivity");
		list.add("AutoPrefreshListViewDemo");
		list.add("SacleImageViewDemo");
		list.add("HroizontialListViewActivity");
		list.add("SwpteMenuDropDownListViewActivity");
		return list;
	}

	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mSwipeLayout.setRefreshing(false);
				ArrayList<String> tempList = new ArrayList<String>();
				tempList = list;
				list.addAll(tempList);
				handler.sendEmptyMessage(0);
			}
		}, 2000);
	}

	public Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			mListView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_list_item_1, list));
		};
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		if(position== 0){
			intent.setClass(getApplicationContext(), AutoLinePrefreshListViewActivity.class);
		}else if(position == 1){
			intent.setClass(getApplicationContext(), RoundListViewDemo.class);
		}else if(position == 2){
			intent.setClass(getApplicationContext(), CustomLayoutActivity.class);
		}else if(position == 3){
			intent.setClass(getApplicationContext(), RotatePicBrowserActivity.class);
		}else if(position == 4){
			intent.setClass(getApplicationContext(), ExpendLayoutActivity.class);
		}else if(position == 5){
			intent.setClass(getApplicationContext(), DragGridActivity.class);
		}else if(position == 6){
			intent.setClass(getApplicationContext(), SwipeRefreshLayoutActivity.class);
		}else if(position == 7){
			intent.setClass(getApplicationContext(), AutoPrefreshListViewActivity.class);
		}else if(position == 8){
			intent.setClass(getApplicationContext(), ScaleImageViewActivity.class);
		}else if(position == 9){
			intent.setClass(getApplicationContext(), HroizontialListViewActivity.class);
		}else if(position == 10){
			intent.setClass(getApplicationContext(), SwpteMenuDropDownListViewActivity.class);
		}
		startActivity(intent);
	}
}
