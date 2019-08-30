package com.study.androidnote.notice.view.adapter;

import com.study.androidnote.notice.R;
import com.study.androidnote.notice.model.bean.MsgNotice;
import com.study.androidnote.notice.util.TimeUtils;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 消息通知Adapter
 */
public class MsgNoticeAdapter extends BaseQuickAdapter<MsgNotice, BaseViewHolder> {

    public MsgNoticeAdapter(List<MsgNotice> data) {
        super(R.layout.notice_item_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgNotice bean) {
        helper.setText(R.id.tv_system_time, TimeUtils.stampToYmdHm(bean.getTime()));
        helper.setText(R.id.tv_msg_title, bean.getTitle());
        helper.addOnClickListener(R.id.ll_msg_layout);
    }
}
