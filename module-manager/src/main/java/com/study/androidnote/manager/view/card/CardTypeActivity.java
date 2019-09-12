package com.study.androidnote.manager.view.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.bean.CardType;
import com.study.androidnote.manager.model.bean.PaperworkType;
import com.study.androidnote.manager.view.card.adapter.CardTypeAdapter;
import com.study.biz.db.bean.CardBean;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 卡类型
 */
public class CardTypeActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_card_type)
    RecyclerView mRecyclerView;

    @BindArray(R2.array.manager_card)
    String [] mCards ;

    private List<CardType> mCardList;
    private CardTypeAdapter mAdapter;

    private int [] mLogos = { R.mipmap.manager_card_0, R.mipmap.manager_card_1, R.mipmap.manager_card_2};

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_card_type;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mCardList = new ArrayList<>();
        for (int i=0; i<mCards.length; i++) {
            CardType cardType = new CardType();
            cardType.setLogoId(mLogos[i]);
            cardType.setCardName(mCards[i]);
            mCardList.add(cardType);
        }
        mAdapter = new CardTypeAdapter(this, mCardList);
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
            CardType cardType = mCardList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("CardType", cardType);
            Intent intent = getIntent();
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }
}
