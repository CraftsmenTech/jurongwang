package com.orong.activity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.adapter.MainPaperAdapter;
import com.orong.entity.UserInfo;
import com.orong.fragment.HomeFragment;
import com.orong.fragment.InvestmentFragment;
import com.orong.fragment.MoreFragment;
import com.orong.fragment.MyOrongFragment;
import com.orong.fragment.CaptureFragment;
import com.orong.utils.CustomRunnable;
import com.orong.utils.FileUitls;
import com.orong.utils.LoadImageRespone;
import com.orong.utils.ThreadPoolService;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.NetUtils.DownloadCallback;
import com.orong.view.CustomDialog;
import com.orong.view.CustomDialog.ButtonRespond;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends BaseFragmentActivity {

	public static final int HomeIndex = 0;
	public static final int InvestIndex = 1;
	public static final int CaptureIndex = 2;
	public static final int MyOrongIndex = 3;
	public static final int MoreIndex = 4;

	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentList;

	private MyOrongFragment myOrongFragment;
	private InvestmentFragment investmentFragment;
	private CaptureFragment shootingFragment;
	private MoreFragment moreFragment;
	private HomeFragment homeFragment;

	private RadioGroup rgNavigation;// 导航栏的单选框
	private RadioButton rbHome;// 聚融
	private RadioButton rbMyOrong;// 聚融
	private RadioButton rbInvestment;// 投资
	private RadioButton rbShooting;// 拍拍
	private RadioButton rbMore;// 更多

	private CustomDialog dialog;
	private int lastIndex = 0;// 上一条目
	private int currentIndex = 0;// 当前页
	private UserInfo user;

	public UserInfo getUser() {
		return user;
	}

	public Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		Serializable serializable = intent.getSerializableExtra("User");
		if (serializable != null) {
			user = (UserInfo) serializable;
			OrongApplication.user = user;
		}
		initView();

		// 通过线程方式管理Sd卡下面的图片缓存
		ThreadPoolService.execute(new CustomRunnable<Void, Void>() {
			@Override
			public Void executeTask(Void... param) {
				File projectdirec = FileUitls.createDirectory(MainActivity.this, Constant.PROJECTIMG);
				FileUitls.deleteOldfiles(projectdirec, Constant.MAXFIESNUM, Constant.MAXFIESZISE, 1);
				return null;
			}
		});

		// 清除除开本用户外的其它用户图像
		if (user.getPicture() != null) {
			ThreadPoolService.execute(new CustomRunnable<Void, Void>() {
				@Override
				public Void executeTask(Void... param) {
					File projectdirec = FileUitls.createDirectory(MainActivity.this, Constant.USERICFODER);
					File safe = new File(projectdirec.getPath() + "/" + user.getPicture().substring(user.getPicture().lastIndexOf('/')));
					FileUitls.deleteOtherfiles(projectdirec, safe);
					return null;
				}
			});
		}
	}

	/**
	 * 页面初始化
	 */

	private void initView() {
		viewPager = (ViewPager) this.findViewById(R.id.viewPager);
		myOrongFragment = new MyOrongFragment();
		investmentFragment = new InvestmentFragment();
		shootingFragment = new CaptureFragment();
		moreFragment = new MoreFragment();
		homeFragment = new HomeFragment();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(HomeIndex, homeFragment);
		fragmentList.add(InvestIndex, investmentFragment);
		fragmentList.add(CaptureIndex, shootingFragment);
		fragmentList.add(MyOrongIndex, myOrongFragment);
		fragmentList.add(MoreIndex, moreFragment);

		viewPager.setAdapter(new MainPaperAdapter(getSupportFragmentManager(), fragmentList));
		viewPager.setCurrentItem(0);
		viewPager.setOffscreenPageLimit(5);
		viewPager.setOnPageChangeListener(new MainPaperChangeListener());
		rgNavigation = (RadioGroup) this.findViewById(R.id.rg_navigation);
		rgNavigation.setOnCheckedChangeListener(new NavigationCheckChangeListener());

		rbHome = (RadioButton) this.findViewById(R.id.rb_home);
		rbMyOrong = (RadioButton) this.findViewById(R.id.rb_myorong);
		rbInvestment = (RadioButton) this.findViewById(R.id.rb_investment);
		rbShooting = (RadioButton) this.findViewById(R.id.rb_shooting);
		rbShooting.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					shootingFragment.viewOutscreen();
				}
			}
		});
		rbMore = (RadioButton) this.findViewById(R.id.rb_more);

	}

	class NavigationCheckChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.rb_home:
				viewPager.setCurrentItem(HomeIndex);
				break;
			case R.id.rb_myorong:
				viewPager.setCurrentItem(MyOrongIndex);
				break;
			case R.id.rb_investment:
				viewPager.setCurrentItem(InvestIndex);
				break;
			case R.id.rb_shooting:
				viewPager.setCurrentItem(CaptureIndex);
				shootingFragment.viewOnscreen();
				break;
			case R.id.rb_more:
				viewPager.setCurrentItem(MoreIndex);
				break;

			default:
				break;
			}
		}

	}

	class MainPaperChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int paperIndex) {
			lastIndex = currentIndex;
			switch (paperIndex) {
			case HomeIndex:
				rbHome.setChecked(true);
				break;
			case MyOrongIndex:
				rbMyOrong.setChecked(true);
				break;
			case InvestIndex:
				rbInvestment.setChecked(true);
				investmentFragment.loadProjectFirst();
				break;
			case CaptureIndex:
				rbShooting.setChecked(true);
				break;
			case MoreIndex:
				rbMore.setChecked(true);
				break;

			default:
				break;
			}

			currentIndex = paperIndex;
		}
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public RadioButton getRbShooting() {
		return rbShooting;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (viewPager.getCurrentItem() == CaptureIndex) {
			// 防止 android Skipped 30 frames! The application may be doing too
			// much work on its
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					shootingFragment.viewOnscreen();
				}
			}, 20);
		}
	}

	@Override
	public void onPause() {
		if (viewPager.getCurrentItem() == CaptureIndex) {
			shootingFragment.viewOutscreen();
		}
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == CaptureIndex) {
			if (shootingFragment.isLl_Normal_resultShowqing()) {
				shootingFragment.setLl_Normal_resultVisibility(View.GONE);
				shootingFragment.setviewFinderViewVisibility(View.VISIBLE);
				shootingFragment.restartPreviewAfterDelay(0L);
			} else {
				exitAPP();
			}

		} else {
			exitAPP();
		}
	}

	/**
	 * 退出程序
	 */
	public void exitAPP() {
		dialog = new CustomDialog(MainActivity.this, new ButtonRespond() {

			@Override
			public void buttonRightRespond() {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

			@Override
			public void buttonLeftRespond() {
				// TODO Auto-generated method stub
				dialog.dismiss();
				OrongApplication.instance.onTerminate();
			}
		});
		dialog.setDialogTitle(R.string.exit_app);
		dialog.setDialogMassage(R.string.live_for_time);
		dialog.setLeftButtonText(R.string.no);
		dialog.setRightButtonText(R.string.yes);
		// dialog.setLeftButonBackgroud(R.drawable.b);
		dialog.show();
	}

}
