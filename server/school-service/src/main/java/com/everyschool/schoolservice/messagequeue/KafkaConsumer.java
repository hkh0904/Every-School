package com.everyschool.schoolservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
@Transactional
public class KafkaConsumer {

    private final SchoolApplyService schoolApplyService;

    /**
     * 학부모 학급 등록 신청
     *
     * @param kafkaMessage 카프카 큐잉 메세지
     */
    @KafkaListener(topics = "apply-school-topic")
    public void createParentSchoolApply(String kafkaMessage) {
        Map<Object, Object> map = getMap(kafkaMessage);

        Long parentId = (Long) map.get("parentId");
        Long studentId = (Long) map.get("studentId");
        Long schoolClassId = (Long) map.get("schoolClassId");

        schoolApplyService.createParentSchoolApply(parentId, studentId, schoolClassId);
    }

    /**
     * 카프라 큐잉 메세지 역직렬화
     *
     * @param kafkaMessage 카프카 큐잉 메세지
     * @return 역직렬화된 정보
     */
    private Map<Object, Object> getMap(String kafkaMessage) {
        log.info("Kafka Message: ->" + kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
}

