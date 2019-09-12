package com.study.androidnote.manager.view.bookkeeping;

import android.os.Bundle;
import android.view.View;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import butterknife.OnClick;

/**
 * 记账
 */
@Route(path = ArouterPath.PATH_MANAGER_BOOKKEEPING)
public class BookKeepingActivity extends BaseTopBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_book_keeping;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
