package com.everyschool.schoolservice;

import com.everyschool.schoolservice.config.KafkaConfig;
import com.everyschool.schoolservice.messagequeue.KafkaConsumer;
import com.everyschool.schoolservice.messagequeue.KafkaProducer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

    @MockBean
    protected KafkaConfig kafkaConfig;

    @MockBean
    protected KafkaProducer kafkaProducer;

    @MockBean
    protected KafkaConsumer kafkaConsumer;
}

