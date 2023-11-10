package com.everyschool.callservice.docs.usercall;

import com.everyschool.callservice.api.controller.usercall.UserCallQueryController;
import com.everyschool.callservice.api.controller.usercall.response.UserCallDetailsResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallReportResponse;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.usercall.UserCallQueryService;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCallQueryControllerDocsTest extends RestDocsSupport {
    private final UserCallQueryService userCallQueryService = mock(UserCallQueryService.class);

    @Override
    protected Object initController() {
        return new UserCallQueryController(userCallQueryService);
    }

    @DisplayName("내 통화 내역 조회 API")
    @Test
    void searchMyCalls() throws Exception {
        UserCallResponse r1 = UserCallResponse.builder()
                .userCallId(1L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .receiveCall("Y")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .isBad(false)
                .build();

        UserCallResponse r2 = UserCallResponse.builder()
                .userCallId(2L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .receiveCall("M")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .isBad(false)
                .build();

        List<UserCallResponse> rList = new ArrayList<>();
        rList.add(r1);
        rList.add(r2);

        given(userCallQueryService.searchMyCalls(anyString()))
                .willReturn(rList);

        mockMvc.perform(
                        get("/call-service/v1/calls/{userKey}", UUID.randomUUID().toString())
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-userCalls",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY)
                                        .description("응답 데이터"),
                                fieldWithPath("data[].userCallId").type(JsonFieldType.NUMBER)
                                        .description("통화 ID"),
                                fieldWithPath("data[].senderName").type(JsonFieldType.STRING)
                                        .description("발신자"),
                                fieldWithPath("data[].receiverName").type(JsonFieldType.STRING)
                                        .description("수신자"),
                                fieldWithPath("data[].receiveCall").type(JsonFieldType.STRING)
                                        .description("통화 타입(Y: 통화, M: 부재중, C: 취소)"),
                                fieldWithPath("data[].sender").type(JsonFieldType.STRING)
                                        .description("발신한 사람(T: 선생님, O: 다른 유저)"),
                                fieldWithPath("data[].startDateTime").type(JsonFieldType.ARRAY)
                                        .description("통화 시작 시간"),
                                fieldWithPath("data[].endDateTime").type(JsonFieldType.ARRAY)
                                        .description("통화 종료 시간"),
                                fieldWithPath("data[].isBad").type(JsonFieldType.BOOLEAN)
                                        .description("악성 민원 여부")
                        )
                ));
    }

    @DisplayName("내 통화 상세 조회 API")
    @Test
    void searchCallDetails() throws Exception {
        UserCallReportResponse response = UserCallReportResponse.builder()
                .overallSentiment("negative")
                .overallNeutral(Float.valueOf("0.000001"))
                .overallPositive(Float.valueOf("0.000001"))
                .overallNegative(Float.valueOf("99.9999"))
                .build();

        UserCallDetailsResponse detail1 = UserCallDetailsResponse.builder()
                .content("야!!! 너 내가 누군지 알아? 내가 임마 어?")
                .start(0)
                .length(26)
                .sentiment("negative")
                .neutral(Float.valueOf("0.000001"))
                .positive(Float.valueOf("0.000001"))
                .negative(Float.valueOf("99.9999"))
                .build();

        UserCallDetailsResponse detail2 = UserCallDetailsResponse.builder()
                .content("느그 서장하고 어? 밥도 묵고 어? 싸우나도 가고 으어?")
                .start(26)
                .length(31)
                .sentiment("neutral")
                .neutral(Float.valueOf("78.000001"))
                .positive(Float.valueOf("2.000001"))
                .negative(Float.valueOf("20.9999"))
                .build();

        UserCallDetailsResponse detail3 = UserCallDetailsResponse.builder()
                .content("마 다했어 임마? 으어? 이짜식이 말이야 이거 풀어!")
                .start(31)
                .length(30)
                .sentiment("negative")
                .neutral(Float.valueOf("0.000001"))
                .positive(Float.valueOf("0.000001"))
                .negative(Float.valueOf("99.9999"))
                .build();

        List<UserCallDetailsResponse> detailsList = List.of(detail1, detail2, detail3);
        response.setDetails(detailsList);

        given(userCallQueryService.searchMyCallDetails(anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/call-service/v1/calls/detail/{userCallId}", anyLong())
                                .header("Authorization", "Bearer Access Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("search-userCalls-details",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.overallSentiment").type(JsonFieldType.STRING)
                                        .description("전체 통화 분석 결과"),
                                fieldWithPath("data.overallNeutral").type(JsonFieldType.NUMBER)
                                        .description("전체 통화 중립 척도(퍼센트)"),
                                fieldWithPath("data.overallPositive").type(JsonFieldType.NUMBER)
                                        .description("전체 통화 긍정 척도(퍼센트)"),
                                fieldWithPath("data.overallNegative").type(JsonFieldType.NUMBER)
                                        .description("전체 통화 부정 척도(퍼센트)"),
                                fieldWithPath("data.details").type(JsonFieldType.ARRAY)
                                        .description("통화 세부 분석 리스트"),
                                fieldWithPath("data.details[].content").type(JsonFieldType.STRING)
                                        .description("한문장 내용"),
                                fieldWithPath("data.details[].start").type(JsonFieldType.NUMBER)
                                        .description("시작 시점"),
                                fieldWithPath("data.details[].length").type(JsonFieldType.NUMBER)
                                        .description("문장 길이"),
                                fieldWithPath("data.details[].sentiment").type(JsonFieldType.STRING)
                                        .description("해당 문장 감정 분석"),
                                fieldWithPath("data.details[].neutral").type(JsonFieldType.NUMBER)
                                        .description("해당 문장 중립 척도"),
                                fieldWithPath("data.details[].positive").type(JsonFieldType.NUMBER)
                                        .description("해당 문장 긍정 척도"),
                                fieldWithPath("data.details[].negative").type(JsonFieldType.NUMBER)
                                        .description("해당 문장 부정 척도")
                        )
                ));
    }


}
