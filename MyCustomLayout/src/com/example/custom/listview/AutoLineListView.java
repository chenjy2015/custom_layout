package com.example.custom.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.custom.R;

/**
 * 自定下拉刷新 上拉加载
 * 
 * @author chenjy
 * @create: 2015.3.20
 * 
 */
public class AutoLineListView extends ListView implements OnScrollListener,
		AnimationListener {

	public View headView;
	public View footView;
	/** headView */
	public ImageView headIv1, headIv2;
	/** footView */
	private ImageView arrow;
	private TextView noData;
	private TextView loadFull;
	private TextView more;
	private ProgressBar loading;

	private ScaleAnimation mScale1, mScale2;
	private TranslateAnimation mTReverseAnimation1, mTReverseAnimation2;
	private RotateAnimation rotateLoad, rotateReverse;// 下拉箭头动画
	public OnRefreshListenner onRefreshListenner;
	public OnLoadListenner onLoadListenner;
	private LayoutInflater inflater;

	public int headViewHeight;
	public int footViewHeight;
	public int ImageViewWidth = 50;// 默认移动Item的宽度

	private int firstVisibleItem;
	private int scrollState;
	private int pageSize = 10;
	public int startY;// 记录用户按下位置
	public int mPullMaxHeight = 100;// 下拉最大值默认60dp
	public int state;// 标识当前状态
	public static boolean isStartRefresh;// 标识是否在刷新状态
	private boolean isLoading;// 标识是否正在加载数据
	private boolean loadEnable = true;// 开启或者关闭加载更多功能
	private boolean isLoadFull;// 标识加载数据是否已满
	private boolean isBottom;// 判断是否滑动到了底部

	public static final int NONE = 0;// 正常情况
	public static final int PULL = 1;// 下拉
	public static final int PUSH = 2;// 上拉加载
	public static final int REFRESHING = 3;// 正在刷新
	public static final int RELEAST = 4;// 上推 放弃刷新

	public AutoLineListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public AutoLineListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public AutoLineListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		// TODO Auto-generated constructor stub

		inflater = LayoutInflater.from(getContext());
		headView = inflater.inflate(R.layout.item_autolist_head, null);
		headIv1 = (ImageView) headView.findViewById(R.id.headIv1);
		headIv2 = (ImageView) headView.findViewById(R.id.headIv2);

		inflater = LayoutInflater.from(getContext());
		footView = inflater.inflate(R.layout.listview_footer, null);
		arrow = (ImageView) footView.findViewById(R.id.arrow);
		loadFull = (TextView) footView.findViewById(R.id.loadFull);
		noData = (TextView) footView.findViewById(R.id.noData);
		more = (TextView) footView.findViewById(R.id.more);
		loading = (ProgressBar) footView.findViewById(R.id.loading);

		measureView(headView);
		measureView(footView);
		headViewHeight = headView.getMeasuredHeight();
		footViewHeight = footView.getMeasuredHeight();
		// ImageViewWidth = headIv1.getMeasuredWidth();
		this.addHeaderView(headView);
		this.addFooterView(footView);
		drawableViewBottomPadding(-footViewHeight);
		footView.setVisibility(View.GONE);// 隐藏底部View
		this.setOnScrollListener(this);
		initAnimation();

	}

	// 用来计算header大小的。比较隐晦。因为header的初始高度就是0,貌似可以不用。
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = -p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/** 初始化设置头部控件伸展动画 */
	public void initAnimation() {
		mTReverseAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 100.0f, Animation.RELATIVE_TO_SELF, 0.0f, 0, 0, 0, 0);
		mTReverseAnimation1.setInterpolator(new LinearInterpolator());
		mTReverseAnimation1.setDuration(300);
		mTReverseAnimation1.setRepeatMode(Animation.RESTART);
		mTReverseAnimation1.setAnimationListener(this);
		mTReverseAnimation1.setRepeatCount(1);
		mTReverseAnimation1.setFillAfter(true);
		mTReverseAnimation1.setAnimationListener(this);
		
		mTReverseAnimation2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF, 100.f, 0, 0, 0, 0);
		mTReverseAnimation2.setInterpolator(new LinearInterpolator());
		mTReverseAnimation2.setDuration(300);
		mTReverseAnimation2.setRepeatMode(Animation.RESTART);
		mTReverseAnimation2.setRepeatCount(1);
		mTReverseAnimation2.setFillAfter(true);
		mTReverseAnimation2.setAnimationListener(this);
		// 设置箭头特效
		rotateLoad = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateLoad.setInterpolator(new LinearInterpolator());
		rotateLoad.setDuration(300);
		rotateLoad.setFillAfter(true);

		rotateReverse = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateReverse.setInterpolator(new LinearInterpolator());
		rotateReverse.setDuration(300);
		rotateReverse.setFillAfter(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int y = (int) ev.getY();
		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getY();
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_MOVE:
			setState(ev, y);
			int height = (int) (ev.getY() - startY);// 计算拉动的距离
			if (firstVisibleItem == 0) {
				// drawableViewWidth(getCurrentPullScale(height));
				drawableViewWidth(height);
				// drawableViewTopPadding(height);
				// setState(ev, y);
			} else if (isBottom && state == PULL) {
				drawableViewBottomPadding(height);
			}
			break;
		case MotionEvent.ACTION_UP:
			// setState(ev, y);
			updateViewByState();
			// 保证允许刷新 且目前可见Item第一项是数据源ListView的Item第一项
			if (isStartRefresh && firstVisibleItem == 0) {
				onRefresh();
			} else if (isLoading && isBottom) {
				onLoad();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/** 根据触摸对象判断当前处于那种状态 */
	public void setState(MotionEvent ev, int y) {
		// 下拉中
		if (ev.getY() > startY) {
			int absolute = Math.abs((int) ev.getY() - startY);// 求触摸起点到终点的绝对值
			// 下拉值超过最大限度值 下拉刷新
			if (absolute >= (mPullMaxHeight)) {
				state = REFRESHING;
				isStartRefresh = true;
				// 下拉值少于最大限度 不刷新
			} else {
				// 下拉中
				if (ev.getY() > y) {
					state = PULL;
				} else {
					state = RELEAST;
				}
			}
			// 上拉中
		} else if (ev.getY() < startY) {
			int absolute = Math.abs(startY - (int) ev.getY());// 求触摸起点到终点的绝对值
			// 下拉值超过最大限度值 下拉刷新
			if (absolute >= (mPullMaxHeight) && isBottom) {
				isLoading = true;
				footView.setVisibility(View.VISIBLE);// 下拉值到了 展示底部
				state = PUSH;
				// 上拉值少于最大限度 不刷新
			} else {
				// 上拉中
				if (y > ev.getY()) {
					state = PULL;
					startLoad();
				} else {
					state = RELEAST;
					stopLoad();
				}
			}
		} else {
			state = RELEAST;
			stopLoad();
			isStartRefresh = false;
		}
	}

	/** 更新View */
	public void updateViewByState() {
		switch (state) {
		case NONE:
			drawableViewTopPadding(-headViewHeight);
			headIv1.getLayoutParams().width = ImageViewWidth;
			headIv2.getLayoutParams().width = ImageViewWidth;
			stopRefresh();
			break;

		case PULL:
			onRefreshComplete();
			break;

		case PUSH:
			startLoad();
			break;

		case REFRESHING:
			isStartRefresh = true;
			startRefresh();
			break;

		case RELEAST:
			onRefreshComplete();
			onLoadComplete();
			break;
		}
	}

	public int getImageViewWidth() {
		return ImageViewWidth;
	}

	/** 设置刷新控件的宽度 */
	public void setImageViewWidth(int imageViewWidth) {
		ImageViewWidth = imageViewWidth;
	}

	/** 设置下拉刷新最大长度值 */
	public void setMaxPullHeight(int height) {
		mPullMaxHeight = height;
	}

	/** 重新绘制头部View距离顶部高度 */
	public void drawableViewTopPadding(int topPadding) {
		if (topPadding > mPullMaxHeight) {
			topPadding = mPullMaxHeight;
		}
		headView.setPadding(headView.getPaddingLeft(), topPadding,
				headView.getPaddingRight(), headView.getPaddingBottom());
		headView.invalidate();
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

	/** 重新绘制头部View中的ImageView宽度 */
	public void drawableViewWidth(int width) {
		android.view.ViewGroup.LayoutParams params = headIv1.getLayoutParams();
		params.width = (ImageViewWidth + width);
		headIv1.setLayoutParams(params);
		android.view.ViewGroup.LayoutParams params2 = headIv2.getLayoutParams();
		params2.width = (ImageViewWidth + width);
		headIv2.setLayoutParams(params2);
		headIv1.invalidate();
		headIv2.invalidate();
	}

	/** 上拉加载底部箭头旋转动画 */
	public void startPushAnima(int rotate) {
		arrow.startAnimation(rotateLoad);
	}

	/** 上推放弃加载底部箭头动画 */
	public void stopPushAnima(int rotate) {
		arrow.startAnimation(rotateReverse);
	}

	/** 通过对拉动长度 计算头部View对应拉伸的比例 */
	public int getCurrentPullScale(int height) {
		if (height >= mPullMaxHeight) {
			return mPullMaxHeight;
		} else {
			return ImageViewWidth * (height / mPullMaxHeight);
		}
	}

	/** 循环播放刷新动画 两个Item相反方向移动 */
	private void startRefresh() {
		headIv1.getLayoutParams().width = ImageViewWidth;
		headIv2.getLayoutParams().width = ImageViewWidth;
		headIv2.startAnimation(mTReverseAnimation2);
		//headIv1.startAnimation(mTReverseAnimation1);
	}

	private void stopRefresh() {
		headIv1.clearAnimation();
		headIv2.clearAnimation();
	}

	private void startLoad() {
		arrow.startAnimation(rotateLoad);
	}

	private void stopLoad() {
		arrow.startAnimation(rotateReverse);
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
		drawableViewWidth(ImageViewWidth);
		stopRefresh();
	}

	/** 用于加载更多结束后的回调 */
	public void onLoadComplete() {
		isLoading = false;
		rotateLoad.reset();
		rotateReverse.reset();
		arrow.clearAnimation();
		drawableViewBottomPadding(footViewHeight);
		footView.setVisibility(View.GONE);
	}

	public void setResultSize(int resultSize) {
		if (resultSize == 0) {
			isLoadFull = true;
			loadFull.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		} else if (resultSize > 0 && resultSize < pageSize) {
			isLoadFull = true;
			loadFull.setVisibility(View.VISIBLE);
			loading.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			noData.setVisibility(View.GONE);
		} else if (resultSize == pageSize) {
			isLoadFull = false;
			loadFull.setVisibility(View.GONE);
			loading.setVisibility(View.VISIBLE);
			more.setVisibility(View.VISIBLE);
			noData.setVisibility(View.GONE);
		}

	}

	public void onRefresh() {
		if (onRefreshListenner != null) {
			onRefreshListenner.onRefresh();
		}
	}

	public void onLoad() {
		if (onLoadListenner != null) {
			onLoadListenner.onLoad();
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 设置刷新监听 */
	public void setOnRefreshListenner(OnRefreshListenner onRefreshListenner) {
		this.onRefreshListenner = onRefreshListenner;
	}

	/** 设置加载监听 */
	public void setOnLoadListenner(OnLoadListenner onLoadListenner) {
		this.onLoadListenner = onLoadListenner;
	}

	/*
	 * 定义下拉刷新接口
	 */
	public interface OnRefreshListenner {
		public void onRefresh();
	}

	/*
	 * 定义加载更多接口
	 */
	public interface OnLoadListenner {
		public void onLoad();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		if (firstVisibleItem + visibleItemCount == totalItemCount) {
			isBottom = true;
		} else {
			isBottom = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		// ifNeedLoad(view, scrollState);
	}

	// 根据listview滑动的状态判断是否需要加载更多
	private void ifNeedLoad(AbsListView view, int scrollState) {
		if (!loadEnable) {
			return;
		}
		try {
			// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
			// && !isLoading
			// && view.getLastVisiblePosition() == view
			// .getPositionForView(footView) && !isLoadFull) {
			// onLoad();
			// isLoading = true;
			// }
			// 判断是否可见Item ，是否到最底部位置，是否拉伸位置的状态是上拉加载
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == view
							.getPositionForView(footView) && state == PUSH) {
				// 判断当前是否正在加载数据 或数据已经加载完毕
				if (!isLoading && !isLoadFull) {
					onLoad();
					isLoading = true;
				}
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

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

}
