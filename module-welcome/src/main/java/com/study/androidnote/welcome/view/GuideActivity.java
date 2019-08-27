package com.study.androidnote.welcome.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.biz.constant.ArouterPath;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    @BindView( R2.id.guide_pager)
    ViewPager mViewPager;

    @BindView(R2.id.guide_jump)
    TextView mJump;

    TextView mEnter;

    GuidePagerAdapter mAdapter;
    List<View> mViews;

    private int[] pics = {R.layout.welcome_layout_guid_view0, R.layout.welcome_layout_guid_view1, R.layout.welcome_layout_guid_view2, R.layout.welcome_layout_guid_view3};

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity_guide;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    protected void initAdapter() {
        mViews = new ArrayList<View>();
        for (int i=0; i<pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                mEnter = (TextView) view.findViewById(R.id.guide_enter);
                mEnter.setTag("enter");
                mEnter.setOnClickListener(this);
            }
            mViews.add(view);
        }
        mAdapter = new GuidePagerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new PageChangeListener());
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当滑动状态改变时调用
         */
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        /**
         * 当前页面被滑动时调用
         */
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置
        }

        /**
         * 当新的页面被选中时调用
         */
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mJump.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    mJump.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R2.id.guide_jump})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.guide_jump || id == R.id.guide_enter) {
            // 开始体验
            JumpManager.endOfWelcome();
            finish();
        }
    }
}
