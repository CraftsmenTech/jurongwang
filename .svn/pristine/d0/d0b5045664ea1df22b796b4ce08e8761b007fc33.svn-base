package com.orong.fragment;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.orong.Constant;
import com.orong.OrongApplication;
import com.orong.R;
import com.orong.activity.DetailOfInvestmentActivity;
import com.orong.adapter.ProjectsAdapter;
import com.orong.entity.HttpDatas;
import com.orong.entity.LoanDetail;
import com.orong.entity.LoanSummary;
import com.orong.utils.JSONUtil;
import com.orong.utils.net.NetUtils;
import com.orong.utils.net.HttpAsyncTask.TaskCallBack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class InvestmentFragment extends Fragment implements OnItemClickListener, OnCheckedChangeListener, OnClickListener {

	private TextView tvTitle;// 标题

	private CheckBox cbSequBySum;// 按金额排序
	private CheckBox cbSequByPro;// 按进度排序
	private CheckBox cbSequByTime;// 按期限排序
	private CheckBox cbSequByInterRate;// 按利率排序
	private ListView lvProjects;// 项目列表的listView
	private Button btPrevious;
	private Button btNext;
	private TextView tv_no_project;
	private ProjectsAdapter adapter;// 项目列表Adapter
	private ArrayList<LoanSummary> projectList;
	public HashMap<String, SoftReference<Bitmap>> pictureMap;
	private static int pageSize = 8;
	private static int count = 0;// 信息列表的总数
	private static int pageIndex = 1;// 始起页

	private sortName sort;// 排序条件
	private boolean sType = false;// 记录排序类型 降序为false 升序为true

	private enum sortName {
		Amount, rate, Financing_Duration, SpeedOfProgress
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_investment, container, false);
		initView(view);
		return view;
	}

	/**
	 * 初始化View对象
	 * 
	 * @param view
	 */
	private void initView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		OrongApplication.setBoldText(tvTitle);
		tvTitle.setText(R.string.doinvestment);

		cbSequBySum = (CheckBox) view.findViewById(R.id.cb_sequ_by_sum);
		cbSequBySum.setOnCheckedChangeListener(this);

		cbSequByPro = (CheckBox) view.findViewById(R.id.cb_sequ_by_pro);
		cbSequByPro.setOnCheckedChangeListener(this);

		cbSequByTime = (CheckBox) view.findViewById(R.id.cb_sequ_by_time);
		cbSequByTime.setOnCheckedChangeListener(this);

		cbSequByInterRate = (CheckBox) view.findViewById(R.id.cb_sequ_by_interest_rate);
		cbSequByInterRate.setOnCheckedChangeListener(this);
		lvProjects = (ListView) view.findViewById(R.id.lv_projects);
		View head =LayoutInflater.from(getActivity()).inflate(R.layout.invement_listview_head, null);
		lvProjects.addHeaderView(head);

		tv_no_project = (TextView) view.findViewById(R.id.tv_no_project);
		btPrevious = (Button) view.findViewById(R.id.bt_previous);
		btPrevious.setOnClickListener(this);
		btNext = (Button) view.findViewById(R.id.bt_next);
		btNext.setOnClickListener(this);

		projectList = new ArrayList<LoanSummary>();
		pictureMap = new HashMap<String, SoftReference<Bitmap>>();
		adapter = new ProjectsAdapter(projectList, getActivity(), pictureMap);
		lvProjects.setAdapter(adapter);
		lvProjects.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_previous:
			int page = pageIndex - 1;
			List<LoanSummary> li = projectList.subList((page - 1) * pageSize, page * pageSize);
			notifiAdapterChange(li);
			pageIndex--;
			setPageButtonVisable();
			break;
		case R.id.bt_next:
			int index = pageIndex * pageSize;
			if (index < projectList.size()) {// 已经加载过
				if ((index + pageSize) > projectList.size()) {
					notifiAdapterChange(projectList.subList(index, projectList.size()));
				} else {
					notifiAdapterChange(projectList.subList(index, index + pageSize));
				}
				pageIndex++;
				setPageButtonVisable();
			} else {
				int page2 = pageIndex + 1;
				loadProjectSummary(sort, sType, page2, pageSize);
			}
			break;

		default:
			break;
		}
	}

	// 首次加载项目
	public void loadProjectFirst() {
		if (projectList == null || projectList.size() == 0) {
			loadProjectSummary(sortName.Amount, cbSequBySum.isChecked(), 1, pageSize);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg0.equals(lvProjects)) {
			LoanSummary summary = (LoanSummary) adapter.getItem(arg2);
			loadDetailLoanInfo(summary);
		}
	}

	/**
	 * 获取融资详情
	 * 
	 * @param summary
	 */
	private void loadDetailLoanInfo(final LoanSummary summary) {
		HttpDatas datas = new HttpDatas(Constant.LOANAPI);
		datas.putParam("method", "GetLoan");
		datas.putParam("loanID", summary.getLoanID());

		NetUtils.sendRequest(datas, getActivity(), getString(R.string.requesting), new TaskCallBack() {
			LoanDetail detail;

			@Override
			public int excueHttpResponse(String respondsStr) {
				// TODO Auto-generated method stub
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == Constant.RESPONSE_OK) {
						detail = JSONUtil.jsonObject2Bean(jsonObject, LoanDetail.class);
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
				switch (result) {
				case Constant.RESPONSE_OK:
					Intent intent = new Intent(getActivity(), DetailOfInvestmentActivity.class);
					Bundle bundle = new Bundle();
					Bitmap bitmap = null;
					String url = detail.getPicture();
					String key = url.substring(url.lastIndexOf('/') + 1);
					SoftReference<Bitmap> softReference = pictureMap.get(key);
					if (softReference != null) {
						bitmap = softReference.get();
					}
					// bundle.putParcelable("picture",
					// bitmap);如果bitmap太大会引起bunder失败!!! FAILED BINDER
					// TRANSACTION !!!
					bundle.putString("Loanid", summary.getLoanID());
					bundle.putSerializable("LoanDetail", detail);
					intent.putExtras(bundle);
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
	public void onCheckedChanged(CompoundButton v, boolean isChecked) {

		switch (v.getId()) {

		case R.id.cb_sequ_by_sum:
			loadProjectSummary(sortName.Amount, isChecked, 1, pageSize);
			break;
		case R.id.cb_sequ_by_pro:
			loadProjectSummary(sortName.SpeedOfProgress, isChecked, 1, pageSize);
			break;
		case R.id.cb_sequ_by_time:
			loadProjectSummary(sortName.Financing_Duration, isChecked, 1, pageSize);
			break;
		case R.id.cb_sequ_by_interest_rate:
			loadProjectSummary(sortName.rate, isChecked, 1, pageSize);
			break;

		default:
			break;
		}
		setFilterBackground(v.getId());
	}

	private void loadProjectSummary(final sortName sName, final boolean sortType, final int pIndex, int pageSize) {
		HttpDatas datas = new HttpDatas(Constant.LOANAPI);
		datas.putParam("method", "GetLoanList");
		datas.putParam("sortName", sName.name());
		datas.putParam("sortType", String.valueOf(sortType));
		datas.putParam("pageIndex", String.valueOf(pIndex));
		datas.putParam("pageSize", String.valueOf(pageSize));
		NetUtils.sendRequest(datas, getActivity(), getString(R.string.loading), new TaskCallBack() {
			ArrayList<LoanSummary> list;

			@Override
			public int excueHttpResponse(String respondsStr) {
				int code = 0;
				try {
					JSONObject jsonObject = new JSONObject(respondsStr);
					code = jsonObject.getInt("code");
					if (code == Constant.RESPONSE_OK) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						list = JSONUtil.jsonArray2ArrayList(jsonArray, LoanSummary.class);
						count = jsonObject.getInt("count");
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
				switch (result) {
				case Constant.RESPONSE_OK:
					if (projectList == null) {
						projectList = list;
					} else {
						if (pIndex == 1) {
							projectList.clear();
						}
						projectList.addAll(list);
						pageIndex = pIndex;
					}
					notifiAdapterChange(list);
					sort = sName;
					sType = sortType;
					setPageButtonVisable();
					break;
				default:
					OrongApplication.showResultToast(result, getActivity());
					break;
				}
			}

		});
	}

	private void notifiAdapterChange(List list) {
		if (adapter == null) {
			adapter = new ProjectsAdapter(list, getActivity(), pictureMap);
		} else {
			adapter.setProjectList(list);
			adapter.notifyDataSetChanged();
		}
		if (list == null || list.size() == 0) {
			tv_no_project.setVisibility(View.VISIBLE);
			lvProjects.setVisibility(View.GONE);
		} else {
			tv_no_project.setVisibility(View.GONE);
			lvProjects.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置上下一页按钮的可见
	 * 
	 * @param pageIndex
	 */
	private void setPageButtonVisable() {
		if (pageIndex == 1) {
			btPrevious.setVisibility(View.GONE);
			if (pageSize < count) {// 说明还有下一页
				btNext.setVisibility(View.VISIBLE);
			} else {
				btNext.setVisibility(View.GONE);
			}
		} else {
			btPrevious.setVisibility(View.VISIBLE);
			if (pageSize * pageIndex < count) {
				btNext.setVisibility(View.VISIBLE);
			} else {
				btNext.setVisibility(View.GONE);
			}
		}
	}

	private void setFilterBackground(int checkBoxId) {
		if (cbSequByInterRate.getId() == checkBoxId) {
			cbSequByInterRate.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.sequence_backgound_selector), null);
		} else {
			cbSequByInterRate.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.unsequence_backgound_selector), null);
		}
		if (cbSequByPro.getId() == checkBoxId) {
			cbSequByPro.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.sequence_backgound_selector), null);
		} else {
			cbSequByPro.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.unsequence_backgound_selector), null);
		}

		if (cbSequBySum.getId() == checkBoxId) {
			cbSequBySum.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.sequence_backgound_selector), null);
		} else {
			cbSequBySum.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.unsequence_backgound_selector), null);
		}

		if (cbSequByTime.getId() == checkBoxId) {
			cbSequByTime.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.sequence_backgound_selector), null);
		} else {
			cbSequByTime.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.unsequence_backgound_selector), null);
		}

	}
}
