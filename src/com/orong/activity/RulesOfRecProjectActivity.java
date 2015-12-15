package com.orong.activity;

import android.os.Bundle;

import com.orong.R;

/**
 * @Title: RulesOfRecProjectActivity.java
 * @Description: 推荐项目规则界面
 * @author lanhaizhong
 * @date 2013年7月10日下午4:52:11
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class RulesOfRecProjectActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules_of_recproject);

		setTitle(this, R.string.recproject_rules_title);
		initivReabck(this);
	}

}
