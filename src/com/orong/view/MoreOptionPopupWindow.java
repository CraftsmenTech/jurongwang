package com.orong.view;

import com.orong.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @Title: MoreOptionPopupWindow.java
 * @Description: 弹出更多选择的PopupWindow
 * @author lanhaizhong
 * @date 2013年7月16日下午2:44:23
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class MoreOptionPopupWindow extends PopupWindow implements OnClickListener {
	private LinearLayout conterView;
	private TextView tvRulesOfRecMember;
	private TextView tvRulesOfRecProject;
	private TextView tvRulesOfDeputy;

	private ClickOptionItemCallBack callback;

	public MoreOptionPopupWindow(Context context, int width, int height, ClickOptionItemCallBack callBack) {

		super(context);
		this.callback = callBack;

		conterView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.more_option_menu_popup, null);

		tvRulesOfRecMember = (TextView) conterView.findViewById(R.id.tv_rules_of_recmember);
		tvRulesOfRecMember.setOnClickListener(this);

		tvRulesOfRecProject = (TextView) conterView.findViewById(R.id.tv_rules_of_recproject);
		tvRulesOfRecProject.setOnClickListener(this);

		tvRulesOfDeputy = (TextView) conterView.findViewById(R.id.tv_rules_of_deputy);
		tvRulesOfDeputy.setOnClickListener(this);

		setContentView(conterView);
		setWidth(width);
		setHeight(height);

		setFocusable(true);
		setBackgroundDrawable(new ColorDrawable(-000000));
	}

	public interface ClickOptionItemCallBack {
		/**
		 * 点击会员推荐规则调用的方法
		 */
		public void clickRecMember();

		/**
		 * 点击项目推荐规则调用的方法
		 */
		public void clickRecProject();

		/**
		 * 点击投资者代表规则调用的方法
		 */
		public void clickDeputy();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_rules_of_recmember:
			callback.clickRecMember();
			break;
		case R.id.tv_rules_of_recproject:
			callback.clickRecProject();
			break;
		case R.id.tv_rules_of_deputy:
			callback.clickDeputy();
			break;

		default:
			break;
		}
	}
}
