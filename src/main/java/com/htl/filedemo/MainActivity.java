package com.htl.filedemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private Object mExternalStorageDirectory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//是否挂载SDcard
		boolean isMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

//		getExternalStorageDirectory();
//		getDownloadCacheDirectory();
//		checkListFile();
//		totalDirs();
//		SystemDris();

		sdcardDris();
	}

	/**
	 * /data/user/0/com.htl.filedemo/cache
	 * /data/user/0/com.htl.filedemo/files
	 */
	private void sdcardDris() {
		String cachePath = this.getCacheDir().getAbsolutePath();
		String filePath = this.getFilesDir().getAbsolutePath();
		Log.d(TAG, "sdcardDris: " + cachePath + "====" + filePath);
	}

	/**
	 * 系统目录
	 * /system
	 */
	private void SystemDris() {
		File rootDirectory = Environment.getRootDirectory();
		File[] files = rootDirectory.listFiles();
		Log.d(TAG, "SystemDris: " + rootDirectory.getAbsolutePath());
		Log.d(TAG, "SystemDris: " + files.length);
	}

	/**
	 * 获取总根目录文件
	 */
	private void totalDirs() {
		File file = new File(File.separator);
		File[] files = file.listFiles();
		Log.d(TAG, "systemDirs: " + files.length);
		for (File f : files) {
			if (f.isDirectory()) {
				Log.d(TAG, "systemDirs: " + f.getName());
			}
		}
	}

	/**
	 * 文件总根目录
	 * files.length==1
	 * files[0].getAbsolutePath()=/
	 */
	private void checkListFile() {
		File[] files = File.listRoots();
		Log.d(TAG, "checkListFile: " + files.length + "==" + files[0].getAbsolutePath());
	}


	public void getExternalStorageDirectory() {
		//内部存储->htl->dd.text
		String path = Environment.getExternalStorageDirectory() + File.separator + "htl";

		File file = new File(path);
		file.mkdirs();
		Log.d(TAG, "getExternalStorageDirectory: " + file.getAbsolutePath());
		Toast.makeText(MainActivity.this, "==" + file.exists(), Toast.LENGTH_SHORT).show();
	}

	/**
	 * /data
	 * 不能创建文件夹
	 */
	public void getDownloadCacheDirectory() {
		String path = Environment.getDownloadCacheDirectory() + File.separator + "dd.text";
		File file = new File(path);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Toast.makeText(MainActivity.this, "==" + file.exists(), Toast.LENGTH_SHORT).show();
		Log.d(TAG, "getDownloadCacheDirectory: " + Environment.getDownloadCacheDirectory().getAbsolutePath());
	}
}
