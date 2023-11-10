package com.everyschool.callservice.docs.usercall;

import com.everyschool.callservice.api.client.VoiceAiServiceClient;
import com.everyschool.callservice.api.client.response.RecordStartInfo;
import com.everyschool.callservice.api.client.response.RecordStopInfo;
import com.everyschool.callservice.api.controller.FileStore;
import com.everyschool.callservice.api.controller.usercall.UserCallController;
import com.everyschool.callservice.api.controller.usercall.request.CreateUserCallRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStartRequest;
import com.everyschool.callservice.api.controller.usercall.request.RecordStopRequest;
import com.everyschool.callservice.api.controller.usercall.response.UserCallResponse;
import com.everyschool.callservice.api.service.usercall.UserCallService;
import com.everyschool.callservice.api.service.usercall.dto.CreateUserCallDto;
import com.everyschool.callservice.docs.RestDocsSupport;
import com.everyschool.callservice.domain.usercall.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserCallControllerDocsTest extends RestDocsSupport {

    private final UserCallService userCallService = mock(UserCallService.class);
    private final VoiceAiServiceClient voiceAiServiceClient = mock(VoiceAiServiceClient.class);
    private final FileStore fileStore = mock(FileStore.class);
    private final UserCallController userCallController = mock(UserCallController.class);

    @Override
    protected Object initController() {
        return new UserCallController(userCallService, fileStore, voiceAiServiceClient);
    }

    @DisplayName("선생님 통화 종료시 통화 내역 저장 API")
    @Test
    void createCallInfo() throws Exception {

        CreateUserCallRequest request = CreateUserCallRequest.builder()
                .otherUserKey(UUID.randomUUID().toString())
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(12))
                .endDateTime(LocalDateTime.now().minusHours(11))
                .file(null)
                .build();

        UploadFile uploadFile = UploadFile.builder()
                .uploadFileName("통화 원본")
                .storeFileName(UUID.randomUUID().toString())
                .build();


        given(fileStore.storeFile(request.getFile()))
                .willReturn(uploadFile);


        CreateUserCallDto dto = request.toDto();
        dto.setStoreFileName(uploadFile.getStoreFileName());
        dto.setUploadFileName(uploadFile.getUploadFileName());
        dto.setIsBad(false);

        UserCallResponse r1 = UserCallResponse.builder()
                .userCallId(1L)
                .senderName("신성주")
                .receiverName("임우택 선생님")
                .sender("O")
                .startDateTime(LocalDateTime.now().minusHours(10))
                .endDateTime(LocalDateTime.now().minusHours(9))
                .isBad(false)
                .build();

        given(userCallService.createCallInfo(dto, request.getOtherUserKey(), UUID.randomUUID().toString()))
                .willReturn(r1);

        Mockito.doNothing().when(userCallController).createRecordAnalysis(null, 2L);

        mockMvc.perform(
                        post("/call-service/v1/calls/")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-userCall-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("선생님과 통화 하는 상대방"),
                                fieldWithPath("sender").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("발신자"),
                                fieldWithPath("startDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 시작 시간"),
                                fieldWithPath("endDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 종료 시간"),
                                fieldWithPath("file").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("파일 업로드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING)
                                        .description("응답 데이터")
                        )
                ));
    }

    @DisplayName("통화 녹음 시작 API")
    @Test
    void createRecordStart() throws Exception {

        RecordStartRequest request = RecordStartRequest.builder()
                .cname("test")
                .uid("123")
                .token("chatroomtoken")
                .userKey(UUID.randomUUID().toString())
                .otherUserKey(UUID.randomUUID().toString())
                .build();

        RecordStartInfo res = RecordStartInfo.builder()
                .cname("test")
                .uid("uid")
                .resourceId("resourceId")
                .sid("sid")
                .build();

        given(voiceAiServiceClient.recordStart(request))
                .willReturn(res);

        mockMvc.perform(
                        post("/call-service/v1/calls/record/start")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("userCall-record-start",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cname").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("생성 채널 이름"),
                                fieldWithPath("uid").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("채널 생성자 id"),
                                fieldWithPath("token").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("채널 토큰"),
                                fieldWithPath("userKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("통화 거는 사람 userKey"),
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("통화 받는 사람 userKey")
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
                                fieldWithPath("data.cname").type(JsonFieldType.STRING)
                                        .description("생성된 채팅방 이름"),
                                fieldWithPath("data.uid").type(JsonFieldType.STRING)
                                        .description("생성한 유저 id"),
                                fieldWithPath("data.resourceId").type(JsonFieldType.STRING)
                                        .description("생성된 채팅방의 resourceId"),
                                fieldWithPath("data.sid").type(JsonFieldType.STRING)
                                        .description("생성된 채팅방의 sid")
                        )
                ));
    }

    @DisplayName("통화 녹음 종료 API")
    @Test
    void createRecordStop() throws Exception {

        RecordStopRequest request = RecordStopRequest.builder()
                .cname("test")
                .uid("123")
                .resourceId("chatroomresourceId")
                .sid("chatroomsid")
                .otherUserKey("34d78c06-abcc-43a3-bfbd-551348bcb7ab")
                .sender("T")
                .startDateTime(LocalDateTime.now().minusHours(3))
                .endDateTime(LocalDateTime.now().minusHours(2))
                .build();

        RecordStopInfo res = RecordStopInfo.builder()
                .cname("test")
                .uid("uid")
                .resourceId("resourceId")
                .sid("sid")
                .fileDir("userKey/otherUserKey/date")
                .uploadStatus("completed")
                .build();

        given(voiceAiServiceClient.recordStop(request))
                .willReturn(res);

        mockMvc.perform(
                        post("/call-service/v1/calls/record/stop")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("userCall-record-stop",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cname").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("종료할 채널 이름"),
                                fieldWithPath("uid").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("종료할 생성자 id"),
                                fieldWithPath("resourceId").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("종료할 채널의 resourceId"),
                                fieldWithPath("sid").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("종료할 채널의 sid"),
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("상대방 유저 키"),
                                fieldWithPath("sender").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("발신자 타입(T: 선생님, O:다른 유저)"),
                                fieldWithPath("startDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 시작 시간"),
                                fieldWithPath("endDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("통화 종료 시간")
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
                                fieldWithPath("data.cname").type(JsonFieldType.STRING)
                                        .description("종료된 채팅방 이름"),
                                fieldWithPath("data.uid").type(JsonFieldType.STRING)
                                        .description("종료된 유저 id"),
                                fieldWithPath("data.resourceId").type(JsonFieldType.STRING)
                                        .description("종료된 채팅방의 resourceId"),
                                fieldWithPath("data.sid").type(JsonFieldType.STRING)
                                        .description("종료된 채팅방의 sid"),
                                fieldWithPath("data.fileDir").type(JsonFieldType.STRING)
                                        .description("녹음 저장된 경로"),
                                fieldWithPath("data.uploadStatus").type(JsonFieldType.STRING)
                                        .description("저장 처리 결과")
                        )
                ));
    }
}
