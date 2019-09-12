package com.study.commonlib.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.commonlib.R;
import com.study.commonlib.R2;
import com.study.commonlib.util.utilcode.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 编辑Item
 */
public class EditItem extends LinearLayout {

    @BindView( R2.id.tv_title)
    TextView mTvTitle;

    @BindView( R2.id.edit_content)
    EditText mEtContent;

    @BindView( R2.id.ll_uneditable)
    LinearLayout mUneditable;

    @BindView( R2.id.tv_content)
    TextView mTvContent;

    @BindView( R2.id.iv_arrow)
    ImageView mArrowIcon;

    @BindView( R2.id.v_line)
    View mDividerLine;

    private String mTitleText;
    private int mTitleTextColor;
    private float mTitleTextSize;

    private String mContentText;
    private String mContentTextHint;
    private int mContentTextColor;
    private int mContentTextColorHint;
    private float mContentTextSize;
    private boolean mContentEditable;

    private int mArrowResId;
    private boolean mArrowVisible;

    private boolean mDividerVisible;
    private int mDividerColor;

    public EditItem(Context context) {
        super(context);
    }

    public EditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        bindView(context);
        initData();
    }

    public EditItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(context, attrs);
        bindView(context);
        initData();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditItem);

        // 标题
        mTitleText = typedArray.getString(R.styleable.EditItem_et_titleText);
        mTitleTextColor = typedArray.getColor(R.styleable.EditItem_et_titleTextColor,  getResources().getColor(R.color.colorBlack));
        mTitleTextSize = typedArray.getDimension(R.styleable.EditItem_et_titleTextSize, ScreenUtils.sp2px(18));

        // 内容
        mContentText = typedArray.getString(R.styleable.EditItem_et_contentText);
        mContentTextHint = typedArray.getString(R.styleable.EditItem_et_contentTextHint);
        mContentTextColor = typedArray.getColor(R.styleable.EditItem_et_contentTextColor, getResources().getColor(R.color.colorEditNormal));
        mContentTextColorHint = typedArray.getColor(R.styleable.EditItem_et_contentTextColorHnt, getResources().getColor(R.color.colorEditHint));
        mContentTextSize = typedArray.getDimension(R.styleable.EditItem_et_contentTextSize, ScreenUtils.sp2px(18));
        mContentEditable = typedArray.getBoolean(R.styleable.EditItem_et_contentEditable, true);

        // 方向图标
        mArrowResId = typedArray.getResourceId(R.styleable.EditItem_et_arrowIcon, -1);
        mArrowVisible = typedArray.getBoolean(R.styleable.EditItem_et_arrowIconVisible, false);

        // 分割线
        mDividerVisible = typedArray.getBoolean(R.styleable.EditItem_et_bottomDividerVisible, true);
        mDividerColor = typedArray.getColor(R.styleable.EditItem_et_bottomDividerColor, getResources().getColor(R.color.colorStroke));

        typedArray.recycle();
    }

    private void bindView(Context context) {
        View view = inflate(context, R.layout.layout_edit_item, this);
        ButterKnife.bind(this, view);
    }

    private void initData() {
        // 设置标题
        mTvTitle.setText(mTitleText);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);

        // 设置内容
        if (mContentEditable) {
            mEtContent.setText(mContentText);
            mEtContent.setHint(mContentTextHint);
            mEtContent.setTextColor(mContentTextColor);
            mEtContent.setHintTextColor(mContentTextColorHint);
            mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentTextSize);

            mEtContent.setVisibility(View.VISIBLE);
            mUneditable.setVisibility(View.GONE);
        } else {
            mTvContent.setText(mContentText);
            mTvContent.setHint(mContentTextHint);
            mTvContent.setTextColor(mContentTextColor);
            mTvContent.setHintTextColor(mContentTextColorHint);
            mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentTextSize);

            mUneditable.setVisibility(View.VISIBLE);
            mEtContent.setVisibility(View.GONE);

            // 设置方向图标
            if (mArrowVisible) {
                if (mArrowResId > 0) {
                    mArrowIcon.setImageResource(mArrowResId);
                }
                mArrowIcon.setVisibility(View.VISIBLE);
            } else {
                mArrowIcon.setVisibility(View.GONE);
            }
        }

        // 设置分割线
        if (mDividerVisible) {
            mDividerLine.setBackgroundColor(mDividerColor);
            mDividerLine.setVisibility(View.VISIBLE);
        } else {
            mDividerLine.setVisibility(View.GONE);
        }
    }

    public void setContent(String content) {
        if (mContentEditable) {
            mEtContent.setText(content);
        } else {
            mTvContent.setText(content);
        }
    }

    public String getContent() {
        if (mContentEditable) {
            return mEtContent.getText().toString();
        } else {
            return mTvContent.getText().toString();
        }
    }

    public EditText getEditText() {
        return mEtContent;
    }
}
