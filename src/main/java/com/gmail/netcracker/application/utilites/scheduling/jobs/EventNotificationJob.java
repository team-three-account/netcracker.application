package com.gmail.netcracker.application.utilites.scheduling.jobs;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

@Setter
public class EventNotificationJob extends QuartzJobBean {
    private EmailConstructor emailConstructor;
    private Event event;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger.getLogger("logger").info("SATAN MY MASTER!!!");
        emailConstructor.notifyAboutEvent(event);
    }
}
