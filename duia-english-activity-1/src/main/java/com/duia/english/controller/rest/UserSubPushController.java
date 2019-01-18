package com.duia.english.controller.rest;

import com.duia.english.common.annotation.IgnoreValidation;
import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.constant.Result;
import com.duia.english.model.UserSubPush;
import com.duia.english.service.IUserSubPushService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by xiaochao on 2018/8/2.
 */
@Api(value = "预约与推送",description = "预约与推送接口")
@RestController
@RequestMapping("/sub")
public class UserSubPushController {
    @Autowired
    private IUserSubPushService userSubPushService;

    @ApiOperation(value="预约四六级成绩查询",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams(value ={ @ApiImplicitParam(name = "mobile",value = "手机号",required = true,paramType = "query"),
             @ApiImplicitParam(name = "userId",value = "用户ID",paramType = "query") })
//    @ApiModelProperty(name="subType" ,value = "类型",dataType="Integer",hidden = true)
    @IgnoreValidation
    @PostMapping("/save")
    public Result save(String mobile,Long userId){
        if (StringUtils.isBlank(mobile)) {
            return ResponseEntity.FAILURE("mobile can not be null");
        }
        try {
            UserSubPush push = new UserSubPush();
            push.setMobile(mobile);
            push.setUserId(userId);
            push.setCreateTime(new Date());
            this.userSubPushService.save(push);
        } catch (DuplicateKeyException e) {

        }
        return ResponseEntity.OK.setMessage("预约成功");
    }

}
