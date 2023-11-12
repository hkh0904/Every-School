package com.everyschool.openaiservice.messagequeue;

import com.everyschool.openaiservice.messagequeue.dto.ChatReviewSaveDto;
import com.everyschool.openaiservice.messagequeue.dto.ChatUpdateDto;
import com.everyschool.openaiservice.messagequeue.dto.KafkaTestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ChatUpdateDto send(String topic, ChatUpdateDto dto) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("[카프카] 채팅 업데이트. id = {}, 사유 = {} ", dto.getChatId(), dto.getReason());

        return dto;
    }

    public ChatReviewSaveDto saveReviewDate(String topic, ChatReviewSaveDto dto) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("[카프카] 문제 채팅 날짜 기록. 채팅방Id = {}, 제목 = {}", dto.getChatRoomId(), dto.getTitle());

        return dto;
    }

    public KafkaTestDto test(String topic, KafkaTestDto dto) {
        ObjectMapper mapper = new ObjectMapper();

        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("[카프카] 카프카 테스트중 ");

        return dto;
    }
}