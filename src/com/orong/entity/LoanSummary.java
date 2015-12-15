package com.orong.entity;

import com.orong.utils.Bean;

/**
 * @Title: ProjectSummary.java
 * @Description: 融资项目摘要
 * @author lanhaizhong
 * @date 2013年8月19日 上午11:31:42
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class LoanSummary implements Bean {

	private String loanID;// ID
	private String picture;// 图片的URL
	private String loanName;// 主题
	private double money;// 金额
	private double schedule;// 进度
	private double interestRate;// 利率
	private int deadline;// 期限 天
	//private int measureUnit;

	public LoanSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoanSummary(String loanID, String picture, String title, double amount, double speedOfProgress, double rate, int loanTerm) {
		super();
		this.loanID = loanID;
		this.picture = picture;
		this.loanName = title;
		this.money = amount;
		this.schedule = speedOfProgress;
		this.interestRate = rate;
		this.deadline = loanTerm;
	//	this.measureUnit = measureUnit;
	}

	public String getLoanID() {
		return loanID;
	}

	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getSchedule() {
		return schedule;
	}

	public void setSchedule(double schedule) {
		this.schedule = schedule;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

//	public int getMeasureUnit() {
//		return measureUnit;
//	}
//
//	public void setMeasureUnit(int measureUnit) {
//		this.measureUnit = measureUnit;
//	}


}
