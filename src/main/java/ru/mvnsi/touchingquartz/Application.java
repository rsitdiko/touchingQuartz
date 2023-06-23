package ru.mvnsi.touchingquartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mvnsi.touchingquartz.quartzJobs.ConsoleWriter;

import java.util.concurrent.TimeUnit;

import static org.quartz.JobBuilder.newJob;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SpringApplication.run(Application.class, args);
        new SchedulerManager().startScheduler();
        new JobStarter().runJob();
        TimeUnit.SECONDS.sleep(10);
        Scheduler scheduler = SchedulerManager.getScheduler();

        JobDetail jobDetail = newJob(ConsoleWriter.class)
                .withIdentity("ConsoleWriterJobDetail")
                .build();

        scheduler.interrupt(jobDetail.getKey());
    }

}
