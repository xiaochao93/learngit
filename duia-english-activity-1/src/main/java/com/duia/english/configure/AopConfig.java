package com.duia.english.configure;

import com.duia.english.common.annotation.IgnoreValidation;
import com.duia.english.common.annotation.MustValidation;
import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.verify.SecurityVerifyRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhao on 2018/3/8.
 */
@Component
@Aspect
public class AopConfig {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${verify.security:false}")
    private boolean security;

    @Resource
    private SecurityVerifyRequest verifyRequest;

    /**
     * 环绕通知，1.所有@RestController的类中的@PostMapping,@GetMapping,@PutMapping,@DeleteMapping,@RequestMapping方法.
     * 2.所有@Controller的类中的@ResponseBody方法.
     * <p>
     * security参数为全局控制是否需要做参数验证的条件，默认为false。如果为true，则除去@IgnoreValidation注解的方法，其他均验证;
     * 如果为false,则除去@MustValidation注解的方法，其他均不验证
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("(within(@org.springframework.web.bind.annotation.RestController *) && "
            + "(@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.DeleteMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.RequestMapping))) || "
            + " within(@org.springframework.stereotype.Controller *) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object validationController(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Method targetMethod = ((MethodSignature) signature).getMethod();
        boolean pass;
        //判断是否需要进入验证流程
        if (security) {
            pass = !targetMethod.isAnnotationPresent(IgnoreValidation.class);
        } else {
            pass = targetMethod.isAnnotationPresent(MustValidation.class);
        }

        if (pass) {
            //进入验证流程
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            String parameterName, parameterValue;
            Enumeration<String> e = request.getParameterNames();
            Map<String, String> params = new HashMap<>();
            while (e.hasMoreElements()) {
                parameterName = e.nextElement();
                parameterValue = request.getParameter(parameterName);
                params.put(parameterName, parameterValue);
            }
            boolean verifyPass = verifyRequest.verifyRequest(params);
            if (verifyPass) {
                return pjp.proceed();
            } else {
                return ResponseEntity.VALIDATION_FAILURE;
            }
        } else {
            //不用验证流程
            return pjp.proceed();
        }
    }

}
