package com.gmail.netcracker.application.utilites.scheduling.jobs;

import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.logging.Logger;

@Setter
public class PersonalPlanNotificationJob extends QuartzJobBean{
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //TODO send pdf-file with plan
        Logger.getLogger(PersonalPlanNotificationJob.class.getName()).info("It works!!!");
    }
}
