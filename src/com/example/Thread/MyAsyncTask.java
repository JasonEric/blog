package com.example.Thread;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.http.MyFinalHttp;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;

@SuppressLint("NewApi")
public class MyAsyncTask extends AsyncTask<String, Integer, Void> {

	private static ExecutorService SINGLE_TASK_EXECUTOR;
	private static ExecutorService LIMITED_TASK_EXECUTOR;
	private static ExecutorService FULL_TASK_EXECUTOR;
	Handler handler=new Handler();
	public MyAsyncTask(){
		SINGLE_TASK_EXECUTOR = (ExecutorService) Executors.newSingleThreadExecutor();  
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(7);  
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
		new MyAsyncTask().executeOnExecutor(FULL_TASK_EXECUTOR, "");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				handler.removeCallbacks(r);
			}
		});
	}
//	static class AsyncDrawable extends BitmapDrawable {
//	    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
//
//	    public AsyncDrawable(Resources res, Bitmap bitmap,BitmapWorkerTask bitmapWorkerTask) {
//	        super(res, bitmap);
//	        bitmapWorkerTaskReference =
//	            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
//	    }
//
//	    public BitmapWorkerTask getBitmapWorkerTask() {
//	        return bitmapWorkerTaskReference.get();
//	    }
//	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
