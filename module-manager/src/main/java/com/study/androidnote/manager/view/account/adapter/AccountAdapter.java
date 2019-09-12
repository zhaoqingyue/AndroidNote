package com.study.androidnote.manager.view.account.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.biz.db.bean.AccountBean;
import com.study.commonlib.ui.recycleradapter.BaseMultiItemQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;
import com.study.commonlib.util.utilcode.LogUtils;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/4.
 * 账号Adapter
 */
public class AccountAdapter extends BaseMultiItemQuickAdapter<AccountBean, BaseViewHolder> {

    private Context mContext;

    public static final int ACCOUNT_TYPE_NOR = 0;
    public static final int ACCOUNT_TYPE_ADD = 1;

    public AccountAdapter(Context context, List<AccountBean> data) {
        super(data);
        mContext = context;
        addItemType(ACCOUNT_TYPE_NOR, R.layout.manager_item_account);
        addItemType(ACCOUNT_TYPE_ADD, R.layout.manager_item_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountBean bean) {
        switch (helper.getItemViewType()) {
            case ACCOUNT_TYPE_NOR: {
                helper.setImageResource(R.id.iv_icon, bean.getLogoId());
                helper.setText(R.id.tv_userName, bean.getUserName());
                helper.setText(R.id.tv_account_type, bean.getAccountType());
                helper.setText(R.id.tv_account, bean.getAccount());
                helper.addOnClickListener(R.id.ll_content);
                break;
            }
            case ACCOUNT_TYPE_ADD: {
                helper.setText(R.id.tv_add_name, mContext.getString(R.string.manager_add_account));
                helper.addOnClickListener(R.id.ll_content_add);
                helper.addOnClickListener(R.id.iv_add);
                break;
            }
        }
    }
}

