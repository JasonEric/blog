package com.example.base;



import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blog.R;
import com.example.interfaces.ActivityInterface;
import com.example.utils.AppManager;
import com.exapmle.annotation.AnnotateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * @author shengzh
 * @date 2015-1-5
 * @description
 */
public abstract class BaseActivity extends Activity implements ActivityInterface{
	
	private long mExitTime;
	public static final int WHICH_MSG = 0X37210;
	private TextView title;
	private ImageView left,right;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
		AppManager.getInstance().addActivity(this);
		  if (isFullScreen()) {
			  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		}
		  activityExitAnim(R.anim.popup_enter,R.anim.popup_exit);
		  if (requireTitle()) {
			  setContentView(R.layout.base);
			  title=(TextView)findViewById(R.id.tv_title);
			  title.setText(setTitle());
			  FrameLayout content=(FrameLayout)this.findViewById(R.id.content);
			  View view = LayoutInflater.from(this).inflate(setMainView(), null);
			  content.addView(view);
		}else {
			  setRootView();
		}
		  AnnotateUtil.initInjectedView(BaseActivity.this);
		  initView();
		  initData();
	}
	public CharSequence setTitle() {
		// TODO Auto-generated method stub
		return setTitle();
	}
	/**
	 * 是否全屏
	 * @return 默认false不全屏
	 */
	public abstract boolean isFullScreen();
	/**
	 * 是否显示自定义标题
	 * @return 默认false不显示
	 */
	public abstract boolean requireTitle();
	@Override
	public void setRootView() {
		
	}
	/**
	 * activity进入动画
	 * @param enter activity进入动画
	 * @param exit  activity退出动画
	 */
	public void activityEnterAnim(int enter,int exit){
		if (enter!=0&&exit!=0) {
			overridePendingTransition(enter, exit);
		}else
		overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
		
	}
	/**
	 * activity退出动画
	 * @param enter activity进入动画
	 * @param exit  activity退出动画
	 */
	public void activityExitAnim(int enter,int exit){
		if (enter!=0&&exit!=0) {
			overridePendingTransition(enter, exit);
		}else
		overridePendingTransition(R.anim.popup_enter2, R.anim.popup_exit2);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageLoader.getInstance().clearMemoryCache();
//		AppManager.getAppManager().AppExit(this);
		AppManager.getInstance().finishActivity();
		activityExitAnim(R.anim.popup_enter2,R.anim.popup_exit2);
	}
	
/*	*//**
	 * 按两次返回键退出程序
	 *//*
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        mExitTime = System.currentTimeMillis();

                } else {
                        AppManager.getInstance().finishAllActivity();
                        AppManager.getInstance().AppExit(this);
                        
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
}*/
}

