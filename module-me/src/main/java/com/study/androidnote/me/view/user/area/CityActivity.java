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
import com.study.androidnote.me.util.Constant;
import com.study.androidnote.me.view.user.area.adapter.CityAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.util.utilcode.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 城市列表
 */
public class CityActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.rv_city)
    RecyclerView mRecyclerView;

    private CityAdapter mAdapter;
    private List<CityInfoBean> mCityInfoBeans;
    private CityInfoBean mCurProInfo;
    private CityBean mCurCityBean;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_city;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mCurProInfo = getIntent().getParcelableExtra("provinceBean");
        if (mCurProInfo != null && mCurProInfo.getCityList().size() > 0) {
            mCityInfoBeans = mCurProInfo.getCityList();
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
        mCurCityBean = new CityBean();
        mCurCityBean.setId(cityInfoBean.getId());
        mCurCityBean.setName(cityInfoBean.getName());
        Bundle bundle = new Bundle();
        bundle.putParcelable("cityInfoBean", cityInfoBean);
        goToActivityForResult(AreaActivity.class, bundle, Constant.RESULT_DATA_TO_AREA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            CityBean curArea = data.getParcelableExtra("area");
            Intent intent = new Intent();
            intent.putExtra("city", mCurCityBean);
            intent.putExtra("area", curArea);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
