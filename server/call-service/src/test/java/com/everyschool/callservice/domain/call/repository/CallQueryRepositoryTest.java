package com.everyschool.callservice.domain.call.repository;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.controller.call.response.CallResponse;
import com.everyschool.callservice.domain.call.Call;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class CallQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CallQueryRepository callQueryRepository;

    @Autowired
    private CallRepository callRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("선생님 ID로 선생님 통화 목록 가져오기")
    @Test
    void findAllByTeacherId() {
        // given
        Call call1 = saveCal(1L, 2L, "T", "신성주", "홍경환", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);
        Call call2 = saveCal(1L, 2L, "O", "홍경환", "신성주", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "파일명", "파일명", false);
        Call call3 = saveCal(1L, 3L, "T", "신성주", "이예리", LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), "파일명", "파일명", false);
        Call call4 = saveCal(4L, 2L, "T", "이지혁", "홍경환", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), "파일명", "파일명", false);
        Call call5 = saveCal(1L, 5L, "O", "임우택", "신성주", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1), "파일명", "파일명", false);

        // when
        List<CallResponse> responses = callQueryRepository.findAllByTeacherId(1L);

        // then
        assertThat(responses).hasSize(4);
        assertThat(responses)
                .extracting("senderName", "receiverName")
                .containsExactlyInAnyOrder(
                        tuple("신성주", "홍경환"),
                        tuple("홍경환", "신성주"),
                        tuple("신성주", "이예리"),
                        tuple("임우택", "신성주")
                );
    }

    @DisplayName("일반 회원 ID로 통화 목록 가져오기")
    @Test
    void findAllById() {
        // given
        Call call1 = saveCal(1L, 2L, "T", "신성주", "홍경환", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);
        Call call2 = saveCal(1L, 2L, "O", "홍경환", "신성주", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "파일명", "파일명", false);
        Call call3 = saveCal(1L, 3L, "T", "신성주", "이예리", LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), "파일명", "파일명", false);
        Call call4 = saveCal(4L, 2L, "T", "이지혁", "홍경환", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), "파일명", "파일명", false);
        Call call5 = saveCal(1L, 5L, "O", "임우택", "신성주", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1), "파일명", "파일명", false);

        // when
        List<CallResponse> responses = callQueryRepository.findAllById(2L);

        // then
        assertThat(responses).hasSize(3);
        assertThat(responses)
                .extracting("senderName", "receiverName")
                .containsExactlyInAnyOrder(
                        tuple("신성주", "홍경환"),
                        tuple("홍경환", "신성주"),
                        tuple("이지혁", "홍경환")
                );
    }

    private Call saveCal(Long teacherId, Long otherUserId, String sender, String senderName, String receiverName,
                         LocalDateTime startDateTime, LocalDateTime endDateTime, String uploadFileName, String storeFileName,
                         Boolean isBad) {
        Call call = Call.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(sender)
                .senderName(senderName)
                .receiverName(receiverName)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .uploadFileName(uploadFileName)
                .storeFileName(storeFileName)
                .isBad(isBad)
                .build();
        return callRepository.save(call);
    }
}