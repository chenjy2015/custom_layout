package com.example.custom.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.listview.AutoLineListView.OnLoadListenner;
import com.example.custom.listview.AutoLineListView.OnRefreshListenner;

@SuppressLint("ClickableViewAccessibility")
public class AutoLineListView2 extends ListView implements OnScrollListener{

	private Context mContext;
	private LayoutInflater mInflater;

	/**haedview*/
	private View headView;
	private TextView leftImg;
	private TextView rightImg;
	
	/**footview*/
	private View footView;
	private ImageView arrow;
	private TextView noData;
	private TextView loadFull;
	private TextView more;
	private ProgressBar loading;
	
	/**measured param*/
	public int headViewHeight;//头部View高度
	public int footViewHeight;//底部View高度
	public int mPullMaxHeight;//下拉最大值默认60dp
	
	/**callback listenner*/
	public OnRefreshListenner onRefreshListenner;//下拉刷新回调接口
	public OnLoadListenner onLoadListenner;//上拉加载回调接口
	
	/**The calculated value*/
	private float startX;
	private float startY;
	
	
	
	/** dp换算 */
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	public AutoLineListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	
	
	public void init(){
		
		/**headView*/
		mInflater = LayoutInflater.from(mContext);
		headView = mInflater.inflate(R.layout.item_autolist_head, null);
		leftImg = (TextView) headView.findViewById(R.id.headIv1);
		rightImg = (TextView) headView.findViewById(R.id.headIv2);
		leftImg.setVisibility(View.GONE);
		rightImg.setVisibility(View.GONE);
		
		/**footview*/
		mInflater = LayoutInflater.from(mContext);
		footView = mInflater.inflate(R.layout.listview_footer, null);
		arrow = (ImageView) footView.findViewById(R.id.arrow);
		loadFull = (TextView) footView.findViewById(R.id.loadFull);
		noData = (TextView) footView.findViewById(R.id.noData);
		more = (TextView) footView.findViewById(R.id.more);
		loading = (ProgressBar) footView.findViewById(R.id.loading);
		
		/**MeasuredViewHeight*/
		mPullMaxHeight = dip2px(mContext, 60);
		headViewHeight = headView.getMeasuredHeight();
		footViewHeight = footView.getMeasuredHeight();
		
		/**initView*/
		this.addHeaderView(headView);
		this.addFooterView(footView);
		drawableViewBottomPadding(-footViewHeight);
		footView.setVisibility(View.GONE);// 隐藏底部View
		setOnScrollListener(this);
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
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN://按下
			startX = ev.getX();
			startY = ev.getY();
			break;
			
		case MotionEvent.ACTION_MOVE://移动中
			
			break;
			
		case MotionEvent.ACTION_UP://弹起
			break;

		}
		
		return super.onTouchEvent(ev);
	}
	
	
	private void setState(){
		
	}



	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	/** 设置刷新监听 */
	public void setOnRefreshListenner(OnRefreshListenner onRefreshListenner) {
		this.onRefreshListenner = onRefreshListenner;
	}

	/** 设置加载监听 */
	public void setOnLoadListenner(OnLoadListenner onLoadListenner) {
		this.onLoadListenner = onLoadListenner;
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


}
