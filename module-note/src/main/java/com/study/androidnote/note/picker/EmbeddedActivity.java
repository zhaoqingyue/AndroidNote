package com.study.androidnote.note.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.androidnote.note.R;
import com.study.androidnote.note.R2;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ConvertUtils;
import com.study.commonlib.util.utilcode.SizeUtils;
import com.study.commonlib.util.utilcode.ToastUtils;
import com.study.pickerlib.listener.OnMoreItemPickListener;
import com.study.pickerlib.listener.OnMoreWheelListener;
import com.study.pickerlib.picker.CarNumberPicker;
import com.study.pickerlib.widget.LineConfig;
import com.study.pickerlib.widget.WheelListView;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 内嵌视图选择器
 */
public class EmbeddedActivity extends BaseTopBarActivity {

    @BindView(R2.id.wheelListView)
    WheelListView wheelListView;

    @BindView(R2.id.tv_selected_nation)
    TextView mSelectedNation;

    @BindView(R2.id.wheelViewContainer)
    LinearLayout mWheelViewContainer;

    @BindView(R2.id.tv_selected_car_number)
    TextView mSelectedCarNumber;

    @BindArray( R2.array.note_nationality )
    String[] nationality;

    private CarNumberPicker picker;

    @Override
    protected int getLayoutId() {
        return R.layout.note_activity_embedded;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        initWheelListView();
        initCarNumberPicker();
    }

    private void initWheelListView() {
        wheelListView.setItems(nationality, 0);
        wheelListView.setSelectedTextColor(0xFFFF00FF);
        LineConfig config = new LineConfig();
        config.setColor(Color.parseColor("#26A1B0"));//线颜色
        config.setAlpha(100);//线透明度
        config.setThick(SizeUtils.dp2px(3));//线粗
        config.setShadowVisible(false);
        wheelListView.setLineConfig(config);
        wheelListView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {

            @Override
            public void onItemSelected(int index, String item) {
                mSelectedNation.setText(getString(R.string.note_nationality) + ": " + item);
            }
        });
    }

    private void initCarNumberPicker() {
        picker = new CarNumberPicker(this);
        picker.setWeightEnable(true);
//        picker.setColumnWeight(0.5f,0.5f,1);
        picker.setWheelModeEnable(false);
        picker.setTextSize(18);
        picker.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setCanLoop(false);
        picker.setOffset(3);
        picker.setSelectedIndex(3, 3);
        picker.setSelectedTextColor(getColor(R.color.blue_0084FF));
        picker.setUnSelectedTextColor(getColor(R.color.gray_9b9b9b));
        LineConfig lineConfig = new LineConfig();
        lineConfig.setColor(Color.RED);
        picker.setLineConfig(lineConfig);
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {
            @Override
            public void onItemPicked(String s1, String s2, String s3) {
                s3 = !TextUtils.isEmpty(s3) ? ", item3: " + s3 : "";
                ToastUtils.showShortToast( "item1: " + s1 + ", item2: " + s2 + s3);
            }
        });
        picker.setOnMoreWheelListener(new OnMoreWheelListener() {

            @Override
            public void onFirstWheeled(int index, String item) {
                mSelectedCarNumber.setText(item + picker.getSelectedSecondItem());
            }

            @Override
            public void onSecondWheeled(int index, String item) {
                mSelectedCarNumber.setText(picker.getSelectedFirstItem() + item);
            }

            @Override
            public void onThirdWheeled(int index, String item) {

            }
        } );
        mWheelViewContainer.addView(picker.getContentView());
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
            picker.setTopHeight(50);
            picker.setTopLineColor(getColor(R.color.main_divider));
            picker.setTopLineHeight(1);
            picker.setTitleText(getString(R.string.note_picker_plate));
            picker.show();
        }
    }
}
