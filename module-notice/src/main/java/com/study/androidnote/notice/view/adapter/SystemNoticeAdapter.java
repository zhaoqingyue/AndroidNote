package com.study.androidnote.notice.view.adapter;

import com.study.androidnote.notice.R;
import com.study.androidnote.notice.model.bean.SystemNotice;
import com.study.androidnote.notice.util.TimeUtils;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 系统公告Adapter
 */
public class SystemNoticeAdapter extends BaseQuickAdapter<SystemNotice, BaseViewHolder> {

    public SystemNoticeAdapter(List<SystemNotice> data) {
        super(R.layout.notice_item_sys, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemNotice bean) {
        helper.setText(R.id.tv_system_time, TimeUtils.stampToYmdHm(bean.getTime()));
        helper.setText(R.id.tv_sys_title, bean.getTitle());
        helper.addOnClickListener(R.id.ll_sys_layout);
    }
}
