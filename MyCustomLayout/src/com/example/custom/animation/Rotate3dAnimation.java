package com.example.custom.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {

	
	private float mFromDegrees;
	private float mToDegrees;
	private float mCenterX;
	private float mCenterY;
	private float mDepthZ;
	private boolean mReverse;
	private Camera mCamera;
	
	public Rotate3dAnimation(float fromDegrees, float toDegrees,  
            float centerX, float centerY, float depthZ, boolean reverse) {  
        mFromDegrees = fromDegrees;  
        mToDegrees = toDegrees;  
        mCenterX = centerX;  
        mCenterY = centerY;  
        mDepthZ = depthZ;  
        mReverse = reverse;  
    }  
	
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
		
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// TODO Auto-generated method stub
		super.applyTransformation(interpolatedTime, t);
		
		final float fromDegrees = mFromDegrees;  
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);  
  
        final float centerX = mCenterX;  
        final float centerY = mCenterY;
        //Camera就像一个摄像机，一个物体在原地不动，然后我们带着这个摄像机四处移动，在摄像机里面呈现出来的画面，就会有立体感，就可以从各个角度观看这个物体。
        //它有旋转、平移的一系列方法，实际上都是在改变一个Matrix对象，一系列操作完毕之后，我们得到这个Matrix，然后画我们的物体，就可以了。
        final Camera camera = mCamera;  
  
        final Matrix matrix = t.getMatrix();
        //保存原有参数
        camera.save();  
        if (mReverse) {  
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);  //平移一段距离
        } else {  
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime)); //平移一段距离
        }  
        camera.rotateY(degrees);
        //将Camera对象的变化参数设置到matrix对象上面
        camera.getMatrix(matrix);
        //恢复参数
        camera.restore();

        //preTranslate函数是在缩放前移动而postTranslate是在缩放完成后移动。
        matrix.preTranslate(-centerX, -centerY);  
        matrix.postTranslate(centerX, centerY);  
	}
	
	
}
