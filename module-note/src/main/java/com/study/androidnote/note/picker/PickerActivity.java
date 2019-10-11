package com.study.androidnote.note.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.androidnote.note.R;
import com.study.androidnote.note.R2;
import com.study.biz.constant.ArouterPath;
import com.study.commonlib.base.activity.BaseTopBarActivity;
import com.study.commonlib.util.utilcode.ToastUtils;
import com.study.pickerlib.listener.OnItemPickListener;
import com.study.pickerlib.listener.OnSingleWheelListener;
import com.study.pickerlib.picker.DatePicker;
import com.study.pickerlib.picker.DateTimePicker;
import com.study.pickerlib.picker.NumberPicker;
import com.study.pickerlib.picker.SinglePicker;
import com.study.pickerlib.picker.TimePicker;
import com.study.pickerlib.widget.LineConfig;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.OnClick;

/**
 * 选择器
 */
@Route( path = ArouterPath.PATH_NOTE_PICKER )
public class PickerActivity extends BaseTopBarActivity {

    private int mTextSize;

    @Override
    protected int getLayoutId() {
        return R.layout.note_activity_picker;
    }

    @Override
    protected void initData(Bundle saveInstanceState) {
        mTextSize = (int) getResources().getDimension(R.dimen.sp_6);
    }

    @OnClick( {R2.id.ll_leftLayout} )
    public void onBackPressed(View view) {
        int id = view.getId();
        if (id == R.id.ll_leftLayout) {
            finish();
        }
    }

