package com.study.androidnote.manager.view.bank.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.model.manager.ComplexManager;
import com.study.biz.db.bean.BankBean;
import com.study.commonlib.ui.recycleradapter.BaseMultiItemQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 银行卡Adapter
 */
public class BankAdapter extends BaseMultiItemQuickAdapter<BankBean, BaseViewHolder> {

    private Context mContext;

    public static final int BANK_TYPE_NOR = 0;
    public static final int BANK_TYPE_ADD = 1;

    public BankAdapter(Context context, List<BankBean> data) {
        super(data);
        mContext = context;
        addItemType(BANK_TYPE_NOR, R.layout.manager_item_bank);
        addItemType(BANK_TYPE_ADD, R.layout.manager_item_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean bean) {
        switch (helper.getItemViewType()) {
            case BANK_TYPE_NOR: {
                helper.setImageResource(R.id.iv_icon, bean.getLogoId());
                helper.setText(R.id.tv_bank_name, bean.getBankTypeName());
                helper.setText(R.id.tv_bank_type, ComplexManager.getInstance().getBankTypeByPos(bean.getBankType()));
                helper.setText(R.id.tv_bank_number, bean.getAccount());
                helper.addOnClickListener(R.id.ll_content);
                break;
            }
            case BANK_TYPE_ADD: {
                helper.setText(R.id.tv_add_name, mContext.getString(R.string.manager_add_bank));
                helper.addOnClickListener(R.id.ll_content_add);
                helper.addOnClickListener(R.id.iv_add);
                break;
            }
        }
    }
}
