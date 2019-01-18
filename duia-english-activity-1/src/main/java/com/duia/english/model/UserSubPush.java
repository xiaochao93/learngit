package com.duia.english.model;

import com.duia.english.common.core.CoreEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xiaochao on 2018/8/2.
 */
@Table(name = "user_sub_push")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSubPush extends CoreEntity {

    @Column(name = "mobile")
    private String mobile;
    /**
     * 1 四六级成绩查询预约
     */
    @JsonIgnore
    @Column(name = "sub_type")
    private Integer subType;

    @Column(name = "user_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSubType() {
        return subType;
    }

    public UserSubPush setSubType(Integer subType) {
        this.subType = subType;
        return this;
    }

    /**
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
