package com.example.custom;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.custom.animation.Rotate3dAnimation;
import com.example.custom.listview.adapter.PictureAdapter;
import com.example.custom.object.PictureVO;
/**
 * 自定义3D旋转测试类
 * @author chenjy
 * 2015.3.18
 */
public class RotatePicBrowserActivity extends Activity {

	/**
	 * 根布局
	 */
	private RelativeLayout layout;

	/**
	 * 用于展示图片列表的ListView
	 */
	private ListView picListView;

	/**
	 * 用于展示图片详细的ImageView
	 */
	private ImageView picture;

	/**
	 * 图片列表的适配器
	 */
	private PictureAdapter adapter;

	/**
	 * 存放所有图片的集合
	 */
	private List<PictureVO> picList = new ArrayList<PictureVO>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rotatepicbrowser);
		initPics();
		layout = (RelativeLayout) findViewById(R.id.layout);
		picListView = (ListView) findViewById(R.id.lv);
		picture = (ImageView) findViewById(R.id.picture);

		adapter = new PictureAdapter(this, 0, picList);  
        picListView.setAdapter(adapter);  
        picListView.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
                // 当点击某一子项时，将ImageView中的图片设置为相应的资源  
                picture.setImageResource(picList.get(position).getSource());  
                // 获取布局的中心点位置，作为旋转的中心点  
                float centerX = layout.getWidth() / 2f;  
                float centerY = layout.getHeight() / 2f;  
                // 构建3D旋转动画对象，旋转角度为0到90度，这使得ListView将会从可见变为不可见  
                final Rotate3dAnimation rotation = new Rotate3dAnimation(0, 90, centerX, centerY,  
                        310.0f, true);  
                // 动画持续时间500毫秒  
                rotation.setDuration(500);  
                // 动画完成后保持完成的状态  
                rotation.setFillAfter(true);  
                rotation.setInterpolator(new AccelerateInterpolator());  
                // 设置动画的监听器  
                rotation.setAnimationListener(new TurnToImageView());  
                layout.startAnimation(rotation);  
            }  
        });  
        picture.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // 获取布局的中心点位置，作为旋转的中心点  
                float centerX = layout.getWidth() / 2f;  
                float centerY = layout.getHeight() / 2f;  
                // 构建3D旋转动画对象，旋转角度为360到270度，这使得ImageView将会从可见变为不可见，并且旋转的方向是相反的  
                final Rotate3dAnimation rotation = new Rotate3dAnimation(360, 270, centerX,  
                        centerY, 310.0f, true);  
                // 动画持续时间500毫秒  
                rotation.setDuration(500);  
                // 动画完成后保持完成的状态  
                rotation.setFillAfter(true);  
                rotation.setInterpolator(new AccelerateInterpolator());  
                // 设置动画的监听器  
                rotation.setAnimationListener(new TurnToListView());  
                layout.startAnimation(rotation);  
            }  
        });  
	}
	
	

	 /** 
     * 初始化图片列表数据。 
     */  
    private void initPics() {  
    	PictureVO p0 = new PictureVO("loading0", R.drawable.com_tencent_icon);
    	PictureVO p1 = new PictureVO("loading1", R.drawable.p02);
    	PictureVO p2 = new PictureVO("loading2", R.drawable.p03);
    	PictureVO p3 = new PictureVO("loading3", R.drawable.p04);
    	PictureVO p4 = new PictureVO("loading4", R.drawable.p05);
    	PictureVO p5 = new PictureVO("loading5", R.drawable.p06);
    	PictureVO p6 = new PictureVO("loading6", R.drawable.p07);
    	picList.add(p0);
    	picList.add(p1);
    	picList.add(p2);
    	picList.add(p3);
    	picList.add(p4);
    	picList.add(p5);
    	picList.add(p6);
    }  
    
    /** 
     * 注册在ListView点击动画中的动画监听器，用于完成ListView的后续动画。 
     *  
     * @author guolin 
     */  
    class TurnToImageView implements AnimationListener {  
  
        @Override  
        public void onAnimationStart(Animation animation) {  
        }  
  
        /** 
         * 当ListView的动画完成后，还需要再启动ImageView的动画，让ImageView从不可见变为可见 
         */  
        @Override  
        public void onAnimationEnd(Animation animation) {  
            // 获取布局的中心点位置，作为旋转的中心点  
            float centerX = layout.getWidth() / 2f;  
            float centerY = layout.getHeight() / 2f;  
            // 将ListView隐藏  
            picListView.setVisibility(View.GONE);  
            // 将ImageView显示  
            picture.setVisibility(View.VISIBLE);  
            picture.requestFocus();  
            // 构建3D旋转动画对象，旋转角度为270到360度，这使得ImageView将会从不可见变为可见  
            final Rotate3dAnimation rotation = new Rotate3dAnimation(270, 360, centerX, centerY,  
                    310.0f, false);  
            // 动画持续时间500毫秒  
            rotation.setDuration(500);  
            // 动画完成后保持完成的状态  
            rotation.setFillAfter(true);  
            rotation.setInterpolator(new AccelerateInterpolator());  
            layout.startAnimation(rotation);  
        }  
  
        @Override  
        public void onAnimationRepeat(Animation animation) {  
        }  
  
    }  
  
    /** 
     * 注册在ImageView点击动画中的动画监听器，用于完成ImageView的后续动画。 
     *  
     * @author guolin 
     */  
    class TurnToListView implements AnimationListener {  
  
        @Override  
        public void onAnimationStart(Animation animation) {  
        }  
  
        /** 
         * 当ImageView的动画完成后，还需要再启动ListView的动画，让ListView从不可见变为可见 
         */  
        @Override  
        public void onAnimationEnd(Animation animation) {  
            // 获取布局的中心点位置，作为旋转的中心点  
            float centerX = layout.getWidth() / 2f;  
            float centerY = layout.getHeight() / 2f;  
            // 将ImageView隐藏  
            picture.setVisibility(View.GONE);  
            // 将ListView显示  
            picListView.setVisibility(View.VISIBLE);  
            picListView.requestFocus();  
            // 构建3D旋转动画对象，旋转角度为90到0度，这使得ListView将会从不可见变为可见，从而回到原点  
            final Rotate3dAnimation rotation = new Rotate3dAnimation(90, 0, centerX, centerY,  
                    310.0f, false);  
            // 动画持续时间500毫秒  
            rotation.setDuration(500);  
            // 动画完成后保持完成的状态  
            rotation.setFillAfter(true);  
            rotation.setInterpolator(new AccelerateInterpolator());  
            layout.startAnimation(rotation);  
        }  
  
        @Override  
        public void onAnimationRepeat(Animation animation) {  
        }  
  
    }  
}
