package com.nde.sch.spbatch;

import com.nde.sch.ScheduleJob;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class SPBatchScheduleJobProvider {
    private final List<Job> jobList;
    private final JobLauncher jobLauncher;

    @Bean
    List<ScheduleJob> jobs() {
        return jobList.stream()
                .map(job -> new SPBatchScheduleJob(job, jobLauncher))
                .collect(Collectors.toUnmodifiableList());
    }
}
