package com.study.commonlib.ui.fragmentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.study.commonlib.ui.fragmentation.helper.internal.LifecycleHelper;
import com.study.commonlib.ui.fragmentation.helper.internal.OnFragmentDestroyViewListener;
import com.study.commonlib.ui.fragmentation.helper.internal.ResultRecord;
import com.study.commonlib.ui.fragmentation.helper.internal.TransactionRecord;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/15.
 * SupportFragment
 */
public class SupportFragment extends Fragment implements ISupportFragment {

    // LaunchMode
    public static final int STANDARD = 0;
    public static final int SINGLETOP = 1;
    public static final int SINGLETASK = 2;

    // ResultCode
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;

    private static final long SHOW_SPACE = 200L;
    private static final long DEFAULT_ANIM_DURATION = 300L;

    private Bundle mNewBundle;

    private boolean mIsRoot, mIsSharedElement;
    private boolean mIsHidden = true;   // 用于记录Fragment show/hide 状态

    // SupportVisible相关
    private boolean mIsSupportVisible;
    private boolean mNeedDispatch = true;
    private boolean mInvisibleWhenLeave;
    private boolean mIsFirstVisible = true;
    private boolean mFixStatePagerAdapter;
    private Bundle mSaveInstanceState;
    private InputMethodManager mIMM;
    private boolean mNeedHideSoft;  // 隐藏软键盘

    protected SupportActivity _mActivity;
    protected FragmentationDelegate mFragmentationDelegate;
    private int mContainerId;   // 该Fragment所处的Container的id
    protected boolean mLocking; // 是否加锁 用于Fragmentation-SwipeBack库

    private OnFragmentDestroyViewListener mOnDestoryViewListener;
    private TransactionRecord mTransactionRecord;

