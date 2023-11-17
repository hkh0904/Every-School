package com.everyschool.schoolservice.messagequeue;

import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppService;
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

    private final SchoolApplyAppService schoolApplyService;

    /**
     * 학부모 학급 등록 신청
     *
     * @param kafkaMessage 카프카 큐잉 메세지
     */
    @KafkaListener(topics = "parent-school-apply")
    public void createParentSchoolApply(String kafkaMessage) {
        Map<Object, Object> map = getMap(kafkaMessage);

        Integer parentId = (Integer) map.get("parentId");
        Integer studentId = (Integer) map.get("studentId");
        Integer schoolClassId = (Integer) map.get("schoolClassId");

        schoolApplyService.createParentSchoolApply(Long.valueOf(parentId), Long.valueOf(studentId), Long.valueOf(schoolClassId));
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
