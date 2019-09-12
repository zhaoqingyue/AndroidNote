package com.study.androidnote.manager.view.bank.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.model.bean.BankType;
import com.study.androidnote.manager.model.bean.CardType;
import com.study.biz.db.bean.CardBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 选择银行卡Adapter
 */
public class BankSelectAdapter extends BaseQuickAdapter<BankType, BaseViewHolder> {

    private Context mContext;

    public BankSelectAdapter(Context context, List<BankType> data) {
        super(R.layout.manager_item_bank_select, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankType bean) {
        helper.setImageResource(R.id.iv_icon, bean.getLogoId());
        helper.setText(R.id.tv_bank_name, bean.getBankName());
        helper.addOnClickListener(R.id.ll_content);
    }
}
