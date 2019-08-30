package com.study.androidnote.main.view.fragment;

import android.os.Bundle;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 主页—管理Fragment
 */
public class ManagerFragment extends BaseSupportFragment {

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

    }
}
