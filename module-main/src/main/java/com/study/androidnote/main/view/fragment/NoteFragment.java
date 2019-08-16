package com.study.androidnote.main.view.fragment;

import android.os.Bundle;

import com.study.androidnote.main.R;
import com.study.commonlib.base.fragment.BaseSupportFragment;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—任务Fragment
 */

public class NoteFragment extends BaseSupportFragment {

    public static NoteFragment newInstance() {
        Bundle args = new Bundle();
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_note;
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
