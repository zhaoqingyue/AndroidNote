package com.study.androidnote.me.view.user.area.adapter;

import android.content.Context;

import com.study.androidnote.me.R;
import com.study.androidnote.me.model.bean.CityInfoBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 城市Adapter
 */
public class CityAdapter extends BaseQuickAdapter<CityInfoBean, BaseViewHolder> {

    public CityAdapter(Context context, List<CityInfoBean> data) {
        super(R.layout.me_item_city, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CityInfoBean bean) {
        helper.addOnClickListener(R.id.ll_city);
        helper.setText(R.id.tv_name, bean.getName());
        int position = helper.getAdapterPosition();
        int len = getData().size();
        if (position == len-1) {
            helper.setGone(R.id.v_line, false);
        } else {
            helper.setGone(R.id.v_line, true);
        }
    }
}
