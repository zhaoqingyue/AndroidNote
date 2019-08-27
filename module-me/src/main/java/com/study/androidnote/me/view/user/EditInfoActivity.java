package com.study.androidnote.me.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.biz.constant.AppConstant;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 编辑信息
 */
public class EditInfoActivity extends BaseTopBarActivity {

    @BindView(R2.id.tv_title)
    TextView mTitle;

    @BindView(R2.id.et_content)
    EditText mEditText;

    private int mEditType;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_edit_info;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String title = "";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mEditType = bundle.getInt(AppConstant.EDIT_INFO_TYPE);
        switch (mEditType) {
            case AppConstant.EDIT_INFO_USERNAME: {
                title = getString(R.string.me_user_name);
                break;
            }
            case AppConstant.EDIT_INFO_NICKNAME: {
                title = getString(R.string.me_user_nick);
                break;
            }
            case AppConstant.EDIT_INFO_ACCOUNT: {
                title = getString(R.string.me_user_account);
                break;
            }
            case AppConstant.EDIT_INFO_PHONE: {
                title = getString(R.string.me_user_mobile);
                break;
            }
            case AppConstant.EDIT_INFO_EMAIL: {
                title = getString(R.string.me_user_email);
                break;
            }
            case AppConstant.EDIT_INFO_SIGNATURE: {
                title = getString(R.string.me_user_signature);
                break;
            }
        }
        mTitle.setText(title);
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
            submit();
        }
    }

    private void submit() {
        String editStr = mEditText.getText().toString();
        if (TextUtils.isEmpty(editStr)) {
            ToastUtils.showShortToast("提交内容不可为空");
            return;
        }

        LoadingDialog.showProgress(this, getString(R.string.tip_sending));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                switch (mEditType) {
                    case AppConstant.EDIT_INFO_USERNAME: {
                        UserInfoAPI.getInstance().updateUserName(editStr);
                        break;
                    }
                    case AppConstant.EDIT_INFO_NICKNAME: {
                        UserInfoAPI.getInstance().updateNickName(editStr);
                        break;
                    }
                    case AppConstant.EDIT_INFO_ACCOUNT: {
                        UserInfoAPI.getInstance().updateAccount(editStr);
                        break;
                    }
                    case AppConstant.EDIT_INFO_PHONE: {
                        UserInfoAPI.getInstance().updatePhone(editStr);
                        break;
                    }
                    case AppConstant.EDIT_INFO_EMAIL: {
                        UserInfoAPI.getInstance().updateEmail(editStr);
                        break;
                    }
                    case AppConstant.EDIT_INFO_SIGNATURE: {
                        UserInfoAPI.getInstance().updateSignature(editStr);
                        break;
                    }
                }
                finish();
            }
        }, 1500);
    }
}
