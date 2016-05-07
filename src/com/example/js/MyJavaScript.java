package com.example.js;

import com.example.blog.MainActivity2;
import com.example.blog.TestActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class MyJavaScript {
	public Context mContext;
	public Handler handler;
	public MyJavaScript(Context context,Handler handler) {
		mContext = context;
		this.handler=handler;
	}

	@JavascriptInterface
	public void gotoActivity1(){
		Intent intent=new Intent(mContext,MainActivity2.class);
		mContext.startActivity(intent);
	
	}        
	@JavascriptInterface
	public void gotoActivity2(String url){
		System.out.println(url);
		Toast.makeText(mContext, url,Toast.LENGTH_LONG).show();
		Intent intent=new Intent(mContext,TestActivity.class);
		mContext.startActivity(intent);
	}
}
