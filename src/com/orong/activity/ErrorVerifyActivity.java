package com.orong.activity;

import com.orong.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Title: ErrorVerifyActivity.java
 * @Description: TODO
 * @author lanhaizhong
 * @date 2013年9月11日 下午2:34:56
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class ErrorVerifyActivity extends BaseActivity {
	private TextView tvTelephone;
	private ImageView ivCallPhone;
	private Button btResummit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error_verify_sum);
		initivReabck(this);
		setTitle(this, R.string.error_verify);
		initView();

	}

	@Override
	public void initView() {
		tvTelephone = (TextView) this.findViewById(R.id.tv_telephone);
		btResummit = (Button) this.findViewById(R.id.bt_resummit);
		btResummit.setOnClickListener(this);
		ivCallPhone = (ImageView) this.findViewById(R.id.iv_callPhone);
		ivCallPhone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_resummit:
			startActivity(new Intent(this, NameVerifyActivity.class));
			break;
		case R.id.iv_callPhone:
			callPhone();
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	/**
	 * 启动打电话功能
	 */
	private void callPhone() {
		String phone = (tvTelephone.getText().toString().replace("-", ""));

		Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
		startActivity(intent);
	}
}
