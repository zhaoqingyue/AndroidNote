package com.study.biz.bean.event;

/**
 * Created by zhao.qingyue on 2019/8/26.
 * 锁模式Event
 */
public class LockEvent {

    public int msgId;
    public int errCode;
    public String msg;

    public LockEvent() {
        this.msgId = -1;
        this.errCode = -1;
        this.msg = "";
    }

    public LockEvent(int msgId, int errCode, String msg) {
        this.msgId = msgId;
        this.errCode = errCode;
        this.msg = msg;
    }

    public LockEvent(LockEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
        this.msg = event.msg;
    }
}
