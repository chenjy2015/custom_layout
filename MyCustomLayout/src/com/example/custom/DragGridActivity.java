package com.example.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import com.example.custom.view.DragGridView;
import com.example.custom.view.DragGridView.OnChanageListener;


/***
 * 长按Item可移动的GridView
 * 
 * @author chenjy
 * 
 */
public class DragGridActivity extends Activity implements OnChanageListener{

	private DragGridView mDragGridView;
	private List<HashMap<String, Object>> mDataSourceList;
	private SimpleAdapter mSimpleAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_gridview);
		mDragGridView = (DragGridView) findViewById(R.id.dragGridView);
		mDragGridView.setOnChangeListener(this);
		init();
		initView();
		
	}
	
	public void init(){
		mDataSourceList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 30; i++) {
			HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
			itemHashMap.put("item_image",R.drawable.com_tencent_icon);
			itemHashMap.put("item_text", "拖拽 " + Integer.toString(i));
			mDataSourceList.add(itemHashMap);
		}
	}
	
	public void initView(){
		mSimpleAdapter = new SimpleAdapter(this, mDataSourceList,
				R.layout.grid_item, new String[] { "item_image", "item_text" },
				new int[] { R.id.item_image, R.id.item_text });
		mDragGridView.setAdapter(mSimpleAdapter);
	}

	@Override
	public void onChange(int from, int to) {
		// TODO Auto-generated method stub
		HashMap<String, Object> temp = mDataSourceList.get(from);
		//这里的处理需要注意下
		if(from < to){
			for(int i=from; i<to; i++){
				Collections.swap(mDataSourceList, i, i+1);
			}
		}else if(from > to){
			for(int i=from; i>to; i--){
				Collections.swap(mDataSourceList, i, i-1);
			}
		}
		
		mDataSourceList.set(to, temp);
		
		mSimpleAdapter.notifyDataSetChanged();
	}
	
}
