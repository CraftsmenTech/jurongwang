package com.orong.activity;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Title: BaseActivity.java
 * @Description: 基础activity类是个基类
 * @author lanhaizhong
 * @date 2013年7月3日 上午11:17:29
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class BaseActivity extends Activity implements OnClickListener {
	public ImageView ivReback;
	public TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		OrongApplication.addActivity2Stack(this);
		// initView();
	}

	/**
	 * view 对象初始化工作
	 */
	public void initView() {
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		OrongApplication.removeActivityFromStack(this);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_reback) {
			doReback();
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param activity
	 * @param titleStr
	 *            标题文字
	 */
	public void setTitle(Activity activity, String titleStr) {
		tvTitle = (TextView) activity.findViewById(R.id.tv_title);
		setBoldText(tvTitle);
		tvTitle.setText(titleStr);
	}

	/**
	 * 通过资源id设置标题
	 * 
	 * @param activity
	 * @param titleResouceId
	 *            资源ID
	 */
	public void setTitle(Activity activity, int titleResouceId) {
		tvTitle = (TextView) activity.findViewById(R.id.tv_title);
		setBoldText(tvTitle);
		tvTitle.setText(titleResouceId);
	}

	/**
	 * 初始化ivReback
	 * 
	 * @param activity
	 */
	public void initivReabck(Activity activity) {
		ivReback = (ImageView) activity.findViewById(R.id.iv_reback);
		ivReback.setOnClickListener((OnClickListener) activity);
	}

	/**
	 * 点击ivReback的事件相应
	 */
	public void doReback() {
		finish();
	}

	/**
	 * 将中文字体设置为粗体字 android默认不支持中文
	 * 
	 * @param tv
	 */
	public static void setBoldText(TextView tv) {
		TextPaint tp2 = tv.getPaint();
		tp2.setFakeBoldText(true);
	}

	public static void showResulttoast(int result, Context context) {
		OrongApplication.showResultToast(result, context);
	}
	public static SharedPreferences getOrSharedPrefences(Context context){
		return context.getSharedPreferences(Constant.ORPREFERENCES, Context.MODE_PRIVATE);
		
	}
}
