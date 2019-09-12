package com.study.androidnote.manager.view.card.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.biz.db.bean.CardBean;
import com.study.commonlib.ui.recycleradapter.BaseMultiItemQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 卡Adapter
 */
public class CardAdapter extends BaseMultiItemQuickAdapter<CardBean, BaseViewHolder> {

    private Context mContext;

    public static final int CARD_TYPE_NOR = 0;
    public static final int CARD_TYPE_ADD = 1;

    public CardAdapter(Context context, List<CardBean> data) {
        super(data);
        mContext = context;
        addItemType(CARD_TYPE_NOR, R.layout.manager_item_card);
        addItemType(CARD_TYPE_ADD, R.layout.manager_item_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardBean bean) {
        switch (helper.getItemViewType()) {
            case CARD_TYPE_NOR: {
                helper.setText(R.id.tv_userName, bean.getUserName());
                helper.setText(R.id.tv_account, bean.getAccount());
                helper.addOnClickListener(R.id.ll_content);
                break;
            }
            case CARD_TYPE_ADD: {
                helper.setText(R.id.tv_add_name, mContext.getString(R.string.manager_add_card));
                helper.addOnClickListener(R.id.ll_content_add);
                helper.addOnClickListener(R.id.iv_add);
                break;
            }
        }
    }
}
