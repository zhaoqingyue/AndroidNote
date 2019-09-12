package com.study.androidnote.main.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.main.R;
import com.study.androidnote.main.R2;
import com.study.androidnote.main.model.ManagerBean;
import com.study.androidnote.main.view.adapter.ManagerAdapter;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.fragment.BaseSupportFragment;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 主页—管理Fragment
 */
public class ManagerFragment extends BaseSupportFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_manager)
    RecyclerView mRecyclerView;

    private String[] mNameList;
    private List<ManagerBean> mManagerList;
    private ManagerAdapter mAdapter;

    public static ManagerFragment newInstance() {
        Bundle args = new Bundle();
        ManagerFragment fragment = new ManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_manager;
    }

    protected boolean isMainFragment() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        initManagerList();
        initAdapter();
    }

    private void initManagerList() {
        mManagerList = new ArrayList<>();
        mNameList = getResources().getStringArray(R.array.main_manager);
        for (int i=0; i<mNameList.length; i++) {
            ManagerBean managerBean = new ManagerBean();
            managerBean.setName(mNameList[i]);
            switch (i) {
                case 0: {
                    managerBean.setResId(R.mipmap.main_manager_bank);
                    break;
                }
                case 1: {
                    managerBean.setResId(R.mipmap.main_manager_card);
                    break;
                }
                case 2: {
                    managerBean.setResId(R.mipmap.main_manager_paperwork);
                    break;
                }
                case 3: {
                    managerBean.setResId(R.mipmap.main_manager_account);
                    break;
                }
                case 4: {
                    managerBean.setResId(R.mipmap.main_manager_bookkeeping);
                    break;
                }
            }
            mManagerList.add(managerBean);
        }
    }

    private void initAdapter() {
        mAdapter = new ManagerAdapter(mManagerList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0: {
                // 银行卡
                ARouter.getInstance().build(ArouterPath.PATH_MANAGER_BANK).navigation();
                break;
            }
            case 1: {
                // 其它卡
                ARouter.getInstance().build(ArouterPath.PATH_MANAGER_CARD).navigation();
                break;
            }
            case 2: {
                // 证件
                ARouter.getInstance().build(ArouterPath.PATH_MANAGER_PAPERWORK).navigation();
                break;
            }
            case 3: {
                // 账号
                ARouter.getInstance().build(ArouterPath.PATH_MANAGER_ACCOUNT).navigation();
                break;
            }
            case 4: {
                // 记账
                ARouter.getInstance().build(ArouterPath.PATH_MANAGER_BOOKKEEPING).navigation();
                break;
            }
        }
    }
}
