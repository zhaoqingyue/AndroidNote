package com.study.androidnote.welcome.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.androidnote.welcome.R;
import com.study.androidnote.welcome.R2;
import com.study.androidnote.welcome.view.adapter.FmPagerAdapter;
import com.study.androidnote.welcome.view.custom.ExtendedViewPager;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;
import com.study.commonlib.util.utilcode.LogUtils;
import com.study.commonlib.util.utilcode.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuideVideoActivity extends BaseActivity {

    @BindView( R2.id.vp_guide)
    ExtendedViewPager vpGuide;

    @BindView( R2.id.ll_dot)
    LinearLayout llDot;

    @BindView( R2.id.tv_enter)
    TextView tvEnter;

    private FmPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int[] videoRes = new int[]{R.raw.welcome_guide1, R.raw.welcome_guide2, R.raw.welcome_guide3};
    private LinearLayout.LayoutParams params2;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity_guide_video;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        init();
        setPageChange();
    }

    private void init() {
        mPosition = 0;
        vpGuide.setOffscreenPageLimit(4);
        for (int i = 0; i < videoRes.length; i++) {
            GuidePagerFragment fragment = new GuidePagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("res", videoRes[i]);
            bundle.putInt("page", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        vpGuide.setAdapter(pagerAdapter);
        initDot();
    }

    /**
     * 创建小圆点
     */
    private void initDot() {
        int width =  SizeUtils.dp2px(10);
        int height = SizeUtils.dp2px(10);
        params2 = new LinearLayout.LayoutParams(width, height);
        params2.leftMargin =  SizeUtils.dp2px(15);
        View dot;
        for (int i=0; i<videoRes.length; i++) {
            dot = new View(this);
            if (i == 0) {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.welcome_shape_dot_focus);
            } else {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.welcome_shape_dot_unfocus);
            }
            llDot.addView(dot);
        }
    }

    /**
     * 根据viewPager项切换指示点位置
     */
    private void setCurrentDot(int position) {
        for (int i = 0; i < llDot.getChildCount(); i++) {
            View dot = llDot.getChildAt(i);
            if (i == position) {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.welcome_shape_dot_focus);
            } else {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.welcome_shape_dot_unfocus);
            }
        }

        if (position < videoRes.length-1) {
            tvEnter.setText(getString(R.string.welcome_next));
            tvEnter.setTextColor(getColor(R.color.welcome_stroke_color));
            tvEnter.setBackgroundResource(R.drawable.welcome_shape_btn_guide);
        } else {
            tvEnter.setText(getString(R.string.welcome_experience));
            tvEnter.setTextColor(getColor(R.color.colorWhite));
            tvEnter.setBackgroundResource(R.drawable.welcome_selector_btn_guide_bg);
        }
    }

    private void setPageChange() {
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void next(int position) {
        LogUtils.e( "ZQY", "next 000 position: " + position );

        if (position < videoRes.length-1) {
            LogUtils.e( "ZQY", "next 111 position: " + position );
            position += 1;
            vpGuide.setCurrentItem(position, true);
            setCurrentDot(position);
            LogUtils.e( "ZQY", "next 222 position: " + position );
        }
        LogUtils.e( "ZQY", "next 333 position: " + position );
    }

    @OnClick({R2.id.guide_jump, R2.id.tv_enter})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.guide_jump) {
            // 开始体验
            JumpManager.endOfWelcome();
            finish();
        } else if (id == R.id.tv_enter) {
            onJump();
        }
    }

    private void onJump() {
        if (mPosition < videoRes.length-1) {
            next(mPosition);
        } else {
            //进入主页面
            JumpManager.endOfWelcome();
            finish();
        }
    }
}
