package com.orong.fragment;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.activity.BecomeDeputyActivity;
import com.orong.activity.DeclareProjectActivity;
import com.orong.activity.MainActivity;
import com.orong.activity.MyIdentityActivity;
import com.orong.activity.RecomMemberActivity;
import com.orong.activity.UserNewSListActivity;
import com.orong.entity.UserInfo;
import com.orong.utils.CustomRunnable;
import com.orong.utils.FileUitls;
import com.orong.utils.LoadImageRespone;
import com.orong.utils.ThreadPoolService;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.NetUtils.DownloadCallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class MyOrongFragment extends Fragment implements OnClickListener {
	private TextView tvTitle;// 标题
	private ImageView ivUser;// 用户图像
	private TextView tvMemberName;// 会员姓名
	private TextView tvMemberPhone;// 会员电话
	private TextView tv_MemberSun;// 会员总资金

	private ImageView ivNews;// 信息
	private TextView tvNotReadCount;// 新信息|未读条目数
	private RelativeLayout rlRecomMember;// 推荐会员
	private RelativeLayout rlDeclareProjec;// 申报项目
	private RelativeLayout rltoBeDeputy;// 成为代表
	private UserInfo user;

	// private Handler handler;

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		setNewsImageBackgroud(user.getMessageCount());
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_myorong, container, false);
		initView(view);
		user = ((MainActivity) getActivity()).getUser();
		// handler = ((MainActivity) getActivity()).handler;
		if (user != null) {

			tvMemberName.setText(user.getUserName());
			
			String phone = user.getPhone();
			tvMemberPhone.setText(String.format(getString(R.string.phoneformat), phone.subSequence(0, 3), phone.subSequence(phone.length() - 3, phone.length())));
			String value = String.format(getString(R.string.sum), user.getAvailBal());
			tv_MemberSun.setText(value);
			int messageCount = user.getMessageCount();
			setNewsImageBackgroud(messageCount);
		}
		setUserIconIamge();

		return view;
	}

	private void initView(View view) {
		ivUser = (ImageView) view.findViewById(R.id.iv_usericon);
		ivUser.setOnClickListener(this);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		OrongApplication.setBoldText(tvTitle);
		tvTitle.setText(R.string.myorong);

		tvMemberName = ((TextView) view.findViewById(R.id.tv_member_name));
		tvMemberPhone = (TextView) view.findViewById(R.id.tv_member_phone);
		tv_MemberSun = (TextView) view.findViewById(R.id.tv_member_sum);
		ivNews = (ImageView) view.findViewById(R.id.iv_news);

		ivNews.setOnClickListener(this);
		tvNotReadCount = (TextView) view.findViewById(R.id.tv_notread_count);

		rlRecomMember = (RelativeLayout) view.findViewById(R.id.rl_recom_member);
		rlRecomMember.setOnClickListener(this);
		rlDeclareProjec = (RelativeLayout) view.findViewById(R.id.rl_declare_project);
		rlDeclareProjec.setOnClickListener(this);
		rltoBeDeputy = (RelativeLayout) view.findViewById(R.id.rl_toBeDeputy);
		rltoBeDeputy.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_usericon:
			startActivityForResult(new Intent(getActivity(), MyIdentityActivity.class), 100);
			break;
		case R.id.iv_news:
			Intent intent = new Intent(getActivity(), UserNewSListActivity.class);
			if (user != null) {
				intent.putExtra("MessageCount", user.getMessageCount());
				// setNewsImageBackgroud(user.getMessageCount());
			}
			startActivityForResult(intent, 10);
			break;
		case R.id.rl_recom_member:
			startActivity(new Intent(getActivity(), RecomMemberActivity.class));
			break;
		case R.id.rl_declare_project:
			startActivity(new Intent(getActivity(), DeclareProjectActivity.class));
			break;
		case R.id.rl_toBeDeputy:
			startActivity(new Intent(getActivity(), BecomeDeputyActivity.class));
			break;

		default:
			break;
		}
	}

	public void setNewsImageBackgroud(int MesageCount) {
		if (MesageCount > 0) {
			tvNotReadCount.setText(String.valueOf(MesageCount));
			ivNews.setImageResource(R.drawable.news_unreaded);
			// ivNews.setBackgroundResource();
			tvNotReadCount.setVisibility(View.VISIBLE);
		} else {
			ivNews.setImageResource(R.drawable.news_readed);
			// ivNews.setBackgroundResource();
			tvNotReadCount.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置用户头像
	 */
	private void setUserIconIamge() {
		SharedPreferences sp = OrongApplication.getOrSharedPrefences(getActivity());
		String filePath = sp.getString(Constant.USERICONPATH, null);
		Bitmap bitmap = null;
		if (filePath != null) {
			if (filePath.startsWith(String.valueOf(user.getQrCode()))) {
				bitmap = FileUitls.getBitmapfromFile(filePath.substring(String.valueOf(user.getQrCode()).length()));
			}
		}
		if (bitmap != null) {
			ivUser.setImageBitmap(bitmap);
		} else if (user.getPicture() != null) {
			NetUtils.downLoadImage(OrongApplication.user.getPicture(), getActivity(), Constant.USERICFODER, new DownloadCallback() {
				@Override
				public void loadCompleteCallback(LoadImageRespone respone) {
					if (respone != null) {
						ivUser.setImageBitmap(respone.getBitmap());
						if (respone.getFileSavePath() != null) {
							SharedPreferences sp = OrongApplication.getOrSharedPrefences(getActivity());
							Editor editor = sp.edit();
							editor.putString(Constant.USERICONPATH, user.getQrCode() + respone.getFileSavePath().getPath());
							editor.commit();
							editor.clear();
						}

					}

				}

				@Override
				public void beforeDownload() {
					// TODO Auto-generated method stub
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// TODO Auto-generated method stub
		if (requestCode == 10 && resultCode == 100) {
			int readCount = data.getIntExtra("readCount", 0);
			int surplus = user.getMessageCount() - readCount;
			user.setMessageCount(surplus);
			setNewsImageBackgroud(surplus);
		}
		if (requestCode == 100 && resultCode == 1000) {
			setUserIconIamge();
		}

	}
}
