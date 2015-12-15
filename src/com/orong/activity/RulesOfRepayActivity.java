package com.orong.activity;

import com.orong.R;

import android.os.Bundle;

/**
 * @Title: RulesOfRepayActivity.java
 * @Description: 还款计划规则详细页面
 * @author lanhaizhong
 * @date 2013年7月11日上午9:41:09
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class RulesOfRepayActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules_of_repayment);

		setTitle(this, R.string.rules_of_repay);
		initivReabck(this);
	}

}
