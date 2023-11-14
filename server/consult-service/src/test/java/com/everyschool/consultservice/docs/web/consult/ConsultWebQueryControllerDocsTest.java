package com.everyschool.consultservice.docs.web.consult;

import com.everyschool.consultservice.api.web.controller.consult.ConsultWebQueryController;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.web.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.web.service.consult.ConsultWebQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.domain.consult.ConsultType;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultWebQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultWebQueryService consultWebQueryService = mock(ConsultWebQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);
    private static final String BASE_URL = "/consult-service/v1/web/{schoolYear}/schools/{schoolId}/consults";

    @Override
    protected Object initController() {
        return new ConsultWebQueryController(consultWebQueryService, tokenUtils);
    }

    @DisplayName("상담 내역 조회 API")
    @Test
    void searchConsults() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        ConsultResponse response1 = ConsultResponse.builder()
            .consultId(1L)
            .type(ConsultType.CALL.getCode())
            .status(ProgressStatus.WAIT.getCode())
            .parentInfo("2학년 2반 14번 이예리 어머님")
            .consultDateTime(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .rejectedReason("")
            .build();

        ConsultResponse response2 = ConsultResponse.builder()
            .consultId(2L)
            .type(ConsultType.VISIT.getCode())
            .status(ProgressStatus.WAIT.getCode())
            .parentInfo("2학년 2반 8번 김민기 어머님")
            .consultDateTime(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now())
            .rejectedReason("")
            .build();

        given(consultWebQueryService.searchConsults(anyString(), anyInt(), anyLong(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
                get(BASE_URL, 2023, 100000)
                    .header("Authorization", "Bearer Access Token")
                    .param("status", String.valueOf(ProgressStatus.WAIT.getCode()))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-consults",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("status")
                        .description("상담 진행 상태 코드")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("조회된 데이터 수"),
                    fieldWithPath("data.content[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 아이디"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.content[].status").type(JsonFieldType.STRING)
                        .description("상담 진행 상태"),
                    fieldWithPath("data.content[].parentInfo").type(JsonFieldType.STRING)
                        .description("학부모 정보"),
                    fieldWithPath("data.content[].consultDateTime").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.content[].lastModifiedDate").type(JsonFieldType.ARRAY)
                        .description("최종 수정 일시"),
                    fieldWithPath("data.content[].rejectedReason").type(JsonFieldType.STRING)
                        .optional()
                        .description("거절 사유")
                )
            ));
    }

    @DisplayName("상담 상세 조회 API")
    @Test
    void searchConsult() throws Exception {
        ConsultDetailResponse response = ConsultDetailResponse.builder()
            .consultId(1L)
            .type(ConsultType.VISIT.getCode())
            .status(ProgressStatus.REJECT.getCode())
            .parentInfo("2학년 2반 14번 이예리 어머님")
            .consultDate(LocalDateTime.of(2023, 11, 10, 14, 0))
            .message("우리 아이가 고양이를 좋아해요.")
            .resultContent("리온이와 둘만의 시간을 주는걸 권장하였습니다.")
            .rejectedReason("")
            .lastModifiesDate(LocalDateTime.now())
            .build();

        given(consultWebQueryService.searchConsult(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/{consultId}", 2023, 100000, 1)
                    .header("Authorization", "Bearer Access Token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("web-search-consult",
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
                    fieldWithPath("data.consultId").type(JsonFieldType.NUMBER)
                        .description("상담 아이디"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("상담 진행 상태"),
                    fieldWithPath("data.parentInfo").type(JsonFieldType.STRING)
                        .description("학부모 정보"),
                    fieldWithPath("data.consultDate").type(JsonFieldType.ARRAY)
                        .description("상담 일시"),
                    fieldWithPath("data.message").type(JsonFieldType.STRING)
                        .description("학부모 상담 메세지"),
                    fieldWithPath("data.resultContent").type(JsonFieldType.STRING)
                        .description("상담 결과"),
                    fieldWithPath("data.rejectedReason").type(JsonFieldType.STRING)
                        .description("거절 사유"),
                    fieldWithPath("data.lastModifiesDate").type(JsonFieldType.ARRAY)
                        .description("최종 수정 일시")
                )
            ));
    }
}
