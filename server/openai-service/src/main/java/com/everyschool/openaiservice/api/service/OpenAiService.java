package com.everyschool.openaiservice.api.service;

import com.everyschool.openaiservice.api.client.ChatServiceClient;
import com.everyschool.openaiservice.api.client.GptServiceClient;
import com.everyschool.openaiservice.api.client.request.GptRequest;
import com.everyschool.openaiservice.api.client.response.GptResponse;
import com.everyschool.openaiservice.api.client.response.chat.CheckingChatResponse;
import com.everyschool.openaiservice.api.client.response.dto.Message;
import com.everyschool.openaiservice.api.service.dto.Chat;
import com.everyschool.openaiservice.messagequeue.KafkaProducer;
import com.everyschool.openaiservice.messagequeue.dto.ChatReviewSaveDto;
import com.everyschool.openaiservice.messagequeue.dto.ChatUpdateDto;
import com.everyschool.openaiservice.messagequeue.dto.KafkaTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenAiService {

    private final ChatServiceClient chatServiceClient;
    private final GptServiceClient gptServiceClient;
    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "0 0 2 * * ?")
    public void doChecking() {
        log.debug("[채팅 검토] 스케줄러 실행");
        LocalDate checkDate = LocalDate.now().minusDays(1);
        log.debug("[채팅 검토] 검사 날짜 = {}", checkDate.toString());
        // 채팅방 id 가져오기
        log.debug("[채팅 검토] 채팅방 Id 가져오기");
        List<Long> roomIds = chatServiceClient.searchChatRoomIdByDate(checkDate);
        log.debug("[채팅 검토] 채팅방 Id 수 = {}", roomIds.size());
        CheckingChatResponse chatListResponse;
        for (Long roomId : roomIds) {
            // 채팅 목록 가져오기
            log.debug("[채팅 검토] 채팅 목록 가져오기. 채팅방 Id = {}", roomId);
            chatListResponse = chatServiceClient.searchChatByDateAndChatRoomId(checkDate, roomId);
            log.debug("[채팅 검토] 가져온 채팅방 리스트. 선생님 = {}, 상대방 = {}, 채팅 목록 수 = {}",
                    chatListResponse.getTeacherName(), chatListResponse.getOtherUserName(), chatListResponse.getChats().size());
            // 발신자 정리해서 GPT 보내기
            doChatReview(chatListResponse);
        }
    }

    @Scheduled(fixedRate = 600000)
    public void test() {
        log.debug("[AI 실행] 서비스 실행 됨");
        LocalDate checkDate = LocalDate.now().minusDays(1);
        log.debug("[AI 실행] 채팅 서비스 요청하기");
        String roomIds = chatServiceClient.test();
        log.debug("[AI 실행] 채팅 서비스 결과테 = {}", roomIds);


        GptResponse gptResponse = gptServiceClient.requestGpt(
                "Bearer ",
                generateGptRequest("안녕하세요 해봐"));

        String content = gptResponse.getChoices().get(0).getMessage().getContent();
        log.debug("[AI 실행] gpt 실행되는지 응답 = {}", content);

        log.debug("[AI 실행] 카프카 테스트");
        kafkaProducer.test("kafka-test", KafkaTestDto.builder().content("이건 카프카 테스트임 지피티 답은 >> " + content).build());
    }

    private void doChatReview(CheckingChatResponse chatListResponse) {

        List<Chat> chats = chatListResponse.getChats();
        StringBuilder sb = new StringBuilder();
        Long teacherId = chatListResponse.getTeacherId();
        for (Chat chat : chats) {
            if (Objects.equals(chat.getUserId(), teacherId)) {
                sb.append("T(").append(chat.getId()).append("): ")
                        .append(chat.getContent()).append("\n");
            } else {
                sb.append("O(").append(chat.getId()).append("): ")
                        .append(chat.getContent()).append("\n");
            }
        }

        sb.append("\\n\\n\\n이 대화가 공격적인 대화가 오갔으면 bad, 아니면 good 으로 2개 중 하나로 단답으로 답해줘. " +
                "서술하지 말고 bad, good 만 보내. " +
                "만약 bad이면 그 이유가 되는 문장 숫자랑 그렇게 판단한 사유를 " +
                "'n: 사유'형태로 최대 5개까지 보내줘");

        String prompt = sb.toString();
        log.debug("[채팅 검토] 전송 프롬프트 = {}", prompt);
        GptResponse gptResponse = gptServiceClient.requestGpt(
                "Bearer ",
                generateGptRequest(prompt));

        String content = gptResponse.getChoices().get(0).getMessage().getContent();
        log.debug("[채팅 검토] 지피티 응답 = {}", content);
        String[] result = content.split("\n");
        if (result[0].equals("good")) {
            return;
        }
        // 문제 날짜 저장하기
        kafkaProducer.saveReviewDate("save-chat-review", generateSaveChatReviewDto(chatListResponse, chats));
        for (int i = 1; i < result.length; i++) {
            //  채팅에 사유 업데이트 하기
            String[] reason = result[i].split(":");
            kafkaProducer.send("update-chat-topic", ChatUpdateDto.builder()
                    .chatId(Long.valueOf(reason[0]))
                    .reason(reason[1])
                    .build());
        }
    }

    private ChatReviewSaveDto generateSaveChatReviewDto(CheckingChatResponse chatListResponse, List<Chat> chats) {
        return ChatReviewSaveDto.builder()
                .chatRoomId(chats.get(0).getChatRoomId())
                .chatDate(chats.get(0).getCreatedDate().toLocalDate())
                .title(generateTitle(chatListResponse.getTeacherName(), chatListResponse.getOtherUserName(), chatListResponse.getChildName()))
                .build();
    }

    private String generateTitle(String teacherName, String otherUserName, String childName) {
        if (childName.length() == 0) {
//            return teacherName + "선생님과 " +
            return
                    childName + "학생";
        }
//        return teacherName + "선생님과 " +
        return
                childName + "학생의 학부모 ";//+
//                otherUserName;
    }

    private GptRequest generateGptRequest(String prompt) {
        Message message = Message.builder()
                .role("system")
                .content(prompt)
                .build();
        List<Message> messages = List.of(message);

        return GptRequest.builder()
                .model("gpt-4")
                .messages(messages)
                .build();
    }
}

