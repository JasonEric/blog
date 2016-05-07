package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class AppUtils {
	public static Context		mContext		= null;

	private static final char	HEX_DIGITS[]	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 版本信息Key
	 */
	public static class Version {
		public static String	VERSION_CODE	= "version_code";
		public static String	VERSION_NAME	= "version_name";
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String md5(String s) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static void overridePendingTransition(Activity activity, int inAnim, int exitAnim) {
		Class<?> myTarget;
		Method myMethod = null;
		Class<?>[] paramTypes = { Integer.TYPE, Integer.TYPE };
		try {
			myTarget = Class.forName("android.app.Activity");
			myMethod = myTarget.getDeclaredMethod("overridePendingTransition", paramTypes);
			myMethod.invoke(activity, inAnim, exitAnim);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Version Name
	 */
	public static String getVersionName() {
		return getVersion().get(Version.VERSION_NAME).toString();
	}

	/**
	 * 获取版本信息
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getVersion() {
		PackageManager pm = mContext.getPackageManager();
		HashMap vInfo = new HashMap();
		try {
			PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), 0);
			vInfo.put("VERSION_CODE", info.versionCode);
			vInfo.put("VERSION_NAME", info.versionName);
			vInfo.put(Version.VERSION_CODE, info.versionCode);
			vInfo.put(Version.VERSION_NAME, info.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return vInfo;
	}

	public static boolean isSDCardExist() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}

	/**
	 * 获取或创建Cache目录
	 * 
	 * @param bucket
	 *            临时文件目录，bucket = "/cache/" ，则放在"sdcard/linked-joyrun/cache"; 如果bucket=""或null,则放在"sdcard/linked-joyrun/"
	 */
	public static String getMyCacheDir(String bucket) {
		String dir;

		// 保证目录名称正确
		if (bucket != null) {
			if (!bucket.equals("")) {
				if (!bucket.endsWith("/")) {
					bucket = bucket + "/";
				}
			}
		}

		String joyrun_default = "/AFinal/";

		if (isSDCardExist()) {
			dir = Environment.getExternalStorageDirectory().toString() + joyrun_default + bucket;
		} else {
			dir = Environment.getDownloadCacheDirectory().toString() + joyrun_default + bucket;
		}

		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return dir;
	}

	/** 删除 文件夹 以及 目录下所有文件 */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}

	/**
	 * 复制文件(以超快的速度复制文件)
	 * 
	 * @param srcFile
	 *            源文件File
	 * @param destDir
	 *            目标目录File
	 * @param newFileName
	 *            新文件名
	 * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
	 */
	@SuppressWarnings("resource")
	public static long copyFile(File srcFile, File destDir, String newFileName, CopyFileListener listener) {
		long copySizes = 0;
		if (!srcFile.exists()) {
			if (listener != null)
				listener.exception("源文件不存在");
			copySizes = -1;
		} else if (!destDir.exists()) {
			if (listener != null)
				listener.exception("目标目录不存在");
			copySizes = -1;
		} else if (newFileName == null) {
			if (listener != null)
				listener.exception("文件名为null");
			copySizes = -1;
		} else {
			try {
				File dstFile = new File(destDir, newFileName);

				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				FileChannel fcout = new FileOutputStream(dstFile).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySizes = size;

				if (listener != null)
					listener.success(dstFile.getPath());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copySizes;
	}

	/**
	 * 为一个线程设置一个Handler
	 */
	public static void setUncaughtExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable ex) {
				ex.getLocalizedMessage();
			}
		});
	}

	public interface CopyFileListener {
		void exception(String msg);

		void success(String path);
	}
}
