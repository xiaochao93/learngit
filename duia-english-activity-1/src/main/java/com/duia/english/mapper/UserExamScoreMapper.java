package com.duia.english.mapper;


import com.duia.english.common.core.CoreMapper;
import com.duia.english.model.UserExamScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserExamScoreMapper extends CoreMapper<UserExamScore> {

    @Select("select * from user_exam_score u where u.exam_card_no=#{card} or u.oral_exam_card_no= #{card}")
    UserExamScore findByExamCard(@Param("card") String card);
    @Update("update user_exam_score set update_time=#{updateTime},find_num=#{findNum} " +
            "where exam_card_no = #{examCardNo}")
    void updateByCardNo(UserExamScore examCard);
}