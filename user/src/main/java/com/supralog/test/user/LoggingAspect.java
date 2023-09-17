package com.supralog.test.user;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.supralog.test.user..*.*(..))") //  package or class to intercept
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // Log input, output, and processing time
        logger.info("Method: " + joinPoint.getSignature().toShortString());
        logger.info("Input: " + Arrays.toString(joinPoint.getArgs()));
        logger.info("Output: " + result);
        logger.info("Execution Time: " + executionTime + "ms");

        return result;
    }
}
