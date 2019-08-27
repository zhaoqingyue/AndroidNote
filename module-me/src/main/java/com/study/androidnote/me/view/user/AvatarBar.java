package com.study.androidnote.me.view.user;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.androidnote.me.R;
import com.study.androidnote.me.R2;
import com.study.commonlib.util.utilcode.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/8/21.
 * 头像Bar
 */
public class AvatarBar extends LinearLayout implements View.OnTouchListener {

    @BindView( R2.id.iv_avatar_icon)
    ImageView mAvatarIcon;

    @BindView( R2.id.iv_avatar_type)
    TextView mAvatarType;

    private int mResId;
    private String mTypeValue;

//    public AvatarBar(Context context) {
//        super(context);
//        handleTypedArray(context, null);
//        bindView(context);
//        initData();
//    }

    public AvatarBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        bindView(context);
        initData();
    }

//    public AvatarBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        handleTypedArray(context, attrs);
//        bindView(context);
//        initData();
//    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.me_AvatarBar);
        // 图标
        mResId = typedArray.getResourceId(R.styleable.me_AvatarBar_me_ab_avatarIcon, -1);
        // 类型
        mTypeValue = typedArray.getString(R.styleable.me_AvatarBar_me_ab_avatarType);
        typedArray.recycle();
    }

    private void bindView(Context context) {
        View view = inflate(context, R.layout.me_layout_avatar_bar, this);
        ButterKnife.bind(this, view);
    }

    private void initData() {
        if (mResId > 0) {
            mAvatarIcon.setImageResource(mResId);
        }
        mAvatarType.setText(mTypeValue);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mAvatarIcon.setSelected(selected);
        mAvatarType.setSelected(selected);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            setSelected(true);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            setSelected(false);
        }
        return false;
    }
}
