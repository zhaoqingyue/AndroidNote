package com.study.androidnote.notice.view;

import android.os.Bundle;
import android.view.View;

import com.study.androidnote.notice.R;
import com.study.androidnote.notice.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;

import butterknife.OnClick;

/**
 * 消息通知详情
 */
public class MsgDetailActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.notice_activity_msg_detail;
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
}
