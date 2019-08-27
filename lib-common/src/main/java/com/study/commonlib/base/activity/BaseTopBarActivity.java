package com.study.commonlib.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.study.commonlib.R;
import com.study.commonlib.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhao.qingyue on 2019/8/15.
 * 基类Activity
 * 带自定义TopBar的基类Activity
 */
public abstract class BaseTopBarActivity extends BaseActivity {

//    @BindView(R2.id.tv_title)
//    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (isNeedBindTitle()) {
//            mTitle.setText(getTitleValue());
//        }
    }

//    protected boolean isNeedBindTitle() {
//        return false;
//    }
//
//    protected String getTitleValue() {
//        return "";
//    }
}
