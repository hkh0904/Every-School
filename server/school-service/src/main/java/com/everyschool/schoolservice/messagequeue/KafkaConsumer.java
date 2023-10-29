package com.everyschool.schoolservice.messagequeue;

import com.everyschool.schoolservice.api.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.SchoolApplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class KafkaConsumer {

    private final SchoolApplyService schoolApplyService;

    @KafkaListener(topics = "apply-school-topic")
    public void createSchoolApply(String kafkaMessage) {
        log.info("Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String userKey = (String) map.get("userKey");
        Long schoolClassId = (Long) map.get("schoolClassId");

        CreateSchoolApplyResponse response = schoolApplyService.createSchoolApply(schoolClassId, userKey);
    }
}

