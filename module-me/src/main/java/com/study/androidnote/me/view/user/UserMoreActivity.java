package com.study.androidnote.me.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.bean.CityBean;
import com.study.androidnote.me.view.user.area.NationActivity;
import com.study.biz.bean.event.UserInfoUpdateEvent;
import com.study.biz.constant.AppConstant;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.UserInfoDao;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.UserInfoDBManager;
import com.study.biz.db.manager.UserInfoManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.LogUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息——更多
 */
public class UserMoreActivity extends BaseTopBarActivity {

    @BindView(R2.id.mc_user_name)
    MultiCard mUserName;

    @BindView(R2.id.mc_user_mobile)
    MultiCard mMobile;

    @BindView(R2.id.mc_user_sex)
    MultiCard mSex;

    @BindView(R2.id.mc_user_birth)
    MultiCard mBirthday;

    @BindView(R2.id.mc_user_email)
    MultiCard mEmail;

    @BindView(R2.id.mc_user_area)
    MultiCard mArea;

    @BindView(R2.id.mc_user_university)
    MultiCard mUniversity;

    @BindView(R2.id.mc_user_signature)
    MultiCard mSignature;

    private UserInfoDBManager<UserInfo, UserInfoDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_user_more;
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

        // 用户名
        String username = userInfo.getUserName();
        if (TextUtils.isEmpty(username)) {
            username = getString(R.string.me_user_hint_unset);
        }
        mUserName.setContent(username);

        // 手机号
        String mobile = userInfo.getPhone();
        if (TextUtils.isEmpty(mobile)) {
            mobile = getString(R.string.me_user_hint_unset);
        }
        mMobile.setContent(mobile);

        // 性别
        String sex = userInfo.getSex();
        if (TextUtils.isEmpty(sex)) {
            sex = getString(R.string.me_user_hint_unset);
        }
        mSex.setContent(sex);

        // 出生日期
        String birthday = userInfo.getBirthday();
        if (TextUtils.isEmpty(birthday)) {
            birthday = getString(R.string.me_user_hint_unset);
        }
        mBirthday.setContent(birthday);

        // 邮箱
        String email = userInfo.getEmail();
        if (TextUtils.isEmpty(email)) {
            email = getString(R.string.me_user_hint_unset);
        }
        mEmail.setContent(email);

        // 地区
        String area = userInfo.getArea();
        if (TextUtils.isEmpty(area)) {
            area = getString(R.string.me_user_hint_select_area);
        }
        mArea.setContent(area);

        // 大学
        String university = "";
        if (TextUtils.isEmpty(area)) {
            university = getString(R.string.me_user_hint_select_university);
        }
        mUniversity.setContent(university);

        // 签名
        String signature = userInfo.getSignature();
        if (TextUtils.isEmpty(signature)) {
            signature = getString(R.string.me_user_hint_unset);
        }
        mSignature.setContent(signature);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_user_name, R2.id.mc_user_mobile, R2.id.mc_user_sex, R2.id.mc_user_birth, R2.id.mc_user_email, R2.id.mc_user_area, R2.id.mc_user_university, R2.id.mc_user_signature})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_user_name) {
            // 姓名
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_USERNAME);
            goToActivity(EditInfoActivity.class, extras);
        } else if (id == R.id.mc_user_mobile) {
            // 手机号
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_PHONE);
            goToActivity(EditInfoActivity.class, extras);
        } else if (id == R.id.mc_user_sex) {
            // 设置性别
            goToActivity(SexActivity.class);
        } else if (id == R.id.mc_user_birth) {
            // 出生日期
            goToActivity(BirthdayActivity.class);
        } else if (id == R.id.mc_user_email) {
            // 邮箱
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_EMAIL);
            goToActivity(EditInfoActivity.class, extras);
        } else if (id == R.id.mc_user_area) {
            // 地区
            goToActivity(NationActivity.class);
        } else if (id == R.id.mc_user_university) {
            // 大学
            goToActivity(UniversityActivity.class);
        } else if (id == R.id.mc_user_signature) {
            // 个人签名
            Bundle extras = new Bundle();
            extras.putInt(AppConstant.EDIT_INFO_TYPE, AppConstant.EDIT_INFO_SIGNATURE);
            goToActivity(EditInfoActivity.class, extras);
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
            case AppConstant.EDIT_INFO_USERNAME: {
                mUserName.setContent(event.msg);
                mDbManager.updateUserNameByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_PHONE: {
                mMobile.setContent(event.msg);
                mDbManager.updatePhoneByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_SEX: {
                mSex.setContent(event.msg);
                mDbManager.updateSexByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_BIRTHDAY: {
                mBirthday.setContent(event.msg);
                mDbManager.updateBirthdayByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_EMAIL: {
                mEmail.setContent(event.msg);
                mDbManager.updateEmailByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_AREA: {
                mArea.setContent(event.msg);
                mDbManager.updateAreaByUserId(userId, event.msg);
                break;
            }
            case AppConstant.EDIT_INFO_SIGNATURE: {
                mSignature.setContent(event.msg);
                mDbManager.updateSignatureByUserId(userId, event.msg);
                break;
            }
        }
    }
}
