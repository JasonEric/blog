package com.example.app;

import java.util.LinkedList;

import android.app.Activity;
import android.app.Application;


public class MyApplication extends Application {
	private static final String TAG = "InitApplication";
	private static boolean isLogged = false;     
	private static MyApplication instance;
	private static LinkedList<Activity> activityList;
	private Activity activity;
	@Override
	public void onCreate()
	{
		instance = this;
		super.onCreate();
		activityList = new LinkedList<Activity>();
		super.onCreate();
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public static MyApplication getInstance()
	{
		if (instance==null) {
			instance=new MyApplication();
		}
		return instance;
	}
	/** 
	 * Activity�ر�ʱ��ɾ��Activity�б��е�Activity����*/  
	public void removeActivity(Activity a){  
		activityList.remove(a);  
	}  
	
	/** 
	 * ��Activity�б������Activity����*/  
	public void addActivity(Activity a){  
		activityList.add(a);  
	}  
	
	/** 
	 * �ر�Activity�б��е�����Activity*/  
	public void finishActivity(){  
		for (Activity activity : activityList) {    
			if (null != activity) {    
				activity.finish();    
			}    
		}
		activityList.clear();
		//ɱ����Ӧ�ý���  
		android.os.Process.killProcess(android.os.Process.myPid());    
	}
	
	public static boolean isLogged() {
		return isLogged;
	}
	public static void setLogged(boolean isLogged) {
		MyApplication.isLogged = isLogged;
	}   

}
