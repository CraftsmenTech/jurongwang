package com.orong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.orong.Constant;
import com.orong.R;
import com.orong.entity.HttpDatas;
import com.orong.utils.MatchUtil;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;

import android.R.integer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @Title: DoRecProjectActivity.java
 * @Description: 填写推荐项目信息的界面
 * @author lanhaizhong
 * @date 2013年7月10日下午5:20:38
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class DoRecProjectActivity extends BaseActivity {
	private EditText etProjectName;// 项目名称
	private EditText etContactName;// 联系人
	private EditText etContactPhone;// 联系人电话
	private EditText etDemands;// 项目需求
	private EditText etCycle;// 项目周期
	private Button btRecommend;// 提交推荐按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_do_recom_project);
		initView();
		initivReabck(this);
		setTitle(this, R.string.declare_project);

	}

	@Override
	public void initView() {
		super.initView();
		etProjectName = (EditText) this.findViewById(R.id.et_project_name);
		etContactName = (EditText) this.findViewById(R.id.et_contact_name);
		etContactPhone = (EditText) this.findViewById(R.id.et_contact_phone);
		etDemands = (EditText) this.findViewById(R.id.et_demands);
		etCycle = (EditText) this.findViewById(R.id.et_cycle);
		btRecommend = (Button) this.findViewById(R.id.bt_recommend);
		btRecommend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bt_recommend:
			reCommend();
			break;

		default:
			super.onClick(v);
			break;
		}

	}

	/**
	 * 推荐
	 */
	private void reCommend() {
		String projectName = etProjectName.getText().toString().trim();
		if ("".equals(projectName)) {
			Toast.makeText(this, getString(R.string.project_name_null), 0).show();
			return;
		} else if (projectName.length() > 20) {
			Toast.makeText(this, getString(R.string.project_name_toolong), 0).show();
			return;
		}
		String contactName = etContactName.getText().toString().trim();
		if (contactName.equals("")) {
			Toast.makeText(this, getString(R.string.contact_name_null), 0).show();
			return;
		} else if (contactName.length() > 16) {
			Toast.makeText(this, getString(R.string.contact_name_toolong), 0).show();
			return;
		}
		String contactPhone = etContactPhone.getText().toString().trim();
		if (contactPhone.equals("")) {
			Toast.makeText(this, getString(R.string.contact_phone_null), 0).show();
			return;
		} else if (!MatchUtil.isContactNum(contactPhone)) {
			Toast.makeText(this, getString(R.string.error_contact_phone), 0).show();
			return;
		}

		String demandStr = etDemands.getText().toString().trim();
		double demand = 0;
		if (demandStr.equals("")) {
			Toast.makeText(this, getString(R.string.please_input_demands), 0).show();
			return;
		} else {
			demand = Double.parseDouble(demandStr);
			if (demand == 0) {
				Toast.makeText(this, getString(R.string.demands_zero), 0).show();
				return;
			} else if (demand > 100000000) {
				Toast.makeText(this, getString(R.string.demands_toobig), 0).show();
				return;
			}
		}
		String cycleStr = etCycle.getText().toString().trim();
		int cycle = 0;
		if ("".equals(cycleStr)) {
			Toast.makeText(this, getString(R.string.please_input_cycle), 0).show();
			return;
		} else {
			cycle = Integer.parseInt(cycleStr);
			if (cycle == 0) {
				Toast.makeText(this, getString(R.string.cycle_zero), 0).show();
				return;
			} else if (cycle > 36) {
				Toast.makeText(this, getString(R.string.cycle_toobig), 0).show();
				return;
			}
		}

		// 开始发送请求
		HttpDatas datas = new HttpDatas(Constant.PROJECTAPI);
		datas.putParam("method", "Recommend");
		datas.putParam("projectName", projectName);
		datas.putParam("contact", contactName);
		datas.putParam("phone", contactPhone);
		datas.putParam("limit", String.valueOf(demand));
		datas.putParam("period", String.valueOf(cycle));
		NetUtils.sendRequest(datas, this, getString(R.string.requesting), new TaskCallBack() {
			boolean isSuccess = false;

			@Override
			public int excueHttpResponse(String respondsStr) {
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == 2000) {
						isSuccess = jsonObject.getBoolean("isSucceed");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return code;
			}

			@Override
			public void beforeTask() {
			}

			@Override
			public void afterTask(int result) {
				switch (result) {
				case Constant.RESPONSE_OK:
					if (isSuccess) {
						Toast.makeText(getApplicationContext(), getString(R.string.recproject_success), 1).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.recproject_fail), 0).show();
					}
					break;
				case 4007:
					Toast.makeText(getApplicationContext(), getString(R.string.not_oauthor), 1).show();
					break;
				case 5000:
					Toast.makeText(getApplicationContext(), getString(R.string.recproject_fail), 0).show();
					break;
				default:
					showResulttoast(result, DoRecProjectActivity.this);
					break;
				}
			}
		});
	}
}
