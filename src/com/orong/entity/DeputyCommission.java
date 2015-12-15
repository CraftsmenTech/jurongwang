package com.orong.entity;

/**
 * @Title: DeputyCommission.java
 * @Description: 投资者代表佣金实体
 * @author lanhaizhong
 * @date 2013年8月16日 上午9:24:31
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class DeputyCommission extends Commission {
	private int isDeputy;// 投资者代表标识符 3008 已是投资者代表 3009 正在审核中 2000 可申请 -1不是投资者代表

	public DeputyCommission() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeputyCommission(int count, double earned, double without) {
		super(count, earned, without);
		// TODO Auto-generated constructor stub
	}

	public DeputyCommission(int count, double earned, double without, int isDeputy) {
		super(count, earned, without);
		this.isDeputy = isDeputy;
	}

	public int getIsDeputy() {
		return isDeputy;
	}

	public void setIsDeputy(int isDeputy) {
		this.isDeputy = isDeputy;
	}

}
