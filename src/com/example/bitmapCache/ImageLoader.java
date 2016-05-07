package com.example.bitmapCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

public class ImageLoader {
	private static final int SUCCESS = 1;
	private static final int FAILED = 0;

	private static int sImageWidth;

	private static Context sContext;
	private static ImageLoader sInstance;
	private static ExecutorService sThreadPool = Executors
			.newCachedThreadPool();
	private LruCache<String, Bitmap> mLruCache;
	private DiskLruCache mDiskCache;

	/**
	 * ��ʼ��ImageLoader
	 * 
	 * @param context
	 * @param width
	 *            Ԥ���ͼƬ���
	 */
	public static void init(Context context, int width) {
		sContext = context;
		sImageWidth = width;
	}

	/**
	 * ���� ��ȡImageLoader��ʵ��
	 * 
	 * @return
	 */
	public synchronized static ImageLoader getInstance() {
		if (null == sInstance) {
			sInstance = new ImageLoader();
		}
		return sInstance;
	}

	private ImageLoader() {
		// begin ��ʼ��LruCache
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int maxSize = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
			
		};
		// end

		// begin ��ʼ��DiskLruCache
		try {
			mDiskCache = DiskLruCache.open(getCacheDir(), getAppVersion(), 1,
					10 * 1024 * 1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// end
	}

	public void load(final String url, final OnImageListener l) {
		final Handler handler = new Handler() {
			public  void handleMessage(Message msg) {
				switch (msg.what) {
				case SUCCESS:
					l.onResult((Bitmap) msg.obj);
					break;
				case FAILED:
					l.onResult(null);
					break;
				}
			}
		};

		// ��memory�л�ȡ
		Bitmap bmp = getFromLru(url);
		if (null != bmp) {
			System.out.println("--getFromLru");
			Message msg = handler.obtainMessage(SUCCESS, bmp);
			msg.sendToTarget();
			return;
		}

		// ���memory��û��
		// ��disk�л�ȡ
		bmp = getFromDisk(url);
		if (null != bmp) {
			System.out.println("---getFromDisk");
			Message msg = handler.obtainMessage(SUCCESS, bmp);
			msg.sendToTarget();
			return;
		}

		// ���disk��û�У� �������������
		sThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("----getFromNet");
				DefaultHttpClient client = null;
				try {
					client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);
					if (200 == response.getStatusLine().getStatusCode()) {
						InputStream in = response.getEntity().getContent();
						Bitmap bmp = BitmapFactory.decodeStream(in);
						bmp = scaleImage(bmp);

						// ���浽����
						addToDisk(url, bmp);
						// ���浽memory
						addToLru(url, bmp);
						Message msg = handler.obtainMessage(SUCCESS, bmp);
						msg.sendToTarget();
					}
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(FAILED);
				} finally {
					if (null != client) {
						client.getConnectionManager().shutdown();
					}
				}
			}
		});
	}

	// ����ͼƬ
	private Bitmap scaleImage(Bitmap bmp) {
		int sample = bmp.getWidth() / sImageWidth;
		int height = bmp.getHeight() / sample;
		return ThumbnailUtils.extractThumbnail(bmp, sImageWidth, height);
	}

	// ��memory�л�ȡ
	private Bitmap getFromLru(String url) {
		return mLruCache.get(url);
	}

	// ��ӵ��ڴ���
	private void addToLru(String url, Bitmap bmp) {
		if (getFromLru(url) == null) {
			System.out.println("++addToLru");
			mLruCache.put(url, bmp);
		}
	}

	// �ӱ��ػ����ȡ
	private Bitmap getFromDisk(String url) {
		Bitmap bmp = null;
		try {
			DiskLruCache.Snapshot snapshot = mDiskCache.get(url);
			InputStream in = snapshot.getInputStream(0);
			bmp = BitmapFactory.decodeStream(in);
			addToLru(url, bmp); // ������Զ��� �ڴ��п϶�û��
			in.close();
		} catch (Exception e) {
		}
		return bmp;
	}

	// ��ӵ����ػ���
	private void addToDisk(String url, Bitmap bmp) throws Exception {
		System.out.println("+++addtoDisk");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, baos);
		byte[] buf = baos.toByteArray();

		DiskLruCache.Editor editor = mDiskCache.edit(url);
		OutputStream out = editor.newOutputStream(0);
		out.write(buf);
		out.flush();
		editor.commit();
	}

	// ��ȡ����Ŀ¼
	private File getCacheDir() {
		File dir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			dir = new File(sContext.getExternalCacheDir().getPath()
					+ File.separator + "images" + File.separator);
		} else {
			dir = new File(sContext.getCacheDir().getPath() + File.separator
					+ "images" + File.separator);
		}
		return dir;
	}

	// ��ȡ����汾
	private int getAppVersion() {
		PackageInfo pi;
		try {
			pi = sContext.getPackageManager().getPackageInfo(
					sContext.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public interface OnImageListener {
		public void onResult(Bitmap bmp);
	}
}
