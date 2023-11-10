package com.everyschool.callservice.docs.donotdisturb;

import com.everyschool.callservice.api.controller.donotdisturb.DoNotDisturbController;
import com.everyschool.callservice.api.controller.donotdisturb.request.DoNotDisturbRequest;
import com.everyschool.callservice.api.controller.donotdisturb.response.DoNotDisturbResponse;
import com.everyschool.callservice.api.service.donotdisturb.DoNotDisturbService;
import com.everyschool.callservice.api.service.donotdisturb.dto.DoNotDisturbDto;
import com.everyschool.callservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

public class DoNotDisturbControllerDocsTest extends RestDocsSupport {

    private final DoNotDisturbService doNotDisturbService = mock(DoNotDisturbService.class);

    @Override
    protected Object initController() {
        return new DoNotDisturbController(doNotDisturbService);
    }

    @DisplayName("방해 금지 등록 API")
    @Test
    void createDoNotDisturb() throws Exception {
        DoNotDisturbRequest request = DoNotDisturbRequest.builder()
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusDays(1))
            .isActivate(true)
            .build();

        DoNotDisturbResponse response = DoNotDisturbResponse.builder()
            .doNotDisturbId(1L)
            .startTime(LocalDateTime.now().minusHours(1))
            .endTime(LocalDateTime.now().minusMinutes(30))
            .isActivate(false)
            .build();

        given(doNotDisturbService.createDoNotDisturb(any(DoNotDisturbDto.class), anyString()))
                .willReturn(response);


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
                    .description("활성화 상태")
            ),
            responseFields(
                fieldWithPath("code").type(JsonFieldType.NUMBER)
                    .description("코드"),
                fieldWithPath("status").type(JsonFieldType.STRING)
                    .description("상태"),
                fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("메시지"),
                fieldWithPath("data").type(JsonFieldType.OBJECT)
                    .description("생성된 방해 금지 모드"),
                fieldWithPath("data.doNotDisturbId").type(JsonFieldType.NUMBER)
                    .description("방해 금지 모드 Id"),
                fieldWithPath("data.startTime").type(JsonFieldType.ARRAY)
                    .description("방해 금지 모드 시작 시간"),
                fieldWithPath("data.endTime").type(JsonFieldType.ARRAY)
                    .description("방해 금지 모드 종료 시간"),
                fieldWithPath("data.isActivate").type(JsonFieldType.BOOLEAN)
                    .description("활성화 상태")
            )
        ));
    }

    @DisplayName("방해 금지 켜기/끄기")
    @Test
    void updateIsActivate() throws Exception {

        DoNotDisturbResponse response = DoNotDisturbResponse.builder()
                .doNotDisturbId(1L)
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().minusMinutes(30))
                .isActivate(false)
                .build();

        given(doNotDisturbService.updateIsActivate(anyLong()))
                .willReturn(response);


        mockMvc.perform(
                        patch("/call-service/v1/do-not-disturbs/on-off/{doNotDisturbId}", anyLong())
                                .header("Authorization", "Bearer Token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-isActivate",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("생성된 방해 금지 모드"),
                                fieldWithPath("data.doNotDisturbId").type(JsonFieldType.NUMBER)
                                        .description("방해 금지 모드 Id"),
                                fieldWithPath("data.startTime").type(JsonFieldType.ARRAY)
                                        .description("방해 금지 모드 시작 시간"),
                                fieldWithPath("data.endTime").type(JsonFieldType.ARRAY)
                                        .description("방해 금지 모드 종료 시간"),
                                fieldWithPath("data.isActivate").type(JsonFieldType.BOOLEAN)
                                        .description("바뀐 활성화 상태")
                        )
                ));
    }
}
