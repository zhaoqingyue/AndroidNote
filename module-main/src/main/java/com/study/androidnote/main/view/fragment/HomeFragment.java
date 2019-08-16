package com.study.androidnote.main.view.fragment;

import android.os.Bundle;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—首页Fragment
 */
public class HomeFragment extends BaseSupportFragment {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_home;
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