    @OnClick( {R2.id.mc_embedded, R2.id.mc_single, R2.id.mc_nationality, R2.id.mc_year_month_day_time, R2.id.mc_year_month_day, R2.id.mc_year_month, R2.id.mc_month_day, R2.id.mc_time, R2.id.mc_numeral_height, R2.id.mc_numeral_weight, R2.id.mc_numeral_age, R2.id.mc_numeral_temperature, R2.id.mc_constellation, R2.id.mc_programming_language, R2.id.mc_plate, R2.id.mc_address_province_city_county, R2.id.mc_address_province_city, R2.id.mc_address_city_county} )
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mc_embedded) {
            // 内嵌视图选择器
            goToActivity(EmbeddedActivity.class);

        } else if (id == R.id.mc_single) {
            // 单项选择
            singlePicker();

        } else if (id == R.id.mc_nationality) {
            // 民族选择
            nationalityPicker();

        } else if (id == R.id.mc_year_month_day_time) {
            // 年月日时间选择
            yearMonthDayTimePicker();

        } else if (id == R.id.mc_year_month_day) {
            // 年月日选择
            yearMonthDayPicker();

        } else if (id == R.id.mc_year_month) {
            // 年月选择
            yearMonthPicker();

        } else if (id == R.id.mc_month_day) {
            // 月日选择
            monthDayPicker();

        } else if (id == R.id.mc_time) {
            // 时间选择
            timePicker();

        } else if (id == R.id.mc_numeral_height) {
            // 数字选择——身高
            numeralHeightPicker();

        } else if (id == R.id.mc_numeral_weight) {
            // 数字选择——体重
            numeralWeightPicker();

        } else if (id == R.id.mc_numeral_age) {
            // 数字选择——年龄
            numeralAgePicker();

        } else if (id == R.id.mc_numeral_temperature) {
            // 数字选择——温度
            numeralTemperaturePicker();

        } else if (id == R.id.mc_constellation) {
            // 星座选择
            constellationPicker();

        } else if (id == R.id.mc_programming_language) {
            // 编程语言选择
            programmingLanguagePicker();

        } else if (id == R.id.mc_plate) {
            // 车牌选择
            platePicker();

        } else if (id == R.id.mc_address_province_city_county) {
            // 地址选择——省 + 市 + 县
            addressProvinceCityCountyPicker();

        } else if (id == R.id.mc_address_province_city) {
            // 地址选择——省 + 市
            addressProvinceCityPicker();

        } else if (id == R.id.mc_address_city_county) {
            // 地址选择——市 + 县
            addressCityCountyPicker();
        }
    }

    /**
     * 单项选择
     */
    private void singlePicker() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            String s = "";
            if (i < 10) {
                s = "0" + i;
            } else {
                s = i + "";
            }
            list.add(s);
        }
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        // 标题栏
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        // 标题
        picker.setTitleText(getString(R.string.note_picker_single));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);

        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setTextSize(mTextSize);
        picker.setSelectedIndex(8);
        picker.setWheelModeEnable(false);
        //启用权重 setWeightWidth 才起作用
        picker.setLabel("分");
        picker.setWeightEnable(true);
        picker.setWeightWidth(1);
        picker.setItemWidth(200);
        picker.setSelectedTextColor(Color.GREEN);//前四位值是透明度
        picker.setUnSelectedTextColor(Color.RED);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {

            @Override
            public void onWheeled(int index, String item) {
//                ToastUtils.showShortToast("index=" + index + ", item=" + item);
            }
        });
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    @BindArray( R2.array.note_nationality )
    String[] nationality;

    /**
     * 民族选择
     */
    private void nationalityPicker() {
        SinglePicker<String> picker = new SinglePicker<>(this, nationality);
        picker.setCanLoop(false); // 不禁用循环
        picker.setWheelModeEnable(false);
        // 标题栏
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        // 标题
        picker.setTitleText(getString(R.string.note_picker_nationality));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);
        // 取消
        picker.setCancelTextColor(getColor(R.color.btn_text_normal));
        picker.setCancelTextSize(mTextSize);
        // 确定
        picker.setSubmitTextColor(getColor(R.color.btn_text_normal));
        picker.setSubmitTextSize(mTextSize);
        // 选中
        picker.setSelectedTextColor(getColor(R.color.blue_0084FF));
        picker.setUnSelectedTextColor(getColor(R.color.gray_9b9b9b));
        // 选中线
        LineConfig config = new LineConfig();
        config.setColor(Color.RED);//线颜色
        config.setAlpha(120);//线透明度
        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(getColor(R.color.main_dialog_bg));
        picker.setSelectedIndex(0);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {

            @Override
            public void onItemPicked(int index, String item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    /**
     * 年月日时间选择
     */
    private void yearMonthDayTimePicker() {
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        picker.setTitleText(getString(R.string.note_picker_year_month_day_time));

        picker.setOffset(2);
        picker.setActionButtonTop(true);
        picker.setCanLoop(false);
        picker.setDateRangeStart(2017, 1, 1);
        picker.setDateRangeEnd(2025, 12, 31);
        picker.setSelectedItem(2019, 10, 10, 10, 10);
        picker.setTimeRangeStart(0, 0);
        picker.setTimeRangeEnd(23, 59);
        picker.setCanLinkage(false);
        picker.setWeightEnable(true);
        LineConfig config = new LineConfig();
        config.setColor(Color.BLUE);//线颜色
        config.setAlpha(120);//线透明度
        config.setVisible(true);//线不显示 默认显示
        picker.setLineConfig(config);

        picker.setLabel(null, null, null, null, null);
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {

            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                ToastUtils.showShortToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        picker.show();
    }

    /**
     * 年月日选择
     */
    private void yearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        picker.setTopPadding(15);
        picker.setTitleText(getString(R.string.note_picker_year_month_day));
        picker.setOffset(2);
        picker.setActionButtonTop(true);
        picker.setCanLoop(false);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
//        picker.setWheelModeEnable(true);

        picker.setRangeStart(2016, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(2019, 10, 14);

        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                ToastUtils.showShortToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {

            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(getString(R.string.note_picker_year_month_day) + ": " + year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(getString(R.string.note_picker_year_month_day) + ": " + picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(getString(R.string.note_picker_year_month_day) + ": " + picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 年月选择
     */
    private void yearMonthPicker() {
        DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        picker.setTopPadding(15);
        picker.setTitleText(getString(R.string.note_picker_year_month));

        picker.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        picker.setWidth(picker.getScreenWidthPixels());
        picker.setRangeStart(2010, 1, 1);
        picker.setRangeEnd(2030, 12, 31);
        picker.setSelectedItem(2019, 10);
        picker.setCanLinkage(false);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
            @Override
            public void onDatePicked(String year, String month) {
                ToastUtils.showShortToast(year + "-" + month);
            }
        });
        picker.show();
    }

    /**
     * 月日选择
     */
    private void monthDayPicker() {
        DatePicker picker = new DatePicker(this, DatePicker.MONTH_DAY);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        picker.setTopPadding(15);
        picker.setTitleText(getString(R.string.note_picker_month_day));

        picker.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setCanLoop(false);
        picker.setWeightEnable(true);
        picker.setCanLinkage(false);
        LineConfig lineConfig = new LineConfig();
        lineConfig.setColor(Color.GREEN);
        picker.setLineConfig(lineConfig);
        picker.setRangeStart(1, 1);
        picker.setRangeEnd(12, 31);
        picker.setSelectedItem(10, 1);
        picker.setOnDatePickListener(new DatePicker.OnMonthDayPickListener() {
            @Override
            public void onDatePicked(String month, String day) {
                ToastUtils.showShortToast(month + "-" + day);
            }
        });
        picker.show();
    }

    /**
     * 时间选择
     */
    private void timePicker() {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        picker.setTopPadding(15);
        picker.setTitleText(getString(R.string.note_picker_time));

        picker.setGravity(Gravity.CENTER); // 弹框居中
        picker.setRangeStart(0, 0);// 09:00
        picker.setRangeEnd(23, 59); // 18:30
        picker.setSelectedItem(10, 10);
        picker.setOffset(2);
        picker.setTopLineVisible(true);
        picker.setLineVisible(false);
//        picker.setWheelModeEnable(false);
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                ToastUtils.showShortToast(hour + ":" + minute);
            }
        });
        picker.show();
    }

    /**
     * 数字选择——身高
     */
    private void numeralHeightPicker() {
        NumberPicker picker = new NumberPicker(this);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);

        // 标题
        picker.setTitleText(getString(R.string.note_picker_numeral_height));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);

        picker.setItemWidth(200);
        picker.setCanLoop(false);
        picker.setOffset(2);//偏移量
        picker.setRange(20, 200, 1);//数字范围
        picker.setSelectedIndex(10);
        picker.setLabel("CM");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item.doubleValue());
            }
        });
        picker.show();
    }

    /**
     * 数字选择——体重
     */
    private void numeralWeightPicker() {
        NumberPicker picker = new NumberPicker(this);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);

        // 标题
        picker.setTitleText(getString(R.string.note_picker_numeral_weight));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);

        picker.setItemWidth(200);
        picker.setCanLoop(false);
        picker.setOffset(2);//偏移量
        picker.setRange(3, 100, 1);//数字范围
        picker.setSelectedIndex(10);
        picker.setLabel("KG");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item.doubleValue());
            }
        });
        picker.show();
    }

    /**
     * 数字选择——年龄
     */
    private void numeralAgePicker() {
        NumberPicker picker = new NumberPicker(this);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);

        // 标题
        picker.setTitleText(getString(R.string.note_picker_numeral_age));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);

        picker.setItemWidth(200);
        picker.setCanLoop(false);
        picker.setOffset(2);//偏移量
        picker.setRange(1, 100, 1);//数字范围
        picker.setSelectedIndex(20);
        picker.setLabel("岁");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item.doubleValue());
            }
        });
        picker.show();
    }

    /**
     * 数字选择——温度
     */
    private void numeralTemperaturePicker() {
        NumberPicker picker = new NumberPicker(this);
        picker.setAnimationStyle(R.style.note_AnimationPopup);

        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);

        // 标题
        picker.setTitleText(getString(R.string.note_picker_numeral_temperature));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);

        picker.setItemWidth(200);
        picker.setCanLoop(false);
        picker.setOffset(2);//偏移量
        picker.setRange(0.5, 40, 0.5);//数字范围
        picker.setSelectedIndex(3);
        picker.setLabel("℃");
        picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item.doubleValue());
            }
        });
        picker.show();
    }

    @BindArray( R2.array.note_constellation )
    String[] constellations;

    /**
     * 星座选择
     */
    private void constellationPicker() {
        SinglePicker<String> picker = new SinglePicker<>(this, constellations);
        picker.setCanLoop(false); // 不禁用循环
        picker.setWheelModeEnable(false);
        // 标题栏
        picker.setTopBackgroundColor(0xFFEEEEEE);
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        // 标题
        picker.setTitleText(getString(R.string.note_picker_constellation));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);
        // 取消
        picker.setCancelTextColor(getColor(R.color.btn_text_normal));
        picker.setCancelTextSize(mTextSize);
        // 确定
        picker.setSubmitTextColor(getColor(R.color.btn_text_normal));
        picker.setSubmitTextSize(mTextSize);
        // 选中
        picker.setSelectedTextColor(getColor(R.color.blue_0084FF));
        picker.setUnSelectedTextColor(getColor(R.color.gray_9b9b9b));
        // 选中线
        LineConfig config = new LineConfig();
        config.setColor(getColor(R.color.gray_9b9b9b));//线颜色
        config.setAlpha(120);//线透明度
        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(getColor(R.color.main_dialog_bg));
