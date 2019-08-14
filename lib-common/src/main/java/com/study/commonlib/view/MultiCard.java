package com.study.commonlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.commonlib.R;
import com.study.commonlib.util.utilcode.ScreenUtils;

/**
 * Created by zhao.qingyue on 2019/4/3.
 * 多功能卡片
 */
public class MultiCard extends LinearLayout {

    private TextView mLetter;
    private ImageView mIcon;
    private TextView mTitle;
    private TextView mTitleHint;
    private TextView mTvContent;
    private EditText mEtContent;
    private ImageView mArrow;

    private int iconResId;
    private String title;
    private int titleTextColor;
    private float titleTextSize;
    private String titleHint;
    private boolean hasLetter;
    private boolean isTitleHintVisible;
    private boolean hasContent;
    private String content;
    private int contentTextColor;
    private float contentTextSize;
    private String contentHint;
    private int contentHintTextColor;
    private float contentHintTextSize;
    private boolean isMobile;
    private boolean isEdited;
    private boolean alignRight;
    private boolean hasArrow;
    private int arrowResId;
    private boolean hasDivider;
    private int dividerColor;
    private float dividerMarginLeft;
    private float dividerMarginRight;

    public MultiCard(Context context) {
        super(context);
    }

    public MultiCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        initView(context);
    }

    public MultiCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleTypedArray(context, attrs);
        initView(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiCard);
        int def_color = getResources().getColor(R.color.colorBlack);

        // 图标
        iconResId = typedArray.getResourceId(R.styleable.MultiCard_mc_icon, -1);

        // 标题
        title = typedArray.getString(R.styleable.MultiCard_mc_title);
        titleTextColor = typedArray.getColor(R.styleable.MultiCard_mc_titleTextColor, def_color);
        titleTextSize = typedArray.getDimension(R.styleable.MultiCard_mc_titleTextSize, ScreenUtils.sp2px(18));
        hasLetter = typedArray.getBoolean(R.styleable.MultiCard_mc_hasLetter, false);

        // 标题下面的提示
        titleHint = typedArray.getString(R.styleable.MultiCard_mc_titleHint);
        isTitleHintVisible = typedArray.getBoolean(R.styleable.MultiCard_mc_titleHintVisible, false);

        // 内容
        hasContent = typedArray.getBoolean(R.styleable.MultiCard_mc_hasContent, false);
        content = typedArray.getString(R.styleable.MultiCard_mc_content);
        contentTextColor = typedArray.getColor(R.styleable.MultiCard_mc_contentTextColor, def_color);
        contentTextSize = typedArray.getDimension(R.styleable.MultiCard_mc_contentTextSize, ScreenUtils.sp2px(18));
        contentHint = typedArray.getString(R.styleable.MultiCard_mc_contentHint);
        int defHintColor = getResources().getColor(R.color.gray_868686);
        contentHintTextColor = typedArray.getColor(R.styleable.MultiCard_mc_contentHintTextColor, defHintColor);
        contentHintTextSize = typedArray.getDimension(R.styleable.MultiCard_mc_contentHintTextSize, ScreenUtils.sp2px(15));
        isMobile = typedArray.getBoolean(R.styleable.MultiCard_mc_isMobile, false);
        isEdited = typedArray.getBoolean(R.styleable.MultiCard_mc_isEdited, false);
        alignRight = typedArray.getBoolean(R.styleable.MultiCard_mc_alignRight, true);

        // 箭头
        hasArrow = typedArray.getBoolean(R.styleable.MultiCard_mc_hasArrow, true);
        arrowResId = typedArray.getResourceId(R.styleable.MultiCard_mc_arrowIcon, -1);

        // 间隔线
        hasDivider = typedArray.getBoolean(R.styleable.MultiCard_mc_hasDivider, true);
        int defLineColor = getResources().getColor(R.color.main_divider);
        dividerColor = typedArray.getColor(R.styleable.MultiCard_mc_dividerColor, defLineColor);
        dividerMarginLeft = typedArray.getDimension(R.styleable.MultiCard_mc_dividerMarginLeft, ScreenUtils.dp2px(27.5f));
        dividerMarginRight = typedArray.getDimension(R.styleable.MultiCard_mc_dividerMarginRight, ScreenUtils.dp2px(27.5f));

        typedArray.recycle();
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_multicard, this);
        /**
         * 设置图标
         */
        if (iconResId > 0) {
            mIcon = (ImageView) findViewById(R.id.iv_icon);
            mIcon.setImageResource(iconResId);
            mIcon.setVisibility(View.VISIBLE);
        }

        /**
         * 设置标题
         */
        mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText(title);
        mTitle.setTextColor(titleTextColor);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);

        /**
         * 设置第一个字符
         */
        if (hasLetter && !TextUtils.isEmpty(title)) {
            mLetter = (TextView) findViewById(R.id.tv_letter);
            mLetter.setText(title.subSequence(0, 1));
            mLetter.setVisibility(View.VISIBLE);
        }

        /**
         * 设置标题下面的提示
         */
        mTitleHint = (TextView) findViewById(R.id.tv_title_hint);
        mTitleHint.setText(titleHint);
        if(isTitleHintVisible){
            mTitleHint.setVisibility(VISIBLE);
        }else{
            mTitleHint.setVisibility(GONE);
        }

        /**
         * 设置内容
         */
        if (hasContent) {
            mTvContent = (TextView) findViewById(R.id.tv_content);
            mEtContent = (EditText) findViewById(R.id.et_content);
            if (isEdited) {
                mEtContent.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(contentHint)) {
                    mEtContent.setHint(contentHint);
                    mEtContent.setHintTextColor(contentHintTextColor);
                    mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentHintTextSize);
                }

                if (!TextUtils.isEmpty(content)) {
                    mEtContent.setText(content);
                    mEtContent.setTextColor(contentTextColor);
                    mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
                }

                if (!alignRight) {
                    // content左对齐
                    mEtContent.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                } else {
                    // content右对齐
                    mEtContent.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                }

                if (isMobile) {
                    mEtContent.setInputType(InputType.TYPE_CLASS_PHONE);
                    mEtContent.setMaxLines(11);
                } else {
                    mEtContent.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            } else {
                mTvContent.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(contentHint)) {
                    mTvContent.setText(contentHint);
                    mTvContent.setTextColor(contentHintTextColor);
                    mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentHintTextSize);
                }

                if (!TextUtils.isEmpty(content)) {
                    mTvContent.setText(content);
                    mTvContent.setTextColor(contentTextColor);
                    mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
                }

                if (!alignRight) {
                    // content左对齐
                    mTvContent.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                } else {
                    // content右对齐
                    mTvContent.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                }

                if (isMobile) {
                    int mobileColor = context.getResources().getColor(R.color.colorMobile);
                    mTvContent.setTextColor(mobileColor);
                    mTvContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        }

        /**
         * 显示右箭头
         */
        if (hasArrow) {
            mArrow = (ImageView) findViewById(R.id.iv_arrow);
            mArrow.setVisibility(View.VISIBLE);
            if (arrowResId > 0) {
                mArrow.setImageResource(arrowResId);
            }
        }

        /**
         * 间隔线不可见
         */
        View line = findViewById(R.id.v_line);
        if (!hasDivider) {
            line.setVisibility(View.INVISIBLE);
        } else {
            line.setVisibility(View.VISIBLE);
            line.setBackgroundColor(dividerColor);
            setMargins(line, (int)dividerMarginLeft, 0, (int)dividerMarginRight, 0);
        }
    }

    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            if (!isEdited) {
                mTvContent.setText(content);
                mTvContent.setTextColor(contentTextColor);
                mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
            } else {
                mEtContent.setText(content);
                mEtContent.setTextColor(contentTextColor);
                mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
            }
        }
    }

    public String getContent() {
        if (!isEdited) {
            return mTvContent.getText().toString().trim();
        } else {
            return mEtContent.getText().toString().trim();
        }
    }

    public static void setMargins (View v, int left, int top, int right, int bottom) {
        if (v.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }

    /**
     *  设置EditText的hint字体的大小
     */
    public static void  setEditTextHintSize(EditText editText, String hintText, int size){
        // 定义hint的值
        SpannableString ss = new SpannableString(hintText);
        // 设置字体大小 true表示单位是sp
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
