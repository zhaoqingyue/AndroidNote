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
 * 主页—笔记Fragment
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

    @OnClick({R2.id.mc_picker, R2.id.mc_net })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_picker) {
            // 选择器
            ARouter.getInstance().build(ArouterPath.PATH_NOTE_PICKER).navigation();
        } else if (id == R.id.mc_net) {
            // 监听网络变化
            ARouter.getInstance().build(ArouterPath.PATH_NOTE_NET).navigation();
//            ToastUtils.showShortToast("该功能暂未实现");
        }
    }
}
