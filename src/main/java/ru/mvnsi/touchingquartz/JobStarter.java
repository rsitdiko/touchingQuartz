package ru.mvnsi.touchingquartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import ru.mvnsi.touchingquartz.quartzJobs.ConsoleWriter;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobStarter {

    public void runJob() throws SchedulerException {
        JobDetail jobDetail = newJob(ConsoleWriter.class)
                .withIdentity("ConsoleWriter")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("ConsoleWriterTrigger")
                .build();

        Scheduler scheduler = SchedulerManager.getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
