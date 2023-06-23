package ru.mvnsi.touchingquartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerManager {

    private static Logger LOG = LoggerFactory.getLogger(SchedulerManager.class);

    private static Scheduler scheduler;

    public static Scheduler getScheduler() throws SchedulerException {
        if (scheduler == null) {
            scheduler = new StdSchedulerFactory().getScheduler();
        }
        return scheduler;
    }

    public void startScheduler() {
        {
            try {
                scheduler = SchedulerManager.getScheduler();
                scheduler.start();
                LOG.info("\n --- Scheduler started: " + scheduler.getSchedulerInstanceId());
            } catch (SchedulerException e) {
                LOG.error("\n --- Failed to create scheduler or start scheduler");
                e.printStackTrace();
            }
        }
    }


}
