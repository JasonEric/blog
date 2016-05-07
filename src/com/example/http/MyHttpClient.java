package com.example.http;

import java.util.HashMap;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author shengzh
 * @date 2015-1-8
 * @description AsyncHttpClient封装类
 */
public class MyHttpClient {

	private static int CONNECTTIMEOUT = 10 * 1000;
	private static AsyncHttpClient client;
	private static MyHttpClient myHttpClient;
	private MyHttpClient() {

	}

	public static AsyncHttpClient getHttpClient() {
		if (client == null) {
			client = new AsyncHttpClient();
			client.setTimeout(CONNECTTIMEOUT);
		}
		return client;
	}
	public static MyHttpClient getInstance(){
		if (myHttpClient==null) {
			myHttpClient=new MyHttpClient();
		}
		return myHttpClient;
	}

	/**
	 * RequestParams params = new RequestParams(); 
	 * params.put("key", "value");
	 * params.put("more", "data");
	 * 带参数的get请求
	 * @param urlString
	 * @param params
	 * @param res
	 */
	public static void sendGet(String urlString, RequestParams params,AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public static void sendGet(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(url, responseHandler);
	}

	/**
	 * 请求Json
	 * 
	 * @param context
	 * @param url
	 *            url
	 * @param handler
	 *            JsonHttpResponseHandler
	 */
	public static void getJson(Context context, String url,
			JsonHttpResponseHandler handler) {
		client.get(context, url, handler);
	}

	public static void DownloadFile(String url, FileAsyncHttpResponseHandler handler) {
		client.get(url, handler);
	}

	/**
	 * HashMap<String, String> vaules = new HashMap<String, String>();
	 * paramMap.put("key", "value");
	 * RequestParams params = new
	 * RequestParams(vaules);
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @param vaules
	 * @param responseHandler
	 *            AsyncHttpResponseHandler
	 */
	public void sendPost(String url, RequestParams params,HashMap<String, String> vaules,AsyncHttpResponseHandler responseHandler) {
		client.post(url, params, responseHandler);
	}
}
