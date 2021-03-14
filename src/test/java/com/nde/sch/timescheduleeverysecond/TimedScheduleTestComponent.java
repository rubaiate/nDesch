package com.nde.sch.timescheduleeverysecond;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.enums.ScheduleAction;
import com.nde.sch.enums.TriggerType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.nde.sch.enums.ParameterKeys.SCHEDULE_EXPRESSION;
import static com.nde.sch.enums.ParameterKeys.TIME_ZONE;

@TestComponent
public class TimedScheduleTestComponent {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private JobRepository jobRepository;

    CountDownLatch countDownLatch = new CountDownLatch(3);

    @Bean
    Step createStep() {
        var step = new AbstractStep() {
            @Override
            protected void doExecute(StepExecution stepExecution) {
                countDownLatch.countDown();
                System.out.println(String.format("Executing %s. CountDown %d", this.getName(), countDownLatch.getCount()));
            }
        };
        step.setName("Step1");
        step.setAllowStartIfComplete(true);
        step.setJobRepository(jobRepository);
        return step;
    }

    @Bean
    Job createJob(Step step) {
        return jobs.get("Job1").start(step).build();
    }

    @Bean
    List<ScheduleEntity> entities() {
        var params = Map.of(
                SCHEDULE_EXPRESSION.toString(), "* * * * * *",
                TIME_ZONE.toString(), "Asia/Singapore"
        );
        return Collections.singletonList(new ScheduleEntity("sch1", "Job1", TriggerType.TIMED, params, true, ScheduleAction.RUN));
    }

}
