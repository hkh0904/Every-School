package com.everyschool.schoolservice.messagequeue;

import com.everyschool.schoolservice.messagequeue.dto.CreateStudentParentDto;
import com.everyschool.schoolservice.messagequeue.dto.EditStudentClassInfoDto;
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
     * 학생 학급 정보 수정 메세지 큐
     *
     * @param topic                   메세지 토픽
     * @param editStudentClassInfoDto 학급 정보
     */
    public void editStudentClassInfo(String topic, EditStudentClassInfoDto editStudentClassInfoDto) {
        //topic: edit-student-class-info
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(editStudentClassInfoDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the School microservice: " + editStudentClassInfoDto);
    }

    /**
     * 보호자 관계 연결 메세지 큐
     *
     * @param topic                  메세지 토픽
     * @param createStudentParentDto 보호자 관계
     */
    public void createStudentParent(String topic, CreateStudentParentDto createStudentParentDto) {
        //topic: create-student-parent
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(createStudentParentDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka Producer sent data from the School microservice: " + createStudentParentDto);
    }
}
