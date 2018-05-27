package com.gmail.netcracker.application.utilites.scheduling.jobs;

import com.gmail.netcracker.application.dto.model.User;
import com.gmail.netcracker.application.utilites.EmailConstructor;
import com.lowagie.text.DocumentException;
import lombok.Data;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.sql.Timestamp;

import static com.gmail.netcracker.application.utilites.Utilities.getTimestamp;

@Data
public class PersonalPlanNotificationJob extends QuartzJobBean{
    private EmailConstructor emailConstructor;
    private User user;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Timestamp fromDate = getTimestamp(jobExecutionContext.getFireTime());
        Timestamp tillDate = getTimestamp(jobExecutionContext.getNextFireTime()); // for using
        Timestamp month = Timestamp.valueOf("2018-06-30 17:00:00.000000"); // for demonstration
        emailConstructor.notifyAboutPersonPlan(fromDate, month, user);
    }
}
