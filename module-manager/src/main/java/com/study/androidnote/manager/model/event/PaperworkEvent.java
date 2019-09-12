package com.study.androidnote.manager.model.event;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 证件Event
 */
public class PaperworkEvent {

    public int msgId;
    public int errCode;

    public PaperworkEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public PaperworkEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public PaperworkEvent(PaperworkEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
