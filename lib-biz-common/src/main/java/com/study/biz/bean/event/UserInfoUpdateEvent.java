package com.study.biz.bean.event;

/**
 * Created by zhao.qingyue on 2019/8/26.
 * 用户信息更新Event
 */
public class UserInfoUpdateEvent {

    public int msgId;
    public int errCode;
    public String msg;

    public UserInfoUpdateEvent() {
        this.msgId = -1;
        this.errCode = -1;
        this.msg = "";
    }

    public UserInfoUpdateEvent(int msgId, int errCode, String msg) {
        this.msgId = msgId;
        this.errCode = errCode;
        this.msg = msg;
    }

    public UserInfoUpdateEvent(UserInfoUpdateEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
        this.msg = event.msg;
    }
}
