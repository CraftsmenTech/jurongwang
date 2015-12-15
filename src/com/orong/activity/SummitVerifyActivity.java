package com.orong.activity;

import com.orong.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @Title: SummitVerifyActivity.java
 * @Description: 实名认证之提交认证信息
 * @author lanhaizhong
 * @date 2013年9月11日 下午12:25:03
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class SummitVerifyActivity extends BaseActivity {
	private ScrollView scrollView;
	private Button btSummitVerify;
	private TextView tv_bankName;
	private ListView lvBankList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summit_verify);
		initivReabck(this);
		setTitle(this, R.string.verify_sum_title);
		initView();
	}

	@Override
	public void initView() {
		scrollView = (ScrollView) this.findViewById(R.id.sv_scrollview);
		btSummitVerify = (Button) this.findViewById(R.id.bt_summit);
		btSummitVerify.setOnClickListener(this);
		tv_bankName = (TextView) this.findViewById(R.id.tv_bankname);
		tv_bankName.setOnClickListener(this);

		lvBankList = (ListView) this.findViewById(R.id.lv_banklist);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_bankname:
			lvBankList.setVisibility(View.VISIBLE);

			scrollView.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					scrollView.fullScroll(ScrollView.FOCUS_DOWN);
				}
			});
			break;
		case R.id.bt_summit:
			startActivity(new Intent(this, VerifyResultActivity.class));
			lvBankList.setVisibility(View.GONE);
			break;

		default:
			super.onClick(v);
			break;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (lvBankList.getVisibility() == View.VISIBLE) {
			lvBankList.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}

	}
}
