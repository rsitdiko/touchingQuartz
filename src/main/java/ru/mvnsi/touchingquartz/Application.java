package ru.mvnsi.touchingquartz;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SchedulerException {
        SpringApplication.run(Application.class, args);
        new SchedulerManager().startScheduler();
        new JobStarter().runJob();
    }

}
