package com.study.androidnote.me.view.adapter;

import android.content.Context;

import com.study.androidnote.me.R;
import com.study.androidnote.me.model.bean.NationCodeBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 国家编码Adapter
 */
public class NationCodeAdapter extends BaseQuickAdapter<NationCodeBean, BaseViewHolder> {

    private List<NationCodeBean> mAreaCodes;

    public NationCodeAdapter(Context context, List<NationCodeBean> data) {
        super(R.layout.me_item_nation, data);
        mContext = context;
        mAreaCodes = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NationCodeBean bean) {
        helper.addOnClickListener(R.id.ll_area_code);
        helper.setText(R.id.tv_name, bean.getName());
        helper.setText(R.id.tv_code, "+" + bean.getCode());
        String firstSpell = bean.getFirstSpell().toUpperCase();
        int position = helper.getAdapterPosition();
        if (position == 0) {
            helper.setText(R.id.tv_tag, firstSpell);
            helper.setGone(R.id.tv_tag, true);
            helper.setGone(R.id.v_line, false);
        } else {
            String lastFirstSpell = mAreaCodes.get(position-1).getFirstSpell().toUpperCase();
            if (firstSpell.equals(lastFirstSpell)) {
                helper.setGone(R.id.tv_tag, false);
                helper.setGone(R.id.v_line, true);
            } else {
                helper.setText(R.id.tv_tag, firstSpell);
                helper.setGone(R.id.tv_tag, true);
                helper.setGone(R.id.v_line, false);
            }
        }
    }
}
