package com.gmail.netcracker.application.utilites;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Logger;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Logger.getLogger(SessionListener.class.getName()).info("Create session");
        event.getSession().setMaxInactiveInterval(60*60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        Logger.getLogger(SessionListener.class.getName()).info("==== Session is destroyed ====");
    }
}

