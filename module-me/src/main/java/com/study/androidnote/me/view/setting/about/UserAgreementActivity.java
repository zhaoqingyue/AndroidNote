package com.study.androidnote.me.view.setting.about;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.FileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户协议
 */
@Route(path = ArouterPath.PATH_ME_USER_AGREEMENT)
public class UserAgreementActivity extends BaseTopBarActivity {

    @BindView( R2.id.tv_agreement)
    TextView mUserAgreement;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_user_agreement;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String text = FileUtils.getFromAssets(this, "user_agreement.txt");
        mUserAgreement.setText(text);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
