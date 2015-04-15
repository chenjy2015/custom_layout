package com.example.custom;

import com.example.custom.view.CustomButton;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

/**
 * 自定义View
 * 
 * @author chenjy 2015.3.18
 */
public class CustomLayoutActivity extends Activity {

	private CustomButton btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_layout);

		// CustomTextView ctv = (CustomTextView) findViewById(R.id.ctv);
		// ctv.setText("My Custom View ");

		btn = (CustomButton) findViewById(R.id.btn);
		btn.setText("hello world");
		btn.setTextColor(Color.BLUE);
	}
}
