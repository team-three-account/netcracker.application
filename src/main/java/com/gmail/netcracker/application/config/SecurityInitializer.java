package com.gmail.netcracker.application.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


/**
 * Class extends AbstractSecurityWebApplicationInitializer.
 * * The class is needed in order to
 * * make sure that the settings
 * * security included in the main
 * * Application context (they were seen and
 * * Retrieved Root Application Context).
 * * This class is needed for this. We need
 * * adjust everything so that a certain
 * * The URL passed through the security level.
 */
public class SecurityInitializer
        extends AbstractSecurityWebApplicationInitializer {

    @Override
    public boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
