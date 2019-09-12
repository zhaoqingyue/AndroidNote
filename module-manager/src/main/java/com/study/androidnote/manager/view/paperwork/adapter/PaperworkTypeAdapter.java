package com.study.androidnote.manager.view.paperwork.adapter;

import android.content.Context;

import com.study.androidnote.manager.R;
import com.study.androidnote.manager.model.bean.CardType;
import com.study.androidnote.manager.model.bean.PaperworkType;
import com.study.biz.db.bean.CardBean;
import com.study.biz.db.bean.PaperworkBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/30.
 * 证件类型Adapter
 */
public class PaperworkTypeAdapter extends BaseQuickAdapter<PaperworkType, BaseViewHolder> {

    private Context mContext;

    public PaperworkTypeAdapter(Context context, List<PaperworkType> data) {
        super(R.layout.manager_item_paperwork_type, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PaperworkType bean) {
        helper.setImageResource(R.id.iv_icon, bean.getLogoId());
        helper.setText(R.id.tv_type, bean.getPaperworkName());
        helper.addOnClickListener(R.id.ll_content);
    }
}
