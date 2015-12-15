package com.orong.fragment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.activity.MainActivity;
import com.orong.activity.MemberSpaceActivity;
import com.orong.entity.HttpDatas;
import com.orong.entity.MemberInfo;
import com.orong.utils.CustomRunnable;
import com.orong.utils.JSONUtil;
import com.orong.utils.QCCodeUtil;
import com.orong.utils.ThreadPoolService;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;
import com.orong.view.CustomDialog;
import com.orong.zxing.camera.CameraManager;
import com.orong.zxing.decoding.CaptureActivityHandler;
import com.orong.zxing.view.ViewfinderView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CaptureFragment extends android.support.v4.app.Fragment implements SurfaceHolder.Callback {

	private TextView tvTitle;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private LinearLayout ll_Normal_result;
	private TextView txtResult;
	private ImageView iv_result_image;
	// private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	View view;
	CustomRunnable<Void, Integer> timerExitScan;// 定时退出扫描 如果操作者长时间呆在此页面不做任何操作
												// 将退出扫描界面
	public CustomDialog memberDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capture, container, false);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTitle.setText(R.string.capture_title);
		ll_Normal_result = (LinearLayout) view.findViewById(R.id.ll_normal_result);

		txtResult = (TextView) view.findViewById(R.id.tv_txtResult);
		iv_result_image = (ImageView) view.findViewById(R.id.iv_result_image);
		hasSurface = false;
		this.view = view;
		//解决低版本通过滑动屏幕进入拍摄界面想却打开不了摄像头的问题。
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if(currentapiVersion < 11){
			viewOnscreen();
		}
		return view;
	}

	public void viewOnscreen() {
		CameraManager.init(getActivity());
		viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
		SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		ll_Normal_result.setVisibility(View.GONE);
		viewfinderView.setVisibility(View.VISIBLE);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		// 版本兼容问题
		if (currentapiVersion > 10) {
			initCamera(surfaceHolder);
		} else {

			if (hasSurface) {
				initCamera(surfaceHolder);
			} else {
				surfaceHolder.removeCallback(this);
				surfaceHolder.addCallback(this);
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
				// initCamera(surfaceHolder);
			}
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		// initCamera(surfaceHolder);
		vibrate = true;
	}

	// 退出时的操作
	public void viewOutscreen() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
			if (timerExitScan != null) {
				timerExitScan.cancleTask();
			}

		}
		CameraManager.get().closeDriver();
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion < 11) {
			if (!hasSurface) {
				SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);
				SurfaceHolder surfaceHolder = surfaceView.getHolder();
				surfaceHolder.removeCallback(this);
			}
		}

	}

	@Override
	public void onDestroy() {
		// inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		if (timerExitScan != null) {
			timerExitScan.cancleTask();
		}
		ThreadPoolService.execute(instanceCustomRunnable());
		playBeepSoundAndVibrate();
		String resultText = obj.getText();
		HashMap<String, String> map = QCCodeUtil.checkQCCode(resultText);
		if (map.isEmpty()) {
			ll_Normal_result.setVisibility(View.VISIBLE);
			viewfinderView.setVisibility(View.GONE);
			iv_result_image.setImageBitmap(barcode);
			txtResult.setText(resultText);
			txtResult.setTextColor(getResources().getColor(R.color.white));
			if (resultText.startsWith("http://") || resultText.startsWith("https://")) {
				txtResult.setAutoLinkMask(Linkify.WEB_URLS);
				txtResult.setTextColor(getResources().getColor(R.color.linkblue));
			}
		} else {
			if ("00".equals(map.get("type"))) {
				// showMemberDialog(map.get("qrCode"));
				getMemberInfo(map.get("qrCode"));
			}

		}
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
	}

	public LinearLayout getLl_Normal_result() {
		return ll_Normal_result;
	}

	public void setLl_Normal_result(LinearLayout ll_Normal_result) {
		this.ll_Normal_result = ll_Normal_result;
	}

	/**
	 * 结果显示页是否显示
	 * 
	 * @return
	 */
	public boolean isLl_Normal_resultShowqing() {
		return ll_Normal_result.getVisibility() == 0 ? true : false;

	}

	public void setLl_Normal_resultVisibility(int visibility) {
		ll_Normal_result.setVisibility(visibility);
	}

	public void setviewFinderViewVisibility(int visibility) {
		viewfinderView.setVisibility(visibility);
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public void setHasSurface(boolean hasSurface) {
		this.hasSurface = hasSurface;
	}

	private CustomRunnable instanceCustomRunnable() {
		timerExitScan = new CustomRunnable<Void, Integer>(null) {

			@Override
			public Integer executeTask(Void... param) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(6 * 10 * 1000L);// 一分钟
					return 100;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return 0;
			}

			@Override
			public void afterTask(Integer result) {
				// TODO Auto-generated method stub
				int i = result;
				// if (i != 0) {
				((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
				// }
				super.afterTask(result);

			}
		};
		return timerExitScan;

	}

	private void getMemberInfo(String qcResult) {
		// memberDialog.dismiss();
		HttpDatas datas = new HttpDatas(Constant.USERAPI);
		datas.putParam("method", "GetUser");
		datas.putParam("qRCodeID", qcResult);
		NetUtils.sendRequest(datas, getActivity(), getString(R.string.requesting), new TaskCallBack() {
			MemberInfo info;

			@Override
			public int excueHttpResponse(String respondsStr) {
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == Constant.RESPONSE_OK) {
						info = JSONUtil.jsonObject2Bean(jsonObject, MemberInfo.class);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return code;
			}

			@Override
			public void beforeTask() {
			}

			@Override
			public void afterTask(int result) {
				result = 2000;
				switch (result) {
				case Constant.RESPONSE_OK:
					Intent intent = new Intent(getActivity(), MemberSpaceActivity.class);
					intent.putExtra("MemberInfo", info);
					startActivity(intent);
					break;

				default:
					OrongApplication.showResultToast(result, getActivity());
					break;
				}
			}
		});
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}
}