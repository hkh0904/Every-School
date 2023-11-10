package com.everyschool.callservice.docs.call;

import com.everyschool.callservice.api.client.UserServiceClient;
import com.everyschool.callservice.api.controller.FCM.FCMNotificationController;
import com.everyschool.callservice.api.controller.FCM.request.CallDeniedRequest;
import com.everyschool.callservice.api.controller.FCM.request.OtherUserFcmRequest;
import com.everyschool.callservice.api.service.FCM.FCMNotificationService;
import com.everyschool.callservice.docs.RestDocsSupport;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

public class FCMNotificationControllerDocsTest extends RestDocsSupport {

    private final FCMNotificationService fcmNotificationService = mock(FCMNotificationService.class);
    private final FirebaseMessaging firebaseMessaging = mock(FirebaseMessaging.class);
    private final UserServiceClient userServiceClient = mock(UserServiceClient.class);

    @Override
    protected Object initController() {
        return new FCMNotificationController(fcmNotificationService);
    }

    @DisplayName("통화 걸기 요청 후 상대방 알림 생성 API")
    @Test
    void sendNotificationByToken() throws Exception {

        OtherUserFcmRequest request = OtherUserFcmRequest.builder()
                .otherUserKey(UUID.randomUUID().toString())
                .senderName("오연주스")
                .cname("전화좀 받아라")
                .build();

        Notification notification = Notification.builder()
                .setTitle(request.getSenderName())
                .setBody("calling...")
                .build();

        given(userServiceClient.searchUserFcmByUserKey(request.getOtherUserKey()))
                .willReturn("ehfEDZqQQrSjVNTd8O1Ur1:APA91bHX2xyspcdF01k5BNlfe9tF6bsw23m0zu38As_doANeFbaG3vAFMWsanc3XgL4NZEbYbZ883Hami54Y6NwOXHzjUKomRp2qqL3XzeGGjGzdirxEfeF4-QwPjivY4V4xeOyVmmuM");


        Message message = Message.builder()
                .setToken("ehfEDZqQQrSjVNTd8O1Ur1:APA91bHX2xyspcdF01k5BNlfe9tF6bsw23m0zu38As_doANeFbaG3vAFMWsanc3XgL4NZEbYbZ883Hami54Y6NwOXHzjUKomRp2qqL3XzeGGjGzdirxEfeF4-QwPjivY4V4xeOyVmmuM")
                .setNotification(notification)
                .putData("type", "call")
                .putData("cname", request.getCname())
                .build();

        given(firebaseMessaging.send(message))
                .willReturn("projects/everyschool-10f65/messages/0:1699369934516948%109e4fc3109e4fc3");

        given(fcmNotificationService.sendNotificationByToken(request.toDto()))
                .willReturn("projects/everyschool-10f65/messages/0:1699369934516948%109e4fc3109e4fc3");

        mockMvc.perform(
                        post("/call-service/v1/calls/calling")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("fcm-calling",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 요청할 상대방 유저키"),
                                fieldWithPath("senderName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 거는 사람 이름"),
                                fieldWithPath("cname").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("채널 네임")
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

    @DisplayName("부재중 API")
    @Test
    void createUserCallClosed() throws Exception{

        CallDeniedRequest request = CallDeniedRequest.builder()
                .otherUserKey(UUID.randomUUID().toString())
                .senderName("오연주 바보")
                .startDateTime(LocalDateTime.now().minusHours(2))
                .endDateTime(LocalDateTime.now().minusHours(1))
                .build();

        given(fcmNotificationService.createUserCallDenied(request.toDto(), "token"))
                .willReturn(Boolean.TRUE);

        mockMvc.perform(
                        post("/call-service/v1/calls/calling/cancel")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("calling-cancel",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 요청할 상대방 유저키"),
                                fieldWithPath("senderName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 거는 사람 이름"),
                                fieldWithPath("startDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("전화 건 시간"),
                                fieldWithPath("endDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("전화 종료 시간")
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

    @DisplayName("전화 취소 API")
    @Test
    void createUserCallDenied() throws Exception{

        CallDeniedRequest request = CallDeniedRequest.builder()
                .otherUserKey(UUID.randomUUID().toString())
                .senderName("오연주 바보")
                .startDateTime(LocalDateTime.now().minusHours(2))
                .endDateTime(LocalDateTime.now().minusHours(1))
                .build();

        given(fcmNotificationService.createUserCallCancel(request.toDto(), "token"))
                .willReturn(Boolean.TRUE);

        mockMvc.perform(
                        post("/call-service/v1/calls/calling/denied")
                                .header("Authorization", "Bearer Access Token")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("calling-denied",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("otherUserKey").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 요청할 상대방 유저키"),
                                fieldWithPath("senderName").type(JsonFieldType.STRING)
                                        .optional()
                                        .description("전화 거는 사람 이름"),
                                fieldWithPath("startDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("전화 건 시간"),
                                fieldWithPath("endDateTime").type(JsonFieldType.ARRAY)
                                        .optional()
                                        .description("전화 취소 시간")
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
}
