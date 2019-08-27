package com.study.androidnote.account.model;

/**
 * Created by zhao.qingyue on 2019/8/22.
 * 登录Event
 */
public class LoginEvent {

    public int msgId;
    public int errCode;

    public LoginEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public LoginEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public LoginEvent(LoginEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
