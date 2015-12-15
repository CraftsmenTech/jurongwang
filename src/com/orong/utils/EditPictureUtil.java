package com.orong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import com.orong.activity.MyIdentityActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * @Title: EditPictureUtil.java
 * @Description: 编辑图片相关工具类
 * @author lanhaizhong
 * @date 2013年7月29日 上午10:04:09
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class EditPictureUtil {
	private static String albumPath = "/DCIM/camera";
	private static String tempPhotographIamgeName = "/orong_tempImage.jpg";// 拍照的临时存储文件
	private static String tempCutImageName = "/or_tempCutIamge.jpg";

	/**
	 * 判断Sd卡是否可用
	 * 
	 * @param context
	 * @return
	 */
	private static boolean isSdCardUsed(Context context) {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 创建临时保存图片的file对象
	 * 
	 * @param context
	 * @return
	 */
	public static File createTempImageFile(Context context) {
		File file = new File(getAlbumFile(context) + tempPhotographIamgeName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 创建临时保存剪切的图片的file对象
	 * 
	 * @param context
	 * @return
	 */
	public static File createTempCropImageFile(Context context) {
		File file = new File(getAlbumFile(context) + tempCutImageName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	/**
	 * 获取相册的路径
	 * 
	 * @param context
	 * @return
	 */
	private static File getAlbumFile(Context context) {
		String path;
		if (isSdCardUsed(context)) {
			path = Environment.getExternalStorageDirectory() + albumPath;
		} else {
			path = Environment.getRootDirectory() + albumPath;
		}

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 启动Gallery 的Intent
	 * 
	 * @param width
	 * @param height
	 * @param outputFile
	 * @return
	 */
	public static Intent getGalleryIntent(int width, int height, File outputFile) {
		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
		intent.putExtra("output", Uri.fromFile(outputFile));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", width);// 输出图片大小
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		return intent;

	}

	/**
	 * * 调用图片剪辑程序 的intent
	 * 
	 * @param photoUri
	 *            图像的uri
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param outputFile
	 *            剪切后的图片的保存文件
	 * @return
	 */
	public static Intent getCropImageIntent(Uri photoUri, int width, int height, File outputFile) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		intent.putExtra("output", Uri.fromFile(outputFile));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 启动拍照并把拍照图片保存到临时文件中
	 * 
	 * @param context
	 * @return
	 */
	public static Intent getCaptureIntent(Context context) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createTempImageFile(context)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 返回拍照后图片临时保存的Uri
	 * 
	 * @param context
	 * @return
	 */
	public static Uri getCaptureTempFileUri(Context context) {
		return Uri.fromFile(createTempImageFile(context));

	}

	/**
	 * 返回图片剪切后图片临时保存的Uri
	 * 
	 * @param context
	 * @return
	 */
	public static Uri getCropImageTempFileUri(Context context) {
		return Uri.fromFile(createTempCropImageFile(context));

	}

	public static Bitmap getBitmapFromUri(Context context, Uri uri) {
		try {
			return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
