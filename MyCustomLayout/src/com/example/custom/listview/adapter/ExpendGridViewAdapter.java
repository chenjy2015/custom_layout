package com.example.custom.listview.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpendGridViewAdapter extends BaseAdapter {
	
	private List<ResolveInfo> mInfoList;
	private Context mContext;
	public static final int APP_PAGE_SIZE = 8;//每一页装载数据的大小
	private PackageManager pm;//定义一个PackageManager对象\
	
	public ExpendGridViewAdapter(Context context,List<ResolveInfo> apps,int index){
		this.mContext = context;
		pm = mContext.getPackageManager();
		this.mInfoList = new ArrayList<ResolveInfo>();
		initData(apps,index);
	}
	
	/***
	 * 初始化数据源
	 */
	private void initData(List<ResolveInfo> apps,int index){
		int startIndex = APP_PAGE_SIZE * index;
		int endIndex = APP_PAGE_SIZE +startIndex;
		while ((startIndex<apps.size()) && (startIndex<endIndex)) {
			mInfoList.add(apps.get(startIndex));
			startIndex++;
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mVh;
		if(convertView == null){
			mVh = new ViewHolder();
			mVh.layout = new LinearLayout(mContext);
			mVh.iv = new ImageView(mContext);
			mVh.tv = new TextView(mContext);
			
			//ViewGroup
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mVh.layout.setLayoutParams(params);
			mVh.layout.setOrientation(LinearLayout.VERTICAL);
			
			//ImageView
			LayoutParams ImageParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mVh.iv.setLayoutParams(ImageParams);
			mVh.iv.setScaleType(ScaleType.CENTER_INSIDE);
			mVh.layout.addView(mVh.iv);
			//TextViewse
			LayoutParams TextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mVh.tv.setLayoutParams(TextParams);
			mVh.tv.setTextColor(Color.WHITE);
			mVh.layout.addView(mVh.tv);
			
			//绑定Tag
			convertView = mVh.layout;
			convertView.setTag(mVh);
		}else{
			mVh = (ViewHolder) convertView.getTag();
		}
		
		//ImageView
		mVh.iv.setImageDrawable(mInfoList.get(position).loadIcon(pm));
		//TextView
		mVh.tv.setText(mInfoList.get(position).loadLabel(pm));
//		convertView.setTag(mVh);
		return mVh.layout;
	}
	
	public class ViewHolder{
		private LinearLayout layout;
		private ImageView iv;
		private TextView tv;
	}

}
