package com.study.biz.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao.qingyue on 2019/8/26.
 * 搜索历史
 */
@Entity
public class SearchKey {

    @Id
    private Long historyId;

    @Property(nameInDb = "keyWod")
    private String keyWod;  // 搜索关键字

    @Generated(hash = 414700452)
    public SearchKey(Long historyId, String keyWod) {
        this.historyId = historyId;
        this.keyWod = keyWod;
    }

    @Generated(hash = 11165861)
    public SearchKey() {
    }

    public Long getHistoryId() {
        return this.historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getKeyWod() {
        return this.keyWod;
    }

    public void setKeyWod(String keyWod) {
        this.keyWod = keyWod;
    }
}
