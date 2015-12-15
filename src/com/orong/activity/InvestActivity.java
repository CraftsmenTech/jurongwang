package com.orong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.entity.HttpDatas;
import com.orong.entity.UserInfo;
import com.orong.utils.Md5Util;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: InvestActivity.java
 * @Description: 我要投资
 * @author lanhaizhong
 * @date 2013年7月15日下午2:45:21
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class InvestActivity extends BaseActivity {

	private TextView tvAvailabaleOver;// 可用金额
	private TextView tvMayInvestSum;// 可投资
	private Button btSetBestSum;// 点击自动填写最佳方案
	private EditText etInvestSum;// 投资金额
	private EditText etTransactionPw;// 交易密码
	private Button btIdentifyInvestment;// 确定投资
	private double availBal;
	private double sum;// 可投资金额
	private String loanId;// 借款id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invest);
		initivReabck(this);
		setTitle(this, R.string.doinvestment);
		initView();

		Intent intent = getIntent();
		availBal = intent.getDoubleExtra("Bal", 0);
		tvAvailabaleOver.setText(String.format(getString(R.string.float_format), availBal));
		sum = intent.getDoubleExtra("Sum", 0);
		tvMayInvestSum.setText(String.format(getString(R.string.float_format), sum));
		loanId = intent.getStringExtra("LoanId");
	}

	@Override
	public void initView() {
		super.initView();
		tvAvailabaleOver = (TextView) this.findViewById(R.id.tv_availabale_over);
		tvMayInvestSum = (TextView) this.findViewById(R.id.tv_may_invest_sum);
		btSetBestSum = (Button) this.findViewById(R.id.bt_set_best_sum);

		btSetBestSum.setOnClickListener(this);
		etInvestSum = (EditText) this.findViewById(R.id.et_invest_sum);
		etTransactionPw = (EditText) this.findViewById(R.id.et_transaction_password);

		btIdentifyInvestment = (Button) this.findViewById(R.id.bt_identify_investment);
		btIdentifyInvestment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_set_best_sum:
			setBestVelue(availBal, sum, etInvestSum);
			break;
		case R.id.bt_identify_investment:
			invert();
			break;

		default:
			super.onClick(v);
			break;
		}

	}

	/**
	 * 投资
	 */
	private void invert() {
		String invertSumStr = etInvestSum.getText().toString().trim();
		double invertSun = 0;
		try {
			invertSun = Double.parseDouble(invertSumStr);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "你输入了非法的金额数字", 0).show();
			return;
		}
		if (invertSun < 0 || invertSun == 0) {
			Toast.makeText(getApplicationContext(), "投资金额不能小于零", 0).show();
			return;
		}
		if (invertSun > availBal) {
			Toast.makeText(getApplicationContext(), "投资金额不能大于可用余额", 0).show();
			return;
		}
		invertSumStr=String.format(getString(R.string.float_format), invertSun);
		etInvestSum.setText(invertSumStr);
		String passWord = etTransactionPw.getText().toString().trim();
		if (passWord.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入交易密码", 0).show();
			return;
		}

		HttpDatas datas = new HttpDatas(Constant.LOANAPI);
		datas.putParam("method", "Invest");
		datas.putParam("loanID", loanId);
		datas.putParam("amount", invertSumStr);
		datas.putParam("tradePassword", Md5Util.md5Diagest(passWord, 16));
		NetUtils.sendRequest(datas, InvestActivity.this, getString(R.string.requesting), new TaskCallBack() {
			double money;// 有效投资金额
			boolean isSuccess = false;

			@Override
			public int excueHttpResponse(String respondsStr) {
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == Constant.RESPONSE_OK) {
						money = jsonObject.getDouble("money");
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
					double oldAvalBal = OrongApplication.user.getAvailBal();
					OrongApplication.user.setAvailBal(oldAvalBal - money);
					if (isSuccess) {
						Toast.makeText(getApplicationContext(), "投资成功，有效投资金额为：" + money + "元", 1).show();
						finish();
					}
					break;
				case 3010:
					Toast.makeText(getApplicationContext(), "投资失败，该项目已满标", 0).show();
					break;
				case 3011:
					Toast.makeText(getApplicationContext(), "投资失败，该项目已过期", 0).show();
					break;
				case 3012:
					Toast.makeText(getApplicationContext(), "投资失败，投资金额不能大于可用余额", 0).show();
					break;
				case 4004:
					Toast.makeText(getApplicationContext(), "投资失败，贵用户没有可用投资资金", 0).show();
					break;
				default:
					showResulttoast(result, InvestActivity.this);
					break;
				}
			}
		});
	}

	private void setBestVelue(double availBal, double sum, EditText view) {
		if (availBal > sum) {
			view.setText(String.format(getString(R.string.float_format), sum));
		} else {
			view.setText(String.format(getString(R.string.float_format), availBal));
		}
	}


}
