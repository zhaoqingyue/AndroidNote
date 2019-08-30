package com.study.androidnote.main.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.main.R;
import com.study.androidnote.main.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.fragment.BaseSupportFragment;
import com.study.commonlib.util.utilcode.ToastUtils;

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

    @OnClick({R2.id.ll_search, R2.id.iv_scan, R2.id.iv_notice})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_search) {
            // 搜索
            ARouter.getInstance().build(ArouterPath.PATH_HOME_SEARCH).navigation();
        } else if (id == R.id.iv_scan) {
            // 扫描
            ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.iv_notice) {
            // 消息通知
            ARouter.getInstance().build(ArouterPath.PATH_HOME_NOTICE).navigation();
        }
    }
}
