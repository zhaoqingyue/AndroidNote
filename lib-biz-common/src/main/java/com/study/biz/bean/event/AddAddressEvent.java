package com.study.biz.bean.event;

/**
 * Created by zhao.qingyue on 2019/9/29.
 * 添加地址Event
 */
public class AddAddressEvent {

    public int msgId;
    public int errCode;
    public String msg;

    public AddAddressEvent() {
        this.msgId = -1;
        this.errCode = -1;
        this.msg = "";
    }

    public AddAddressEvent(int msgId, int errCode, String msg) {
        this.msgId = msgId;
        this.errCode = errCode;
        this.msg = msg;
    }

    public AddAddressEvent(AddAddressEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
        this.msg = event.msg;
    }
}
