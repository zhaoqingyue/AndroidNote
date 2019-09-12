package com.study.androidnote.manager.model.event;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 银行卡Event
 */
public class BankEvent {

    public int msgId;
    public int errCode;

    public BankEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public BankEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public BankEvent(BankEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
