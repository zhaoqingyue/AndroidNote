package com.study.androidnote.notice.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.androidnote.notice.R;
import com.study.androidnote.notice.R2;
import com.study.androidnote.notice.model.bean.MsgNotice;
import com.study.androidnote.notice.model.manager.NoticeManager;
import com.study.androidnote.notice.view.MsgDetailActivity;
import com.study.androidnote.notice.view.adapter.MsgNoticeAdapter;
import com.study.commonlib.base.fragment.BaseFragment;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 消息通知
 */
public class MsgNoticeFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_msg_notice)
    RecyclerView mRecyclerView;

    private List<MsgNotice> mMsgNoticeList;
    private MsgNoticeAdapter mAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.notice_fragment_msg;
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    @Override
    protected void initPresenter() {

    }

    private void initAdapter() {
//        mMsgNoticeList = new ArrayList<>();
        mMsgNoticeList = NoticeManager.getMsgNoticeList();
        mAdapter = new MsgNoticeAdapter(mMsgNoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), MsgDetailActivity.class);
        startActivity(intent);
    }
}
