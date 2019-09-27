package com.study.androidnote.me.view.user.area;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.androidnote.me.model.bean.CityInfoBean;
import com.study.androidnote.me.model.bean.CitySortBean;
import com.study.androidnote.me.model.bean.NationCodeBean;
import com.study.androidnote.me.util.CityListLoader;
import com.study.androidnote.me.util.Constant;
import com.study.androidnote.me.view.adapter.CityListAdapter;
import com.study.androidnote.me.view.adapter.NationCodeAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.view.ClearEditView;
import com.study.commonlib.ui.view.SideBar;
import com.study.commonlib.util.utilcode.PinyinUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 城市列表
 */
public class CityListActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.cityInputText)
    ClearEditView mCitySearch;

    @BindView( R2.id.rv_city_list)
    RecyclerView mRecyclerView;

    @BindView( R2.id.tv_selected)
    TextView mSelected;

    @BindView( R2.id.sidebar)
    SideBar mSidebar;

    private CityListAdapter mAdapter;
    private List<CitySortBean> mCitySorts;
    private List<CityInfoBean> mCityInfos;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_city_list;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initCitySort();
        initAdapter();
        initSideBar();
        initClearEdit();
    }

    private void initCitySort() {
        mCityInfos = CityListLoader.getInstance().getCityListData();
        if (mCityInfos == null || mCityInfos.isEmpty())
            return;

        mCitySorts = new ArrayList<>();
        for (int i=0; i<mCityInfos.size(); i++) {
            CitySortBean citySortBean = new CitySortBean();
            CityInfoBean bean = mCityInfos.get(i);
            citySortBean.setId(bean.getId());
            citySortBean.setName(bean.getName());
            citySortBean.setSortLetter(getSortLetter(bean.getName()));
            citySortBean.setNamePinyin(PinyinUtils.ccs2Pinyin(bean.getName()));
            mCitySorts.add(citySortBean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mCitySorts, new CitySortBean.ComparatorPY());
    }

    private void initAdapter() {
        mAdapter = new CityListAdapter(this, mCitySorts);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    private void initSideBar() {
        mSidebar.setTextView(mSelected);
        // 设置右侧触摸监听
        mSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    // 指定的条目滚动到顶部位置
                    mRecyclerView.scrollToPosition(position);
                    LinearLayoutManager mLayoutManager =  (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
    }

    private void initClearEdit() {
        mCitySearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 根据输入框中的值来过滤数据并更新List
     */
    private void filterData(String filterStr) {
        List<CitySortBean> filterDateList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mCitySorts;
        }
        else {
            filterDateList.clear();
            for (CitySortBean sortModel : mCitySorts) {
                String name = sortModel.getName();
                if (name.contains(filterStr) || PinyinUtils.ccs2Pinyin(name).startsWith(filterStr)) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, new CitySortBean.ComparatorPY());
        mAdapter.setNewData(filterDateList);
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
        CitySortBean nationCode = mCitySorts.get(position);
        String name = nationCode.getName();
        ToastUtils.showShortToast("选择了 " + name);
    }

    /**
     *  汉字转换成拼音
     */
    private String getSortLetter(String cityName) {
        if (TextUtils.isEmpty(cityName) || cityName.isEmpty())
            return "#";

        String sortLetter = "";
        String pinyin;
        if (cityName.equals("重庆市")) {
            pinyin = "chong";
        } else if (cityName.equals("长沙市")) {
            pinyin = "chang";
        } else if (cityName.equals("长春市")) {
            pinyin = "chang";
        } else {
            pinyin =PinyinUtils.ccs2Pinyin(cityName);
        }

        if (!TextUtils.isEmpty(pinyin)) {
            sortLetter = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortLetter.matches("[A-Z]")) {
                sortLetter = sortLetter.toUpperCase();
            } else {
                sortLetter = "#";
            }
        }
        return sortLetter;
    }
}
