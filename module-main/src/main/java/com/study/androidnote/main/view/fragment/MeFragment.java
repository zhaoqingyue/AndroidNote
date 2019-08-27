package com.study.androidnote.main.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.study.androidnote.main.R;
import com.study.androidnote.main.R2;
import com.study.biz.bean.event.UserInfoUpdateEvent;
import com.study.biz.constant.AppConstant;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.commonlib.base.fragment.BaseSupportFragment;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhao.qingyue on 2019/8/16.
 * 主页—我的Fragment
 */
public class MeFragment extends BaseSupportFragment {

    @BindView( R2.id.iv_avatar)
    ImageView mAvatar;

    @BindView( R2.id.tv_nick)
    TextView mNickName;

    @BindView( R2.id.tv_account)
    TextView mAccount;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_fragment_me;
    }

    protected boolean isMainFragment() {
        return true;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
//        //设置图片圆角角度
//        RoundedCorners roundedCorners = new RoundedCorners(6);
//        //通过RequestOptions扩展功能
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        // 圆形
//        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                .skipMemoryCache(true);//不做内存缓存
//        Glide.with(this)
//                .load(R.mipmap.main_logo)
////                .apply(mRequestOptions)
//                .into(mAvatar);


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void updateUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        String avatar = userInfo.getAvatar();
        Glide.with(this)
                .load(avatar)
                .error(R.mipmap.biz_avatar)
                .placeholder(R.mipmap.biz_avatar)
                .into(mAvatar);

        // 昵称
        String nick = userInfo.getNickName();
        if (TextUtils.isEmpty(nick)) {
            nick = getString(R.string.main_unset_nick);
        }
        mNickName.setText(nick);

        // 账号
        String account = userInfo.getAccount();
        if (TextUtils.isEmpty(account)) {
            account = "未设置账号？";
        }
        mAccount.setText(account);
    }

    @OnClick({R2.id.rl_user, R2.id.mc_setting, R2.id.mc_wallet })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_user) {
            // 个人信息
            ARouter.getInstance().build(ArouterPath.PATH_ME_USER_INFO).navigation();
        } else if (id == R.id.mc_setting) {
            // 设置
            ARouter.getInstance().build(ArouterPath.PATH_ME_SETTING).navigation();
        } else if (id == R.id.mc_wallet) {
            // 钱包
            ToastUtils.showShortToast("该功能暂未实现");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserInfoUpdateEvent event) {
        switch (event.msgId) {
            case AppConstant.EDIT_INFO_AVATAR: {
                Glide.with(this)
                        .load(event.msg)
                        .error(R.mipmap.biz_avatar)
                        .placeholder(R.mipmap.biz_avatar)
                        .into(mAvatar);
                break;
            }
            case AppConstant.EDIT_INFO_NICKNAME: {
                mNickName.setText(event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_ACCOUNT: {
                mAccount.setText(event.msg);
                break;
            }
        }
    }
}
