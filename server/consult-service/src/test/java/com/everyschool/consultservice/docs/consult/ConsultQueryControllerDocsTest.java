package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultQueryController;
import com.everyschool.consultservice.api.controller.consult.response.WaitConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.everyschool.consultservice.domain.consult.ConsultType.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultQueryControllerDocsTest extends RestDocsSupport {

    private final ConsultQueryService consultQueryService = mock(ConsultQueryService.class);
    private final TokenUtils tokenUtils = mock(TokenUtils.class);

    @Override
    protected Object initController() {
        return new ConsultQueryController(consultQueryService, tokenUtils);
    }

    @DisplayName("[교직원] 상담 확인 조회 API")
    @Test
    void searchWaitConsults() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());
        WaitConsultResponse response1 = createWaitConsultResponse(1L, "10301 하예솔 학생", "하도영 아버님", LocalDateTime.of(2023, 11, 4, 14, 0));
        WaitConsultResponse response2 = createWaitConsultResponse(2L, "10301 하예솔 학생", "박연진 어머님", LocalDateTime.of(2023, 11, 4, 15, 0));

        given(consultQueryService.searchConsults(anyString(), anyInt()))
            .willReturn(List.of(response1, response2));

        mockMvc.perform(
            get("/consult-service/v1/schools/{schoolId}/consults/{schoolYear}/wait", 1L, 2023)
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-wait-consults",
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
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("상담 확인 대기 중인 신청 수"),
                    fieldWithPath("data.content[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 id"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data.content[].studentInfo").type(JsonFieldType.STRING)
                        .description("상담 학생 정보"),
                    fieldWithPath("data.content[].parentInfo").type(JsonFieldType.STRING)
                        .description("상담 학부모 정보"),
                    fieldWithPath("data.content[].consultDate").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    private WaitConsultResponse createWaitConsultResponse(Long consultId, String studentInfo, String parentInfo, LocalDateTime consultDate) {
        return WaitConsultResponse.builder()
            .consultId(consultId)
            .typeId(VISIT.getCode())
            .studentInfo(studentInfo)
            .parentInfo(parentInfo)
            .consultDate(consultDate)
            .build();
    }
}
