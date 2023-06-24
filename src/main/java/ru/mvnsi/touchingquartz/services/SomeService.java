package ru.mvnsi.touchingquartz.services;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mvnsi.touchingquartz.SchedulerManager;

import java.util.Set;

import static org.quartz.JobBuilder.newJob;

@Service
public class SomeService {
    private static Logger LOG = LoggerFactory.getLogger(SomeService.class);

    public boolean stopJob(String jobName) throws SchedulerException {
        LOG.info("\n--- Выполняется метод сервисного слоя по остановке джобы");

        Set<JobKey> jobKeys = SchedulerManager
                .getScheduler()
                .getJobKeys(GroupMatcher.anyJobGroup());

        JobKey jobKeyForSpecifiedJob = jobKeys.stream()
                .filter(jobKey -> jobKey.getName().endsWith(jobName))
                .findFirst()
                .orElseThrow();

        try {
            SchedulerManager.getScheduler().interrupt(jobKeyForSpecifiedJob);
            LOG.info("\n--- Метод interrupt() успешно вызван");
            return true;
        } catch (SchedulerException e) {
            LOG.error("\n--- Вызов метода interrupt() провален");
            e.printStackTrace();
            return false;
        }
    }
}
