package com.example.custom;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.custom.listview.HorizontialListView;
/**
 * 自定义横向ListView测试类
 * @author chenjy
 * 2015.7.1
 */
public class HroizontialListViewActivity extends Activity {

	private HorizontialListView mHorizontialListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hroizontial);
		mHorizontialListView = (HorizontialListView) findViewById(R.id.listview);
		mHorizontialListView.setAdapter(mAdapter);
		mHorizontialListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), position + "click",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private static String[] dataObjects = new String[] { "Text #1", "Text #2",
			"Text #3" };

	private BaseAdapter mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return dataObjects.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.listitem, null);
			TextView title = (TextView) retval.findViewById(R.id.title);
			title.setText(dataObjects[position]);

			return retval;
		}

	};

}
