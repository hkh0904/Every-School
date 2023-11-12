package com.everyschool.chatservice.messagequeue;

import com.everyschool.chatservice.api.service.chat.ChatMongoService;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
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

    private final ChatMongoService chatMongoService;
    private final FilterWordService filterWordService;

    /**
     * 부적절 채팅 상태 업데이트
     *
     * @param kafkaMessage
     */
    @KafkaListener(topics = "update-chat-topic")
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

        Long chatId = (Long) map.get("chatId");
        String reason = (String) map.get("reason");

        chatMongoService.chatUpdate(chatId);

        filterWordService.saveReason(reason, chatId);

        Integer exp = (Integer) map.get("exp");

        // TODO: 2023-11-10 해야함
    }
}
