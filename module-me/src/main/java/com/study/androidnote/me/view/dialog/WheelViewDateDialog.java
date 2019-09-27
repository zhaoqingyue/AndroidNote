package com.study.androidnote.me.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import com.study.androidnote.me.R;
import com.study.androidnote.me.model.UserInfoAPI;
import com.study.commonlib.ui.dialog.nicedialog.BaseNiceDialog;
import com.study.commonlib.ui.dialog.nicedialog.ViewHolder;
import com.study.commonlib.ui.view.wheel.WheelViewDate;
import com.study.commonlib.util.utilcode.LogUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/9/27.
 * WheelViewDate弹框
 */
public class WheelViewDateDialog extends BaseNiceDialog implements WheelViewDate.OnItemSelectedListener<Integer> {

    private WheelViewDate<Integer> mWvYear;
    private WheelViewDate<Integer> mWvMonth;
    private WheelViewDate<Integer> mWvDay;
    private Calendar mCalendar;
    private SparseArray<List<Integer>> DAYS = new SparseArray(1);
    private int mCurYear;
    private int mCurMonth;
    private int mCurDay;
    private int mCurEndDayOfMonth;
    private int mChooseYear;
    private int mChooseMonth ;
    private int mType;

    public static WheelViewDateDialog newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        WheelViewDateDialog dialog = new WheelViewDateDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null)
            return;

        mType = bundle.getInt("type");
    }

    @Override
    public int intLayoutId() {
        return R.layout.me_dialog_wheelview_date;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        mWvYear = holder.getView(R.id.wvdYear);
        mWvMonth = holder.getView(R.id.wvdMonth);
        mWvDay = holder.getView(R.id.wvdDay);
        holder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int selectYear = mWvYear.getSelectedItemData().intValue();
                int selectMonth = mWvMonth.getSelectedItemData().intValue();
                int selectDay = mWvDay.getSelectedItemData().intValue();
                String monthStr = selectMonth + "";
                if (selectMonth < 10) {
                    monthStr = "0" + selectMonth;
                }
                String dayStr = selectDay + "";
                if (selectDay < 10) {
                    dayStr = "0" + selectDay;
                }
                LogUtils.e("ZQY", "selectYear: " + selectYear + ", selectMonth: " + monthStr + ", selectDay: " + dayStr);
                UserInfoAPI.getInstance().updateBirthday(selectYear + "-" + monthStr + "-" + dayStr);
            }
        });

        initCalendar();
        switch (mType) {
            case 0: {
                initYear();
                mWvYear.setVisibility(View.VISIBLE);
                mWvMonth.setVisibility(View.GONE);
                mWvDay.setVisibility(View.GONE);
                break;
            }
            case 1: {
                initYear();
                initMonth();
                mWvYear.setVisibility(View.VISIBLE);
                mWvMonth.setVisibility(View.VISIBLE);
                mWvDay.setVisibility(View.GONE);
                break;
            }
            case 2: {
                initYear();
                initMonth();
                initCurMonthDay();
                mWvYear.setVisibility(View.VISIBLE);
                mWvMonth.setVisibility(View.VISIBLE);
                mWvDay.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void initCalendar() {
        mCalendar = Calendar.getInstance();
        mCurYear = mCalendar.get(Calendar.YEAR);        // Calendar.get(1)
        mCurMonth = mCalendar.get(Calendar.MONTH) + 1;  // Calendar.get(2)
        mCurDay = mCalendar.get(Calendar.DAY_OF_MONTH); // Calendar.get(5)
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        mCurEndDayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        mChooseYear = mCurYear;
        mChooseMonth = mCurMonth;
        Log.e("ZQY", "year: " + mCurYear + ", month: " + mCurMonth + ", day: " + mCurDay);
    }

    /**
     * 初始化年份
     */
    private void initYear() {
        int startYear = 1900;
        int endYear = 2100;
        List<Integer> yearList = new ArrayList<>();
        for (int i=startYear; i<=endYear; i++) {
            yearList.add(i);
        }
        mWvYear.setData(yearList);
        mWvYear.setSelectedItemPosition(mCurYear-startYear);
        mWvYear.setOnItemSelectedListener(this);
    }

    /**
     * 初始化月份
     */
    private void initMonth() {
        List<Integer> monthList = new ArrayList<>();
        for (int i=1; i<=12; i++) {
            monthList.add(i);
        }
        mWvMonth.setData(monthList);
        mWvMonth.setSelectedItemPosition(mCurMonth-1);
        mWvMonth.setOnItemSelectedListener(this);
    }

    /**
     * 当天日期到当月最后一天
     */
    private void initCurMonthDay() {
        List<Integer> days = new ArrayList<>();
        for (int i=1; i<=mCurEndDayOfMonth; i++) {
            days.add(i);
        }
        mWvDay.setData(days);
        mWvDay.setSelectedItemPosition(mCurDay-1);
    }

    private void updateDay() {
        if(mChooseYear == mCurYear && mChooseMonth == mCurMonth) {
            initCurMonthDay();
            return;
        }
        mCalendar.set(Calendar.YEAR, mChooseYear);           // Calendar.YEAR: 1
        mCalendar.set(Calendar.MONTH, mChooseMonth - 1);     // Calendar.MONTH: 2
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);             // Calendar.DAY_OF_MONTH: 5
        mCalendar.roll(Calendar.DAY_OF_MONTH, -1); // Calendar.DAY_OF_MONTH: 5
        int days = mCalendar.get(Calendar.DAY_OF_MONTH);     // Calendar.DAY_OF_MONTH: 5
        Object data = DAYS.get(days);
        if(data == null) {
            data = new ArrayList(1);
            for(int i=1; i<=days; ++i) {
                ((List)data).add(Integer.valueOf(i));
            }
            DAYS.put(days, (List<Integer>) data);
        }
        mWvDay.setData((List)data);
    }

    @Override
    public void onItemSelected(WheelViewDate<Integer> wheelView, Integer data, int position) {
        int id = wheelView.getId();
        if (id == R.id.wvdYear) {
            mChooseYear = data.intValue();
            initMonth();
            updateDay();
        } else if (id == R.id.wvdMonth) {
            mChooseMonth = data.intValue();
            updateDay();
        }
    }
}
