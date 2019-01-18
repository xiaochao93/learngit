package com.duia.english.dto;

import java.util.Date;

/**
 * Created by liuhao on 2018/4/16.
 */
public class DuiaDtoTest {
    private String title;

    private Date createTime;

    private Date updateTime;

    private Integer dCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getdCount() {
        return dCount;
    }

    public void setdCount(Integer dCount) {
        this.dCount = dCount;
    }

    @Override
    public String toString() {
        return "DuiaDtoTest{" +
                "title='" + title + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", dCount=" + dCount +
                '}';
    }
}
