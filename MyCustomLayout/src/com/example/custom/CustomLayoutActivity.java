package com.example.custom;

import com.example.custom.view.CustomTextView;

import android.app.Activity;
import android.os.Bundle;
/**
 * 自定义View
 * @author chenjy
 * 2015.3.18
 */
public class CustomLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_layout);
		
//		CustomTextView ctv = (CustomTextView) findViewById(R.id.ctv);
//		ctv.setText("My Custom View ");
		
		
	}
}
