package com.study.androidnote.account.model;

import android.text.TextUtils;

import com.study.androidnote.account.util.MsgId;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhao.qingyue on 2019/8/22.
 * 账号相关接口
 */
public class AccountAPI {

    private static AccountAPI mAccountAPI = null;

    public static AccountAPI getInstance() {
        if (mAccountAPI == null) {
            synchronized (AccountAPI.class) {
                if (mAccountAPI == null) {
                    mAccountAPI = new AccountAPI();
                }
            }
        }
        return mAccountAPI;
    }

    /**************************** 登录接口 ******************************/

    public void getLoginVerifyCode(String mobile) {
        LoginEvent event = new LoginEvent();
        event.msgId = MsgId.MSGID_GET_LOGIN_VERICODE_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void mobileLogin(String mobile, String verifycode) {
        LoginEvent event = new LoginEvent();
        event.msgId = MsgId.MSGID_LOGIN_MOBILE_LOGIN_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void login(String loginName, String password) {
        LoginEvent event = new LoginEvent();
        event.msgId = MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**************************** 注册接口 ******************************/

    public void getRegisterVerifyCode(String mobile) {
        RegisterEvent event = new RegisterEvent();
        event.msgId = MsgId.MSGID_GET_REGISTER_VERICODE_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void register(String mobile, String verifycode, String pwd, String confirmPwd) {
        RegisterEvent event = new RegisterEvent();
        event.msgId = MsgId.MSGID_REGISTER_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**************************** 重置密码接口 ******************************/

    public void getResetPwdVerifyCode(String mobile) {
        ResetPwdEvent event = new ResetPwdEvent();
        event.msgId = MsgId.MSGID_GET_RESET_PWD_VERICODE_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void resetPwd(String mobile, String verifycode, String pwd, String confirmPwd) {
        ResetPwdEvent event = new ResetPwdEvent();
        event.msgId = MsgId.MSGID_RESET_PWD_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }


    /**
     * 验证手机格式
     */
    public static boolean isPhoneNum(String number) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            // matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 判断字符串是否符合手机号码格式，是否是合法的手机号码
     * 最新手机号段数据（更新于2019-04）
     *
     * 移动号段： 134, 135, 136, 137, 138, 139, 147, 148, 150, 151, 152, 157, 158, 159, 165, 172, 178, 182, 183, 184, 187, 188, 198
     * 联通号段： 130, 131, 132, 145, 146, 155, 156, 166, 171, 175, 176, 185, 186
     * 电信号段： 133, 149, 153, 173, 174, 177, 180, 181, 189, 191, 199
     * 虚拟运营商： 170
     *
     * 13号段：130, 131, 132, 133, 134, 135, 136, 137, 138, 139
     * 14号段：145, 146, 147, 148, 149
     * 15号段：150, 151, 152, 153, 155, 156, 157, 158, 159
     * 16号段：165, 166
     * 17号段：170, 171, 172, 173, 174, 175, 176, 177, 178,
     * 18号段：180, 181, 182, 183, 184, 185, 186, 187, 188, 189
     * 19号段：191, 198, 199
     */
    public static boolean isApprovedMobile(String mobileNum) {
        /**
         * 正则表达式格式
         * 1. "[1]"代表下一位为数字可以是几
         * 2. "[0-9]"代表可以为0-9中的一个
         * 3. "[5,7,9]"表示可以是5,7,9中的任意一位
         * 4. [^4]表示除4以外的任何一个
         * 5. \\d{8}"代表后面是可以是0～9的数字，有8位
         */
        String telRegex = "^((13[0-9])|(14[5,6,7,8,9])|(15[^4])|(16[5,6])|(17[^9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNum)) {
            return false;
        } else {
            return mobileNum.matches(telRegex);
        }
    }
}
