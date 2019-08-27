package com.study.androidnote.account.model;

/**
 * Created by zhao.qingyue on 2019/8/22.
 * 重设密码Event
 */
public class ResetPwdEvent {

    public int msgId;
    public int errCode;

    public ResetPwdEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public ResetPwdEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public ResetPwdEvent(ResetPwdEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
