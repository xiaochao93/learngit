package com.duia.english.model;

import com.duia.english.common.core.CoreEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_exam_score")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserExamScore extends CoreEntity {

    /**
     * 准考证账号
     */
    @Column(name = "exam_card_no")
    private String examCardNo;

    @Column(name = "oral_exam_card_no")
    private String oralExamCardNo;

    @Column(name = "sku_id")
    @JsonIgnore
    private Long skuId;

    /**
     * 录入信息时间
     */
    @JsonIgnore
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 学校
     */
    @Column(name = "real_school")
    private String realSchool;

    /**
     * 总分
     */
    @Column(name = "all_score")
    private BigDecimal allScore;

    /**
     * 口语等级
     */
    @Column(name = "oral_grade")
    private String oralGrade;

    /**
     * 听力
     */
    @Column(name = "listen_score")
    private BigDecimal listenScore;

    /**
     * 阅读
     */
    @Column(name = "read_score")
    private BigDecimal readScore;

    /**
     * 写作和翻译
     */
    @Column(name = "other_score")
    private BigDecimal otherScore;

    /**
     * 查询次数
     */
    @Column(name = "find_num")
    @JsonIgnore
    private Integer findNum;
    /**
     * 英语四级/六级
     */
    @Transient
    private String grade;

  public UserExamScore(String realName, String school, String realCard, BigDecimal allScore, BigDecimal listenScore, BigDecimal readScore, BigDecimal otherScore) {
        this.realName =realName;
        this.realSchool = school;
        this.examCardNo =realCard;
        this.allScore = allScore;
        this.listenScore = listenScore;
        this.readScore = readScore;
        this.otherScore = otherScore;
    }

    public UserExamScore() {
    }

    public String getExamCardNo() {
        return examCardNo;
    }

    public UserExamScore setExamCardNo(String examCardNo) {
        this.examCardNo = examCardNo;
        return this;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRealName() {
        return realName;
    }

    public UserExamScore setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getRealSchool() {
        return realSchool;
    }

    public UserExamScore setRealSchool(String realSchool) {
        this.realSchool = realSchool;
        return this;
    }

    public BigDecimal getAllScore() {
        return allScore;
    }

    public UserExamScore setAllScore(BigDecimal allScore) {
        this.allScore = allScore;
        return this;
    }

    public BigDecimal getListenScore() {
        return listenScore;
    }

    public UserExamScore setListenScore(BigDecimal listenScore) {
        this.listenScore = listenScore;
        return this;
    }

    public BigDecimal getReadScore() {
        return readScore;
    }

    public UserExamScore setReadScore(BigDecimal readScore) {
        this.readScore = readScore;
        return this;
    }

    public BigDecimal getOtherScore() {
        return otherScore;
    }

    public UserExamScore setOtherScore(BigDecimal otherScore) {
        this.otherScore = otherScore;
        return this;
    }

    public Integer getFindNum() {
        return findNum;
    }

    public UserExamScore setFindNum(Integer findNum) {
        this.findNum = findNum;
        return this;
    }

    public String getGrade() {
        return grade;
    }

    public UserExamScore setGrade(String grade) {
        this.grade = grade;
        return this;
    }

    public Long getSkuId() {
        return skuId;
    }

    public UserExamScore setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getOralExamCardNo() {
        return oralExamCardNo;
    }

    public UserExamScore setOralExamCardNo(String oralExamCardNo) {
        this.oralExamCardNo = oralExamCardNo;
        return this;
    }

    public String getOralGrade() {
        return oralGrade;
    }

    public UserExamScore setOralGrade(String oralGrade) {
        this.oralGrade = oralGrade;
        return this;
    }
}