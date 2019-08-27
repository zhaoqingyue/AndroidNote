package com.study.androidnote.account.model;

/**
 * Created by zhao.qingyue on 2019/8/22.
 * 注册Event
 */
public class RegisterEvent {

    public int msgId;
    public int errCode;

    public RegisterEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public RegisterEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public RegisterEvent(RegisterEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
