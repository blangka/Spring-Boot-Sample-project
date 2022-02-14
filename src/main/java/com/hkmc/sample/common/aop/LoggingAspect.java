package com.hkmc.sample.common.aop;

import com.google.common.base.Joiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * AOP에서 Logging 하기위한 설정
 */

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.hkmc.sample.controller.*Controller.*(..))||execution(* com.hkmc.sample.api.*Controller.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

        long t = System.currentTimeMillis();
        String m = pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName();
        HttpServletRequest request = // 5
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String p = request.getParameterMap().entrySet().stream()
                .map(entry -> String.format("%s=%s",
                        entry.getKey(), Joiner.on("&").join(entry.getValue())))
                .collect(Collectors.joining("&"));
        logger.info("[{}] {}, [?{}]", request.getMethod(), m, p);
        Object result = pjp.proceed();
        logger.info("Response - {}({}ms)", m, System.currentTimeMillis() - t);
        return result;
    }
}
