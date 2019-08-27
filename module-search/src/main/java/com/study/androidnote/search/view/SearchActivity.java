package com.study.androidnote.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.study.androidnote.search.R;
import com.study.androidnote.search.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 */
@Route(path = ArouterPath.PATH_HOME_SEARCH)
public class SearchActivity extends BaseActivity {

    @BindView( R2.id.et_search)
    EditText mEditText;

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity_search;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @OnClick({R2.id.tv_cancel })
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
           finish();
        }
    }
}
