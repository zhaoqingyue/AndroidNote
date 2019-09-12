package com.study.commonlib.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.commonlib.R;
import com.study.commonlib.R2;
import com.study.commonlib.util.utilcode.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/8/14.
 * 自定义顶部栏
 */
public class TopBar extends RelativeLayout {

    @BindView( R2.id.rl_root)
    RelativeLayout mRoot;

    @BindView( R2.id.iv_leftImage)
    ImageView mLeftImage;

    @BindView( R2.id.tv_leftText)
    TextView mLeftText;

    @BindView( R2.id.tv_title)
    TextView mTitle;

    @BindView( R2.id.iv_rightImage)
    ImageView mRightImage;

    @BindView( R2.id.tv_rightText)
    TextView mRightText;

    @BindView( R2.id.view_divider)
    View mDividerLine;

    private int mBgColor;

    private int mLeftResId;
    private boolean mLeftIconVisible;

    private String mLeftContent;
    private int mLeftTextColor;
    private float mLeftTextSize;
    private boolean mLeftTextVisible;

    private String mTitleText;
    private int mTitleTextColor;
    private float mTitleTextSize;

    private int mRightResId;
    private boolean mRightIconVisible;

    private String mRightContent;
    private int mRightTextColor;
    private float mRightTextSize;
    private boolean mRightTextVisible;

    private boolean mDividerVisible;
    private int mDividerColor;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        bindView(context);
        initData();
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(context, attrs);
        bindView(context);
        initData();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        int def_color = getResources().getColor(R.color.colorBlack);

        // 背景色
        mBgColor = typedArray.getColor(R.styleable.TopBar_tb_bgColor, getResources().getColor(R.color.colorWhite));

        // 左icon图标
        mLeftResId = typedArray.getResourceId(R.styleable.TopBar_tb_leftIcon, -1);
        mLeftIconVisible = typedArray.getBoolean(R.styleable.TopBar_tb_leftIconVisible, true);

        // 左文本
        mLeftContent = typedArray.getString(R.styleable.TopBar_tb_leftText);
        mLeftTextColor = typedArray.getColor(R.styleable.TopBar_tb_leftTextColor, def_color);
        mLeftTextSize = typedArray.getDimension(R.styleable.TopBar_tb_leftTextSize, ScreenUtils.sp2px(16));
        mLeftTextVisible = typedArray.getBoolean(R.styleable.TopBar_tb_leftTextVisible, false);

        // 标题
        mTitleText = typedArray.getString(R.styleable.TopBar_tb_title);
        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_tb_titleTextColor, def_color);
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_tb_titleTextSize, ScreenUtils.sp2px(20));

        // 右icon图标
        mRightResId = typedArray.getResourceId(R.styleable.TopBar_tb_rightIcon, -1);
        mRightIconVisible = typedArray.getBoolean(R.styleable.TopBar_tb_rightIconVisible, false);

        // 右文本
        mRightContent = typedArray.getString(R.styleable.TopBar_tb_rightText);
        mRightTextColor = typedArray.getColor(R.styleable.TopBar_tb_rightTextColor, def_color);
        mRightTextSize = typedArray.getDimension(R.styleable.TopBar_tb_rightTextSize, ScreenUtils.sp2px(16));
        mRightTextVisible = typedArray.getBoolean(R.styleable.TopBar_tb_rightTextVisible, false);

        // 分割线
        mDividerVisible = typedArray.getBoolean(R.styleable.TopBar_tb_bottomDividerVisible, false);
        mDividerColor = typedArray.getColor(R.styleable.TopBar_tb_bottomDividerColor, getResources().getColor(R.color.colorDivider));

        typedArray.recycle();
    }

    private void bindView(Context context) {
        View view = inflate(context, R.layout.layout_topbar, this);
        ButterKnife.bind(this, view);
    }

    private void initData() {
        // 设置背景色
        mRoot.setBackgroundColor(mBgColor);

        // 设置左icon图标
        if (mLeftIconVisible) {
            if (mLeftResId > 0) {
                mLeftImage.setImageResource(mLeftResId);
            }
            mLeftImage.setVisibility(View.VISIBLE);
        } else {
            mLeftImage.setVisibility(View.GONE);
        }

        // 设置左文本
        if (mLeftTextVisible) {
            mLeftText.setText(mLeftContent);
            mLeftText.setTextColor(mLeftTextColor);
            mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            mLeftText.setVisibility(View.VISIBLE);
        } else {
            mLeftText.setVisibility(View.GONE);
        }

        // 设置标题
        mTitle.setText(mTitleText);
        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);

        // 设置右icon图标
        if (mRightIconVisible) {
            if (mRightResId > 0) {
                mRightImage.setImageResource(mRightResId);
            }
            mRightImage.setVisibility(View.VISIBLE);
        } else {
            mRightImage.setVisibility(View.GONE);
        }

        // 设置右文本
        if (mRightTextVisible) {
            mRightText.setText(mRightContent);
            mRightText.setTextColor(mRightTextColor);
            mRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            mRightText.setVisibility(View.VISIBLE);
        } else {
            mRightText.setVisibility(View.GONE);
        }

        // 设置分割线
        if (mDividerVisible) {
            mDividerLine.setBackgroundColor(mDividerColor);
            mDividerLine.setVisibility(View.VISIBLE);
        } else {
            mDividerLine.setVisibility(View.GONE);
        }
    }
}
