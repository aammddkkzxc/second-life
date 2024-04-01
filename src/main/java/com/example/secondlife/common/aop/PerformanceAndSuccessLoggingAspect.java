package com.example.secondlife.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAndSuccessLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAndSuccessLoggingAspect.class);

    @Pointcut("within(com.example.secondlife..*)")
    public void applicationPackagePointcut() {
    }

    @Around("applicationPackagePointcut()")
    public Object logPerformanceAndSuccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("{} 성공, 실행 시간 : {}ms", joinPoint.getSignature(), System.currentTimeMillis() - startTime);
        } catch (Throwable throwable) {
            logger.error("{} 실패, 실행 시간 : {}ms, 에러 메시지: {}", joinPoint.getSignature(),
                    System.currentTimeMillis() - startTime, throwable.getMessage());
            throw throwable;
        }
        return result;
    }
}
