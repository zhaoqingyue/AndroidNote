package com.study.androidnote.notice.model.manager;

import com.study.androidnote.notice.model.bean.MsgNotice;
import com.study.androidnote.notice.model.bean.SystemNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao.qingyue on 2019/8/29.
 * 通知管理类
 */
public class NoticeManager {

    // 获取消息通知
    public static List<MsgNotice> getMsgNoticeList() {
        List<MsgNotice> systemNotices = new ArrayList<>();

        MsgNotice msgNotice0 = new MsgNotice();
        msgNotice0.setTime(1567065123);
        msgNotice0.setTitle("暴雨黄色预警");
        msgNotice0.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(msgNotice0);

        MsgNotice msgNotice1 = new MsgNotice();
        msgNotice1.setTime(1566984330);
        msgNotice1.setTitle("暴雨橙色预警");
        msgNotice1.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(msgNotice1);

        MsgNotice msgNotice2 = new MsgNotice();
        msgNotice2.setTime(1566872130);
        msgNotice2.setTitle("暴雨红色预警");
        msgNotice2.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(msgNotice2);

        MsgNotice msgNotice3 = new MsgNotice();
        msgNotice3.setTime(1566794730);
        msgNotice3.setTitle("深圳市台风黄色预警");
        msgNotice3.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(msgNotice3);

        return systemNotices;
    }

    // 获取系统公告
    public static List<SystemNotice> getSystemNoticeList() {
        List<SystemNotice> systemNotices = new ArrayList<>();

        SystemNotice systemNotice0 = new SystemNotice();
        systemNotice0.setTime(1567065123);
        systemNotice0.setTitle("暴雨黄色预警");
        systemNotice0.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(systemNotice0);

        SystemNotice systemNotice1 = new SystemNotice();
        systemNotice1.setTime(1566984330);
        systemNotice1.setTitle("暴雨橙色预警");
        systemNotice1.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(systemNotice1);

        SystemNotice systemNotice2 = new SystemNotice();
        systemNotice2.setTime(1566872130);
        systemNotice2.setTitle("暴雨红色预警");
        systemNotice2.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(systemNotice2);

        SystemNotice systemNotice3 = new SystemNotice();
        systemNotice3.setTime(1566794730);
        systemNotice3.setTitle("深圳市台风黄色预警");
        systemNotice3.setContent("深圳局部地区有黄色暴雨预警，请大家注意！");
        systemNotices.add(systemNotice3);

        return systemNotices;
    }


}
