package com.study.androidnote.main.view.adapter;

import com.study.androidnote.main.R;
import com.study.androidnote.main.model.ManagerBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 管理Adapter
 */
public class ManagerAdapter extends BaseQuickAdapter<ManagerBean, BaseViewHolder> {

    public ManagerAdapter(List<ManagerBean> data) {
        super(R.layout.main_item_manager, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ManagerBean bean) {
        helper.setImageResource(R.id.iv_icon, bean.getResId());
        helper.setText(R.id.tv_name, bean.getName());
        helper.addOnClickListener(R.id.ll_content);
    }
}
