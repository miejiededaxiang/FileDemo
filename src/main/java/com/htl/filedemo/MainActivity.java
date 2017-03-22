package com.htl.filedemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private Object mExternalStorageDirectory;

	private Button mBtnInput, mBtnOutput, mBtnRR, mBtnRw, mBtnSc, mBtnBuffer, mBtnSer;

	private String content = "今天天气真好";
	String path = Environment.getExternalStorageDirectory() + File.separator + "htl.text";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		streamrwFile();
		inputFile();
		outputFile();
		rwFile();
		scFile();
		bufferFile();
		serFile();
		//是否挂载SDcard
		boolean isMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

//		getExternalStorageDirectory();
//		getDownloadCacheDirectory();
//		checkListFile();
//		totalDirs();
//		SystemDris();

//		sdcardDris();
	}

	/**
	 * 序列化
	 */
	private void serFile() {

		mBtnSer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String wriPath = Environment.getExternalStorageDirectory() + File.separator + "htll.text";
				Student student = new Student("小名", 21);
				Log.d(TAG, "onClick:1 "+student.toString());
				try {

					ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
					ObjectOutputStream objectoutputStream = new ObjectOutputStream(new FileOutputStream(path));

					objectoutputStream.writeObject(student);
					Toast.makeText(MainActivity.this, "序列化成功", Toast.LENGTH_SHORT).show();

					Student student2 = (Student) objectInputStream.readObject();
					Log.d(TAG, "onClick: "+student2.toString());
					objectInputStream.close();
					objectoutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 缓冲流
	 */
	private void bufferFile() {
		mBtnBuffer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String wriPath = Environment.getExternalStorageDirectory() + File.separator + "htll.text";
				try {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
					BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(wriPath))));

					char[] chars = new char[10];
					int len = 0;
					while ((len = bufferedReader.read(chars)) != -1) {
						bufferedWriter.write(chars, 0, len);
						bufferedWriter.flush();
					}
					Toast.makeText(MainActivity.this, "读写成功", Toast.LENGTH_SHORT).show();
					bufferedReader.close();
					bufferedWriter.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}


			}
		});

	}

	/**
	 * 字节流转为字符流
	 */
	private void scFile() {
		mBtnSc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputStreamReader inputStreamReader = null;
				OutputStreamWriter outputStreamWriter = null;

				try {
					String wriPath = Environment.getExternalStorageDirectory() + File.separator + "htlll.text";
					inputStreamReader = new InputStreamReader(new FileInputStream(new File(path)));
					outputStreamWriter = new OutputStreamWriter(new FileOutputStream(new File(wriPath)));

					char[] chars = new char[10];
					int len = 0;
					while ((len = inputStreamReader.read(chars)) != -1) {
						outputStreamWriter.write(chars, 0, len);
						outputStreamWriter.flush();
					}
					Toast.makeText(MainActivity.this, "读写成功", Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStreamReader.close();
						outputStreamWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * 字符流读写文件
	 */
	private void rwFile() {
		mBtnRw.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FileReader fileReader = null;
				FileWriter fileWriter = null;
				try {
//					String wriPath = Environment.getExternalStorageDirectory() + File.separator + "htl" + File.separator + "htll.text";
					String wriPath = Environment.getExternalStorageDirectory() + File.separator + "htll.text";

					fileReader = new FileReader(new File(path));
					fileWriter = new FileWriter(new File(wriPath));

					char[] chars = new char[4];
					int len = 0;

					if ((len = fileReader.read(chars)) != -1) {
						fileWriter.write(chars, 0, len);
						fileWriter.flush();
					}
					Toast.makeText(MainActivity.this, "读写成功", Toast.LENGTH_SHORT).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						fileReader.close();
						fileWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	/**
	 * 将文字写入到指定文件中
	 */
	private void outputFile() {
		mBtnOutput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean checkMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
				FileOutputStream fileOutputStream = null;
				if (checkMounted) {

					File file = new File(path);
					// 创建的是文件夹，
					// 报java.io.FileNotFoundException: /storage/emulated/0/htl.text:
					// open failed: EISDIR (Is a directory)
					//file.mkdirs();
					//file.getParentFile().mkdirs();//创建文件
					//写入不需要创建文件
					try {
						byte[] bytes = content.getBytes();
						fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(bytes);
						Toast.makeText(MainActivity.this, "写入成功", Toast.LENGTH_SHORT).show();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fileOutputStream != null) {
							try {
								fileOutputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
	}


	/**
	 * 从指定目录中读取文件
	 */
	private void inputFile() {
		mBtnInput.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					File file = new File(path);
					FileInputStream inputStream = new FileInputStream(file);

					byte[] bytes = new byte[inputStream.available()];
					inputStream.read(bytes);
					String str = new String(bytes);

					Log.d(TAG, "onClick: " + str);
					inputStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 字节流读写文件
	 */
	private void streamrwFile() {
		mBtnRR.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					File file = new File(Environment.getExternalStorageDirectory() + File.separator + "htlt.text");
					InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory() + File.separator + "htl.text"));
					OutputStream outputStream = new FileOutputStream(file);

					byte[] by = new byte[1024];
					int len = 0;

					while ((len = inputStream.read(by)) != -1) {
						outputStream.write(by, 0, len);
					}
					Toast.makeText(MainActivity.this, "读写成功", Toast.LENGTH_SHORT).show();
					inputStream.close();
					outputStream.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initView() {
		mBtnInput = (Button) findViewById(R.id.btn_input);
		mBtnOutput = (Button) findViewById(R.id.btn_output);
		mBtnRR = (Button) findViewById(R.id.btn_rr);
		mBtnRw = (Button) findViewById(R.id.btn_rw);
		mBtnSc = (Button) findViewById(R.id.btn_sc);
		mBtnBuffer = (Button) findViewById(R.id.btn_buffer);
		mBtnSer = (Button) findViewById(R.id.btn_ser);
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
