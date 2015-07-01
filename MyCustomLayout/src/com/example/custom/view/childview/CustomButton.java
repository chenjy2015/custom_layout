package com.example.custom.view.childview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.webkit.WebSettings.TextSize;
import android.widget.Button;

public class CustomButton extends View {

	private int mWidth = 80;
	private int mHeight = 60;
	private Activity mContext;
	private Paint mPaint;
	private Paint mTextPaint;
	private Drawable mBackGroundDrawable;
	private int mBackGroundColor;
	private Canvas mCanvas;
	private int mTextColor;
	private int mTextSize=15;
	private String mText;
	private int dpW;//dp与px换算比例
	private int dpH;
	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = (Activity) context;
		mPaint = new Paint();
		mTextPaint = new Paint();
		mBackGroundColor = Color.GREEN;
		mTextSize = 15;
		mTextColor = Color.BLACK;
		mText = "button";
		
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mWidth, mHeight);
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		dpW = dm.widthPixels/160;
		dpH = dm.heightPixels/160;
		mWidth =dpW*50;//换算dp值
		mHeight = dpH*30;
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);

	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mCanvas = canvas;
		Draw();
		
	}
	
	private void Draw(){
		mPaint.setColor(mBackGroundColor);
		mPaint.setAntiAlias(true);
		mCanvas.drawRect(0, 0, mWidth, mHeight, mPaint);
		mTextPaint.setColor(mTextColor);
		mCanvas.drawText(mText, mWidth/10, mHeight/2, mTextPaint);
	}
	
	public void setText(String text){
		mText = text;
	}
	
	public void setTextColor(int color){
		mTextColor = color;
	}
	
	@Override
	public void setBackgroundColor(int color) {
		// TODO Auto-generated method stub
		super.setBackgroundColor(color);
		mBackGroundColor = color;
		invalidate();
	}
}
