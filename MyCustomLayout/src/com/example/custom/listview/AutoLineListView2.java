package com.example.custom.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.R.color;

@SuppressLint("ClickableViewAccessibility")
public class AutoLineListView2 extends ListView implements OnScrollListener,
		AnimationListener {

	private Context mContext;
	private LayoutInflater mInflater;

	/** haedview */
	private View headView;
	private TextView leftImg;
	private TextView rightImg;

	/** footview */
	private View footView;
	private ImageView arrow;
	private TextView noData;
	private TextView loadFull;
	private TextView more;
	private ProgressBar loading;

	/** measured param */
	public int headViewHeight;// 头部View高度
	public int footViewHeight;// 底部View高度
	public int mImageWidth;// 头部View的Img控件宽度
	public int mPullMaxHeight;// 下拉最大值默认60dp

	/** callback listenner */
	public OnRefreshListenner onRefreshListenner;// 下拉刷新回调接口
	public OnLoadListenner onLoadListenner;// 上拉加载回调接口

	/** The calculated value */
	private int startY;
	private boolean isStartRefresh;// 标识当前为刷新状态
	private boolean isStartLoad;// 标识当前为加载状态
	private boolean isTop;// 标识是否第一个Item处于可见状态
	private boolean isBottom;// 标识最后一个Item是否处于可见状态
	private int state;// 标识当前状态
	private static final int PULL = 0;// 下拉状态
	private static final int RELEAST = 1;// 释放 下拉或上推
	private static final int PUSH = 2;// 上推状态

	/** animation */
	private TranslateAnimation mTReverseAnimation1, mTReverseAnimation2;

	/** dp换算 */
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public AutoLineListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mPullMaxHeight = dip2px(mContext, 60);// 初始化最大滑动值为60dp
		init();
		initAnimation();
	}

	public void init() {

		/** headView */
		mInflater = LayoutInflater.from(mContext);
		headView = mInflater.inflate(R.layout.item_autolist_head, null);
		leftImg = (TextView) headView.findViewById(R.id.headIv1);
		rightImg = (TextView) headView.findViewById(R.id.headIv2);

		/** footview */
		mInflater = LayoutInflater.from(mContext);
		footView = mInflater.inflate(R.layout.listview_footer, null);
		arrow = (ImageView) footView.findViewById(R.id.arrow);
		loadFull = (TextView) footView.findViewById(R.id.loadFull);
		noData = (TextView) footView.findViewById(R.id.noData);
		more = (TextView) footView.findViewById(R.id.more);
		loading = (ProgressBar) footView.findViewById(R.id.loading);

		/** MeasuredViewHeight */
		mImageWidth = leftImg.getMeasuredWidth();
		headViewHeight = headView.getMeasuredHeight();
		footViewHeight = footView.getMeasuredHeight();

		/** initView */
		this.addHeaderView(headView);
		this.addFooterView(footView);
		drawableViewBottomPadding(-footViewHeight);
		footView.setVisibility(View.GONE);// 隐藏底部View
		setOnScrollListener(this);
	}

	public void initAnimation() {
		mTReverseAnimation1 = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 100.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, 0, 0, 0, 0);
		mTReverseAnimation1.setInterpolator(new LinearInterpolator());
		mTReverseAnimation1.setDuration(300);
		mTReverseAnimation1.setRepeatMode(Animation.RESTART);
		mTReverseAnimation1.setRepeatCount(1);
		mTReverseAnimation1.setFillAfter(true);
		mTReverseAnimation1.setAnimationListener(this);

		mTReverseAnimation2 = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF,
				100.f, 0, 0, 0, 0);
		mTReverseAnimation2.setInterpolator(new LinearInterpolator());
		mTReverseAnimation2.setDuration(300);
		mTReverseAnimation2.setRepeatMode(Animation.RESTART);
		mTReverseAnimation2.setRepeatCount(1);
		mTReverseAnimation2.setFillAfter(true);
		mTReverseAnimation2.setAnimationListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int y = (int) ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:// 按下
			startY = (int) ev.getY();
			break;

		case MotionEvent.ACTION_MOVE:// 移动中
			setState(y, ev);// 设置状态
			int abs = (int) ev.getY() - startY;
			// 绘制头部或尾部View
			if (isTop) {
				drawHeadViewLine(abs);
			} else if (isBottom) {
				drawableViewBottomPadding(abs);
			}
			break;

		case MotionEvent.ACTION_UP:// 弹起
			int absUp = (int) ev.getY() - startY;
			updateByState();
			break;

		}

		return super.onTouchEvent(ev);
	}

	/** 设置当前滑动事件状态 */
	private void setState(int y, MotionEvent ev) {
		// 判断移动Y轴差值有没有超过最大限度值的一半
		// if (ev.getY() > y && isTop) {// 下拉
		// currentEventType = CURRENT_EVENT_TYPE_PULL;
		// } else {// 上推
		// currentEventType = CURRENT_EVENT_TYPE_PUSH;
		// }
		// 下拉刷新状态
		if (isTop) {
			if (ev.getY() > y) {// 下拉
				int differ = (int) ev.getY() - y;// 计算差值
				if (differ > mPullMaxHeight / 2) {// 如果下拉超过最大下拉限度的一半则进入开始刷新状态
					state = PULL;
				} else {
					state = RELEAST;
				}
			} else {// 上推
				state = PUSH;
			}
			// 上滑加载状态
		} else if (isBottom) {
			if (y > ev.getY()) {

			}
		}
	}

	/** 根据滑动事件状态 设置当前操作 */
	private void updateByState() {

		switch (state) {
		case PULL:
			isStartRefresh = true;
			startRefresh();
			startRefreshAnimation();
			break;
		case PUSH:
			isStartRefresh = false;
			break;
		case RELEAST:
			isStartRefresh = false;
			break;
		}
	}

	/** 重新绘制头部View中的ImageView宽度 */ 
	private void drawHeadViewLine(int width) {
		if (width < 0) {
			width = mImageWidth;
		}
		leftImg.getLayoutParams().width = width;
		rightImg.getLayoutParams().width = width;
		leftImg.invalidate();
		rightImg.invalidate();
	}

	/** 重新绘制底部View距离底部高度 */
	public void drawableViewBottomPadding(int bottomPadding) {
		if (bottomPadding > mPullMaxHeight) {
			bottomPadding = mPullMaxHeight;
		}
		footView.setPadding(headView.getPaddingLeft(),
				footView.getPaddingTop(), headView.getPaddingRight(),
				bottomPadding);
		footView.invalidate();
	}

	public void startRefresh() {
		onRefreshListenner.onRefresh();
	}
	
	public void stopRefresh(){
		isStartRefresh = false;
	}
	
	public void startLoading() {
		onLoadListenner.onLoad();
	}
	
	public void stopLoading(){
		isStartLoad = false;
	}
	
	
	/**开始刷新时动画*/
	public void startRefreshAnimation(){
		leftImg.getLayoutParams().width = mImageWidth;
		rightImg.getLayoutParams().width = mImageWidth;
		leftImg.startAnimation(mTReverseAnimation1);
		rightImg.startAnimation(mTReverseAnimation2);
	}
	
	/**结束刷新动画*/
	public void stopRefreshAnimation(){
		leftImg.clearAnimation();
		rightImg.clearAnimation();
		mTReverseAnimation1.reset();
		mTReverseAnimation2.reset();
	}
	
	/** 用于下拉刷新结束后的回调 */
	public void onRefreshComplete() {
		// 启动动画 头部布局慢慢上滑 隐退
//		TranslateAnimation mTransAnima = new TranslateAnimation(0, 0,
//				mPullMaxHeight, -mPullMaxHeight);
//		mTransAnima.setDuration(500);
//		mTransAnima.setInterpolator(new LinearInterpolator());
//		headView.startAnimation(mTransAnima);
		// 重置头部布局
		isStartRefresh = false;
		mTReverseAnimation1.reset();
		mTReverseAnimation2.reset();
		drawHeadViewLine(mImageWidth);
		stopRefresh();
	}
	
	
	/** 用于加载更多结束后的回调 */
	public void onLoadComplete() {
		isStartLoad = false;
		drawableViewBottomPadding(-footViewHeight);
		footView.setVisibility(View.GONE);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
//		if (firstVisibleItem + visibleItemCount == totalItemCount) {
//			isBottom = true;
//		} else {
//			isBottom = false;
//		}
//		if(firstVisibleItem == view.getFirstVisiblePosition()){
//			isTop = true;
//		}else{
//			isTop = false;
//		}
	}

	/** 设置刷新监听 */
	public void setOnRefreshListenner(OnRefreshListenner onRefreshListenner) {
		this.onRefreshListenner = onRefreshListenner;
	}

	/** 设置加载监听 */
	public void setOnLoadListenner(OnLoadListenner onLoadListenner) {
		this.onLoadListenner = onLoadListenner;
	}
	
	public void setMaxPullHeight(int maxPullHeight){
		this.mPullMaxHeight = maxPullHeight;
	}
	
	public void setImageViewWidth(int width){
		this.mImageWidth = width;
	}

	/**
	 * 定义下拉刷新接口
	 */
	public interface OnRefreshListenner {
		public void onRefresh();
	}

	/**
	 * 定义加载更多接口
	 */
	public interface OnLoadListenner {
		public void onLoad();
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		// 循环播放动画
		if (isStartRefresh && !animation.isInitialized()) {
			animation.startNow();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
