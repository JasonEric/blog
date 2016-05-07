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
	 *           �ϵ������������̨����
	 * @param url
	 * @param params
	 *            new AjaxParams() params.put("username", "michael yang");
	 *            params.put("password", "123456"); 
	 *            params.put("email","test@tsz.net"); 
	 *            params.put("profile_picture", new File("/mnt/sdcard/pic.jpg")); // �ϴ��ļ�
	 *            params.put("profile_picture2", inputStream); // �ϴ�������
	 *            params.put("profile_picture3", new ByteArrayInputStream(bytes)); // �ύ�ֽ���
	 * 
	 * @param savePah
	 * @param isResume
	 *            �Ƿ���������
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
