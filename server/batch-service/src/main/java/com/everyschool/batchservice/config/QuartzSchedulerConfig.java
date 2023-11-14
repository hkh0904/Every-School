package com.everyschool.batchservice.config;

import com.everyschool.batchservice.api.service.QuartzJobLauncher;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import java.util.Date;

@Configuration
public class QuartzSchedulerConfig {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJobLauncher.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean trigger() {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail().getObject());
        factoryBean.setCronExpression("0 0 2 * * ?"); // 매일 2시에 실행
        return factoryBean;
    }
}