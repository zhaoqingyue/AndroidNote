package com.study.androidnote.search.model.bean;

/**
 * Created by zhao.qingyue on 2019/8/28.
 * 搜索Event
 */
public class SearchEvent {

    public int msgId;
    public int errCode;
    public String msg;

    public SearchEvent() {
        this.msgId = -1;
        this.errCode = -1;
        this.msg = "";
    }

    public SearchEvent(int msgId, int errCode, String msg) {
        this.msgId = msgId;
        this.errCode = errCode;
        this.msg = msg;
    }

    public SearchEvent(SearchEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
        this.msg = event.msg;
    }
}
