package com.study.androidnote.me.view.setting.about;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.FileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能介绍
 */
public class FeaturesActivity extends BaseTopBarActivity {

    @BindView( R2.id.tv_features)
    TextView mFeatures;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_features;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        String text = FileUtils.getFromAssets(this, "func.txt");
        mFeatures.setText(text);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
