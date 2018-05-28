package com.gmail.netcracker.application.utilites.scheduling.jobs;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.lowagie.text.DocumentException;
import lombok.Data;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

import static com.gmail.netcracker.application.utilites.Utilities.getTimestamp;

@Setter
public class PersonalPlanNotificationJob extends QuartzJobBean{
    private EmailConstructor emailConstructor;
    private User user;

    /**
     * Specify a job to execute caused by call of Sheduler.
     * Fetch current date, date of next execution and authenticated user from execution context and pass it to emailConstructor
     * @param jobExecutionContext context of job execution
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Timestamp fromDate = getTimestamp(jobExecutionContext.getFireTime());
        Timestamp tillDate = getTimestamp(jobExecutionContext.getNextFireTime()); // for using
        Timestamp tillDateDemo = Timestamp.valueOf("2018-06-30 17:00:00.000000");
        emailConstructor.notifyAboutPersonPlan(fromDate, tillDateDemo, user);
    }
}
