package com.example.utils;


import java.util.Stack;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
/**
 **************************************** 
 * @author ����
 * @Description: ����һ������Activity���࣬���Զ���Ŀ��Activity���������ڽ��й���
 * 							�ﵽ��ȫ�˳���Ŀ�ģ�
 * @date 2014-7-7 ����3:30:52
 ***************************************** 
 */
public class AppManager {
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * ��һʵ��
	 */
	public static AppManager getInstance(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * ���Activity����ջ��
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	/**
	 * ��ȡ��ǰActivity����ջ�����ѹ��ģ�
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * ������ǰActivity����ջ�����ѹ��ģ�
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * ����ָ����Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * ����ָ��������Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 * ��������Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	/**
	 * �����˳�Ӧ�ó��򣬰�ȫ�˳�
	 */
	public void AppExit(Context context) {
		try {
//			ImageLoader.getInstance().clearDiskCache();
			ImageLoader.getInstance().clearMemoryCache();
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {	}
	}
}