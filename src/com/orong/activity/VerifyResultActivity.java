package com.orong.activity;

import com.orong.R;

import android.os.Bundle;

/**
 * @Title: VerifyResultActivity.java
 * @Description: 实名认证-信息审核页面
 * @author lanhaizhong
 * @date 2013年9月22日 下午5:25:58
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class VerifyResultActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_result);
		initivReabck(this);
		setTitle(this,R.string.verify_sum_title);
		initView();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
	}
}
