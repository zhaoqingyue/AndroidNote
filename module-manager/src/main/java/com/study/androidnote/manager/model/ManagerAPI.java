package com.study.androidnote.manager.model;

import com.study.androidnote.manager.model.event.AccountEvent;
import com.study.androidnote.manager.model.event.BankEvent;
import com.study.androidnote.manager.model.event.CardEvent;
import com.study.androidnote.manager.model.event.PaperworkEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 管理相关API
 */
public class ManagerAPI {

    private static ManagerAPI mManagerAPI = null;

    public static ManagerAPI getInstance() {
        if (mManagerAPI == null) {
            synchronized (ManagerAPI.class) {
                if (mManagerAPI == null) {
                    mManagerAPI = new ManagerAPI();
                }
            }
        }
        return mManagerAPI;
    }

    /**
     * 添加银行卡信息
     * @param name
     * @param bankName
     * @param bankType
     * @param bankNumber
     * @param bankPwd
     * @param identity
     * @param mobile
     */
    public void addBank(String name, String bankName, String bankType, String bankNumber, String bankPwd, String identity, String mobile) {
        BankEvent event = new BankEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**
     * 添加卡账号信息
     * @param name
     * @param certificateType
     * @param certificateNumber
     * @param computerNumber
     * @param cardNumber
     * @param bankName
     * @param bankNumber
     * @param mobile
     */
    public void addCard(String name, String certificateType, String certificateNumber, String computerNumber, String cardNumber, String bankName, String bankNumber, String mobile) {
        CardEvent event = new CardEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**
     * 添加证件信息
     * @param name
     * @param certificateType
     * @param certificateNumber
     * @param identity
     * @param mobile
     */
    public void addPaperwork(String name, String certificateType, String certificateNumber, String identity, String mobile) {
        PaperworkEvent event = new PaperworkEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**
     * 添加账户
     * @param name
     * @param nickName
     * @param accountType
     * @param account
     * @param password
     * @param identity
     * @param mobile
     * @param email
     * @param url
     */
    public void addAccount(String name, String nickName,  String accountType, String account, String password, String identity, String mobile,  String email,  String url) {
        AccountEvent event = new AccountEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }
}
