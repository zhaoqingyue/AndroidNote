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
import com.study.androidnote.me.util.CityListLoader;
import com.study.androidnote.me.util.Constant;
import com.study.androidnote.me.view.adapter.CityAdapter;
import com.study.androidnote.me.view.adapter.NationCodeAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 省份列表
 */
public class ProvinceActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R2.id.rv_province)
    RecyclerView mRecyclerView;

    private CityAdapter mAdapter;
    private List<CityInfoBean> mCityInfoBean;
    private CityBean mCurProvinceBean;
    private CityBean mCurCityBean;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_province;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mCityInfoBean = CityListLoader.getInstance().getProListData();
        if (mCityInfoBean == null)
            return;

        mAdapter = new CityAdapter(this, mCityInfoBean);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R2.id.ll_leftLayout, R2.id.tv_rightText})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        } else if (id == R.id.tv_rightText) {
            goToActivity(CityListActivity.class);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        CityInfoBean provinceBean = mCityInfoBean.get(position);
        mCurProvinceBean = new CityBean();
        mCurProvinceBean.setId(provinceBean.getId());
        mCurProvinceBean.setName(provinceBean.getName());
        List<CityInfoBean> cityInfoBeans = provinceBean.getCityList();
        if (cityInfoBeans != null && cityInfoBeans.size() > 0) {
            int length = cityInfoBeans.size();
            if (length == 1) {
                CityInfoBean cityInfoBean = cityInfoBeans.get(0);
                if (cityInfoBean.getName().equals("省直辖县级行政单位")) {
                    mCurCityBean = new CityBean();
                    mCurCityBean.setId(cityInfoBean.getId());
                    mCurCityBean.setName(cityInfoBean.getName());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("cityInfoBean", cityInfoBean);
                    goToActivityForResult(AreaActivity.class, bundle, Constant.RESULT_DATA_TO_AREA);
                    return;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("provinceBean", provinceBean);
            goToActivityForResult(CityActivity.class, bundle, Constant.RESULT_DATA_TO_CITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Intent intent = new Intent();
            CityBean area = data.getParcelableExtra("area");
            intent.putExtra("province", mCurProvinceBean);
            if (requestCode == Constant.RESULT_DATA_TO_CITY) {
                CityBean city = data.getParcelableExtra("city");
                intent.putExtra("city", city);
            } else if (requestCode == Constant.RESULT_DATA_TO_AREA){
                intent.putExtra("city", mCurCityBean);
            }
            intent.putExtra("area", area);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
