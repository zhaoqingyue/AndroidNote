package com.study.commonlib.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.study.commonlib.ui.fragmentation.SupportFragment;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 基类-继承BaseSupportFragment
 */

public abstract class BaseSupportFragment extends SupportFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    protected abstract int getLayoutResID();

    protected abstract void initPresenter();

    protected abstract void initData();

    // 是否是主页的Fragment
    protected boolean isMainFragment() {
        return false;
    }

    boolean doubleBackToExitPressedOnce = false;

    /**
     * 处理回退事件
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (isMainFragment()) {
                if (doubleBackToExitPressedOnce) {
                    _mActivity.finish();
                    return true;
                }

                doubleBackToExitPressedOnce = true;
                ToastUtils.showShortToast("再按一次退出应用");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
        return true;
    }
}
