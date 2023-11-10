package com.everyschool.callservice.domain.usercall;

import com.everyschool.callservice.IntegrationTestSupport;
import com.everyschool.callservice.api.controller.usercall.response.UserCallDetailsResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallReportResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.domain.usercall.repository.UserCallQueryRepository;
import com.everyschool.callservice.domain.usercall.repository.UserCallRepository;
import com.everyschool.callservice.domain.usercalldetails.UserCallDetails;
import com.everyschool.callservice.domain.usercalldetails.repository.UserCallDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class UserCallQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserCallQueryRepository userCallQueryRepository;

    @Autowired
    private UserCallDetailsRepository userCallDetailsRepository;

    @Autowired
    private UserCallRepository userCallRepository;


    @DisplayName("선생님 ID로 선생님 통화 목록 가져오기")
    @Test
    void findAllByTeacherId() {
        // given
        UserCall userCall1 = saveCal(1L, 2L, "T", "신성주", "홍경환", "Y", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);
        UserCall userCall2 = saveCal(1L, 2L, "O", "홍경환", "신성주", "C", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "파일명", "파일명", false);
        UserCall userCall3 = saveCal(1L, 3L, "T", "신성주", "이예리", "M", LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), "파일명", "파일명", false);
        UserCall userCall4 = saveCal(4L, 2L, "T", "이지혁", "홍경환", "Y", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), "파일명", "파일명", false);
        UserCall userCall5 = saveCal(1L, 5L, "O", "임우택", "신성주", "Y", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1), "파일명", "파일명", false);

        // when
        List<UserCallResponse> responses = userCallQueryRepository.findAllByTeacherId(1L);

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
        UserCall userCall1 = saveCal(1L, 2L, "T", "신성주", "홍경환", "Y", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);
        UserCall userCall2 = saveCal(1L, 2L, "O", "홍경환", "신성주", "M", LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(2), "파일명", "파일명", false);
        UserCall userCall3 = saveCal(1L, 3L, "T", "신성주", "이예리", "C", LocalDateTime.now().minusHours(1), LocalDateTime.now().minusMinutes(30), "파일명", "파일명", false);
        UserCall userCall4 = saveCal(4L, 2L, "T", "이지혁", "홍경환", "Y", LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10), "파일명", "파일명", false);
        UserCall userCall5 = saveCal(1L, 5L, "O", "임우택", "신성주", "Y", LocalDateTime.now().minusMinutes(5), LocalDateTime.now().minusMinutes(1), "파일명", "파일명", false);

        // when
        List<UserCallResponse> responses = userCallQueryRepository.findAllById(2L);

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

    @DisplayName("통화 전반적인 내용 조회")
    @Test
    void findByCallId() {
        // given
        UserCall userCall = saveCal(1L, 2L, "T", "신성주", "홍경환", "Y", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);

        userCall.updateCallInfo("negative", Float.valueOf("0.000001"), Float.valueOf("0.000001"), Float.valueOf("99.9999"), true);

        // when
        UserCallReportResponse response = userCallQueryRepository.findByCallId(userCall.getId());

        // then
        assertThat(response.getOverallSentiment()).isEqualTo("negative");
        assertThat(response.getIsBad()).isTrue();
    }

    @DisplayName("통화 상세 내용 조회")
    @Test
    void findAllByCallId() {
        // given
        UserCall userCall = saveCal(1L, 2L, "T", "신성주", "홍경환", "Y", LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4), "파일명", "파일명", false);

        userCall.updateCallInfo("negative", Float.valueOf("0.000001"), Float.valueOf("0.000001"), Float.valueOf("99.9999"), true);
        UserCallDetails detail1 = saveDetails(userCall, "야!!! 너 내가 누군지 알아? 내가 임마 어?", 0, 26, "negative", Float.valueOf("0.000001"), Float.valueOf("0.000001"), Float.valueOf("99.9999"));
        UserCallDetails detail2 = saveDetails(userCall, "느그 서장하고 어? 밥도 묵고 어? 싸우나도 가고 으어?", 0, 26, "positive", Float.valueOf("0.000001"), Float.valueOf("0.000001"), Float.valueOf("99.9999"));
        UserCallDetails detail3 = saveDetails(userCall, "마 다했어 임마? 으어? 이짜식이 말이야 이거 풀어!", 0, 26, "neutral", Float.valueOf("0.000001"), Float.valueOf("0.000001"), Float.valueOf("99.9999"));

        // when
        List<UserCallDetailsResponse> response = userCallQueryRepository.findAllByCallId(userCall.getId());

        // then
        assertThat(response).hasSize(3);
        assertThat(response).extracting("sentiment", "neutral")
                .containsExactlyInAnyOrder(
                        tuple("negative", Float.valueOf("0.000001")),
                        tuple("positive", Float.valueOf("0.000001")),
                        tuple("neutral", Float.valueOf("0.000001"))
                );
    }

    private UserCall saveCal(Long teacherId, Long otherUserId, String sender, String senderName, String receiverName,
                             String receiveCall, LocalDateTime startDateTime, LocalDateTime endDateTime, String uploadFileName,
                             String storeFileName, Boolean isBad) {
        UserCall userCall = UserCall.builder()
                .teacherId(teacherId)
                .otherUserId(otherUserId)
                .sender(sender)
                .senderName(senderName)
                .receiverName(receiverName)
                .receiveCall(receiveCall)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .uploadFileName(uploadFileName)
                .storeFileName(storeFileName)
                .isBad(isBad)
                .build();
        return userCallRepository.save(userCall);
    }

    private UserCallDetails saveDetails(UserCall userCall, String content, int start, int length, String sentiment, Float neutral,
                                        Float positive, Float negative) {
        UserCallDetails detail = UserCallDetails.builder()
                .userCall(userCall)
                .content(content)
                .start(start)
                .length(length)
                .sentiment(sentiment)
                .neutral(neutral)
                .positive(positive)
                .negative(negative)
                .build();

        return userCallDetailsRepository.save(detail);
    }
}