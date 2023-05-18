package ru.mvnsi.touchingquartz;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConsoleWriter implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Integer[] integers = {1, 2};

        System.out.println("Тасклет Initial_ConsoleLogger...");
        System.out.println("Данные = " + Arrays.toString(integers));

        return RepeatStatus.FINISHED;
    }
}
