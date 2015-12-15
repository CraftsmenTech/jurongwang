package com.orong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.entity.UpLoadDatas;
import com.orong.utils.EditPictureUtil;
import com.orong.utils.FileUitls;
import com.orong.utils.QCCodeUtil;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.NetUtils.UploadCallback;
import com.orong.view.CustomDialog;
import com.orong.view.CustomDialog.ButtonRespond;
import com.orong.view.UploadImageDialog;
import com.orong.view.UploadImageDialog.ClickCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @Title: DoRecomMemberActivity.java
 * @Description: 我的会员身份界面
 * @author lanhaizhong
 * @date 2013年7月10日下午4:21:37
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class MyIdentityActivity extends BaseActivity {

	private ImageView ivChangeIc;// 修改图像
	private ImageView ivMemberSpace;// 会员空间
	private UploadImageDialog dialog;
	private CustomDialog customDialog;
	private ProgressDialog progressDialog;

	// 预览剪切后的图片
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myidentity);

		setTitle(this, R.string.my_member_id);
		initView();
	}

	@Override
	public void initView() {
		ivChangeIc = (ImageView) this.findViewById(R.id.iv_change_ic);
		ivChangeIc.setOnClickListener(this);
		String s = OrongApplication.getOrSharedPrefences(this).getString(Constant.USERICONPATH, "");
		String qrcode = String.valueOf(OrongApplication.user.getQrCode());
		Bitmap logo = null;
		if (s.length()>qrcode.length()&&s.startsWith(qrcode)) {
			String imagPath = s.substring(qrcode.length());
			Bitmap bitmap = FileUitls.getBitmapfromFile(imagPath);
			if (bitmap != null) {
				ivChangeIc.setImageBitmap(bitmap);
				logo = getRoundedBitmap(bitmap);
			} else {
				BitmapDrawable bdrawable = (BitmapDrawable) ivChangeIc.getDrawable();
				logo = getRoundedBitmap(bdrawable.getBitmap());
			}
		}

		ivReback = (ImageView) findViewById(R.id.iv_reback);
		ivReback.setOnClickListener(this);
		ivMemberSpace = (ImageView) this.findViewById(R.id.iv_member_space);
		ivMemberSpace.setOnClickListener(this);
		ivMemberSpace.setImageBitmap(encodeQRAsBitmap(QCCodeUtil.creatememberCode(String.valueOf(OrongApplication.user.getQrCode())), logo));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_change_ic:
			showUploadeDialog();
			break;
		case R.id.iv_reback:
			mySetResult();
			finish();
			break;

		default:
			super.onClick(v);
			break;
		}
	}

	private void showUploadeDialog() {
		if (dialog == null) {
			dialog = new UploadImageDialog(MyIdentityActivity.this, new ClickCallback() {

				@Override
				public void clickSelectPhotoCallback() {
					Intent intent = EditPictureUtil.getGalleryIntent(200, 200, EditPictureUtil.createTempCropImageFile(MyIdentityActivity.this));
					startActivityForResult(intent, 2);
					dialog.dismiss();
				}

				@Override
				public void clickPhotographCallback() {
					dialog.dismiss();
					Intent intent = EditPictureUtil.getCaptureIntent(MyIdentityActivity.this);
					startActivityForResult(intent, 1);
				}
			});
		}
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				// 启动修剪相片功能
				Intent intent = EditPictureUtil.getCropImageIntent(EditPictureUtil.getCaptureTempFileUri(MyIdentityActivity.this), 200, 200,
						EditPictureUtil.createTempCropImageFile(MyIdentityActivity.this));
				startActivityForResult(intent, 2);
			}
			if (requestCode == 2) {// 剪切完成

				Uri uri = EditPictureUtil.getCropImageTempFileUri(MyIdentityActivity.this);
				Bitmap bitmap = EditPictureUtil.getBitmapFromUri(MyIdentityActivity.this, uri);
				showCustomDialog(uri);
			}
		}

	}

	private void showCustomDialog(final Uri uri) {
		if (customDialog == null) {
			customDialog = new CustomDialog(MyIdentityActivity.this, new ButtonRespond() {

				@Override
				public void buttonRightRespond() {
					// TODO Auto-generated method stub
					customDialog.dismiss();
					upLoadImage2Server();
				}

				@Override
				public void buttonLeftRespond() {
					// TODO Auto-generated method stub
					customDialog.dismiss();
				}
			});

			View view = LayoutInflater.from(MyIdentityActivity.this).inflate(R.layout.identity, null);
			imageView = (ImageView) view.findViewById(R.id.iv_usericon);
			customDialog.setDialogTitle(R.string.result_preview);
			customDialog.setRightButtonText(R.string.uploade);
			customDialog.setLeftButtonText(R.string.quit);
			customDialog.addView2Frame(view);
			customDialog.setMagssageViewVisibility(View.GONE);
			customDialog.setFrameViewVisibility(View.VISIBLE);
		}

		imageView.setImageBitmap(EditPictureUtil.getBitmapFromUri(MyIdentityActivity.this, uri));
		customDialog.show();
	}

	/**
	 * 上传图片到服务器中
	 */
	private void upLoadImage2Server() {
		String url = Constant.USERAPI;
		final File file = EditPictureUtil.createTempCropImageFile(MyIdentityActivity.this);
		UpLoadDatas datas = new UpLoadDatas(url, file, MyIdentityActivity.this);
		datas.putParam("method", "EditPicture");
		NetUtils.imageUpload(datas, new UploadCallback() {
			@Override
			public void beforeUpload() {
				// TODO Auto-generated method stub
				progressDialog = new ProgressDialog(MyIdentityActivity.this);
				progressDialog.setMessage("图片上传中……");
				progressDialog.show();
			}

			@Override
			public void afterUpload(String response) {
				int code = 0;
				String picture = null;
				try {
					JSONObject jsonObject = new JSONObject(response);
					code = jsonObject.getInt("code");
					if (code == 2000) {
						picture = jsonObject.getString("picture");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (code == 2000) {
					Toast.makeText(getApplicationContext(), "上传成功", 0).show();
					// 保存用户图像新图片
					File uericonFile = new File(FileUitls.createDirectory(MyIdentityActivity.this, Constant.USERICFODER).getPath() + "/"
							+ picture.substring(picture.lastIndexOf('/')));
					try {
						uericonFile.createNewFile();
						// 拷贝文件
						FileUitls.copy(file, uericonFile);
						Bitmap bitmap = FileUitls.getBitmapfromFile(uericonFile.getPath());
						if (bitmap != null) {
							ivChangeIc.setImageBitmap(bitmap);
							Bitmap logo = getRoundedBitmap(bitmap);
							ivMemberSpace.setImageBitmap(encodeQRAsBitmap(QCCodeUtil.creatememberCode(String.valueOf(OrongApplication.user.getQrCode())), logo));
						}
						// 拷贝成功 在SharedPreferences保存拷贝路径
						SharedPreferences sp = OrongApplication.getOrSharedPrefences(MyIdentityActivity.this);
						Editor editor = sp.edit();
						editor.putString(Constant.USERICONPATH, OrongApplication.user.getQrCode() + uericonFile.getPath());
						editor.commit();
						editor.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getApplicationContext(), "上传失败", 0).show();
				}
				progressDialog.dismiss();
				;
			}
		});
	}

	protected void mySetResult() {
		Intent data = new Intent();
		setResult(1000, data);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		mySetResult();
		finish();
		// super.onBackPressed();
	}

	/**
	 * 生成二维码
	 * 
	 * @param encodeStr
	 * @param logo
	 *            图片
	 * @return
	 */
	private Bitmap encodeQRAsBitmap(String encodeStr, Bitmap logo) {
		final int WHITE = 0xFFFFFFFF;
		final int BLACK = 0xFF000000;
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix result = null;
		try {

			result = new MultiFormatWriter().encode(encodeStr, BarcodeFormat.QR_CODE, 300, 300, hints);

		} catch (WriterException e) {

			e.printStackTrace();

		}
		int width = result.getWidth();
		int height = result.getHeight();
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int offset = y * width;
			for (int x = 0; x < width; x++) {
				pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		if (logo != null) {
			int logoWidth = logo.getWidth();
			int logoHeight = logo.getHeight();
			// 缩放比例
			float widthScale = 60f / logoWidth;
			float heightScale = 60f / (float) logoHeight;
			Matrix matrix = new Matrix();
			matrix.postScale(widthScale, heightScale);
			Bitmap bit = Bitmap.createBitmap(logo, 0, 0, logoWidth, logoHeight, matrix, true);

			Canvas canvas = new Canvas(bitmap);
			Paint paint = new Paint();
			canvas.drawBitmap(bit, (width - bit.getWidth()) / 2, (height - bit.getHeight()) / 2, paint);
		}

		return bitmap;
	}

	// 图片圆角处理
	private Bitmap getRoundedBitmap(Bitmap souce) {
		if (souce == null) {
			return null;
		}
		Bitmap mBitmap = souce;
		// 创建新的位图
		Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		// 把创建的位图作为画板
		Canvas mCanvas = new Canvas(bgBitmap);
		if (mCanvas == null) {
			return null;
		}
		Paint mPaint = new Paint();
		Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		RectF mRectF = new RectF(mRect);
		// 设置圆角半径为20
		float roundPx = 15;
		mPaint.setAntiAlias(true);
		// 先绘制圆角矩形
		mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

		// 设置图像的叠加模式
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制图像
		mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

		return bgBitmap;
	}

}
