package com.everyschool.userservice.messagequeue;

import com.everyschool.userservice.messagequeue.dto.ParentSchoolApplyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka Producer
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 학부모 학급 신청 메세지 큐
     *
     * @param topic                메세지 토픽
     * @param parentSchoolApplyDto 학급 신청 정보
     * @return 학급 신청 정보
     */
    public ParentSchoolApplyDto parentSchoolApply(String topic, ParentSchoolApplyDto parentSchoolApplyDto) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(parentSchoolApplyDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the User microservice: " + parentSchoolApplyDto);

        return parentSchoolApplyDto;
    }
}
