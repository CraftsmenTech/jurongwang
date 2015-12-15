package com.orong.fragment;

import com.orong.R;
import com.orong.activity.BecomeDeputyActivity;
import com.orong.activity.DeclareProjectActivity;
import com.orong.activity.MainActivity;
import com.orong.activity.RecomMemberActivity;
import com.orong.activity.SummitVerifyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @Title: HomeFragment.java
 * @Description: 主页面
 * @author lanhaizhong
 * @date 2013年9月10日 上午11:19:17
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class HomeFragment extends Fragment implements OnClickListener {

	private RelativeLayout rlRecomMember;// 推荐会员按键
	private RelativeLayout rlRecomProject;// 推荐项目按键
	private RelativeLayout rlBecomeDeputy;// 成为投资者代表按键
	private RelativeLayout rlInvestment;// 我要投资按键
	private RelativeLayout rlWelcomeCapture;// 怕怕有礼按键

	private LinearLayout ll_verify;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);
		rlRecomMember = (RelativeLayout) view.findViewById(R.id.rl_recom_member);
		rlRecomMember.setOnClickListener(this);
		rlRecomProject = (RelativeLayout) view.findViewById(R.id.rl_recom_project);
		rlRecomProject.setOnClickListener(this);
		rlBecomeDeputy = (RelativeLayout) view.findViewById(R.id.rl_become_deputy);
		rlBecomeDeputy.setOnClickListener(this);
		rlInvestment = (RelativeLayout) view.findViewById(R.id.rl_investment);
		rlInvestment.setOnClickListener(this);
		rlWelcomeCapture = (RelativeLayout) view.findViewById(R.id.rl_welcome_capture);
		rlWelcomeCapture.setOnClickListener(this);
		ll_verify = (LinearLayout) view.findViewById(R.id.ll_verify);
		ll_verify.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_verify:
			startActivity(new Intent(getActivity(), SummitVerifyActivity.class));
			break;
		case R.id.rl_recom_member:// 推荐会员
			startActivity(new Intent(getActivity(), RecomMemberActivity.class));
			break;
		case R.id.rl_recom_project:// 推荐项目
			startActivity(new Intent(getActivity(), DeclareProjectActivity.class));
			break;

		case R.id.rl_become_deputy:// 成为代表
			startActivity(new Intent(getActivity(), BecomeDeputyActivity.class));
			break;

		case R.id.rl_investment:// 我要投资
			// 跳到投资列表界面
			((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
			break;

		case R.id.rl_welcome_capture:// 怕拍有礼
			// 跳到啪啪界面
			((MainActivity) getActivity()).getViewPager().setCurrentItem(MainActivity.CaptureIndex);
			break;

		default:
			break;
		}
	}

}
