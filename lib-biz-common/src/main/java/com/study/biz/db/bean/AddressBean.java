package com.study.biz.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/9/29.
 * 地址Bean
 */
@Entity
public class AddressBean {

    @Id(autoincrement = true)
    private Long addressId;

    @Property(nameInDb = "name")
    private String name;  // 收货人姓名

    @Property(nameInDb = "phone")
    private String phone;  // 收货人手机号

    @Property(nameInDb = "area")
    private String area;  // 地区信息

    @Property(nameInDb = "address")
    private String address;  // 地区信息

    @Property(nameInDb = "postCode")
    private String postCode; // 邮政编码

    @Property(nameInDb = "tag")
    private String tag;     // 标签

    @Property(nameInDb = "defaultAddress")
    private boolean defaultAddress; // 是否默认地址

    public AddressBean() {
    }

    @Generated(hash = 1758571865)
    public AddressBean(Long addressId, String name, String phone, String area,
            String address, String postCode, String tag, boolean defaultAddress) {
        this.addressId = addressId;
        this.name = name;
        this.phone = phone;
        this.area = area;
        this.address = address;
        this.postCode = postCode;
        this.tag = tag;
        this.defaultAddress = defaultAddress;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public boolean getDefaultAddress() {
        return this.defaultAddress;
    }
}
