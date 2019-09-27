package com.study.androidnote.me.view.user.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.bean.CityBean;
import com.study.androidnote.me.model.bean.CityInfoBean;
import com.study.androidnote.me.view.user.area.adapter.CityAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.util.utilcode.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地区列表
 */
public class AreaActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.rv_area)
    RecyclerView mRecyclerView;

    private CityInfoBean mCurProCityInfo = null;
    private CityAdapter mAdapter;
    private List<CityInfoBean> mCityInfoBeans;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_area;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mCurProCityInfo = getIntent().getParcelableExtra("cityInfoBean");
        if (mCurProCityInfo != null && mCurProCityInfo.getCityList().size() > 0) {
            mCityInfoBeans = mCurProCityInfo.getCityList();
        }

        if (mCityInfoBeans == null)
            return;

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new CityAdapter(this, mCityInfoBeans);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        CityInfoBean cityInfoBean = mCityInfoBeans.get(position);
        CityBean curAreaBean = new CityBean();
        curAreaBean.setName(cityInfoBean.getName());
        curAreaBean.setId(cityInfoBean.getId());
        Intent reReturnIntent = new Intent();
        reReturnIntent.putExtra("area", curAreaBean);
        setResult(RESULT_OK, reReturnIntent);
        finish();
    }
}
