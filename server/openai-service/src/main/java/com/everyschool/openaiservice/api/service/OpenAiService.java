package com.everyschool.openaiservice.api.service;

import com.everyschool.openaiservice.api.client.ChatServiceClient;
import com.everyschool.openaiservice.api.client.GptServiceClient;
import com.everyschool.openaiservice.api.client.request.GptRequest;
import com.everyschool.openaiservice.api.client.response.GptResponse;
import com.everyschool.openaiservice.api.client.response.dto.Message;
import com.everyschool.openaiservice.api.service.dto.Chat;
import com.everyschool.openaiservice.messagequeue.KafkaProducer;
import com.everyschool.openaiservice.messagequeue.dto.ChatUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OpenAiService {

    private final ChatServiceClient chatServiceClient;
    private final GptServiceClient gptServiceClient;
    private final KafkaProducer kafkaProducer;

    public void doChecking() {
        // TODO: 2023-11-08 채팅방 id 가져오기
        LocalDate checkDate = LocalDate.now().minusDays(1);
        List<Long> roomIds = chatServiceClient.searchChatRoomIdByDate(checkDate);
        // TODO: 2023-11-08 반복문 안에서 채팅 목록 가져오기
        for (Long roomId : roomIds) {
            chatListResponse = chatServiceClient.searchChatByDateAndChatRoomId(checkDate, roomId);
            // TODO: 2023-11-08 발신자 정리해서 GPT 보내기
            method(chatListResponse);
        }
        // TODO: 2023-11-08 문제 채팅 카프카로 저장하기
    }

    private void method(chatListResponse) {
        List<Chat> chats = chatListResponse.getChatList();
        StringBuilder sb = new StringBuilder();
        Long teacherId = chatListResponse.getTeacherId();
        for (Chat chat : chats) {
            if (chat.getUserId() == teacherId) {
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

        GptResponse gptResponse = gptServiceClient.requestGpt(
                "Bearer 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",
                generateGptRequest(sb.toString()));

        String content = gptResponse.getChoices().get(0).getMessage().getContent();
        String[] result = content.split("\n");
        if (result[0].equals("good")) {
            return;
        }
        // TODO: 2023-11-10 문제 날짜 저장하기
        for (int i = 1; i < result.length; i++) {
            //  채팅에 사유 업데이트 하기
            String[] reason = result[i].split(":");
            kafkaProducer.send("update-chat-topic", ChatUpdateDto.builder()
                    .chatId(Long.valueOf(reason[0]))
                    .reason(reason[1])
                    .build());
        }
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


// TODO: 2023-11-10 해야함