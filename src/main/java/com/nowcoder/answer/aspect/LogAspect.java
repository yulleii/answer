package com.nowcoder.answer.aspect;

import com.nowcoder.answer.controller.IndexController;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;


@Aspect
@Component
public class LogAspect {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);
    @Before("execution(* com.nowcoder.answer.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        logger.info("before+"+joinPoint.toLongString()+"|"+new Date());
    }
}
