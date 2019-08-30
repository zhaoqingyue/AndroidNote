package com.study.androidnote.main;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.main.view.fragment.HomeFragment;
import com.study.androidnote.main.view.fragment.ManagerFragment;
import com.study.androidnote.main.view.fragment.MeFragment;
import com.study.androidnote.main.view.fragment.NoteFragment;
import com.study.biz.bean.event.LockEvent;
import com.study.biz.constant.ArouterPath;
import com.study.biz.manager.JumpManager;
import com.study.biz.manager.SpManager;
import com.study.commonlib.base.activity.BaseSupportActivity;
import com.study.commonlib.ui.fragmentation.SupportFragment;
import com.study.commonlib.ui.view.BottomBar;
import com.study.commonlib.ui.view.BottomBarTab;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

@Route(path = ArouterPath.PATH_MAIN)
public class MainActivity extends BaseSupportActivity implements BottomBar.OnTabSelectedListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;
//    public static final int FIVE = 4;

    @BindView(R2.id.bb_bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[4];
    private BottomBarTab mBottomBarNote;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        BottomBarTab home = new BottomBarTab(this, R.drawable.main_selector_bottom_home, getString(R.string.main_bottom_home));
        mBottomBarNote = new BottomBarTab(this, R.drawable.main_selector_bottom_note, getString(R.string.main_bottom_note));
//        mBottomBarMsg = new BottomBarTab(this, R.drawable.main_selector_bottom_msg, getString(R.string.main_bottom_msg));
//        BottomBarTab card = new BottomBarTab(this, R.drawable.main_selector_bottom_card, getString(R.string.main_bottom_card));
        BottomBarTab manager = new BottomBarTab(this, R.drawable.main_selector_bottom_manager, getString(R.string.main_bottom_manager));
        BottomBarTab me = new BottomBarTab(this, R.drawable.main_selector_bottom_me, getString(R.string.main_bottom_me));
        mBottomBar.addItem(home)
                .addItem(mBottomBarNote)
                .addItem(manager)
                .addItem(me);
        mBottomBar.setOnTabSelectedListener(this);
        mBottomBarNote.setUnreadCount(10);
    }

    @Override
    protected void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = NoteFragment.newInstance();
            mFragments[THIRD] = ManagerFragment.newInstance();
//            mFragments[THIRD] = MsgFragment.newInstance();
//            mFragments[FOUR] = CardFragment.newInstance();
            mFragments[FOUR] = MeFragment.newInstance();
            loadMultipleRootFragment(R.id.id_mainLayout, FIRST, mFragments[FIRST], mFragments[SECOND], mFragments[THIRD], mFragments[FOUR]/*, mFragments[FIVE]*/);
        } else {
            mFragments[FIRST] = findFragment(HomeFragment.class);
            mFragments[SECOND] = findFragment(NoteFragment.class);
            mFragments[THIRD] = findFragment(ManagerFragment.class);
//            mFragments[THIRD] = findFragment(MsgFragment.class);
//            mFragments[FOUR] = findFragment(CardFragment.class);
            mFragments[FOUR] = findFragment(MeFragment.class);
        }
    }

    private int position;
    private int prePosition;

    @Override
    public void onTabSelected(int position, int prePosition) {
        this.position = position;
        this.prePosition = prePosition;
        if (position != FOUR) {
            showHideFragment(mFragments[position], mFragments[prePosition]);
        } else {
            if (SpManager.isOpenGesturePwd()) {
                // 手势解锁
                JumpManager.gotoGesturePwd();
            } else {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        SupportFragment currentFragment = mFragments[position];
        currentFragment.getChildFragmentManager().getBackStackEntryCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LockEvent event) {
        switch (event.msgId) {
            case 0: {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                break;
            }
            case 1: {
                mBottomBar.setCurrentItem(prePosition);
                break;
            }
        }
    }
}
