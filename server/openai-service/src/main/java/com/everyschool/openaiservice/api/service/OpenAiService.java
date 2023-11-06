package com.everyschool.openaiservice.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class OpenAiService {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    private final ChatFeignClient chatFeignClient;

    @Bean
    public Step chatStep() {
        return stepBuilderFactory.get("chatStep")
                .tasklet((contribution, chunkContext) -> {
                    // 매일 2시마다 Chat 서버에 요청을 보냄
                    // TODO: 2023-11-06 채팅 목록 불러오기
                    return null;
                })
                .build();
    }

    @Bean
    public Job chatJob() {
        return jobBuilderFactory.get("chatJob")
                .incrementer(new RunIdIncrementer())
                .start(chatStep())
                .build();
    }
 }


