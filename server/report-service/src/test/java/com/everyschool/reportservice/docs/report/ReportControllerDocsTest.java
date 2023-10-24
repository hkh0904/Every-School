package com.everyschool.reportservice.docs.report;

import com.everyschool.reportservice.api.controller.school.ReportController;
import com.everyschool.reportservice.api.controller.school.request.FileRequest;
import com.everyschool.reportservice.api.controller.school.request.ReportRequest;
import com.everyschool.reportservice.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new ReportController();
    }

    @DisplayName("신고 등록하기 API")
    @Test
    void createReport() throws Exception {
        FileRequest f1 = FileRequest.builder()
                .uploadFileName("코피 사진")
                .build();

        FileRequest f2 = FileRequest.builder()
                .uploadFileName("멍 사진")
                .build();

        List<FileRequest> fList = new ArrayList<>();
        fList.add(f1);
        fList.add(f2);

        ReportRequest r1 = ReportRequest.builder()
                .codeId(13)
                .schoolId(12)
                .year(2023)
                .reportTitle("이예리가 때림요")
                .reportWho("이예리가 때림요")
                .reportWhen("이예리가 때림요")
                .reportWhere("이예리가 때림요")
                .reportWhat("이예리가 때림요")
                .reportHow("이예리가 때림요")
                .reportWhy("이예리가 때림요")
                .uploadFiles(fList)
                .build();


        mockMvc.perform(
        post("/report")
            .header("Authorization", "Bearer Token")
            .content(objectMapper.writeValueAsString(r1))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isCreated())
        .andDo(document("create-report",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("codeId").type(JsonFieldType.NUMBER)
                    .optional()
                    .description("신고 분류 ID"),
                fieldWithPath("schoolId").type(JsonFieldType.NUMBER)
                    .optional()
                    .description("학교 ID"),
                fieldWithPath("year").type(JsonFieldType.NUMBER)
                    .optional()
                    .description("발생 년도"),
                fieldWithPath("reportTitle").type(JsonFieldType.STRING)
                    .optional()
                    .description("신고 제목"),
                fieldWithPath("reportWho").type(JsonFieldType.STRING)
                    .optional()
                    .description("누가"),
                fieldWithPath("reportWhen").type(JsonFieldType.STRING)
                    .optional()
                    .description("언제"),
                fieldWithPath("reportWhere").type(JsonFieldType.STRING)
                    .optional()
                    .description("어디서"),
                fieldWithPath("reportWhat").type(JsonFieldType.STRING)
                    .optional()
                    .description("무엇을"),
                fieldWithPath("reportHow").type(JsonFieldType.STRING)
                    .optional()
                    .description("어떻게"),
                fieldWithPath("reportWhy").type(JsonFieldType.STRING)
                    .optional()
                    .description("왜"),
                fieldWithPath("uploadFiles").type(JsonFieldType.ARRAY)
                    .optional()
                    .description("파일 업로드"),
                fieldWithPath("uploadFiles[].uploadFileName").type(JsonFieldType.STRING)
                    .optional()
                    .description("업로드 파일명")
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

    @DisplayName("신고 내역 조회 API")
    @Test
    void searchReports() throws Exception {
        mockMvc.perform(
            get("/report")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("search-reports",
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
                fieldWithPath("data[].reportType").type(JsonFieldType.STRING)
                    .description("신고 유형"),
                fieldWithPath("data[].user").type(JsonFieldType.OBJECT)
                    .description("신고자"),
                fieldWithPath("data[].user.grade").type(JsonFieldType.NUMBER)
                    .description("학년"),
                fieldWithPath("data[].user.classNum").type(JsonFieldType.NUMBER)
                    .description("반"),
                fieldWithPath("data[].user.name").type(JsonFieldType.STRING)
                    .description("이름"),
                fieldWithPath("data[].reportTitle").type(JsonFieldType.STRING)
                    .description("신고 제목"),
                fieldWithPath("data[].reportWho").type(JsonFieldType.STRING)
                    .description("누가"),
                fieldWithPath("data[].reportWhen").type(JsonFieldType.STRING)
                    .description("언제"),
                fieldWithPath("data[].reportWhere").type(JsonFieldType.STRING)
                    .description("어디서"),
                fieldWithPath("data[].reportWhat").type(JsonFieldType.STRING)
                    .description("무엇을"),
                fieldWithPath("data[].reportHow").type(JsonFieldType.STRING)
                    .description("어떻게"),
                fieldWithPath("data[].reportWhy").type(JsonFieldType.STRING)
                    .description("왜"),
                fieldWithPath("data[].uploadFiles").type(JsonFieldType.ARRAY)
                    .description("파일들"),
                fieldWithPath("data[].uploadFiles[].uploadFileName").type(JsonFieldType.STRING)
                    .description("업로드 제목"),
                fieldWithPath("data[].result").type(JsonFieldType.STRING)
                    .description("처리 결과"),
                fieldWithPath("data[].createdDate").type(JsonFieldType.ARRAY)
                    .description("신청 날짜")
            )
        ));
    }
}
