package com.orong.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.orong.utils.Bean;

/**
 * @Title: Contract.java
 * @Description: 合同信息
 * @author lanhaizhong
 * @date 2013年8月21日 上午10:06:50
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class Contract implements Bean, Serializable {

	private String name;// 借款名称
	private String signDate;// 合同日期
	private String loanPer;// 借款人
	private String lender;// 借出人
	private Date insertDate;// 借出日期
	private String assureWay;// 保障方式

	public Contract() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contract(String name, String signDate, String loanPer, String lender, Date insertDate, String assureWay) {
		super();
		this.name = name;
		this.signDate = signDate;
		this.loanPer = loanPer;
		this.lender = lender;
		this.insertDate = insertDate;
		this.assureWay = assureWay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getLoanPer() {
		return loanPer;
	}

	public void setLoanPer(String loanPer) {
		this.loanPer = loanPer;
	}

	public String getLender() {
		return lender;
	}

	public void setLender(String lender) {
		this.lender = lender;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getAssureWay() {
		return assureWay;
	}

	public void setAssureWay(String assureWay) {
		this.assureWay = assureWay;
	}

	private String getDateFormat(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		return format.format(date);
	}

	public String getinsertDateFormat() {
		return getDateFormat(insertDate);
	}
}
