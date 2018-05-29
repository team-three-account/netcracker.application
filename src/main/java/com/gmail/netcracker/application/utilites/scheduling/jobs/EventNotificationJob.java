package com.gmail.netcracker.application.utilites.scheduling.jobs;

import com.gmail.netcracker.application.dto.model.Event;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Setter
public class EventNotificationJob extends QuartzJobBean {
    private EmailConstructor emailConstructor;
    private Event event;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        emailConstructor.notifyAboutEvent(event);
    }
}
