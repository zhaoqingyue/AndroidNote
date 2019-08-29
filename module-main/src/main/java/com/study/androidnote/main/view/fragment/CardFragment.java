package com.study.androidnote.main.view.fragment;

import android.os.Bundle;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—卡管理Fragment
 */
public class CardFragment extends BaseSupportFragment {

    public static CardFragment newInstance() {
        Bundle args = new Bundle();
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_card;
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
