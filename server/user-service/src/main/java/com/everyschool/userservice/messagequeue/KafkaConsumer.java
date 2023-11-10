package com.everyschool.userservice.messagequeue;

import com.everyschool.userservice.api.service.user.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaConsumer {

    private final StudentService studentService;

    /**
     * 학생 회원 학급 수정
     *
     * @param kafkaMessage 카프카 큐잉 메세지
     */
    @KafkaListener(topics = "edit-student-class-info")
    public void updateExp(String kafkaMessage) {
        log.info("Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Long studentId = (Long) map.get("studentId");
        Long schoolId = (Long) map.get("schoolId");
        Long schoolClassId = (Long) map.get("schoolClassId");

        studentService.editClassInfo(studentId, schoolId, schoolClassId);
    }
}
