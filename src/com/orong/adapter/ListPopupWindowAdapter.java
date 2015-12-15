package com.orong.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Title: ListPopupWindowAdapter.java
 * @Description: TODO
 * @author lanhaizhong
 * @date 2013年9月22日 上午11:46:06
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * @param <E>
 * 
 */
public abstract class ListPopupWindowAdapter<E> extends BaseAdapter {
	protected ArrayList<E> list;
	protected Context context;

	public ListPopupWindowAdapter(ArrayList<E> datalist, Context context) {
		this.list = datalist;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		setItemview(position, convertView, parent);
		return convertView;
	}

	/**
	 * 该方法相对于填写getView方法里面应有的代码
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	public abstract void setItemview(int position, View convertView, ViewGroup parent);
}
