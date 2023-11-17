package com.everyschool.batchservice.config;

import com.everyschool.batchservice.api.client.OpenAiServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OpenAiServiceClient openAiServiceClient;

//    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, OpenAiServiceClient openAiServiceClient) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//        this.openAiServiceClient = openAiServiceClient;
//    }

    @Bean
    public Step chatServiceStep() {
        return stepBuilderFactory.get("chatServiceStep")
                .tasklet((contribution, chunkContext) -> {
                    String result = openAiServiceClient.doCheckChatting();
                    log.debug("[채팅 스케줄러] 결과 = {}", result);
                    return null;
                })
                .build();
    }

//    @Bean
//    public Step callServiceStep() {
//        return stepBuilderFactory.get("callServiceStep")
//                .tasklet((contribution, chunkContext) -> {
//                    String callData = callServiceClient.getCallData();
//                    System.out.println("Call Service Response: " + callData);
//                    return null;
//                })
//                .build();
//    }

    @Bean
    public Job dailyJob() {
        return jobBuilderFactory.get("dailyJob")
                .start(chatServiceStep())
//                .next(callServiceStep())
                .build();
    }
}
