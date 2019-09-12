package com.study.androidnote.manager.view.paperwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.androidnote.manager.model.bean.PaperworkType;
import com.study.androidnote.manager.view.paperwork.adapter.PaperworkTypeAdapter;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账号类型
 */
public class PaperworkTypeActivity extends BaseTopBarActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView( R2.id.rv_paperwork_type)
    RecyclerView mRecyclerView;

    @BindArray(R2.array.manager_paperwork)
    String [] mPaperWorks ;

    private List<PaperworkType> mPaperworkList;
    private PaperworkTypeAdapter mAdapter;

    private int [] mLogos = { R.mipmap.manager_paperwork_0, R.mipmap.manager_paperwork_1, R.mipmap.manager_paperwork_2,
            R.mipmap.manager_paperwork_3, R.mipmap.manager_paperwork_4, R.mipmap.manager_paperwork_5};

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_paperwork_type;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initAdapter();
    }

    private void initAdapter() {
        mPaperworkList = new ArrayList<>();
        for (int i=0; i<mPaperWorks.length; i++) {
            PaperworkType paperworkType = new PaperworkType();
            paperworkType.setLogoId(mLogos[i]);
            paperworkType.setPaperworkName(mPaperWorks[i]);
            mPaperworkList.add(paperworkType);
        }
        mAdapter = new PaperworkTypeAdapter(this, mPaperworkList);
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
        int id = view.getId();
        if (id == R.id.ll_content) {
            PaperworkType paperworkType = mPaperworkList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("PaperworkType", paperworkType);
            Intent intent = getIntent();
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }
}
