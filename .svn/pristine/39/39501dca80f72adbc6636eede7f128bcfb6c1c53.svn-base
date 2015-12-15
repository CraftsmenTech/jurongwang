package com.orong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.orong.Constant;
import com.orong.R;
import com.orong.entity.Commission;
import com.orong.entity.DeputyCommission;
import com.orong.entity.HttpDatas;
import com.orong.utils.JSONUtil;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @Title: BecomeDeputyActivity.java
 * @Description: 成为投资代表页面
 * @author lanhaizhong
 * @date 2013年7月10日上午11:49:44
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class BecomeDeputyActivity extends BaseRecommendActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_become_deputy);
		initView(this);
		initivReabck(this);
		setTitle(this, R.string.my_deputied_project);

		setTextOfTvHasRecommendedNum(getString(R.string.deputied_project_num), 223);
		setTextOfTvHasGotBrokerage(2534.00);
		setTextOfTvWillHaveGotBrokerage(1232312.00);
		setTextOfBtRecommend(getString(R.string.become_deputy));
		tvRecommendedRules.setText(R.string.the_deputy_rules);

		btRecommend.setOnClickListener(this);
		rlRecommendedRules.setOnClickListener(this);

		LoadCommission();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_recommended_rules:
			startActivity(new Intent(this, RulesOfDeputyActivity.class));
			break;
		case R.id.bt_recommend:
			startActivity(new Intent(BecomeDeputyActivity.this, ApplyForDeputyActivity.class));
			break;
		case R.id.iv_option_menu:
			showPopupWindow(BecomeDeputyActivity.this);
			break;
		default:
			super.onClick(v);
			break;
		}

	}

	/**
	 * 加载佣金
	 */
	private void LoadCommission() {
		HttpDatas datas = new HttpDatas(Constant.USERAPI);
		datas.putParam("method", "GetDeputyCommission");
		NetUtils.sendRequest(datas, BecomeDeputyActivity.this, getString(R.string.loading), new TaskCallBack() {
			DeputyCommission commission;

			@Override
			public int excueHttpResponse(String respondsStr) {
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == 2000) {
						commission = JSONUtil.jsonObject2Bean(jsonObject, DeputyCommission.class);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
				case 2000:
					setTextOfTvHasRecommendedNum(getString(R.string.deputied_project_num), commission.getCount());
					setTextOfTvHasGotBrokerage(commission.getEarned());
					setTextOfTvWillHaveGotBrokerage(commission.getWithout());
					int isDeputy = commission.getIsDeputy();
					if (isDeputy == 3008) {// 已经是投资者代表
						setTextOfBtRecommend(getString(R.string.is_deputy_now));
					//	btRecommend.setBackgroundResource(R.drawable.cor6_rounded_rectangle);
						btRecommend.setClickable(false);
					} else if (isDeputy == 3009) {// 正在审核中
						setTextOfBtRecommend(getString(R.string.checking_deputy));
						//btRecommend.setBackgroundResource(R.drawable.cor6_rounded_rectangle);
						btRecommend.setClickable(false);
					} else {
						setTextOfBtRecommend(getString(R.string.become_deputy));
					}
					break;
				default:
					showResulttoast(result, BecomeDeputyActivity.this);
					break;
				}

			}
		});
	}

}
