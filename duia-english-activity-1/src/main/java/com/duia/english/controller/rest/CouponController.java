package com.duia.english.controller.rest;

import com.duia.english.common.annotation.IgnoreValidation;
import com.duia.english.common.constant.Constants;
import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.constant.Result;
import com.duia.english.controller.BaseController;
import com.duia.english.dto.CouponMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaochao on 2018/11/12.
 */
@Api(value = "活动",description = "优惠券活动")
@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ApiOperation(value = "下发活动优惠券", notes = "发送消息队列", produces = MediaType.APPLICATION_JSON_VALUE)
    @IgnoreValidation
    @PostMapping(value = "/issue")
    public Result issue(Integer appType, HttpServletRequest request) {
        Long userId = getLoginUserId(request);
        CouponMsg couponMsg = new CouponMsg(appType, userId);
        if (userId > 0) {
            rabbitTemplate.convertAndSend(Constants.MSG.COUPON_EXCHANGE, Constants.MSG.COUPON_ROUTE,couponMsg);
            return ResponseEntity.OK("领取成功");
        }else{
            return ResponseEntity.UNKNOWUSER();
        }
    }
}
