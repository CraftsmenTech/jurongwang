package com.orong.activity;

import com.orong.R;
import com.orong.entity.ProjectInfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @Title: InfoOfProjectActivity.java
 * @Description: 投资信息详情——》项目信息 具体某一投资项目的项目信息
 * @author lanhaizhong
 * @date 2013年7月15日上午10:24:18
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class ProjectInfoActivity extends BaseActivity {

	private TextView tvProjectInfoTitle;
	private TextView tvCreditedSum;// 授信金额
	private TextView tvUsedSum;// 可用金额

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_info);
		initivReabck(this);
		setTitle(this, R.string.project_info);
		initView();
	}

	@Override
	public void initView() {
		super.initView();
		tvProjectInfoTitle = (TextView) this.findViewById(R.id.tv_projectinfo_title);
		tvCreditedSum = (TextView) this.findViewById(R.id.tv_credited_sum);
		tvUsedSum = (TextView) this.findViewById(R.id.tv_used_sum);

		Intent intent = getIntent();
		ProjectInfo info = (ProjectInfo) intent.getSerializableExtra("ProjectInfo");
		tvProjectInfoTitle.setText(info.getName());
		tvCreditedSum.setText(String.format(getString(R.string.float_format), info.getTotal()));
		tvUsedSum.setText(String.format(getString(R.string.float_format), info.getUsedTotal()));
	}
}
