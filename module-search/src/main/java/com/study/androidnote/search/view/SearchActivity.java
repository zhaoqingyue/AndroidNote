package com.study.androidnote.search.view;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.study.androidnote.search.R;
import com.study.androidnote.search.R2;
import com.study.androidnote.search.model.AppManager;
import com.study.androidnote.search.model.SearchAPI;
import com.study.androidnote.search.model.bean.AppBean;
import com.study.androidnote.search.model.bean.SearchEvent;
import com.study.biz.constant.ArouterPath;
import com.study.biz.db.bean.SearchKey;
import com.study.biz.db.dao.DaoSession;
import com.study.biz.db.dao.SearchKeyDao;
import com.study.biz.db.manager.DaoSessionGenerator;
import com.study.biz.db.manager.SearchKeyDBManager;
import com.study.biz.manager.JumpManager;
import com.study.commonlib.base.activity.BaseActivity;
import com.study.commonlib.ui.dialog.LoadingDialog;
import com.study.commonlib.ui.recycleradapter.BaseQuickAdapter;
import com.study.commonlib.ui.recycleradapter.divider.SpacesItemDecoration;
import com.study.commonlib.util.utilcode.AppUtils;
import com.study.commonlib.util.utilcode.KeyboardUtils;
import com.study.commonlib.util.utilcode.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 搜索
 */
@Route(path = ArouterPath.PATH_HOME_SEARCH)
public class SearchActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener{

    @BindView( R2.id.et_search)
    EditText mEditText;

    @BindView( R2.id.iv_clear)
    ImageView mClear;

    @BindView( R2.id.tv_empty)
    TextView mNoData;

    @BindView( R2.id.ll_recd)
    LinearLayout mRecdLayout;

    @BindView( R2.id.rv_recd)
    RecyclerView mRecdRv;

    @BindView( R2.id.ll_history)
    LinearLayout mHistoryLayout;

    @BindView( R2.id.zfl_history)
    FlowLayout1 mFlowLayout;

    private RecdAdapter mRecdAdapter;
    private List<AppBean> mRecdList;
    private SearchKeyDBManager<SearchKey, SearchKeyDao> mDbManager;

    // 数据模拟，实际应从网络获取此数据
    private String[] mVal = new String[] {"Java是世界上最好的编程语言", "Android", "iOS", "Python", "Mac OS", "PHP", "JavaScript", "Objective-C", "Groovy", "Pascal", "Ruby", "Go", "Swift",
            "1111", "22222", "33333", "4444", "55555", "6666666", "7777777", "8888888"};

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity_search;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initDB();
        initRecdAdapter();
        updateSearchKey(true);
        setFlowLayoutListener();
        setEditListener();
    }

    @Override
    protected boolean receiveEventBus() {
        return true;
    }

    private void initDB() {
        DaoSession daoSession = DaoSessionGenerator.getInstance().getDaoSession();
        mDbManager = new SearchKeyDBManager<>(daoSession.getSearchKeyDao());
    }

    private void initRecdAdapter() {
        mRecdList = AppManager.getRecdAppList(this);
        mRecdAdapter = new RecdAdapter(mRecdList);
        mRecdAdapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecdRv.setLayoutManager(manager);
        mRecdRv.addItemDecoration(new SpacesItemDecoration(10));
        mRecdRv.setAdapter(mRecdAdapter);
    }

    private void updateSearchKey(boolean init) {
        List<SearchKey> searchKeys = mDbManager.queryByOrderAsc();
        if (searchKeys == null || searchKeys.isEmpty()) {
            searchKeys = new ArrayList<>();
            for (int i=0; i<mVal.length; i++) {
                SearchKey searchKey = new SearchKey();
                searchKey.setKeyWod(mVal[i]);
                searchKeys.add(searchKey);
                mDbManager.insertEntity(searchKey);
            }
        }

        if (init) {
//            mFlowLayout.setIsLimitLine(true);
//            mFlowLayout.setLimitLineCount(4);
            mFlowLayout.setAdapter(this, searchKeys);
        } else {
            mFlowLayout.changeAdapter(this, searchKeys);
        }
    }

    private void setFlowLayoutListener() {
        mFlowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {

            @Override
            public void onItemClick(int position, String key) {
                SearchAPI.getInstance().search(key);
            }
        });
    }

    @OnClick({R2.id.iv_clear, R2.id.tv_cancel, R2.id.tv_clear})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_clear) {
            mEditText.setText("");
            onClear();
        } else if (id == R.id.tv_cancel) {
           finish();
        } else if (id == R.id.tv_clear) {
            mDbManager.deleteAll();
            mHistoryLayout.setVisibility(View.GONE);
        }
    }

    private void onClear() {
        mClear.setVisibility(View.GONE);
        mNoData.setVisibility(View.GONE);
        mRecdLayout.setVisibility(View.VISIBLE);
        mHistoryLayout.setVisibility(View.VISIBLE);
        updateSearchKey(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AppBean appBean = mRecdList.get(position);
        if (!TextUtils.isEmpty(appBean.getPkgName())) {
            AppUtils.launchApp(this, appBean.getPkgName());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        switch (event.msgId) {
            case 0: {
                mNoData.setVisibility(View.VISIBLE);
                mRecdLayout.setVisibility(View.GONE);
                mHistoryLayout.setVisibility(View.GONE);
                break;
            }
        }
    }

    private void setEditListener() {
        // EditText的TextWatcher文本改变事件
        RxTextView.textChanges(mEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CharSequence, String>() {

                    @Override
                    public String apply(@NonNull CharSequence charSequence) throws Exception {
                        return new StringBuilder(charSequence).toString();
                    }
                }).subscribe(new Consumer<String>() {

            @Override
            public void accept(String key) throws Exception {
                if (!TextUtils.isEmpty(key)) {
                    mClear.setVisibility(View.VISIBLE);
                    SearchAPI.getInstance().search(key);
                    if (mDbManager.querySearchKey(key) == null) {
                        SearchKey searchKey = new SearchKey();
                        searchKey.setKeyWod(key);
                        mDbManager.insertEntity(searchKey);
                    }
                    // searchPresenter.search(mContext, key);
                } else {
                    // onSearchEmpty(0, key);
                    onClear();
                }
            }
        });

        mEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    KeyboardUtils.hideSoftInput(SearchActivity.this);
                    return true;
                }
                return false;
            }
        });

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    KeyboardUtils.hideSoftInput(SearchActivity.this);
                    return true;
                }
                return false;
            }
        });
    }
}
