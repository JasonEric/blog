package com.example.http;

import java.io.File;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.tsz.afinal.http.HttpHandler;

public class MyFinalHttp {
	public static FinalHttp finalHttp;
	public static MyFinalHttp myFinalHttp;

	public static FinalHttp getInstance() {
		if (finalHttp == null) {
			finalHttp = new FinalHttp();
		}
		return finalHttp;
	}

	/**
	 * new AjaxCallBack<File>(){
	 * 
	 * @Override public void onLoading(long count, long current) {
	 *           progress=(int) (current /(float)count * 100); Message
	 *           message=handler.obtainMessage(); message.arg1=progress;
	 *           handler.sendMessage(message); System.out.println(progress);
	 *           super.onLoading(count, current);
	 * 
	 *           }
	 * @Override public void onSuccess(File t) { super.onSuccess(t); } }
	 * 
	 *           断点续传，服务后台下载
	 * @param url
	 * @param params
	 *            new AjaxParams() params.put("username", "michael yang");
	 *            params.put("password", "123456"); 
	 *            params.put("email","test@tsz.net"); 
	 *            params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // 上传文件
	 *            params.put("profile_picture2", inputStream); // 上传数据流
	 *            params.put("profile_picture3", new ByteArrayInputStream(bytes)); // 提交字节流
	 * 
	 * @param savePah
	 * @param isResume
	 *            是否重新下载
	 * @param callback
	 *            AjaxCallBack
	 * @return
	 */
	public static HttpHandler<File> breakPointDownload(String url, AjaxParams params,
			String savePah, boolean isResume, AjaxCallBack<File> callback) {
		return finalHttp.download(url, params, savePah, isResume, callback);
		// return finalHttp.download(url, savePah, isResume, callback);
	}

}
