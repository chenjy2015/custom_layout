package com.example.custom.listview.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.custom.object.PictureVO;

@SuppressWarnings("rawtypes")
public class PictureAdapter extends ArrayAdapter {

	@SuppressWarnings("unchecked")
	public PictureAdapter(Context context, int textViewResourceId, List<PictureVO> objects) {  
        super(context, textViewResourceId, objects);  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        PictureVO pictureVO = (PictureVO) getItem(position);  
        View view;  
        if (convertView == null) {  
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,  
                    null);  
        } else {  
            view = convertView;  
        }  
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);  
        text1.setText(pictureVO.getName());  
        return view;  
    }  

}
