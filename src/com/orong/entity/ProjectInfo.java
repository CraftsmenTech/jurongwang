package com.orong.entity;

import java.io.Serializable;

import com.orong.utils.Bean;

/**
 * @Title: ProjectInfo.java
 * @Description: 项目信息 APP 融资详情->项目信息
 * @author lanhaizhong
 * @date 2013年8月22日 上午9:40:11
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class ProjectInfo implements Bean, Serializable {

	private String name;
	private double total;
	private double usedTotal;

	public ProjectInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProjectInfo(String name, double total, double usedTotal) {
		super();
		this.name = name;
		this.total = total;
		this.usedTotal = usedTotal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getUsedTotal() {
		return usedTotal;
	}

	public void setUsedTotal(double usedTotal) {
		this.usedTotal = usedTotal;
	}

}
