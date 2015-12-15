package com.orong.view;

import com.orong.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Title: UploadImageDialog.java
 * @Description: 弹出上传图片选择对话框 相机或者照相
 * @author lanhaizhong
 * @date 2013年7月23日 下午4:08:15
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class UploadImageDialog extends Dialog implements android.view.View.OnClickListener {

	private LinearLayout llPhotograph;
	private LinearLayout llSelectPhoto;

	private ClickCallback callback;

	public UploadImageDialog(Context context, ClickCallback callback) {
		super(context, R.style.custom_dialog);
		setContentView(R.layout.dialog_upload);
		llPhotograph = (LinearLayout) this.findViewById(R.id.ll_photograph);
		llPhotograph.setOnClickListener(this);
		llSelectPhoto = (LinearLayout) this.findViewById(R.id.ll_select_photo);
		llSelectPhoto.setOnClickListener(this);
		this.callback = callback;

		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setGravity(Gravity.CENTER);

	}

	public interface ClickCallback {
		/**
		 * 点击拍照条目的回调
		 */
		public void clickPhotographCallback();

		/**
		 * 点击从相册选择的回调
		 */
		public void clickSelectPhotoCallback();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_photograph:
			callback.clickPhotographCallback();
			break;
		case R.id.ll_select_photo:
			callback.clickSelectPhotoCallback();
			break;

		default:
			break;
		}
	}
}
