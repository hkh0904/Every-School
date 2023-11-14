package com.everyschool.batchservice.api.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

public class QuartzJobLauncher implements Job {

    private final JobLauncher jobLauncher;
    private final Job job;

    public QuartzJobLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run((org.springframework.batch.core.Job) job, jobParameters);
        } catch (Exception e) {
            throw new JobExecutionException("Failed to execute batch job", e);
        }
    }
}
