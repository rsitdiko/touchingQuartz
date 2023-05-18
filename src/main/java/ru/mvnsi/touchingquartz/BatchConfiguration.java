package ru.mvnsi.touchingquartz;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ConsoleWriter consoleWriter;

    @Bean//(name="pilotJob")
    @Qualifier("pilotJob")
    public Job pilotJob(Step step1) {
        return jobBuilderFactory
                .get("pilotJob")
                .flow(step1)
                .build()
                .build();
    }

    @Bean(name = "step1")
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .tasklet(consoleWriter)
                .build();
    }

}
