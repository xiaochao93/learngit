package com.duia.english.service;

import com.duia.english.common.core.Service;
import com.duia.english.model.UserExamScore;

/**
 * Created by xiaochao on 2018/7/26.
 */
public interface IUserExamScoreService extends Service<UserExamScore> {
    void updateByCardNo(UserExamScore examCard);

    UserExamScore findByExamCard(String card);
}
