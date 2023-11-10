package com.everyschool.callservice.docs.donotdisturb;

import com.everyschool.callservice.api.controller.donotdisturb.DoNotDisturbController;
import com.everyschool.callservice.api.controller.donotdisturb.request.DoNotDisturbRequest;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

    @DisplayName("방해 금지 등록 API")
    @Test
    void createDoNotDisturb() throws Exception {
        DoNotDisturbRequest request = DoNotDisturbRequest.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusDays(1))
            .isActivate(true)
            .build();

        mockMvc.perform(
            post("/call-service/v1/do-not-disturbs/")
                .header("Authorization", "Bearer Token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("create-do-not-disturb",
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
