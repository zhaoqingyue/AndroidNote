package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.UserInfoDao;
import com.study.biz.db.manager.DBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.UserInfoDBManager;
import com.study.biz.db.manager.UserInfoManager;
import com.study.biz.manager.UserManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 设置性别
 */
public class SexActivity extends BaseTopBarActivity {

    private int checkedPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_sex;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.tv_rightText})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_rightText) {
            LoadingDialog.showProgress(this, getString(R.string.tip_sending));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 更新性别
                    String sex = UserManager.getInstance().getSexByPos(checkedPosition);
                    UserInfoAPI.getInstance().updateSex(sex);
                    finish();
                }
            }, 1500);
        }
    }

    @OnCheckedChanged({R2.id.rb_sex_male, R2.id.rb_sex_female})
    public void OnCheckedChangeListener(CompoundButton view, boolean isChecked ){
        int id = view.getId();
        if (id == R.id.rb_sex_male) {
            if (isChecked) {
                checkedPosition = 0;
                ToastUtils.showShortToast("选择男");
            }
        } else if (id == R.id.rb_sex_female) {
            if (isChecked) {
                checkedPosition = 1;
                ToastUtils.showShortToast("选择女");
            }
        }
    }
}
