package com.gmail.netcracker.application.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;



public class SecurityInitializer
        extends AbstractSecurityWebApplicationInitializer {

    @Override
    public boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
