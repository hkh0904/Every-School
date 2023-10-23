package com.everyschool.chatservice.docs.chat;

import com.everyschool.chatservice.api.controller.chat.ChatController;
import com.everyschool.chatservice.api.controller.chat.request.CreateChatRoomRequest;
import com.everyschool.chatservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerDocsTest extends RestDocsSupport {

    @DisplayName("채팅방 생성 API")
    @Test
    void createChatRoom() throws Exception {
        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
            .userKey(UUID.randomUUID().toString())
            .type("T")
            .build();

        mockMvc.perform(
                post("/chat-service/chat-room")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-chat-room",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userKey").type(JsonFieldType.STRING)
                        .optional()
                        .description("로그인 유저 키"),
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .optional()
                        .description("로그인 유저 타입")
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
                    fieldWithPath("data.roomId").type(JsonFieldType.NUMBER)
                        .description("채팅방 ID"),
                    fieldWithPath("data.roomTitle").type(JsonFieldType.STRING)
                        .description("채팅방 제목")
                )
            ));
    }

    @DisplayName("채팅방 목록 조회 API")
    @Test
    void searchChatRoomList() throws Exception {

        mockMvc.perform(
                get("/chat-service/chat-room/{userKey}", UUID.randomUUID().toString())
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-chat-room-list",
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
                    fieldWithPath("data[].roomId").type(JsonFieldType.NUMBER)
                        .description("채팅방 PK"),
                    fieldWithPath("data[].roomTitle").type(JsonFieldType.STRING)
                        .description("채팅방 제목"),
                    fieldWithPath("data[].lastMessage").type(JsonFieldType.STRING)
                        .description("채팅방 마지막 메세지"),
                    fieldWithPath("data[].updateTime").type(JsonFieldType.ARRAY)
                        .description("채팅방 마지막 대화 시간"),
                    fieldWithPath("data[].unreadMessageNum").type(JsonFieldType.NUMBER)
                        .description("읽지 않은 메세지 수")
                )
            ));
    }

    @DisplayName("채팅 전송 API")
    @Test
    void sendMessage() throws Exception {

        mockMvc.perform(
                post("/chat-service/chat-room/{chatRoomId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("send-chat",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터(채팅 PK)")
                )
            ));
    }

//    @DisplayName(" 수정 API")
//    @Test
//    void editBoard() throws Exception {
//
//        EditBoardRequest request = EditBoardRequest.builder()
//            .title("수업시간 외 학교 체육관 사용에 관한")
//            .content("사용 명부를 작성한 사람만 사용 가능")
//            .categoryId(1L)
//            .uploadFiles(new ArrayList<>())
//            .build();
//        mockMvc.perform(
//                patch("/chat-service/boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
//                    .content(objectMapper.writeValueAsString(request))
//                    .contentType(MediaType.MULTIPART_FORM_DATA)
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("edit-board",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestFields(
//                    fieldWithPath("title").type(JsonFieldType.STRING)
//                        .optional()
//                        .description("게시글 제목"),
//                    fieldWithPath("content").type(JsonFieldType.STRING)
//                        .optional()
//                        .description("게시글 내용"),
//                    fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
//                        .optional()
//                        .description("카테고리 코드"),
//                    fieldWithPath("uploadFiles").type(JsonFieldType.ARRAY)
//                        .description("이미지나 파일")
//                ),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.OBJECT)
//                        .description("응답 데이터"),
//                    fieldWithPath("data.boardId").type(JsonFieldType.NUMBER)
//                        .description(" PK"),
//                    fieldWithPath("data.title").type(JsonFieldType.STRING)
//                        .description(" 제목"),
//                    fieldWithPath("data.content").type(JsonFieldType.STRING)
//                        .description(" 내용"),
//                    fieldWithPath("data.userName").type(JsonFieldType.STRING)
//                        .description("작성자"),
//                    fieldWithPath("data.createDate").type(JsonFieldType.STRING)
//                        .description(" 작성일"),
//                    fieldWithPath("data.uploadFiles").type(JsonFieldType.ARRAY)
//                        .description("파일들")
//                )
//            ));
//    }
//
//    @DisplayName(" 삭제 API")
//    @Test
//    void deleteBoard() throws Exception {
//
//        mockMvc.perform(
//                delete("/chat-service/boards/{schoolId}/{userKey}/{boardId}", 1L, UUID.randomUUID().toString(), 2L)
//            )
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andDo(document("delete-board",
//                preprocessResponse(prettyPrint()),
//                responseFields(
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
//                    fieldWithPath("message").type(JsonFieldType.STRING)
//                        .description("메시지"),
//                    fieldWithPath("data").type(JsonFieldType.STRING)
//                        .description("삭제 결과")
//                )
//            ));
//    }

    @Override
    protected Object initController() {
        return new ChatController();
    }
}
