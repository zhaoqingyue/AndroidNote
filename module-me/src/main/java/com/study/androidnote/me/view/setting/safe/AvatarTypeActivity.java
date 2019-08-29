package com.study.androidnote.me.view.setting.safe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.biz.manager.UserManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.util.utilcode.ToastUtils;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 头像类型
 */
public class AvatarTypeActivity extends BaseTopBarActivity {

    private int checkedPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_avatar_type;
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
            LoadingDialog.showProgress(this, getString(R.string.tip_setting));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 更新头像类型
//                    String sex = UserManager.getInstance().getSexByPos(checkedPosition);
//                    UserInfoAPI.getInstance().updateSex(sex);
                    finish();
                }
            }, 1500);
        }
    }

    @OnCheckedChanged({R2.id.rb_avatar_type_square, R2.id.rb_avatar_type_round_angle, R2.id.rb_avatar_type_circle, R2.id.rb_avatar_type_circle_V})
    public void OnCheckedChangeListener(CompoundButton view, boolean isChecked ){
        int id = view.getId();
        if (id == R.id.rb_avatar_type_square) {
            if (isChecked) {
                checkedPosition = 0;
            }
        } else if (id == R.id.rb_avatar_type_round_angle) {
            if (isChecked) {
                checkedPosition = 1;
            }
        } else if (id == R.id.rb_avatar_type_circle) {
            if (isChecked) {
                checkedPosition = 2;
            }
        } else if (id == R.id.rb_avatar_type_circle_V) {
            if (isChecked) {
                checkedPosition = 3;
            }
        }
    }
}
