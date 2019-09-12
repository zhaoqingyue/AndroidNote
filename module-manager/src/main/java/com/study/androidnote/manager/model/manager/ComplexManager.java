package com.study.androidnote.manager.model.manager;

import com.study.androidnote.manager.R;
import com.study.commonlib.util.utilcode.Utils;

/**
 * Created by zhao.qingyue on 2019/9/3.
 * 综合管理类
 */
public class ComplexManager {

    private static ComplexManager instance;

    public static ComplexManager getInstance() {
        if (instance == null) {
            synchronized(ComplexManager.class) {
                if (instance == null) {
                    instance = new ComplexManager();
                }
            }
        }
        return instance;
    }

    /**
     * 根据checkedPosition获取银行卡类型
     */
    public String getBankTypeByPos(int checkedPosition) {
        String bankType = "";
        switch (checkedPosition) {
            case 0:{
                bankType = Utils.getContext().getString(R.string.manager_bank_type_debit);
                break;
            }
            case 1:{
                bankType = Utils.getContext().getString(R.string.manager_bank_type_credit);
                break;
            }
        }
        return bankType;
    }

    /**
     * 根据pos获取银行卡类型
     */
    public int getBankTypeByName(String bankType) {
        int pos = 0;
        if (bankType.equals(Utils.getContext().getString(R.string.manager_bank_type_debit))) {
            pos = 0;
        } else if (bankType.equals(Utils.getContext().getString(R.string.manager_bank_type_credit))) {
            pos = 1;
        }
        return pos;
    }

}
