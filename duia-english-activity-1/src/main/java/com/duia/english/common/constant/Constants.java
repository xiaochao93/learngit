package com.duia.english.common.constant;

import org.springframework.context.annotation.Configuration;

/**
 * Created by xiaochao on 2018/8/3.
 */
@Configuration
public class Constants {
    public class Cache{
        public final static String ACCESS_TOKEN_PREFIX = "wx.access.token:";

        public final static String API_TICKET_PREFIX = "wx.api.ticket:";
    }

    public class URL{
        /**
         * 验证用户登录票据的地址
         **/
        public final static String VALIDATE_USER_URL = "%s/uc/validate";
    }

    public class MSG{
        /**
         * mq 领取优惠券 exchange
         */
        public static final  String COUPON_EXCHANGE = "template-coupon-exchange";

        /**
         * mq 领取优惠券 routing-key
         */
        public static final  String COUPON_ROUTE= "template-coupon-key";


    }
}
