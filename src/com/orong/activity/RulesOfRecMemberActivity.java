package com.orong.activity;

import com.orong.R;

import android.os.Bundle;

/**
 * @Title: RulesOfRecoMemberActivity.java
 * @Description: 会员推荐详细规则页面
 * @author lanhaizhong
 * @date 2013年7月10日下午3:31:40
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class RulesOfRecMemberActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules_of_recommenber);
		initivReabck(this);
		setTitle(this, R.string.recomenber_rules_title);
	}
}
