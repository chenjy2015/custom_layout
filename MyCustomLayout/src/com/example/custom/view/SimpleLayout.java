package com.example.custom.view;

/**
 * 自定义ViewGroup
 * @author chenjy
 * 2015.3.18
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SimpleLayout extends ViewGroup {

	private Paint mPaint;//画笔
	

	private float mCx;//圆心的x坐标
	private float mCy;//圆心的y坐标
	private float mRadius;//圆的半径。
	private Bitmap mBitmap;//画布背景
	private int mBackgroundColor;//默认背景颜色
	private Activity mContext;
	
	
	public SimpleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = (Activity) context;
		mPaint = new Paint();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mCx = widthMeasureSpec/2;
		mCy = heightMeasureSpec/2;
		mRadius = widthMeasureSpec/2;//半径取宽度一半既中心点
		mBackgroundColor = Color.WHITE;//默认白色背景
		if(getChildCount() > 0){
			View childView = getChildAt(0);
			measureChild(childView, getMeasuredWidth(), getMeasuredHeight());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if(getChildCount() > 0){
			View childView = getChildAt(0);
			childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
		}
	}
	
	@SuppressLint({ "DrawAllocation", "NewApi" })
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		mPaint.setColor(mBackgroundColor);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		canvas = new Canvas(mBitmap);
		canvas.drawCircle(mCx, mCy, mRadius, mPaint);
	}
	
	//这里为xml中配置的颜色值
	@Override
	public void setBackgroundColor(int color) {
		// TODO Auto-generated method stub
		super.setBackgroundColor(color);
		mBackgroundColor = color;
	}
	
	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	public float getmCx() {
		return mCx;
	}

	public void setmCx(float mCx) {
		this.mCx = mCx;
	}

	public float getmCy() {
		return mCy;
	}

	public void setmCy(float mCy) {
		this.mCy = mCy;
	}

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
	

}
