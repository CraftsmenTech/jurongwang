package com.orong.activity;

import java.util.ArrayList;

import com.orong.R;
import com.orong.adapter.ContractInfoAdapter;
import com.orong.entity.Contract;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @Title: ContractInfoActivity.java
 * @Description: 合同信息页面，罗列某项投资系项目的具体合同信息
 * @author lanhaizhong
 * @date 2013年7月15日上午10:48:12
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class ContractInfoActivity extends BaseActivity {
	private TextView tv_projectinfo_title;// 合同名称
	private TextView tvBorrower;// 借款人
	private TextView tvLender;// 借出人
	private TextView tvBorrowerTime;// 借款时间
	private TextView tvProtectionMode;// 保障方式

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contract_info);
		initivReabck(this);
		setTitle(this, R.string.contract_info);
		initView();

	}

	@Override
	public void initView() {
		super.initView();
		this.tvBorrower = (TextView) this.findViewById(R.id.tv_borrower);
		this.tvLender = (TextView) this.findViewById(R.id.tv_lender);
		this.tvBorrowerTime = (TextView) this.findViewById(R.id.tv_borrow_time);
		this.tvProtectionMode = (TextView) this.findViewById(R.id.tv_protection_mode);
		tv_projectinfo_title = (TextView) this.findViewById(R.id.tv_projectinfo_title);

		Intent intent = getIntent();
		Contract contract = (Contract) intent.getSerializableExtra("Contract");
		tv_projectinfo_title.setText(contract.getName());
		tvBorrower.setText(contract.getLoanPer());
		tvLender.setText(contract.getLender());
		tvBorrowerTime.setText(contract.getinsertDateFormat());
		tvProtectionMode.setText(contract.getAssureWay());
	}
}
