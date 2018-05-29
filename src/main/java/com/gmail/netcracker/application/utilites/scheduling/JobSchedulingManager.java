package com.gmail.netcracker.application.utilites.scheduling;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Component
public class JobSchedulingManager {
    @Autowired
    private Scheduler scheduler;

    /**
     * Stop and delete job with group and name comprises job name prefix and entity id.
     * Job name has next format: JOB_NAME_PREFIX + entityId. For example, if we have notification job
     * with JOB_NAME_PREFIX = "notificationJob_" for event with id 666, job name will be "notificationJob_666"
     *
     * @param entityId
     * @param JOB_NAME_PREFIX
     * @param JOB_GROUP_NAME
     */
    public void deleteJob(Long entityId, final String JOB_NAME_PREFIX, final String JOB_GROUP_NAME) {
        try {
            scheduler.deleteJob(JobKey.jobKey(JOB_NAME_PREFIX + entityId, JOB_GROUP_NAME));
        } catch (SchedulerException e) {
            Logger.getLogger(JobSchedulingManager.class.getName()).info(e.getMessage());
        }
    }

    /**
     * Start job execution.
     * Job name has next format: JOB_NAME_PREFIX + entityId. For example, if we have notification job
     * with JOB_NAME_PREFIX = "notificationJob_" for event with id 666, job name will be "notificationJob_666".
     * Analogically triggers.
     *
     * @param entityId used for job name creation
     * @param params map that contains keys (field names of executed job) and values (field values of executed job)
     * @param jobClass job that will be executed
     * @param startDate date when job is starting
     * @param endDate date when job will be stopped
     * @param cron job periodicity
     * @param JOB_NAME_PREFIX
     * @param JOB_GROUP_NAME
     * @param TRIGGER_NAME_PREFIX
     * @param TRIGGER_GROUP_NAME
     */
    public void scheduleJob(Long entityId, Map<String, Object> params, Class<? extends QuartzJobBean> jobClass,
                            Date startDate, Date endDate, String cron,
                            final String JOB_NAME_PREFIX, final String JOB_GROUP_NAME,
                            final String TRIGGER_NAME_PREFIX, final String TRIGGER_GROUP_NAME) {
        JobDetail jobDetail = createJob(entityId, params, jobClass, JOB_NAME_PREFIX, JOB_GROUP_NAME);
        CronTrigger cronTrigger = createCronTrigger(entityId, startDate, endDate, cron, jobDetail,
                TRIGGER_NAME_PREFIX, TRIGGER_GROUP_NAME);
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            Logger.getLogger(JobSchedulingManager.class.getName()).info(e.getMessage());
        }
    }

    private JobDetail createJob(Long entityId, Map<String, Object> params,
                                Class<? extends QuartzJobBean> jobClass,
                                final String JOB_NAME_PREFIX, final String JOB_GROUP_NAME) {
        JobDataMap jobDataMap = new JobDataMap(params != null ? params : new HashMap<>());
        return newJob()
                .ofType(jobClass)
                .setJobData(jobDataMap)
                .withIdentity(JOB_NAME_PREFIX + entityId, JOB_GROUP_NAME)
                .build();
    }

    private CronTrigger createCronTrigger(Long entityId, Date startDate, Date endDate, String cron, JobDetail jobDetail,
                                          final String TRIGGER_NAME_PREFIX, final String TRIGGER_GROUP_NAME) {
        CronExpression cronExpression;
        try {
            cronExpression = new CronExpression(cron);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        CronTrigger cronTrigger = newTrigger()
//                .withSchedule(cronSchedule(cron)) //for using
                .withSchedule(cronSchedule("0/10 * * ? * * *")) //for demonstration
                .withIdentity(TRIGGER_NAME_PREFIX + entityId, TRIGGER_GROUP_NAME)
                .forJob(jobDetail)
                .startAt(cronExpression.getNextValidTimeAfter(startDate))
                .endAt(endDate)
                .build();
        return cronTrigger;
    }
}
