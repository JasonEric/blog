package com.example.blog;

import java.io.File;
import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.base.BaseActivity;
import com.example.view.ImageCycleView;
import com.example.view.ImageCycleView.ImageCycleViewListener;
import com.example.view.ModelPopup;
import com.example.view.ModelPopup.OnDialogListener;
import com.exapmle.annotation.BindView;
import com.lib.bsdiff.BsdiffJNI;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity2 extends BaseActivity implements android.view.View.OnKeyListener{
	@BindView(id=R.id.imageCycleView)
	private ImageCycleView imageCycleView;
	ArrayList<String> imageUrlList;
	@Override
	public void initData() {}
	@Override
	public void initView() {
//		final FinalBitmap finalBitmap=FinalBitmap.create(MainActivity2.this);
//		finalBitmap.configLoadfailImage(R.drawable.ic_launcher);
//		finalBitmap.configLoadingImage(R.drawable.ic_launcher);
//		finalBitmap.configDiskCachePath(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"blog_cache");
//		finalBitmap.configDiskCacheSize(5);
		new ModelPopup(MainActivity2.this, new OnDialogListener() {
			
			@Override
			public void onTakePhoto() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onModel() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChoosePhoto() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		}, false);
		imageUrlList=new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			imageUrlList.add("http://a0.att.hudong.com/10/33/01300000168389121138332470077.jpg");
		}
		//显示图片的配置  
		imageCycleView.setImageResources(imageUrlList, new ImageCycleViewListener() {
			
			@Override
			public void onImageClick(int position, View imageView) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity2.this, "点击事件", 1).show();
				String old = MainActivity2.this.getApplicationInfo().sourceDir;
				//.patch文件下载
				//apk的增量更新
				new BsdiffJNI().bsdiff(old, Environment.getRootDirectory()+"new.apk", Environment.getRootDirectory()+"diff.patch");
				if (new File(Environment.getRootDirectory()+"new.apk").exists()) {
					Intent intent = new Intent(Intent.ACTION_VIEW);  
			        intent.setDataAndType(Uri.fromFile(new File(Environment.getRootDirectory()+"new.apk")),"application/vnd.android.package-archive");  
			        MainActivity2.this.startActivity(intent);  
				}
			
			}
			
			@Override
			public void displayImage(String imageURL, ImageView imageView) {
				// TODO Auto-generated method stub
				ImageLoader.getInstance().displayImage(imageURL, imageView);
			}
		});
	}

	@Override
	public int setMainView() {
		return R.layout.activity_main2;
	}

	@Override
	public boolean isFullScreen() {
		return false;
	}
	@Override
	public CharSequence setTitle() {
		// TODO Auto-generated method stub
		return "我是标题啦";
	}
	@Override
	public boolean requireTitle() {
		return true;
	}
	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2) {

		// TODO Auto-generated method stub
		if (arg1 == KeyEvent.KEYCODE_BACK) {
			MainActivity2.this.finish();
		}
		return false;
	}

}
