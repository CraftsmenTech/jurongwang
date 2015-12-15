package com.orong.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.orong.utils.Bean;

/**
 * @Title: RepayPlan.java
 * @Description: 还款计划
 * @author lanhaizhong
 * @date 2013年8月21日 上午10:47:11
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class RepayPlan implements Bean {

	private int num;// 期数
	private Date Refund_Date;// 还款日期
	private float Principal;// 本金
	private float Interest; // 利息

	public RepayPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepayPlan(int num, Date refund_Date, float principal, float interest) {
		super();
		this.num = num;
		Refund_Date = refund_Date;
		Principal = principal;
		Interest = interest;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getRefund_Date() {
		return Refund_Date;
	}

	public void setRefund_Date(Date refund_Date) {
		Refund_Date = refund_Date;
	}

	public float getPrincipal() {
		return Principal;
	}

	public void setPrincipal(float principal) {
		Principal = principal;
	}

	public float getInterest() {
		return Interest;
	}

	public void setInterest(float interest) {
		Interest = interest;
	}

	private String getDateFormat(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public String getRefund_DateFormat() {
		return getDateFormat(Refund_Date);
	}
}
