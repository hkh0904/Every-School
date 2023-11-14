package com.everyschool.chatservice;

import com.everyschool.chatservice.messagequeue.KafkaConsumer;
import kafka.server.KafkaConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {
    @MockBean
    protected KafkaConfig kafkaConfig;

//    @MockBean
//    protected KafkaProducer kafkaProducer;

    @MockBean
    protected KafkaConsumer kafkaConsumer;
}

