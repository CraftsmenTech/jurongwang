package com.orong.entity;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

/**
 * 封装的http请求参数
 * 
 * @author lanhaizhong
 * 
 */
public class HttpDatas {
	// private Context context;
	private String url;// 请求URL
	private boolean isPost;// 是否是POST请求
	// private BasicNameValuePair header;// 请求头
	private ArrayList<BasicNameValuePair> paramList; // 传递参数

	public HttpDatas(String url, boolean isPost, ArrayList<BasicNameValuePair> paramList) {
		super();
		// this.context = context;
		this.url = url;
		this.isPost = isPost;
		// this.header = header;
		this.paramList = paramList;
	}

	public HttpDatas(String url, boolean isPost) {
		this(url, isPost, null);
	}

	public HttpDatas(String url) {
		this(url, true, null);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}


	public ArrayList<BasicNameValuePair> getParamList() {
		return paramList;
	}

	public void putParam(String name, String value) {
		if (paramList == null) {
			paramList = new ArrayList<BasicNameValuePair>();
		}
		paramList.add(new BasicNameValuePair(name, value));
	}
}
