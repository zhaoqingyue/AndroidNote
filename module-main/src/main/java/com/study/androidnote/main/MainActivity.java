package com.study.androidnote.main;

import android.os.Bundle;
import com.study.androidnote.main.view.fragment.HomeFragment;
import com.study.androidnote.main.view.fragment.MeFragment;
import com.study.androidnote.main.view.fragment.MsgFragment;
import com.study.androidnote.main.view.fragment.NoteFragment;
import com.study.commonlib.base.activity.BaseSupportActivity;
import com.study.commonlib.ui.fragmentation.SupportFragment;
import com.study.commonlib.ui.view.BottomBar;
import com.study.commonlib.ui.view.BottomBarTab;
import butterknife.BindView;

public class MainActivity extends BaseSupportActivity implements BottomBar.OnTabSelectedListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    @BindView(R2.id.bb_bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private BottomBarTab mBottomBarMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        BottomBarTab home = new BottomBarTab(this, R.drawable.main_selector_bottom_home, getString(R.string.main_bottom_home));
        BottomBarTab task = new BottomBarTab(this, R.drawable.main_selector_bottom_note, getString(R.string.main_bottom_note));
        mBottomBarMsg = new BottomBarTab(this, R.drawable.main_selector_bottom_msg, getString(R.string.main_bottom_msg));
        BottomBarTab me = new BottomBarTab(this, R.drawable.main_selector_bottom_me, getString(R.string.main_bottom_me));
        mBottomBar.addItem(home)
                .addItem(task)
                .addItem(mBottomBarMsg)
                .addItem(me);
        mBottomBar.setOnTabSelectedListener(this);
        mBottomBarMsg.setUnreadCount(100);
    }

    @Override
    protected void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = NoteFragment.newInstance();
            mFragments[THIRD] = MsgFragment.newInstance();
            mFragments[FOUR] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.id_mainLayout, FIRST, mFragments[FIRST], mFragments[SECOND], mFragments[THIRD], mFragments[FOUR]);
        } else {
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(NoteFragment.class);
            mFragments[THIRD] = findFragment(MsgFragment.class);
            mFragments[FOUR] = findFragment(MeFragment.class);
        }
    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        showHideFragment(mFragments[position], mFragments[prePosition]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        SupportFragment currentFragment = mFragments[position];
        currentFragment.getChildFragmentManager().getBackStackEntryCount();
    }
}