//        picker.setSelectedItem(constellations[5]);
        picker.setSelectedIndex(6);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {

            @Override
            public void onItemPicked(int index, String item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    @BindArray( R2.array.note_programming_language )
    String[] programmingLanguage;

    /**
     * 编程语言选择
     */
    private void programmingLanguagePicker() {
        SinglePicker<String> picker = new SinglePicker<>(this, programmingLanguage);
        picker.setCanLoop(false); // 不禁用循环
        picker.setWheelModeEnable(false);
        // 标题栏
        picker.setTopHeight(50);
        picker.setTopLineColor(getColor(R.color.main_divider));
        picker.setTopLineHeight(1);
        // 标题
        picker.setTitleText(getString(R.string.note_picker_programming_language));
        picker.setTitleTextColor(getColor(R.color.colorBlack));
        picker.setTitleTextSize(mTextSize);
        // 取消
        picker.setCancelTextColor(getColor(R.color.btn_text_normal));
        picker.setCancelTextSize(mTextSize);
        // 确定
        picker.setSubmitTextColor(getColor(R.color.btn_text_normal));
        picker.setSubmitTextSize(mTextSize);
        // 选中
        picker.setSelectedTextColor(getColor(R.color.blue_0084FF));
        picker.setUnSelectedTextColor(getColor(R.color.gray_9b9b9b));
        // 选中线
        LineConfig config = new LineConfig();
        config.setColor(Color.RED);//线颜色
        config.setAlpha(120);//线透明度
        config.setRatio(1);//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(getColor(R.color.main_dialog_bg));
        picker.setSelectedIndex(3);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {

            @Override
            public void onItemPicked(int index, String item) {
                ToastUtils.showShortToast("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    /**
     * 车牌选择
     */
    private void platePicker() {

    }

    /**
     * 地址选择——省 + 市 + 县
     */
    private void addressProvinceCityCountyPicker() {

    }

    /**
     * 地址选择——省 + 市
     */
    private void addressProvinceCityPicker() {

    }

    /**
     * 地址选择——市 + 县
     */
    private void addressCityCountyPicker() {

    }
}
