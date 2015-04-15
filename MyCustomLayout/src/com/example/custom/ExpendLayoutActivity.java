package com.example.custom;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.custom.listview.adapter.ExpendGridViewAdapter;
import com.example.custom.listview.adapter.ExpendViewPagerAdapter;

/***
 * 自定义可扩展横向显示GridView
 * 
 * @author chenjy
 * 
 */
public class ExpendLayoutActivity extends Activity {

	// data
	private List<GridView> mData;// 装载GridView的集合
	private List<ResolveInfo> mInfoList;// GridView 数据源
	private static final float APP_PAGE_SIZE = 8.0f;// 每个GridView所需对象数量
	// view
	private ViewPager mVp;
	private ImageView showHint;
	private View layout;
	private int layoutHeight = 100;// ViewGroup的高度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_expendlayout);

		layout = findViewById(R.id.layout);
		// 计算ViewGroup的高度
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(this, layoutHeight));
		layout.setLayoutParams(param);
		showHint = (ImageView) findViewById(R.id.iv);
		showHint.setTag(false);
		showHint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				expendLayout();
			}
		});

		init();
		mVp = (ViewPager) findViewById(R.id.vp);
		ExpendViewPagerAdapter adapter = new ExpendViewPagerAdapter(this, mData);
		mVp.setAdapter(adapter);

	}

	/** dp换算 */
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public void init() {
		PackageManager pm = this.getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// get all apps
		final List<ResolveInfo> apps = pm.queryIntentActivities(mainIntent, 0);
		// 计算一共需要多少页面展示完所有数据
		final int PageCount = (int) Math.ceil(apps.size() / APP_PAGE_SIZE);
		mInfoList = new ArrayList<ResolveInfo>();
		mData = new ArrayList<GridView>();
		for (int i = 0; i < PageCount; i++) {
			GridView gv = new GridView(this);
			ExpendGridViewAdapter adapter = new ExpendGridViewAdapter(this,
					apps, i);
			gv.setAdapter(adapter);
			gv.setColumnWidth(4);
			mData.add(gv);
		}
	}

	// 点击图标扩展或收缩布局
	private void expendLayout() {
		//获取最原始的ViewGroup params
		final LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) layout.getLayoutParams();
		int startHeight = dip2px(this, 100);;
		int endHeight = dip2px(this, 200);
		ValueAnimator va;
		final boolean tag = (Boolean) showHint.getTag();
		if(tag){
			showHint.setTag(false);
			va = ValueAnimator.ofInt(endHeight,startHeight);
			
		}else{
			showHint.setTag(true);
			va = ValueAnimator.ofInt(startHeight,endHeight);
		}
		va.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 根据动画伸展高度 改变ViewGroup高度
				params.height = (Integer) animation.getAnimatedValue();
				int hh = params.height;
				layout.setLayoutParams(params);
			}
		});
		va.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				boolean flag = (Boolean) showHint.getTag();
				showHint.setImageResource(flag?R.drawable.zhan:R.drawable.suo);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		va.setDuration(300);
		va.start();
	}

}
