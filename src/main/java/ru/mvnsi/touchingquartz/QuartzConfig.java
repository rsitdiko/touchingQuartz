package ru.mvnsi.touchingquartz;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        Map<String, Object> map = new HashMap();
        map.put("jobName", "pilotJob");
        factoryBean.setJobClass(QuartzJob.class);
        factoryBean.setJobDataAsMap(map);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean jobTrigger() {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(Objects.requireNonNull(jobDetail().getObject()));
        trigger.setCronExpression("0/10 * * ? * * *"); //every 10 seconds
        trigger.setName("jobTrigger");
        return trigger;
    }

    @Bean
    @Qualifier("scheduler")
    public SchedulerFactoryBean scheduler(Properties quartzProperties) throws Exception {

        var schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setQuartzProperties(quartzProperties);
schedulerFactory.setDataSource(dataSource);
        Map<String, Object> schedulerContextMap = new HashMap<>();
        schedulerContextMap.put("jobLocator", jobLocator);
        schedulerContextMap.put("jobLauncher", jobLauncher);
        schedulerFactory.setSchedulerContextAsMap(schedulerContextMap);

//
        schedulerFactory.setJobDetails(jobDetail().getObject());
        schedulerFactory.setTriggers(jobTrigger().getObject());


        return schedulerFactory;
    }



    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
//    @Qualifier("quartzProperties")
    public Properties quartzProperties() throws IOException
    {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
