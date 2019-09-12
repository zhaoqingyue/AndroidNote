package com.study.androidnote.manager.view.paperwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.view.bank.adapter.BankAdapter;
import com.study.androidnote.manager.view.paperwork.adapter.PaperworkAdapter;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.PaperworkBean;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.PaperworkBeanDao;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.PaperworkDBManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.util.utilcode.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 证件
 */
@Route(path = ArouterPath.PATH_MANAGER_PAPERWORK)
public class PaperworkActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener {

    @BindView(R2.id.rv_paperwork)
    RecyclerView mRecyclerView;

    private List<PaperworkBean> mPaperworkList;
    private PaperworkAdapter mAdapter;
    private PaperworkDBManager<PaperworkBean, PaperworkBeanDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_paperwork;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initAdapter();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new PaperworkDBManager<>(daoSession.getPaperworkBeanDao());
    }

    private void initAdapter() {
        updatePaperworkList(true);
        mAdapter = new PaperworkAdapter(this, mPaperworkList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int id = view.getId();
        if (id == R.id.ll_content) {
            PaperworkBean paperworkBean = mPaperworkList.get(position);
            String account = paperworkBean.getAccount();
            Bundle extras = new Bundle();
            extras.putString("Account", account);
            goToActivity(PaperworkDetailActivity.class, extras);
        } else if (id == R.id.ll_content_add || id == R.id.iv_add) {
            // add
            goToActivityForResult(PaperworkAddActivity.class, 0);
        }
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            updatePaperworkList(false);
        }
    }

    private void updatePaperworkList(boolean init) {
        if (mPaperworkList == null) {
            mPaperworkList = new ArrayList<>();
        } else {
            mPaperworkList.clear();
        }
        mPaperworkList.addAll(mDbManager.queryByOrderAsc());
        PaperworkBean paperworkBean = new PaperworkBean();
        paperworkBean.setItemType(PaperworkAdapter.PAPERWORK_TYPE_ADD);
        mPaperworkList.add(paperworkBean);
        if (!init) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
