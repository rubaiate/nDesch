package com.nde.sch.dependentschedule;

import com.nde.sch.ScheduleEntity;
import com.nde.sch.enums.ScheduleAction;
import com.nde.sch.enums.TriggerType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.AbstractStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static com.nde.sch.enums.ParameterKeys.*;

@TestComponent
public class DependentScheduleTestComponent {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobRepository jobRepository;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    Step createCountDownStep() {
        var step = new AbstractStep() {
            @Override
            protected void doExecute(StepExecution stepExecution) {
                countDownLatch.countDown();
                System.out.println(String.format("Executing %s. CountDown %d", this.getName(), countDownLatch.getCount()));
            }
        };
        step.setName("CountDownStep");
        step.setAllowStartIfComplete(true);
        step.setJobRepository(jobRepository);
        return step;
    }

    Step step1() {
        var step = new AbstractStep() {
            @Override
            protected void doExecute(StepExecution stepExecution) {
                System.out.println(String.format("Executing %s", this.getName()));
            }
        };
        step.setName("Step1");
        step.setAllowStartIfComplete(true);
        step.setJobRepository(jobRepository);
        return step;
    }

    @Bean
    List<Job> createJob() {
        return Arrays.asList(jobBuilderFactory.get("job1").start(step1()).build(),
                jobBuilderFactory.get("cd-job").start(createCountDownStep()).build());
    }

    @Bean
    List<ScheduleEntity> entities() {
        var job1Params = Map.of(
                SCHEDULE_EXPRESSION.toString(), "* * * * * *",
                TIME_ZONE.toString(), "Asia/Singapore"
        );

        var cdSchParams = Map.of(
                DEPENDENT_NAME.toString(), "sch1"
        );

        return Arrays.asList(new ScheduleEntity("sch1", "job1", TriggerType.TIMED, job1Params, false, ScheduleAction.RUN),
                new ScheduleEntity("cd-sch", "cd-job", TriggerType.DEPENDENT, cdSchParams, true, ScheduleAction.RUN));
    }

}
