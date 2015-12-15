package com.orong.activity;

import com.orong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @Title: HelpCaptionActivity.java
 * @Description: 更多-->帮助说明界面
 * @author lanhaizhong
 * @date 2013年7月11日下午4:10:50
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class HelpCaptionActivity extends BaseActivity {
	private RelativeLayout rlDeputyCaption;// 投资者代表规则
	private RelativeLayout rlRecMemberCaption;// 会员推荐规则
	private RelativeLayout rlRecProjectCaption;// 项目推荐规则
	private RelativeLayout rlRepaymentCaption;// 还款计划

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_caption);
		initivReabck(this);
		setTitle(this, R.string.help_caption);
		initView();
	}

	@Override
	public void initView() {
		super.initView();
		rlDeputyCaption = (RelativeLayout) this.findViewById(R.id.rl_deputy_caption);
		rlDeputyCaption.setOnClickListener(this);
		rlRecMemberCaption = (RelativeLayout) this.findViewById(R.id.rl_recmember_caption);
		rlRecMemberCaption.setOnClickListener(this);

		rlRecProjectCaption = (RelativeLayout) this.findViewById(R.id.rl_recproject_caption);
		rlRecProjectCaption.setOnClickListener(this);
		rlRepaymentCaption = (RelativeLayout) this.findViewById(R.id.rl_repayment_caption);
		rlRepaymentCaption.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_deputy_caption:
			startActivity(new Intent(this, RulesOfDeputyActivity.class));
			break;
		case R.id.rl_recmember_caption:
			startActivity(new Intent(this, RulesOfRecMemberActivity.class));
			break;
		case R.id.rl_recproject_caption:
			startActivity(new Intent(this, RulesOfRecProjectActivity.class));
			break;
		case R.id.rl_repayment_caption:
			startActivity(new Intent(this, RulesOfRepayActivity.class));
			break;

		default:
			super.onClick(v);
			break;
		}

	}
}
