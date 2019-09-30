package com.study.androidnote.me.view.user.area.adapter;

import android.content.Context;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.model.bean.CityInfoBean;
import com.study.biz.db.bean.AddressBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/26.
 * 地址Adapter
 */
public class AddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    public AddressAdapter(Context context, List<AddressBean> data) {
        super(R.layout.me_item_address, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean bean) {
        helper.addOnClickListener(R.id.ll_address);
        helper.setText(R.id.tv_name, bean.getName());
        helper.setText(R.id.tv_phone, bean.getPhone());
        helper.setText(R.id.tv_address, bean.getArea() + " " + bean.getAddress());
        helper.setGone(R.id.tv_status, bean.isDefaultAddress() ? true: false);
        int position = helper.getAdapterPosition();
        int len = getData().size();
        if (position == len-1) {
            helper.setGone(R.id.v_line, false);
        } else {
            helper.setGone(R.id.v_line, true);
        }
    }
}
