package com.orong.adapter;

import java.util.ArrayList;
import java.util.List;

import com.orong.R;
import com.orong.entity.MessageSummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @Title: UserNewsListAdapter.java
 * @Description: 展示提醒用户的信息列表
 * @author lanhaizhong
 * @date 2013年7月9日下午4:32:34
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class UserNewsListAdapter extends BaseAdapter {

	private List<MessageSummary> newsList;
	private Context context;

	public UserNewsListAdapter(List<MessageSummary> messageList, Context context) {
		this.newsList = messageList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return newsList == null ? 0 : newsList.size();
	}

	@Override
	public Object getItem(int position) {
		return newsList == null ? null : newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.usernews_item, null);
			holder = new ViewHolder();
			holder.cbIsReaded = (CheckBox) convertView.findViewById(R.id.cb_is_readed);
			holder.tvNewsTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
			holder.tvNewsDate = (TextView) convertView.findViewById(R.id.tv_news_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MessageSummary summary = newsList.get(position);
		int state = summary.getRead_Flag();
		holder.cbIsReaded.setChecked(1 == state);//
		holder.tvNewsTitle.setText(summary.getTitle());
		holder.tvNewsDate.setText(summary.getFomatDate());
		return convertView;
	}

	static class ViewHolder {
		CheckBox cbIsReaded;
		TextView tvNewsTitle;
		TextView tvNewsDate;
	}

	public List<MessageSummary> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<MessageSummary> newsList) {
		this.newsList = newsList;
	}

	public void setSummaryRead(int position, boolean isRead) {
		newsList.get(position).setRead_Flag(isRead ? 1 : 0);
	}
}
