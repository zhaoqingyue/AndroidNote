package com.study.pickerlib.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 省份
 */
public class Province extends ItemBean {

    private List<City> cities = new ArrayList<City>();

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}