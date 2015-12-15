package com.orong.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * @Title: CustomListPopupWindow.java
 * @Description: 自定义listView的PopupWindow
 * @author lanhaizhong
 * @date 2013年9月22日 上午11:02:00
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class CustomListPopupWindow extends PopupWindow implements OnItemClickListener {
	private CustomItemClickCallback callback;
	private View contentView;// 根布局
	private ListView listView;
	private int width;
	private int height;

	public CustomListPopupWindow(Context context, int layoutResouse, int listViewid, int popupWidth, int popupHeight, CustomItemClickCallback callback) {
		super(context);
		this.callback = callback;
		this.width = popupWidth;
		this.height = popupHeight;
		contentView = LayoutInflater.from(context).inflate(layoutResouse, null);
		listView = (ListView) contentView.findViewById(listViewid);
		listView.setOnItemClickListener(this);

		setContentView(contentView);
		setWidth(width);
		setHeight(height);
		setFocusable(true);
		setBackgroundDrawable(new ColorDrawable(-00000000));
	}

	public interface CustomItemClickCallback {
		public void onitemClickCallback(View view, int position);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if (parent.getId() == listView.getId()) {
			callback.onitemClickCallback(view, position);
		}
	}

}
