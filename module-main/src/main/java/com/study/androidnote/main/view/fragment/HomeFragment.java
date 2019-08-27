package com.study.androidnote.main.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.main.R;
import com.study.androidnote.main.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.fragment.BaseSupportFragment;

import butterknife.OnClick;

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

    @OnClick({R2.id.ll_search })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_search) {
            ARouter.getInstance().build(ArouterPath.PATH_HOME_SEARCH).navigation();
        }
    }
}
