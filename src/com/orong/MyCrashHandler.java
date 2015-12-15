package com.orong;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * MyCrashHandler 是我们自己定义的异常处理类
 * 
 * @author Administrator
 * 
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static MyCrashHandler myCrashHandler;

	private MyCrashHandler() {
	};

	private Context context;

	public synchronized static MyCrashHandler getInstance() {
		if (myCrashHandler == null) {
			myCrashHandler = new MyCrashHandler();
			return myCrashHandler;
		} else {
			return myCrashHandler;
		}
	}

	public void init(Context context) {
		this.context = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			// 在throwable的参数里面保存的有程序的异常信息
			StringBuffer sb = new StringBuffer();
			// 1.得到手机的版本信息 硬件信息
			// Field[] fields = Build.class.getDeclaredFields();
			// for(Field filed:fields){
			// filed.setAccessible(true); //暴力反射
			// String name = filed.getName();
			// String value = filed.get(null).toString();
			// sb.append(name);
			// sb.append("=");
			// sb.append(value);
			// sb.append("\n");
			// }
			//
			//
			// //2.得到当前程序的版本号
			// PackageInfo info =
			// context.getPackageManager().getPackageInfo(context.getPackageName(),
			// 0);
			// sb.append( info.versionName);
			// sb.append("\n");
			// 3.得到当前程序的异常信息
			Writer writer = new StringWriter();
			PrintWriter printwriter = new PrintWriter(writer);

			ex.printStackTrace(printwriter);
			printwriter.flush();
			printwriter.close();

			sb.append(writer.toString());
		
			// 4.提交异常信息到服务器
			Log.e(TAG, sb.toString());
			// System.out.println(sb.toString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "异常退出", 1).show();
				Looper.loop();

			}

		}.start();

		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				android.os.Process.killProcess(android.os.Process.myPid());
			}

		}.start();

	}

}
