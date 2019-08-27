package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.biz.bean.event.UserInfoUpdateEvent;
import com.study.biz.constant.AppConstant;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.UserInfoDao;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.UserInfoDBManager;
import com.study.biz.db.manager.UserInfoManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息
 */
@Route(path = ArouterPath.PATH_ME_USER_INFO)
public class UserInfoActivity extends BaseTopBarActivity {

    @BindView( R2.id.iv_avatar)
    ImageView mAvatar;

    @BindView(R2.id.mc_user_nick)
    MultiCard mNickName;

    @BindView(R2.id.mc_user_account)
    MultiCard mAccount;

    @BindView(R2.id.mc_user_verified)
    MultiCard mVerified;

    @BindView(R2.id.mc_user_shipping_address)
    MultiCard mShipAddress;

    private UserInfoDBManager<UserInfo, UserInfoDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_user_info;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        updateUserInfo();
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new UserInfoDBManager<>(daoSession.getUserInfoDao());
    }

    private void updateUserInfo() {
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        // 头像
        String avatar = userInfo.getAvatar();
        Glide.with(this)
                .load(avatar)
                .error(R.mipmap.biz_avatar)
                .placeholder(R.mipmap.biz_avatar)
                .into(mAvatar);

        // 昵称
        String nick = userInfo.getNickName();
        if (TextUtils.isEmpty(nick)) {
            nick = getString(R.string.me_user_hint_unset);
        }
        mNickName.setContent(nick);

        // 账号
        String account = userInfo.getAccount();
        if (TextUtils.isEmpty(account)) {
            account = getString(R.string.me_user_hint_unset);
        }
        mAccount.setContent(account);

        // 收货地址
        String shipAddress = userInfo.getShipAddress();
        if (TextUtils.isEmpty(shipAddress)) {
            shipAddress = getString(R.string.me_user_hint_unset);
        }
        mShipAddress.setContent(shipAddress);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.rl_user_avatar, R2.id.mc_user_nick, R2.id.mc_user_account, R2.id.mc_user_qrcode, R2.id.mc_user_more, R2.id.mc_user_verified, R2.id.mc_user_shipping_address})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_user_avatar) {
            // 头像
            goToActivity(EditAvatarActivity.class);
        } else if (id == R.id.mc_user_nick) {
            // 昵称
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_NICKNAME);
            goToActivity(EditInfoActivity.class, extras);
        } else if (id == R.id.mc_user_account) {
            // 账号
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_ACCOUNT);
            goToActivity(EditInfoActivity.class, extras);
        } else if (id == R.id.mc_user_qrcode) {
            // 二维码
            goToActivity(QrCodeActivity.class);
        } else if (id == R.id.mc_user_more) {
            // 更多
            goToActivity(UserMoreActivity.class);
        } else if (id == R.id.mc_user_verified) {
            // 实名认证
            goToActivity(VerifiedActivity.class);
        } else if (id == R.id.mc_user_shipping_address) {
            // 收货地址
            goToActivity(ShipAddressActivity.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserInfoUpdateEvent event) {
        if (event.errCode != 0) {
            ToastUtils.showShortToast("修改失败");
            return;
        }

        ToastUtils.showShortToast("修改成功");
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        Long userId = userInfo.getUserId();
        switch (event.msgId) {
            case AppConstant.EDIT_INFO_AVATAR: {
                Glide.with(this)
                        .load(event.msg)
                        .error(R.mipmap.biz_avatar)
                        .placeholder(R.mipmap.biz_avatar)
                        .into(mAvatar);
                mDbManager.updateAvatarByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_NICKNAME: {
                mNickName.setContent(event.msg);
                mDbManager.updateNickNameByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_ACCOUNT: {
                mAccount.setContent(event.msg);
                mDbManager.updateAccountByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_SHOP_ADDRESS: {
                mShipAddress.setContent(event.msg);
                mDbManager.updateShipAddressByUserId(userId, event.msg);
                break;
            }
        }
    }
}
