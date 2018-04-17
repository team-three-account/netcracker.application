package com.gmail.netcracker.application.aspects;

import com.gmail.netcracker.application.service.interfaces.UserService;
import com.gmail.netcracker.application.utilites.VerificationToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

@Aspect
public class TokenLifeAspect {

    public TokenLifeAspect() {
    }

    @Autowired
    UserService userService;

    @AfterReturning(pointcut = "execution(* com.gmail.netcracker.application.dto.dao.interfaces.VerificationTokenDao.create(..))",
            returning = "result")
    public void logAfterReturningAllMethods(JoinPoint joinPoint, Object result) throws Throwable {
        Logger.getLogger(TokenLifeAspect.class.getName()).info("fsdfdsfsdfs");
        Logger.getLogger(TokenLifeAspect.class.getName()).info(result.toString());

        Thread.sleep(86400000 );
        userService.deleteVerificationToken((VerificationToken) result);
        Logger.getLogger(TokenLifeAspect.class.getName()).info("token dead");

    }


}