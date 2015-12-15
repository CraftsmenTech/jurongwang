package com.orong.entity;

import java.io.Serializable;

import com.orong.utils.Bean;

/**
 * @Title: User.java
 * @Description: 用户信息实体，用户名 用户头型的uri 电话 用户类型等
 * @author lanhaizhong
 * @date 2013年8月13日 下午3:54:51
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class UserInfo implements Bean, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;// 用户名
	private String picture; // 下载图路径
	private String phone;// 手机电话
	private int userFlag;// 用户身份 0 借款 1投资 2其它
	private int deputyFlag;// 是否是投资者代表 0不是 1是
	private int messageCount;
	private double availBal;
	private long qrCode;// 用户二维码

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(String userName, String picture, String phone, int userFlag, int deputyFlag, int messageCount, double availBal, long qrCode) {
		super();
		this.userName = userName;
		this.picture = picture;
		this.phone = phone;
		this.userFlag = userFlag;
		this.deputyFlag = deputyFlag;
		this.messageCount = messageCount;
		this.availBal = availBal;
		this.qrCode = qrCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(int userFlag) {
		this.userFlag = userFlag;
	}

	public int getDeputyFlag() {
		return deputyFlag;
	}

	public void setDeputyFlag(int deputyFlag) {
		this.deputyFlag = deputyFlag;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public double getAvailBal() {
		return availBal;
	}

	public void setAvailBal(double availBal) {
		this.availBal = availBal;
	}

	public long getQrCode() {
		return qrCode;
	}

	public void setQrCode(long qrCode) {
		this.qrCode = qrCode;
	}

}
