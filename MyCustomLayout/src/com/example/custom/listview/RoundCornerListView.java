package com.example.custom.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.custom.R;


/**
 * @author: 自定义圆角ListView
 * @Create: 2015.3.17
 * chenjy
 */
public class RoundCornerListView extends ListView {
	
	public RoundCornerListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RoundCornerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RoundCornerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			// 如果无效 不做处理
			if(itemnum == AdapterView.INVALID_POSITION){
				break;
			}else{
				if(itemnum == 0){
					if(itemnum == (getAdapter().getCount()-1)){
						 //只有一项
                        setSelector(R.drawable.app_list_corner_round); 
//                        setBackgroundResource(R.drawable.item_app_list_corner_round_background);
					}else{
						 //第一项
                        setSelector(R.drawable.app_list_corner_round_top); 
					}
				}else if(itemnum == (getAdapter().getCount() - 1)){
					 //如果list.size() == 0 
                    setSelector(R.drawable.app_list_corner_round_bottom);
				}else{
					 //中间项
                    setSelector(R.drawable.app_list_corner_round_center); 
				}
				
			}
			
			break;

		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
}











