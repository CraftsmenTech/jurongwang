package com.orong.activity;

import com.orong.R;

import android.os.Bundle;

/**
 * @Title: NameVerifyActivity.java
 * @Description: 实名认证界面
 * @author lanhaizhong
 * @date 2013年9月11日 下午3:09:38
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class NameVerifyActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summit_verify);
		initivReabck(this);
		setTitle(this, R.string.error_verify);
	}
}
