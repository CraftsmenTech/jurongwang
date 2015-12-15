package com.orong.activity;

import java.io.Serializable;

import com.orong.R;
import com.orong.entity.DetailMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: DetailsActivity.java
 * @Description: 提醒信息的详细信息
 * @author lanhaizhong
 * @date 2013年7月9日下午5:09:36
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class DetailsActivity extends BaseActivity {
	private TextView tv_messageTitle;
	private TextView tv_messageContent;
	private TextView tv_messageTime;
	//private Button bt_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		setTitle(this, "信息详情");
		initivReabck(this);
		initView();

		Intent intent = getIntent();
		Serializable serializable = intent.getSerializableExtra("Message");
		if (serializable == null) {
			Toast.makeText(this, "对不起页面出错了", 0).show();
			finish();
		} else {
			DetailMessage message = (DetailMessage) serializable;
			tv_messageTitle.setText(message.getTitle());
			tv_messageContent.setText(message.getContent());
			tv_messageTime.setText(message.getFomatDate());
		}
	}

	@Override
	public void initView() {
		tv_messageTitle = (TextView) this.findViewById(R.id.tv_messageTitle);
		tv_messageContent = (TextView) this.findViewById(R.id.tv_message_content);
		tv_messageTime = (TextView) this.findViewById(R.id.tv_messageTime);
//		bt_sure = (Button) this.findViewById(R.id.bt_sure);
//		bt_sure.setOnClickListener(this);
		super.initView();
	}

}
