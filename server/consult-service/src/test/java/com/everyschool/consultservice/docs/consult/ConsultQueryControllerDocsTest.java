package com.everyschool.consultservice.docs.consult;

import com.everyschool.consultservice.api.controller.consult.ConsultQueryController;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.docs.RestDocsSupport;
import com.everyschool.consultservice.utils.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
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

    @DisplayName("상담 내역 조회 API")
    @Test
    void searchConsults() throws Exception {
        given(tokenUtils.getUserKey())
            .willReturn(UUID.randomUUID().toString());

        ConsultResponse response1 = createConsultResponse(1L, 2001, 3001);
        ConsultResponse response2 = createConsultResponse(2L, 2002, 3002);
        ConsultResponse response3 = createConsultResponse(3L, 2001, 3003);

        given(consultQueryService.searchConsults(anyString()))
            .willReturn(List.of(response1, response2, response3));

        mockMvc.perform(
                get("/consult-service/v1/consults")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-consults",
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
                    fieldWithPath("data[].consultId").type(JsonFieldType.NUMBER)
                        .description("상담 id"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .description("상담 유형"),
                    fieldWithPath("data[].progressStatus").type(JsonFieldType.STRING)
                        .description("진행 상태"),
                    fieldWithPath("data[].title").type(JsonFieldType.STRING)
                        .description("상담 제목"),
                    fieldWithPath("data[].consultDate").type(JsonFieldType.ARRAY)
                        .description("상담 일시")
                )
            ));
    }

    private ConsultResponse createConsultResponse(long consultId, int typeId, int progressStatusId) {
        return ConsultResponse.builder()
            .consultId(consultId)
            .typeId(typeId)
            .progressStatusId(progressStatusId)
            .title("1학년 3반 이예리 선생님")
            .consultDate(LocalDateTime.now())
            .build();
    }
}