    @IntDef({STANDARD, SINGLETOP, SINGLETASK})
    @Retention(RetentionPolicy.SOURCE)
    @interface LaunchMode {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof SupportActivity) {
            this._mActivity = (SupportActivity) activity;
            mFragmentationDelegate = _mActivity.getFragmentationDelegate();
        } else {
            throw new RuntimeException(activity.toString() + "must extends SupportActivity!");
        }

        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONATTACH, null, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mIsRoot = bundle.getBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT, false);
            mIsSharedElement = bundle.getBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT, false);
            mContainerId = bundle.getInt(FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER);
        }

        if (savedInstanceState != null) {
            mSaveInstanceState = savedInstanceState;
            mIsHidden = savedInstanceState.getBoolean(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_HIDDEN);
            if (!mFixStatePagerAdapter) { // setUserVisibleHint() may be called before onCreate()
                mInvisibleWhenLeave = savedInstanceState.getBoolean(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE);
            }
            if (mContainerId == 0) { // After strong kill, mContianerId may not be correct restored.
                mIsRoot = savedInstanceState.getBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT, false);
                mIsSharedElement = savedInstanceState.getBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT, false);
                mContainerId = savedInstanceState.getInt(FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER);
            }
        }

        if (restoreInstanceState()) {
            // 解决重叠问题
            processRestoreInstanceState(savedInstanceState);
        }

        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONCREATE, savedInstanceState, false);
    }

    private void processRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden()) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    /**
     * 内存重启后,是否让Fragmentation帮你恢复子Fragment状态
     */
    protected boolean restoreInstanceState() {
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mIsRoot) {
            outState.putBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_ROOT, true);
        }
        if (mIsSharedElement) {
            outState.putBoolean(FragmentationDelegate.FRAGMENTATION_ARG_IS_SHARED_ELEMENT, true);
        }
        outState.putInt(FragmentationDelegate.FRAGMENTATION_ARG_CONTAINER, mContainerId);
        outState.putBoolean(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_HIDDEN, isHidden());
        outState.putBoolean(FragmentationDelegate.FRAGMENTATION_STATE_SAVE_IS_INVISIBLE_WHEN_LEAVE, mInvisibleWhenLeave);
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONSAVEINSTANCESTATE, outState, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        initFragmentBackground(view);
        // 防止某种情况 上一个Fragment仍可点击问题
        if (view != null) {
            view.setClickable(true);
        }

        if (savedInstanceState != null || mIsRoot || (getTag() != null && getTag().startsWith("android:switcher:"))) {
            notifyNoAnim();
        }

        if (!mInvisibleWhenLeave && !isHidden() && (getUserVisibleHint() || mFixStatePagerAdapter)) {
            if ((getParentFragment() != null && !getParentFragment().isHidden()) || getParentFragment() == null) {
                mNeedDispatch = false;
                dispatchSupportVisible(true);
            }
        }

        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONACTIVITYCREATED, savedInstanceState, false);
    }

    private void notifyNoAnim() {
        notifyEnterAnimationEnd(mSaveInstanceState);
        _mActivity.setFragmentClickable(true);
    }

    protected void initFragmentBackground(View view) {
        setBackground(view);
    }

    protected void setBackground(View view) {
        if (view != null && view.getBackground() == null) {
            int defaultBg = _mActivity.getDefaultFragmentBackground();
            if (defaultBg == 0) {
                int background = getWindowBackground();
                view.setBackgroundResource(background);
            } else {
                view.setBackgroundResource(defaultBg);
            }
        }
    }

    protected int getWindowBackground() {
        TypedArray a = _mActivity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();
        return background;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mIsFirstVisible) {
            if (!mIsSupportVisible && !mInvisibleWhenLeave && !isHidden() && getUserVisibleHint()) {
                mNeedDispatch = false;
                dispatchSupportVisible(true);
            }
        }

        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONRESUME, null, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mIsSupportVisible && !isHidden() && getUserVisibleHint()) {
            mNeedDispatch = false;
            mInvisibleWhenLeave = false;
            dispatchSupportVisible(false);
        } else {
            mInvisibleWhenLeave = true;
        }

        if (mNeedHideSoft) {
            hideSoftInput();
        }

        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONPAUSE, null, false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isResumed()) {
            dispatchSupportVisible(!hidden);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            if (!mIsSupportVisible && isVisibleToUser) {
                dispatchSupportVisible(true);
            } else if (mIsSupportVisible && !isVisibleToUser) {
                dispatchSupportVisible(false);
            }
        } else if (isVisibleToUser) {
            mInvisibleWhenLeave = false;
            mFixStatePagerAdapter = true;
        }
    }

    /**
     * Called when the fragment is vivible.
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    public void onSupportVisible() {
    }

    /**
     * Called when the fragment is invivible.
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    public void onSupportInvisible() {
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    final public boolean isSupportVisible() {
        return mIsSupportVisible;
    }

    /**
     * Lazy initial，Called when fragment is first called.
     * <p>
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 入栈动画 结束时,回调
     */
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
    }

    private void dispatchSupportVisible(boolean visible) {
        mIsSupportVisible = visible;

        if (!mNeedDispatch) {
            mNeedDispatch = true;
        } else {
            FragmentManager fragmentManager = getChildFragmentManager();
            if (fragmentManager != null) {
                @SuppressLint( "RestrictedApi" )
                List<Fragment> childFragments = fragmentManager.getFragments();
                if (childFragments != null) {
                    for (Fragment child : childFragments) {
                        if (child instanceof SupportFragment && !child.isHidden() && child.getUserVisibleHint()) {
                            ((SupportFragment) child).dispatchSupportVisible(visible);
                        }
                    }
                }
            }
        }

        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onLazyInitView(mSaveInstanceState);
                dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONLAZYINITVIEW, null, false);
            }

            onSupportVisible();
            if (_mActivity != null) {
                _mActivity.setFragmentClickable(true);
            }
            dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONSUPPORTVISIBLE, null, true);
        } else {
            onSupportInvisible();
            dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONSUPPORTINVISIBLE, null, false);
        }
    }

    boolean isSupportHidden() {
        return mIsHidden;
    }

    /**
     * 获取该Fragment所在的容器id
     */
    int getContainerId() {
        return mContainerId;
    }

    private void notifyEnterAnimationEnd(final Bundle savedInstanceState) {
        _mActivity.getHandler().post(new Runnable() {
            @Override
            public void run() {
                onEnterAnimationEnd(savedInstanceState);
                dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONENTERANIMATIONEND, savedInstanceState, false);
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        if (getView() != null) {
            initImm();
            mIMM.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected void showSoftInput(final View view) {
        if (view == null) return;
        initImm();
        view.requestFocus();
        mNeedHideSoft = true;
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIMM.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, SHOW_SPACE);
    }

    private void initImm() {
        if (mIMM == null) {
            mIMM = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    public boolean onBackPressedSupport() {
        return false;
    }

    /**
     * Add some action when calling start()/startXX()
     */
    public SupportTransaction transaction() {
        return new SupportTransaction.SupportTransactionImpl<>(this);
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    public void loadRootFragment(int containerId, SupportFragment toFragment) {
        mFragmentationDelegate.loadRootTransaction(getChildFragmentManager(), containerId, toFragment);
    }

    /**
     * 以replace方式加载根Fragment
     */
    public void replaceLoadRootFragment(int containerId, SupportFragment toFragment, boolean addToBack) {
        mFragmentationDelegate.replaceLoadRootTransaction(getChildFragmentManager(), containerId, toFragment, addToBack);
    }

    /**
     * 加载多个同级根Fragment
     *
     * @param containerId 容器id
     * @param toFragments 目标Fragments
     */
    public void loadMultipleRootFragment(int containerId, int showPosition, SupportFragment... toFragments) {
        mFragmentationDelegate.loadMultipleRootTransaction(getChildFragmentManager(), containerId, showPosition, toFragments);
    }

    /**
     * show一个Fragment,hide其他同栈所有Fragment
     * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
     * <p>
     * 建议使用更明确的{@link #showHideFragment(SupportFragment, SupportFragment)}
     *
     * @param showFragment 需要show的Fragment
     */
    public void showHideFragment(SupportFragment showFragment) {
        showHideFragment(showFragment, null);
    }

    /**
     * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
     *
     * @param showFragment 需要show的Fragment
     * @param hideFragment 需要hide的Fragment
     */
    public void showHideFragment(SupportFragment showFragment, SupportFragment hideFragment) {
        mFragmentationDelegate.showHideFragment(getChildFragmentManager(), showFragment, hideFragment);
    }

    /**
     * 启动目标Fragment
     *
     * @param toFragment 目标Fragment
     */
    public void start(SupportFragment toFragment) {
        start(toFragment, STANDARD);
    }

    public void start(final SupportFragment toFragment, @LaunchMode final int launchMode) {
        mFragmentationDelegate.dispatchStartTransaction(getFragmentManager(), this, toFragment, 0, launchMode, FragmentationDelegate.TYPE_ADD);
    }

    public void startForResult(SupportFragment toFragment, int requestCode) {
        mFragmentationDelegate.dispatchStartTransaction(getFragmentManager(), this, toFragment, requestCode, STANDARD, FragmentationDelegate.TYPE_ADD_RESULT);
    }

    public void startWithPop(SupportFragment toFragment) {
        mFragmentationDelegate.dispatchStartTransaction(getFragmentManager(), this, toFragment, 0, STANDARD, FragmentationDelegate.TYPE_ADD_WITH_POP);
    }

    @Override
    public void replaceFragment(SupportFragment toFragment, boolean addToBack) {
        mFragmentationDelegate.replaceTransaction(this, toFragment, addToBack);
    }

    /**
     * @return 位于栈顶的Fragment
     */
    public SupportFragment getTopFragment() {
        return mFragmentationDelegate.getTopFragment(getFragmentManager());
    }

    /**
     * @return 位于栈顶的子Fragment
     */
    @Override
    public SupportFragment getTopChildFragment() {
        return mFragmentationDelegate.getTopFragment(getChildFragmentManager());
    }

    /**
     * @return 位于当前Fragment的前一个Fragment
     */
    @Override
    public SupportFragment getPreFragment() {
        return mFragmentationDelegate.getPreFragment(this);
    }

    /**
     * @return 栈内fragmentClass的fragment对象
     */
    public <T extends SupportFragment> T findFragment(Class<T> fragmentClass) {
        return mFragmentationDelegate.findStackFragment(fragmentClass, null, getFragmentManager());
    }

    public <T extends SupportFragment> T findFragment(String fragmentTag) {
        FragmentationDelegate.checkNotNull(fragmentTag, "tag == null");
        return mFragmentationDelegate.findStackFragment(null, fragmentTag, getFragmentManager());
    }

    /**
     * @return 栈内fragmentClass的子fragment对象
     */
    @Override
    public <T extends SupportFragment> T findChildFragment(Class<T> fragmentClass) {
        return mFragmentationDelegate.findStackFragment(fragmentClass, null, getChildFragmentManager());
    }

    @Override
    public <T extends SupportFragment> T findChildFragment(String fragmentTag) {
        FragmentationDelegate.checkNotNull(fragmentTag, "tag == null");
        return mFragmentationDelegate.findStackFragment(null, fragmentTag, getChildFragmentManager());
    }

    /**
     * 出栈
     */
    public void pop() {
        mFragmentationDelegate.back(getFragmentManager());
    }

    /**
     * 子栈内 出栈
     */
    @Override
    public void popChild() {
        mFragmentationDelegate.back(getChildFragmentManager());
    }

    /**
     * 出栈到目标fragment
     *
     * @param targetFragmentClass 目标fragment
     * @param includeTargetFragment   是否包含该fragment
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        popTo(targetFragmentClass.getName(), includeTargetFragment);
    }

    public void popTo(String targetFragmentTag, boolean includeTargetFragment) {
        popTo(targetFragmentTag, includeTargetFragment, null);
    }

    /**
     * 用于出栈后,立刻进行FragmentTransaction操作
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        popTo(targetFragmentClass.getName(), includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        mFragmentationDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, getFragmentManager());
    }

    /**
     * 子栈内
     */
    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        popToChild(targetFragmentClass.getName(), includeTargetFragment);
    }

    @Override
    public void popToChild(String targetFragmentTag, boolean includeTargetFragment) {
        popToChild(targetFragmentTag, includeTargetFragment, null);
    }

    @Override
    public void popToChild(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        popTo(targetFragmentClass.getName(), includeTargetFragment, afterPopTransactionRunnable);
    }

    @Override
    public void popToChild(String targetFragmentTag, boolean includeTargetFragment, Runnable afterPopTransactionRunnable) {
        mFragmentationDelegate.popTo(targetFragmentTag, includeTargetFragment, afterPopTransactionRunnable, getChildFragmentManager());
    }

    void popForSwipeBack() {
        mLocking = true;
        mFragmentationDelegate.back(getFragmentManager());
        mLocking = false;
    }

    /**
     * 设置Result数据 (通过startForResult)
     *
     * @param resultCode resultCode
     * @param bundle     设置Result数据
     */
    public void setFragmentResult(int resultCode, Bundle bundle) {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(FragmentationDelegate.FRAGMENTATION_ARG_RESULT_RECORD)) {
            return;
        }

        ResultRecord resultRecord = args.getParcelable(FragmentationDelegate.FRAGMENTATION_ARG_RESULT_RECORD);
        if (resultRecord != null) {
            resultRecord.resultCode = resultCode;
            resultRecord.resultBundle = bundle;
        }
    }

    /**
     * 接受Result数据 (通过startForResult的返回数据)
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        Result数据
     */
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     *
     * @param args 通过上个Fragment的putNewBundle(Bundle newBundle)时传递的数据
     */
    protected void onNewBundle(Bundle args) {
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     */
    public void putNewBundle(Bundle newBundle) {
        this.mNewBundle = newBundle;
    }

    Bundle getNewBundle() {
        return mNewBundle;
    }

    /**
     * 入场动画结束时,回调
     */
    void notifyEnterAnimEnd() {
        notifyEnterAnimationEnd(null);
        _mActivity.setFragmentClickable(true);
    }

    void setTransactionRecord(TransactionRecord record) {
        this.mTransactionRecord = record;
    }

    TransactionRecord getTransactionRecord() {
        return mTransactionRecord;
    }

    Bundle getSaveInstanceState() {
        return mSaveInstanceState;
    }

    /**
     * @see OnFragmentDestroyViewListener
     */
    void setOnFragmentDestoryViewListener(OnFragmentDestroyViewListener listener) {
        this.mOnDestoryViewListener = listener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONVIEWCREATED, savedInstanceState, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONSTART, null, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONSTOP, null, false);
    }

    @Override
    public void onDestroyView() {
        _mActivity.setFragmentClickable(true);
        super.onDestroyView();
        if (mOnDestoryViewListener != null) {
            mOnDestoryViewListener.onDestroyView();
            mOnDestoryViewListener = null;
        }
        mIsFirstVisible = true;
        mFixStatePagerAdapter = false;
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONDESTROYVIEW, null, false);
    }

    @Override
    public void onDestroy() {
        mFragmentationDelegate.handleResultRecord(this);
        super.onDestroy();
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONDESTROY, null, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dispatchFragmentLifecycle(LifecycleHelper.LIFECYLCE_ONDETACH, null, false);
    }

    private void dispatchFragmentLifecycle(int lifecycle, Bundle bundle, boolean visible) {
        if (_mActivity == null) return;
        _mActivity.dispatchFragmentLifecycle(lifecycle, SupportFragment.this, bundle, visible);
    }
}
