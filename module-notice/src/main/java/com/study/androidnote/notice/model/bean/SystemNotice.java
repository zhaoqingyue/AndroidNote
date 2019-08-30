package com.study.androidnote.notice.model.bean;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 系统公告
 */
public class SystemNotice {

    private long time;      // 时间
    private String title;   // 标题
    private String content; // 内容

    public SystemNotice() {

    }

    public SystemNotice(long time, String title, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
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

    @Override
    public String toString() {
        return "SystemNotice{" + "time='" + time + '\'' + ", title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }
}
