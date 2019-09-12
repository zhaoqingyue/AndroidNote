package com.study.androidnote.manager.view.card.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.model.bean.CardType;
import com.study.biz.db.bean.CardBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 卡类型Adapter
 */
public class CardTypeAdapter extends BaseQuickAdapter<CardType, BaseViewHolder> {

    private Context mContext;

    public CardTypeAdapter(Context context, List<CardType> data) {
        super(R.layout.manager_item_card_type, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CardType bean) {
        helper.setImageResource(R.id.iv_icon, bean.getLogoId());
        helper.setText(R.id.tv_type, bean.getCardName());
        helper.addOnClickListener(R.id.ll_content);
    }
}
