package com.study.androidnote.search.model;

import com.study.androidnote.search.model.bean.SearchEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhao.qingyue on 2019/8/28.
 * 搜索API
 */
public class SearchAPI {

    private static SearchAPI mSearchAPI = null;

    public static SearchAPI getInstance() {
        if (mSearchAPI == null) {
            synchronized (SearchAPI.class) {
                if (mSearchAPI == null) {
                    mSearchAPI = new SearchAPI();
                }
            }
        }
        return mSearchAPI;
    }

    public void search(String keyword) {
        SearchEvent event = new SearchEvent();
        event.msgId = 0;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

}
