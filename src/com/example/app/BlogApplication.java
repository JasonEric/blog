package com.example.app;

import java.io.File;

import com.example.blog.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;

/**
 * 
 * @author shengzh
 * @date 2015-1-5
 * @description
 */
public class BlogApplication extends Application {


	private static BlogApplication instance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

//		System.out.println("ceshi");
//			new Thread(){
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					for (int i = 0; i < 1000; i++) {
//						System.out.println("app"+i);
//						try {
//							sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					
//				
//				}
//			}.start();
		 File cacheDir = StorageUtils.getOwnCacheDirectory(this, "sip/cache");
		 //ͼƬ����
		DisplayImageOptions options=new DisplayImageOptions
				.Builder()
				.cacheOnDisk(true)
				.showImageOnLoading(R.drawable.ic_launcher)
				.displayer(new FadeInBitmapDisplayer(2000))//ͼƬ���غú���Ķ���
				.displayer(new RoundedBitmapDisplayer(15))//ͼ��Բ��
				.build();
		ImageLoaderConfiguration configuration=new ImageLoaderConfiguration
				.Builder(this)
				.defaultDisplayImageOptions(options)
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.build();
		ImageLoader.getInstance().init(configuration);
	}
//	private BlogApplication() {
//	}

	// ����ģʽ�л�ȡΨһ��ExitApplication ʵ��
	public static synchronized BlogApplication getInstance() {
		if (null == instance) {
			instance = new BlogApplication();
		}
		return instance;

	}
}
