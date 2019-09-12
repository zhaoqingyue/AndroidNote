package com.study.androidnote.manager.model.event;

/**
 * Created by zhao.qingyue on 2019/9/5.
 * 账户Event
 */
public class AccountEvent {

    public int msgId;
    public int errCode;

    public AccountEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public AccountEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public AccountEvent(AccountEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
