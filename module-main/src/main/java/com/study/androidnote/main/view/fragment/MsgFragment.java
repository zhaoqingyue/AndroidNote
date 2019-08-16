package com.study.androidnote.main.view.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;
import com.study.commonlib.util.utilcode.ToastUtils;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—消息Fragment
 */
public class MsgFragment extends BaseSupportFragment {

    public static MsgFragment newInstance() {
        Bundle args = new Bundle();
        MsgFragment fragment = new MsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_msg;
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
