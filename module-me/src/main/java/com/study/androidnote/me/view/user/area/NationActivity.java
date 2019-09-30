package com.study.androidnote.me.view.user.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.androidnote.me.model.bean.CityBean;
import com.study.androidnote.me.model.bean.NationCodeBean;
import com.study.androidnote.me.util.CityListLoader;
import com.study.androidnote.me.util.Constant;
import com.study.androidnote.me.view.user.area.adapter.NationCodeAdapter;
import com.study.biz.db.bean.UserInfo;
import com.study.biz.db.manager.UserInfoManager;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.view.MultiCard;
import com.study.commonlib.util.utilcode.LogUtils;
import com.study.commonlib.util.utilcode.PinyinUtils;
import com.study.commonlib.util.utilcode.ToastUtils;
import com.study.commonlib.util.zqy.ReadAssetsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 所有国家 & 地区
 */
public class NationActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.mc_current_location)
    MultiCard mCurLocation;

    @BindView( R2.id.rv_all_area)
    RecyclerView mRecyclerView;

    private NationCodeAdapter mAdapter;
    private List<NationCodeBean> mAreaCodes;
    private String mCurArea;

    @Override
    protected int getLayoutId() {
        return R.layout.me_activity_nation;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initCurLocation();
        initAreaCode();
        initAdapter();
    }

    private void initCurLocation() {
        UserInfo userInfo = UserInfoManager.getInstance();
        if (userInfo == null)
            return;

        // 定位当前位置
        mCurArea = userInfo.getArea();
        if (TextUtils.isEmpty(mCurArea)) {
            mCurArea = getString(R.string.me_user_hint_location);
        }
        mCurLocation.setTitle(mCurArea);
    }

    private void initAreaCode() {
        mAreaCodes = new ArrayList<>();
        JSONArray array = ReadAssetsUtils.getJSONArray(this, Constant.JSON_NATION_CODE);
        if (null == array) array = new JSONArray();
        for (int i=0; i<array.length(); i++) {
            NationCodeBean bean = new NationCodeBean();
            JSONObject jsonObject = array.optJSONObject(i);
            bean.setName(jsonObject.optString("zh"));
            bean.setFirstSpell(PinyinUtils.getPinyinFirstLetter(bean.getName().substring(0,1)));
            bean.setNamePinyin(PinyinUtils.ccs2Pinyin(bean.getName()));
            bean.setCode(jsonObject.optString("code"));
            bean.setLocale(jsonObject.optString("locale"));
            bean.setEnName(jsonObject.optString("en"));
            mAreaCodes.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mAreaCodes, new NationCodeBean.ComparatorPY());
    }

    private void initAdapter() {
        mAdapter = new NationCodeAdapter(this, mAreaCodes);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R2.id.mc_current_location})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_current_location) {
            if (mCurArea.startsWith("中国")) {
                goToActivityForResult(ProvinceActivity.class, Constant.RESULT_DATA_TO_PROVINCE);
            }
        }
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
        NationCodeBean nationCode = mAreaCodes.get(position);
        String code = nationCode.getCode();
        String name = nationCode.getName();
        if (code.equals("86")) {
            goToActivityForResult(ProvinceActivity.class, Constant.RESULT_DATA_TO_PROVINCE);
        } else {
            ToastUtils.showShortToast("选择" + name + ", " + code);
            UserInfoAPI.getInstance().updateArea(name);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RESULT_DATA_TO_PROVINCE && resultCode == RESULT_OK && data != null) {
            CityBean area = data.getParcelableExtra("area");
            CityBean city = data.getParcelableExtra("city");
            CityBean province = data.getParcelableExtra("province");
            String text = "所选省市区城市： " + province.getName() + " " + province.getId() + "\n" + city.getName() + " " + city.getId() + "\n" + area.getName() + " " + area.getId() + "\n";
            LogUtils.e("ZQY", text);

            String areaStr;
            if (city.getName().equals("省直辖县级行政单位")) {
                areaStr = "中国 " + province.getName() + " " + area.getName();
            } else {
                areaStr = "中国 " + province.getName() + " " + city.getName() + " " + area.getName();
            }
            mCurLocation.setContent(areaStr);
            UserInfoAPI.getInstance().updateArea(areaStr);
            finish();
        }
    }
}
