package com.study.androidnote.note.net;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.note.R;
import com.study.androidnote.note.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseNetStateActivity;
import com.study.commonlib.util.utilcode.NetworkUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 监听网络变化状态
 */
@Route( path = ArouterPath.PATH_NOTE_NET )
public class NetStateActivity extends BaseNetStateActivity {

    @BindView( R2.id.rl_state_content )
    RelativeLayout mRlContent;

    @BindView( R2.id.tv_state )
    TextView mTvState;

    @Override
    protected int getLayoutId() {
        return R.layout.note_activity_net_state;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetworkType type) {
        mTvState.setText("网络连接正常" + "（" + type.name() + "）");
        mRlContent.setVisibility(View.GONE);
    }

    @Override
    protected void onNetworkDisConnected() {
        mTvState.setText("网络连接断开");
        mRlContent.setVisibility(View.VISIBLE);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
