package ru.mvnsi.touchingquartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.quartz.JobBuilder.newJob;

public class JobStarter {

    public void runJob() throws SchedulerException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {

        Constructor<?> constructor = Class.forName("ru.mvnsi.touchingquartz.jobs.consoleWriter.ConsoleWriter").getConstructor();
        Object object = constructor.newInstance();
        Method getJobDetailMethod = object.getClass().getMethod("getJobDetail");
        Method getTriggerMethod = object.getClass().getMethod("getTrigger");

        JobDetail jobDetail = (JobDetail) getJobDetailMethod.invoke(object);
        Trigger trigger = (Trigger) getTriggerMethod.invoke(object);

        Scheduler scheduler = SchedulerManager.getScheduler();

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
