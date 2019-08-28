package com.study.androidnote.search.view;

import android.text.TextUtils;
import com.study.androidnote.search.R;
import com.study.androidnote.search.model.bean.AppBean;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.BaseViewHolder;

import java.util.List;

/**
 * Created by zhao.qingyue on 2019/7/1.
 * 应用建议
 */
public class RecdAdapter extends BaseQuickAdapter<AppBean, BaseViewHolder> {

    public RecdAdapter(List<AppBean> data) {
        super(R.layout.search_item_app_recd, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppBean bean) {
        if (!TextUtils.isEmpty(bean.getAppName())) {
            helper.setText(R.id.tv_appName, bean.getAppName());
        }

        if (bean.getAppIcon() != null) {
            helper.setImageDrawable(R.id.iv_appIcon, bean.getAppIcon());
        }
    }
}
