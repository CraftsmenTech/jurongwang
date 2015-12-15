package com.orong.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: DetailMessage.java
 * @Description: TODO
 * @author lanhaizhong
 * @date 2013年8月16日 下午3:03:50
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class DetailMessage extends MessageSummary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7256288710715450554L;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DetailMessage() {
		super();
	}

	public DetailMessage(String message_ID, int read_Flag, String title, Date send_Date) {
		super(message_ID, read_Flag, title, send_Date);
		// TODO Auto-generated constructor stub
	}

	public DetailMessage(String message_ID, int read_Flag, String title, Date send_Date, String content) {
		super(message_ID, read_Flag, title, send_Date);
		this.content = content;
	}

	public DetailMessage(MessageSummary summary, String content) {
		super();
		if(summary!=null){
			this.setMessage_ID(summary.getMessage_ID());
			this.setTitle(summary.getTitle());
			this.setRead_Flag(summary.getRead_Flag());
			this.setSend_Date(summary.getSend_Date());
		}
		this.content = content;
	}

}
