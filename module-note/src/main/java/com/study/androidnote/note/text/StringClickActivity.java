package com.study.androidnote.note.text;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.note.R;
import com.study.androidnote.note.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

@Route( path = ArouterPath.PATH_NOTE_STRING_CLICK )
public class StringClickActivity extends BaseTopBarActivity {

    @BindView( R2.id.tv_text1 )
    TextView mtvText1;

    @BindView( R2.id.tv_text2 )
    TextView mtvText2;

    @Override
    protected int getLayoutId() {
        return R.layout.note_activity_string_click;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {

        SpannableString spannableInfo = new SpannableString(mtvText1.getText().toString().trim());
        spannableInfo.setSpan( new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                // 点击事件
                ToastUtils.showShortToast( "点击选中字符串" );
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState( ds );
                //TextPaint继承Paint，可以设置更多熟悉
                ds.setColor(getColor(R.color.colorRed));
                ds.setTextSize(30);
            }

        }, 7, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        mtvText1.setText(spannableInfo);
        mtvText1.setMovementMethod( LinkMovementMethod.getInstance());

    }

    @OnClick({R2.id.ll_leftLayout})
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }
}
