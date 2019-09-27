package com.study.androidnote.me.view.adapter;

import android.content.Context;

import com.study.androidnote.me.R;
import com.study.androidnote.me.model.bean.CityInfoBean;
import com.study.androidnote.me.model.bean.CitySortBean;
import com.study.androidnote.me.model.bean.NationCodeBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/27.
 * 城市列表Adapter
 */
public class CityListAdapter extends BaseQuickAdapter<CitySortBean, BaseViewHolder> {

    private List<CitySortBean> mCitySorts;

    public CityListAdapter(Context context, List<CitySortBean> data) {
        super(R.layout.me_item_city_list, data);
        mContext = context;
        mCitySorts = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CitySortBean bean) {
        helper.addOnClickListener(R.id.ll_area_code);
        helper.setText(R.id.tv_name, bean.getName());
        String firstSpell = bean.getSortLetter().toUpperCase();
        int position = helper.getAdapterPosition();
        if (position == 0) {
            helper.setText(R.id.tv_tag, firstSpell);
            helper.setGone(R.id.tv_tag, true);
            helper.setGone(R.id.v_line, false);
        } else {
            String lastFirstSpell = mCitySorts.get(position-1).getSortLetter().toUpperCase();
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

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i=0; i< mCitySorts.size(); i++) {
            String sortStr = mCitySorts.get(i).getSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
