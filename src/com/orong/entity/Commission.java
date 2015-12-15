package com.orong.entity;

import com.orong.utils.Bean;

/**
 * @Title: Commission.java
 * @Description: 佣金实体
 * @author lanhaizhong
 * @date 2013年8月15日 下午5:26:11
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class Commission implements Bean {

	private int count;// 数目统计
	private double earned;// 已得佣金
	private double without;// 未获得佣金

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getEarned() {
		return earned;
	}

	public void setEarned(double earned) {
		this.earned = earned;
	}

	public double getWithout() {
		return without;
	}

	public void setWithout(double without) {
		this.without = without;
	}

	public Commission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commission(int count, double earned, double without) {
		super();
		this.count = count;
		this.earned = earned;
		this.without = without;
	}

}
