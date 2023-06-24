package ru.mvnsi.touchingquartz.quartzJobs;

import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class ConsoleWriter implements Job, InterruptableJob {

    private static Logger LOG = LoggerFactory.getLogger(ConsoleWriter.class);
    private Boolean shouldInterrupt = false;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (int i = 0; i < 10_000; i++) {
            if (shouldInterrupt) {
                LOG.info("\n--- Джоба прервана ---");
                return;
            }
            LOG.info("\nИтерация номер = " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOG.error("\n --- Не получилось проспать 1 сек...");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        LOG.info("\n--- Прерываю джобу ---");
        shouldInterrupt = true;
    }

    public JobDetail getJobDetail() {
        return newJob(ConsoleWriter.class)
                .withIdentity("ConsoleWriter")
                .build();
    }

    public Trigger getTrigger() {
        return newTrigger()
                .withIdentity("ConsoleWriterTrigger")
                .build();
    }

}
