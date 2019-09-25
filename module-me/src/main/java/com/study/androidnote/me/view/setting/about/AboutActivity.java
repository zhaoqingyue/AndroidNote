package com.study.androidnote.me.view.setting.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.AppUtils;
import com.study.commonlib.util.utilcode.LogUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于
 */
public class AboutActivity extends BaseTopBarActivity {

    @BindView(R2.id.tv_about_version)
    TextView mVerName;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_about;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String verName = AppUtils.getAppVersionName(this);
        mVerName.setText("V" + verName);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick({R2.id.mc_features, R2.id.mc_homepage, R2.id.mc_update, R2.id.mc_copyright, R2.id.mc_score, R2.id.mc_share, R2.id.mc_user_agreement, R2.id.mc_privacy_agreement, R2.id.mc_reward})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_features) {
            // 功能介绍
            goToActivity(FeaturesActivity.class);
        } else if (id == R.id.mc_homepage) {
            // 项目主页
            goToActivity(GitHubActivity.class);
        } else if (id == R.id.mc_update) {
            // 版本更新
            ToastUtils.showShortToast("该功能暂未实现");
        } else if (id == R.id.mc_copyright) {
            // 版权信息
            goToActivity(CopyrightActivity.class);
        } else if (id == R.id.mc_score) {
            // 评分
            gotoScore();
        } else if (id == R.id.mc_share) {
            // 分享
            share();
        } else if (id == R.id.mc_user_agreement) {
            // 用户协议
            goToActivity(UserAgreementActivity.class);
        } else if (id == R.id.mc_privacy_agreement) {
            // 隐私协议
            goToActivity(UserAgreementActivity.class);
        } else if (id == R.id.mc_reward) {
            // 打赏
            goToActivity(RewardActivity.class);
        }
    }

    /**
     * 去评分
     */
    private void gotoScore() {
        try{
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException a){
            a.getMessage();
            LogUtils.e("ZQY", a.toString());
            ToastUtils.showShortToast("没有安装支持评分的应用市场");
        }
    }

    /**
     * 分享好友
     */
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "分享给好友");
        intent = Intent.createChooser(intent, "请选择分享方式");
        startActivity(intent);
    }
}
