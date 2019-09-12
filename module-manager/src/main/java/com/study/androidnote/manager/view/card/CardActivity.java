package com.study.androidnote.manager.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.view.card.adapter.CardAdapter;
import com.study.androidnote.manager.view.paperwork.adapter.PaperworkAdapter;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.bean.PaperworkBean;
import com.study.biz.db.dao.CardBeanDao;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.manager.CardDBManager;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 其它卡
 */
@Route(path = ArouterPath.PATH_MANAGER_CARD)
public class CardActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemChildLongClickListener{

    @BindView(R2.id.rv_card)
    RecyclerView mRecyclerView;

    private List<CardBean> mCardList;
    private CardAdapter mAdapter;
    private CardDBManager<CardBean, CardBeanDao> mDbManager;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_card;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initAdapter();
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new CardDBManager<>(daoSession.getCardBeanDao());
    }

    private void initAdapter() {
        updateCardList(true);
        mAdapter = new CardAdapter(this, mCardList);
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
            CardBean cardBean = mCardList.get(position);
            String certificateNumber = cardBean.getCertificateNumber();
            Bundle extras = new Bundle();
            extras.putString("CertificateNumber", certificateNumber);
            goToActivity(CardDetailActivity.class, extras);
        } else if (id == R.id.ll_content_add || id == R.id.iv_add) {
            // add
            goToActivityForResult(CardAddActivity.class, 0);
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
            updateCardList(false);
        }
    }

    private void updateCardList(boolean init) {
        if (mCardList == null) {
            mCardList = new ArrayList<>();
        } else {
            mCardList.clear();
        }
        mCardList.addAll(mDbManager.queryByOrderAsc());
        CardBean cardBean = new CardBean();
        cardBean.setItemType(CardAdapter.CARD_TYPE_ADD);
        mCardList.add(cardBean);
        if (!init) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
