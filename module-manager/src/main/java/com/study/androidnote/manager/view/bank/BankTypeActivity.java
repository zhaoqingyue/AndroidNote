package com.study.androidnote.manager.view.bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import com.study.androidnote.manager.R;
import com.study.androidnote.manager.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 银行卡类型
 */
public class BankTypeActivity extends BaseTopBarActivity {

    private int checkedPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.manager_activity_bank_type;
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

    @OnClick({R2.id.tv_rightText})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_rightText) {
            // 不处理
        }
    }

    @OnCheckedChanged({R2.id.rb_bank_type_debit, R2.id.rb_bank_type_credit})
    public void OnCheckedChangeListener(CompoundButton view, boolean isChecked ){
        int id = view.getId();
        if (id == R.id.rb_bank_type_debit) {
            if (isChecked) {
                checkedPosition = 0;
                ToastUtils.showShortToast("借记卡");

                Intent intent = getIntent();
                intent.putExtra("BankType", checkedPosition);
                setResult(1, intent);
                finish();
            }
        } else if (id == R.id.rb_bank_type_credit) {
            if (isChecked) {
                checkedPosition = 1;
                ToastUtils.showShortToast("信用卡");

                Intent intent = getIntent();
                intent.putExtra("BankType", checkedPosition);
                setResult(1, intent);
                finish();
            }
        }
    }
}
