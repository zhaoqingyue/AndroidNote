package com.study.androidnote.manager.view.paperwork.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.bean.PaperworkBean;
import com.study.commonlib.ui.recycleradapter.BaseMultiItemQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 证件Adapter
 */
public class PaperworkAdapter extends BaseMultiItemQuickAdapter<PaperworkBean, BaseViewHolder> {

    private Context mContext;

    public static final int PAPERWORK_TYPE_NOR = 0;
    public static final int PAPERWORK_TYPE_ADD = 1;

    public PaperworkAdapter(Context context, List<PaperworkBean> data) {
        super(data);
        mContext = context;
        addItemType(PAPERWORK_TYPE_NOR, R.layout.manager_item_paperwork);
        addItemType(PAPERWORK_TYPE_ADD, R.layout.manager_item_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, PaperworkBean bean) {
        switch (helper.getItemViewType()) {
            case PAPERWORK_TYPE_NOR: {
                helper.setImageResource(R.id.iv_icon, bean.getLogoId());
                helper.setText(R.id.tv_userName, bean.getUserName());
                helper.setText(R.id.tv_paperwork_type, bean.getPaperworkTypeName());
                helper.setText(R.id.tv_account, bean.getAccount());
                helper.addOnClickListener(R.id.ll_content);
                break;
            }
            case PAPERWORK_TYPE_ADD: {
                helper.setText(R.id.tv_add_name, mContext.getString(R.string.manager_add_paperwork));
                helper.addOnClickListener(R.id.ll_content_add);
                helper.addOnClickListener(R.id.iv_add);
                break;
            }
        }
    }
}
