package com.study.androidnote.manager.view.account.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.model.bean.AccountType;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/5.
 * 账户类型Adapter
 */
public class AccountTypeAdapter extends BaseQuickAdapter<AccountType, BaseViewHolder> {

    public AccountTypeAdapter(Context context, List<AccountType> data) {
        super(R.layout.manager_item_account_type, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountType bean) {
        helper.setImageResource(R.id.iv_icon, bean.getLogoId());
        helper.setText(R.id.tv_type, bean.getAccountName());
        helper.addOnClickListener(R.id.ll_content);
    }
}
