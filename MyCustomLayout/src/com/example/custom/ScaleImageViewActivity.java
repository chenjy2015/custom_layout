package com.example.custom;

import android.app.Activity;
import android.os.Bundle;

import com.example.custom.view.ScaleImageView;

/**
 * 可缩放图片展示
 * @author Administrator
 *
 */
public class ScaleImageViewActivity extends Activity {

	private ScaleImageView siv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_scale_imageview);
		siv = (ScaleImageView) findViewById(R.id.iv);
		siv.setImageResource(R.drawable.com_tencent_icon);
	}
	
	

}
