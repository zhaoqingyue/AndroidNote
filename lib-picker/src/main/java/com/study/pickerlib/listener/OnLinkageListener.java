package com.study.pickerlib.listener;

import com.study.pickerlib.entity.City;
import com.study.pickerlib.entity.County;
import com.study.pickerlib.entity.Province;

public interface OnLinkageListener {
    /**
     * 选择地址
     *
     * @param province the province
     * @param city     the city
     * @param county   the county ，if {@code hideCounty} is true，this is null
     */
    void onAddressPicked(Province province, City city, County county);
}
