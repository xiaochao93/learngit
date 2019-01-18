package com.duia.english;

import com.duia.english.common.verify.SecurityVerifyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhao on 2018/4/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityVerifyRequestTest {
    @Autowired
    private SecurityVerifyRequest securityVerifyRequest;

    @Test
    public void testVerify() {
        Map map = new HashMap();
        map.put("id", "1");
        map.put("data", "{aa:dd}");
        map.put("signature", "c993670a684369654b71980a1c9df467");
        Assert.isTrue(securityVerifyRequest.verifyRequest(map),"参数没通过验证");
    }
}
