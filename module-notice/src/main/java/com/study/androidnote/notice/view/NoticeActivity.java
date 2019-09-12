package com.study.androidnote.notice.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.notice.R;
import com.study.androidnote.notice.R2;
import com.study.androidnote.notice.view.fragment.MsgNoticeFragment;
import com.study.androidnote.notice.view.fragment.SysNoticeFragment;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.NoScrollViewPager;
import com.study.commonlib.ui.view.RVPIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息通知
 */
@Route(path = ArouterPath.PATH_HOME_NOTICE)
public class NoticeActivity extends BaseTopBarActivity implements ViewPager.OnPageChangeListener {

    @BindView(R2.id.indicator)
    RVPIndicator mIndicator;

    @BindView(R2.id.viewpager)
    NoScrollViewPager mViewPager;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mData;

    @Override
    protected int getLayoutId() {
        return R.layout.notice_activity_notice;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mData = new ArrayList<>();
        mData.add(getString(R.string.notice_msg_notice));
        mData.add(getString(R.string.notice_sys_notice));
        mTabContents = new ArrayList<>();
        mTabContents.add(new MsgNoticeFragment());
        mTabContents.add(new SysNoticeFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
        mViewPager.setNoScroll(false);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mIndicator.setTitleList(mData);
        mIndicator.setViewPager(mViewPager, 0);
    }

    @OnClick({R2.id.iv_leftImage})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.iv_leftImage) {
            finish();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
