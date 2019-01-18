package com.duia.english.service.impl;

import com.duia.english.common.core.AbstractService;
import com.duia.english.mapper.UserExamScoreMapper;
import com.duia.english.model.UserExamScore;
import com.duia.english.service.IUserExamScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaochao on 2018/7/26.
 */
@Service
public class UserExamScoreService extends AbstractService<UserExamScore> implements IUserExamScoreService {
    @Resource
    private UserExamScoreMapper userExamCardMapper;

    @Override
    public void updateByCardNo(UserExamScore examCard) {
        this.userExamCardMapper.updateByCardNo(examCard);
    }

    @Override
    public UserExamScore findByExamCard(String card) {
        return this.userExamCardMapper.findByExamCard(card);
    }
}
