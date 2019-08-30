package com.study.androidnote.notice.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.androidnote.notice.R;
import com.study.androidnote.notice.R2;
import com.study.androidnote.notice.model.bean.SystemNotice;
import com.study.androidnote.notice.model.manager.NoticeManager;
import com.study.androidnote.notice.view.SysDetailActivity;
import com.study.androidnote.notice.view.adapter.SystemNoticeAdapter;
import com.study.commonlib.base.fragment.BaseFragment;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 系统公告
 */
public class SystemNoticeFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_sys_notice)
    RecyclerView mRecyclerView;

    private List<SystemNotice> mSystemNoticeList;
    private SystemNoticeAdapter mAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.notice_fragment_sys;
    }

    @Override
    protected void initData() {
        initAdapter();
    }

    @Override
    protected void initPresenter() {

    }

    private void initAdapter() {
//        mSystemNoticeList = new ArrayList<>();
        mSystemNoticeList = NoticeManager.getSystemNoticeList();
        mAdapter = new SystemNoticeAdapter(mSystemNoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), SysDetailActivity.class);
        startActivity(intent);
    }
}
