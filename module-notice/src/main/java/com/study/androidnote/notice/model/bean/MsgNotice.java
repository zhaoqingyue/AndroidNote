package com.study.androidnote.notice.model.bean;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 消息通知
 */
public class MsgNotice {

    private long time;      // 时间
    private String title;   // 标题
    private String content; // 内容
    private int msgType;    // 消息类型

    public MsgNotice() {

    }

    public MsgNotice(long time, String title, String content, int msgType) {
        this.time = time;
        this.title = title;
        this.content = content;
        this.msgType = msgType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "MsgNotice{" + "time='" + time + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + ", msgType=" + msgType + '}';
    }
}
