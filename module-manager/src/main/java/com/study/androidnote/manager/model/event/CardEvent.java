package com.study.androidnote.manager.model.event;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 卡Event
 */
public class CardEvent {

    public int msgId;
    public int errCode;

    public CardEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public CardEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public CardEvent(CardEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
