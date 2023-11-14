package com.everyschool.chatservice.messagequeue;

import com.everyschool.chatservice.api.service.chat.ChatMongoService;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
import com.everyschool.chatservice.domain.chat.ChatReview;
import com.everyschool.chatservice.domain.chat.repository.ChatReviewRepository;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
@Transactional
public class KafkaConsumer {

    private final ChatMongoService chatMongoService;
    private final FilterWordService filterWordService;
    private final ChatReviewRepository chatReviewRepository;
    private final ChatRoomRepository chatRoomRepository;


    /**
     * 부적절 채팅 상태 업데이트
     *
     * @param kafkaMessage
     */
    @KafkaListener(topics = "update-chat-topic")
    public void updateExp(String kafkaMessage) {
        log.debug("[카프카] 부적절 채팅 업데이트 카프가 요청 들어옴");

        Map<Object, Object> map = getKafkaRequestDto(kafkaMessage);

        Integer chatId = (Integer) map.get("chatId");
        String reason = (String) map.get("reason");

        log.debug("[카프카] 부적절 사유 = {}", reason);

        chatMongoService.chatUpdate(Long.valueOf(chatId));
        log.debug("[카프카] 부적절 채팅 상태 변경 함");

        filterWordService.saveReason(reason, Long.valueOf(chatId));
        log.debug("[카프카] 부적절 채팅 사유 저장함");
    }

    @KafkaListener(topics = "save-chat-review")
    public void saveReviewDate(String kafkaMessage) {
        log.debug("[카프카] 리뷰 등록 요청 들어옴");

        Map<Object, Object> map = getKafkaRequestDto(kafkaMessage);

        Integer chatRoomId = (Integer) map.get("chatRoomId");
        ArrayList<Integer> chatDateList = (ArrayList<Integer>) map.get("chatDate");
        String title = (String) map.get("title");

        LocalDate chatDate = LocalDate.of(chatDateList.get(0), chatDateList.get(1), chatDateList.get(2));
        ChatRoom findChatRoom = chatRoomRepository.findById(Long.valueOf(chatRoomId)).get();

        ChatReview chatReview = ChatReview.builder()
                .chatDate(chatDate)
                .title(title)
                .chatRoom(findChatRoom)
                .build();

        ChatReview savedChatReview = chatReviewRepository.save(chatReview);
        log.debug("[카프카] 리뷰 제목 = {}", savedChatReview.getTitle());
    }

    private Map<Object, Object> getKafkaRequestDto(String kafkaMessage) {
        Map<Object, Object> map = new HashMap<>();
        log.info("Kafka Message: ->" + kafkaMessage);

        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.registerModule(new JavaTimeModule()).readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
