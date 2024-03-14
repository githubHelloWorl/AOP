package com.aop.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.aop.annotation.AuthCheck;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验 AOP
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();

        log.info("mustRole = {}",mustRole);

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 必须有该权限才通过
        if (StringUtils.isNotBlank(mustRole)) {
            String user = (String) request.getSession().getAttribute("user");
            if(user == null){
                throw new Exception("user == null");
            }
            if(user.equals(mustRole)){
                throw new Exception("user === mustRole");
            }

        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

