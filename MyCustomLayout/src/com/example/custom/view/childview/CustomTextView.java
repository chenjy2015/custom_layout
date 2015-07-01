package com.example.custom.view.childview;

/**
 * 自定义TextView
 * @author chenjy
 * 2015.3.18
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomTextView extends View {
	
	private Paint mPaint;
	private String text;

	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//绘制背景
		mPaint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		//绘制图片 这里包括文字
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(20);
		String text = "custom text";
		canvas.drawText(text, getWidth()/2, getHeight()/2, mPaint);//居中显示
	}
}
