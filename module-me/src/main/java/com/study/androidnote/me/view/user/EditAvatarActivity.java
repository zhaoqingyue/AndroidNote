package com.study.androidnote.me.view.user;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.OnClick;

/**
 * 编辑头像
 */
public class EditAvatarActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_edit_avatar;
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

    @OnClick({R2.id.ll_avatar_camera, R2.id.ll_avatar_default, R2.id.ll_avatar_select})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_avatar_camera) {
            ToastUtils.showShortToast("打开摄像头");

        } else if (id == R.id.ll_avatar_default) {

        } else if (id == R.id.ll_avatar_select) {

        }
    }
}
