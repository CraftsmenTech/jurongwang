package com.orong.adapter;

import java.util.ArrayList;

import com.orong.R;
import com.orong.entity.RepayPlan;
import com.orong.utils.NumberConert;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @Title: RepayPlanAdapter.java
 * @Description: 还款计划的适配器
 * @author lanhaizhong
 * @date 2013年7月17日上午10:09:35
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class RepayPlanAdapter extends BaseAdapter {

	private ArrayList<RepayPlan> repayPlanList;
	private Context context;

	public RepayPlanAdapter(ArrayList<RepayPlan> repayPlanList, Context context) {
		this.context = context;
		this.repayPlanList = repayPlanList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return repayPlanList == null ? 0 : repayPlanList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return repayPlanList == null ? null : repayPlanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.repay_plan_item, null);
			holder = new ViewHolder();

			holder.tvRepayDate = (TextView) view.findViewById(R.id.tv_repay_date);
			holder.tvRepayIndex = (TextView) view.findViewById(R.id.tv_repay_index);
			// 设置中文字体加粗
			TextPaint tp = holder.tvRepayIndex.getPaint();
			tp.setFakeBoldText(true);

			holder.tvCapital = (TextView) view.findViewById(R.id.tv_capital);
			holder.tvInterest = (TextView) view.findViewById(R.id.tv_interest);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		RepayPlan repayPlan = repayPlanList.get(position);
		String repayInextStr = String.format(context.getString(R.string.repayindexformatstr), NumberConert.translate(repayPlan.getNum()));
		holder.tvRepayIndex.setText(repayInextStr);
		holder.tvRepayDate.setText(repayPlan.getRefund_DateFormat());
		holder.tvCapital.setText(String.format(context.getString(R.string.float_format), repayPlan.getPrincipal()));
		holder.tvInterest.setText(String.format(context.getString(R.string.float_format), repayPlan.getInterest()));
		return view;
	}

	class ViewHolder {
		TextView tvRepayIndex;
		TextView tvRepayDate;
		TextView tvCapital;
		TextView tvInterest;
	}
}
