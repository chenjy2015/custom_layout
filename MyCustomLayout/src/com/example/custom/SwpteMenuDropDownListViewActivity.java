package com.example.custom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.example.custom.swipemenu.DropDownListView;
import com.example.custom.swipemenu.DropDownListView.OnDropDownListener;
import com.example.custom.swipemenu.SwipeMenu;
import com.example.custom.swipemenu.SwipeMenuCreator;
import com.example.custom.swipemenu.SwipeMenuItem;
import com.example.custom.swipemenu.SwipeMenuItemToggleListenner;
import com.example.custom.swipemenu.SwipeMenuListView;
import com.example.custom.swipemenu.SwipeMenuListView.OnMenuItemClickListener;

/**
 * 结合下拉刷新与左右滑动出菜单ListView
 * 并解决了Item左右滑动与slidmenu的冲突问题
 * 重新设置了Item滑出与收回的事件
 * @author chenjy
 * 2015.3.18
 */
public class SwpteMenuDropDownListViewActivity extends Activity implements
		SwipeMenuItemToggleListenner, OnDropDownListener, OnClickListener,
		OnItemClickListener, OnMenuItemClickListener {

	private ArrayAdapter<String> mAdapter;
	private SwipeMenuListView mSwipeMenuListView;// 滑动菜单ListView
	private DropDownListView mDropListView;// 下拉ListView
	private Context mContext;
	private SwipeMenuItemToggleListenner mSwipeChangeListenner;// 滑动菜单展开与关闭回调
	private ArrayList<String> mData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hroizontial);
		mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.listview);
		mContext = this;
		initView();
		initData();
		setAdapter();
	}

	private void initData() {
		mData = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			mData.add(getTimeFormat());
		}
	}

	private String getTimeFormat() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		return format.format(date);
	}

	private void setAdapter() {
		if (mAdapter == null && mData != null) {
			mAdapter = new ArrayAdapter<String>(mContext,
					android.R.layout.simple_dropdown_item_1line,
					android.R.id.text1, mData);
		}
		mSwipeMenuListView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	private void initView() {
		// swipemenulistview继承了droplistview
		mDropListView = mSwipeMenuListView;
		// mDropListView.setAutoLoadOnBottom(true);
		mDropListView.setDropDownStyle(true);
		mDropListView.setOnBottomStyle(true);
		mDropListView.setDrawSelectorOnTop(false);
		mDropListView.setPadding(0, 0, 0, 40);
		mDropListView.setOnDropDownListener(this);// 下拉监听设置
		mDropListView.setOnBottomListener(this);// 屏幕滑动到底部监听

		// 创建滑出按钮
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(mContext);
				// set item background
				openItem.setBackground(new ColorDrawable(mContext
						.getResources().getColor(R.color.blue_litter)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Delete");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);
			}
		};

		// 设置滑出按钮
		mSwipeMenuListView.setMenuCreator(creator);
		// 设置Item点击事件
		mSwipeMenuListView.setOnItemClickListener(this);
		// 设置滑动弹出的Item点击事件
		mSwipeMenuListView.setOnMenuItemClickListener(this);
		// 设置Item滑动菜单展开与关闭监听
		mSwipeMenuListView
				.setOnSwipeMenuItemToggleListenner(mSwipeChangeListenner);
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	/** 滑动菜单Item点击事件监听 */
	@Override
	public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		// TODO Auto-generated method stub

		return false;
	}

	/** 下拉事件触发 */
	@Override
	public void onDropDown() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessageDelayed(101, 1000);
	}

	/** ListView 滑动到底部位置监听 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	/** Item点击事件监听 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "item"+position, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSwipeItemOpen() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "onSwipeItemOpen", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onSwipeItemClose() {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "onSwipeItemClose", Toast.LENGTH_SHORT).show();
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//下拉结束恢复常态
			mDropListView.onDropDownComplete();
		};
	};

}
