package com.orong.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.entity.HttpDatas;
import com.orong.entity.UserInfo;
import com.orong.utils.AESUtil;
import com.orong.utils.APPUltil;
import com.orong.utils.JSONUtil;
import com.orong.utils.MatchUtil;
import com.orong.utils.Md5Util;
import com.orong.utils.net.HttpAsyncTask;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;
import com.orong.view.CustomDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: LoginActivity.java
 * @Description: 登录界面
 * @author lanhaizhong
 * @date 2013年7月3日 下午3:03:57
 * @version V1.0 Copyright (c) 2013 Crong.com,Inc. All Rights Reserved.
 */
public class LoginActivity extends BaseActivity {

	private TextView tvPhoneRegisted;// 号码已经注册提示
	private EditText etAccount;// 帐号
	private EditText etPassword;
	private CheckBox cbSavedpw;
	private Button btRegister;// 注册按钮
	private Button btLogin;// 登录按钮
	private UserInfo user = null;
	private String username = null;
	String userFormat;
	String name = null;
	private APPUltil appUltil;// 版本控制
	private CustomDialog newVersionDialog; // 新版本提示对话框
	private HttpAsyncTask cheVersionTask; // 检测新版本 的一步任务

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		appUltil = new APPUltil(LoginActivity.this, null);
		cheVersionTask = appUltil.getNewstVersion(null);
		SharedPreferences sp = getOrSharedPrefences(this);
		boolean isChecked = sp.getBoolean(Constant.ISSAVEPW, false);
		cbSavedpw.setChecked(isChecked);
		if (isChecked) {
			username = sp.getString(Constant.USERNAME, null);
			userFormat = String.format(getString(R.string.userformat), username.subSequence(0, 1), username.substring(username.length() - 1));
			etAccount.setText(userFormat);
			String pw = sp.getString(Constant.PASSWORD, null);
			if (pw != null) {
				try {
					etPassword.setText(AESUtil.hexDecrypt(pw, Constant.ENCODEPASSWORD));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		// etAccount.addTextChangedListener(new UsernameTextWatcher());

	}

	@Override
	public void initView() {
		super.initView();
		etAccount = (EditText) this.findViewById(R.id.et_account);
		etPassword = (EditText) this.findViewById(R.id.et_password);
		cbSavedpw = (CheckBox) this.findViewById(R.id.cb_savedpw);
		TextView tvTitle = (TextView) this.findViewById(R.id.tv_labletext1);
		setBoldText(tvTitle);
		this.btLogin = (Button) findViewById(R.id.bt_login);
		btLogin.setOnClickListener(this);
		// 设置中文字体加粗
		setBoldText(btLogin);
		btRegister = (Button) findViewById(R.id.bt_register);
		// 设置中文字体加粗
		setBoldText(btRegister);
		btRegister.setOnClickListener(this);
		tvPhoneRegisted = (TextView) this.findViewById(R.id.tv_phoneRegister);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bt_register:
			startActivityForResult(new Intent(this, RegisterActivity.class), 100);
			break;
		case R.id.bt_login:
			doLongin();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == 3001) {
				String registerAccount = data.getStringExtra("Account");
				if (registerAccount != null && registerAccount.length() > 1) {
					String text = String.format(getString(R.string.phone_registerNotifide), registerAccount.charAt(0), registerAccount.charAt(registerAccount.length() - 1));
					tvPhoneRegisted.setText(text);
					tvPhoneRegisted.setVisibility(View.VISIBLE);
				}
			} else {
				tvPhoneRegisted.setVisibility(View.GONE);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 执行登录操作
	 */
	private void doLongin() {

		name = etAccount.getText().toString().trim();
		if (name.equals(userFormat)) {
			name = username;
		}
		if (!MatchUtil.isLicitAccount(name)) {
			if ("".equals(name)) {
				Toast.makeText(this, R.string.null_account, 0).show();
			} else if (name.length() < 3) {
				Toast.makeText(this, R.string.short_account, 0).show();
			} else {
				Toast.makeText(this, R.string.error_account, 0).show();
			}
			return;
		}
		// 验证密码
		final String loginPasWord = etPassword.getText().toString().trim();
		if (!MatchUtil.isLicitPassword(loginPasWord)) {
			if ("".equals(loginPasWord)) {
				Toast.makeText(this, R.string.null_password, 0).show();
			} else if (loginPasWord.length() < 6) {
				Toast.makeText(this, R.string.short_password, 0).show();
			} else {
				Toast.makeText(this, R.string.error_password, 0).show();
			}
			return;
		}
		HttpDatas datas = new HttpDatas(Constant.USERAPI);
		datas.putParam("method", "login");
		datas.putParam("username", name);
		datas.putParam("pwd", Md5Util.md5Diagest(loginPasWord, 16));
		NetUtils.sendRequest(datas, LoginActivity.this, getString(R.string.login_now), new TaskCallBack() {

			@Override
			public int excueHttpResponse(String strResponds) {
				System.out.println(strResponds);
				int code = 0;
				JSONObject jsonObject = JSONUtil.instaceJsonObject(strResponds);
				if (jsonObject != null) {
					try {
						code = jsonObject.getInt("code");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if (code == 2000) {
					user = JSONUtil.jsonObject2Bean(jsonObject, UserInfo.class);
					if (cbSavedpw.isChecked()) {
						SharedPreferences sp = LoginActivity.getOrSharedPrefences(LoginActivity.this);
						Editor editor = sp.edit();
						editor.putBoolean(Constant.ISSAVEPW, true);
						editor.putString(Constant.USERNAME, name);
						editor.putString(Constant.PASSWORD, AESUtil.hexEncrypt(loginPasWord, Constant.ENCODEPASSWORD));
						editor.commit();
						editor.clear();
					}
					return Constant.STATAS_OK;
				}
				return code;

			}

			@Override
			public void beforeTask() {
			}

			@Override
			public void afterTask(int result) {
				System.out.println(result);
				switch (result) {
				case Constant.STATAS_OK:
					Toast.makeText(getApplicationContext(), LoginActivity.this.getString(R.string.login_success), 0).show();
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("User", user);
					startActivity(intent);
					break;
				// case 5000:
				// Toast.makeText(getApplicationContext(),
				// getString(R.string.error_pw_or_user), 0).show();
				// break;
				case 4004:
					Toast.makeText(getApplicationContext(), getString(R.string.error_pw_or_user), 0).show();
					break;
				default:
					showResulttoast(result, LoginActivity.this);
					break;
				}
			}
		});

	}

	class UsernameTextWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			int length = s.length();
			if (username != null && userFormat != null) {
				// 输入输入框中的文字是以X***开头并且 最新输入的文字不是空格 则认为用户正准备输入新的帐号
				if (length > 3 && s.toString().subSequence(1, 4).equals("***") && s.charAt(length - 1) != ' ') {
					if (!userFormat.equals(s.toString().trim())) {// 正要改变
						// if (userFormat.length() < length) {
						// s.delete(0, userFormat.length());
						// username = null;
						// userFormat = null;
						// } else {
						//
						// }

					}
				}
			}
		}
	}

}
