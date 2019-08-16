package com.study.androidnote.main.view.fragment;

import android.os.Bundle;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—我的Fragment
 */
public class MeFragment extends BaseSupportFragment {

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_me;
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
