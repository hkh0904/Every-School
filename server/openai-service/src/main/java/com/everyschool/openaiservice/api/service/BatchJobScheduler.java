//package com.everyschool.openaiservice.api.service;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BatchJobScheduler {
//
//    @Autowired
//    private JobLauncher jobLauncher;
//    @Autowired
//    private Job job;
//
//    @Scheduled(cron = "0 0 2 * * ?") // 매일 오전 2시에 실행
//    public void runBatchJob() throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addLong("time", System.currentTimeMillis())
//                .toJobParameters();
//        jobLauncher.run(job, jobParameters);
//    }
//}
