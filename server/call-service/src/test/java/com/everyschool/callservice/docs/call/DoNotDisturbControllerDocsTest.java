package com.everyschool.callservice.docs.call;

import com.everyschool.callservice.api.controller.call.DoNotDisturbController;
import com.everyschool.callservice.api.controller.call.request.DoNotDisturbRequest;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoNotDisturbControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new DoNotDisturbController();
    }

    @DisplayName("방해 금지 리스트 API")
    @Test
    void searchDoNotDisturbs() throws Exception {
        mockMvc.perform(
            get("/donotdisturb")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-donotdisturbs",
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
                fieldWithPath("data[].startTime").type(JsonFieldType.ARRAY)
                    .description("시작 시간"),
                fieldWithPath("data[].endTime").type(JsonFieldType.ARRAY)
                    .description("끝 나는 시간"),
                fieldWithPath("data[].isActivate").type(JsonFieldType.BOOLEAN)
                    .description("활성화 상태")
            )
        ));
    }

    @DisplayName("방해 금지 등록 API")
    @Test
    void createClassroom() throws Exception {
        DoNotDisturbRequest request = DoNotDisturbRequest.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusDays(1))
            .isActivate(true)
            .build();

        mockMvc.perform(
            post("/donotdisturb")
                .header("Authorization", "Bearer Token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("create-donotdisturb",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("startTime").type(JsonFieldType.ARRAY)
                    .optional()
                    .description("시작 시간"),
                fieldWithPath("endTime").type(JsonFieldType.ARRAY)
                    .optional()
                    .description("끝나는 시간"),
                fieldWithPath("isActivate").type(JsonFieldType.BOOLEAN)
                    .optional()
                    .description("활성화")
            ),
            responseFields(
                fieldWithPath("code").type(JsonFieldType.NUMBER)
                    .description("코드"),
                fieldWithPath("status").type(JsonFieldType.STRING)
                    .description("상태"),
                fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("메시지"),
                fieldWithPath("data").type(JsonFieldType.STRING)
                    .description("생성 완료")
            )
        ));
    }
}
