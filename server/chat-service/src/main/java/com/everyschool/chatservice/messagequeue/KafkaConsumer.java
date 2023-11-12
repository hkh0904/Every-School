package com.everyschool.chatservice.messagequeue;

import com.everyschool.chatservice.api.service.SequenceGeneratorService;
import com.everyschool.chatservice.api.service.chat.ChatMongoService;
import com.everyschool.chatservice.api.service.filterword.FilterWordService;
import com.everyschool.chatservice.domain.chat.Chat;
import com.everyschool.chatservice.domain.chat.ChatReview;
import com.everyschool.chatservice.domain.chat.ChatStatus;
import com.everyschool.chatservice.domain.chat.repository.ChatRepository;
import com.everyschool.chatservice.domain.chat.repository.ChatReviewRepository;
import com.everyschool.chatservice.domain.chatroom.ChatRoom;
import com.everyschool.chatservice.domain.chatroom.repository.ChatRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    // TODO: 2023/11/12 아래 2개 확인 후 지우기
    private final ChatRepository chatRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    /**
     * 부적절 채팅 상태 업데이트
     *
     * @param kafkaMessage
     */
    @KafkaListener(topics = "update-chat-topic")
    public void updateExp(String kafkaMessage) {
        log.debug("[카프카] 부적절 채팅 업데이트 카프가 요청 들어옴");

        Map<Object, Object> map = getKafkaRequestDto(kafkaMessage);

        Long chatId = (Long) map.get("chatId");
        String reason = (String) map.get("reason");

        log.debug("[카프카] 부적절 사유 = {}", reason);

        chatMongoService.chatUpdate(chatId);
        log.debug("[카프카] 부적절 채팅 상태 변경 함");

        filterWordService.saveReason(reason, chatId);
        log.debug("[카프카] 부적절 채팅 사유 저장함");
    }

    @KafkaListener(topics = "save-chat-review")
    public void saveReviewDate(String kafkaMessage) {
        log.debug("[카프카] 리뷰 등록 요청 들어옴");

        Map<Object, Object> map = getKafkaRequestDto(kafkaMessage);

        Long chatRoomId = (Long) map.get("chatRoomId");
        LocalDate chatDate = (LocalDate) map.get("chatDate");
        String title = (String) map.get("title");

        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoomId).get();

        ChatReview chatReview = ChatReview.builder()
                .chatDate(chatDate)
                .title(title)
                .chatRoom(findChatRoom)
                .build();

        ChatReview savedChatReview = chatReviewRepository.save(chatReview);
        log.debug("[카프카] 리뷰 제목 = {}", savedChatReview.getTitle());
    }

    @KafkaListener(topics = "kafka-test")
    public void test(String kafkaMessage) {
        log.debug("[카프카] 이건 카프카 테스트임");

        Map<Object, Object> map = getKafkaRequestDto(kafkaMessage);

        String content = (String) map.get("content");

        log.debug("[카프카] 이거 카프카 요청 옮. content는 지피티가 보낸 내용임 = {}", content);
        log.debug("[카프카] 그걸 채팅으로 저장해보기");

        Chat savedChat = chatRepository.save(Chat.builder()
                .id(sequenceGeneratorService.generateSequence(Chat.SEQUENCE_NAME))
                .userId(-1L)
                .content(content)
                .status(ChatStatus.PLANE.getCode())
                .chatRoomId(-1L)
                .build());
        log.debug("[카프카] 저장된 채팅 아이디 = {}", savedChat.getId());
    }

    private Map<Object, Object> getKafkaRequestDto(String kafkaMessage) {
        Map<Object, Object> map = new HashMap<>();
        log.info("Kafka Message: ->" + kafkaMessage);

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
