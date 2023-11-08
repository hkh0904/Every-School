package com.everyschool.reportservice.api.app.controller.report;

import com.everyschool.reportservice.ControllerTestSupport;
import com.everyschool.reportservice.api.app.controller.report.request.CreateReportRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import static com.everyschool.reportservice.domain.report.ReportType.VIOLENCE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportAppControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/report-service/v1/app/{schoolYear}/schools/{schoolId}/reports";

    @DisplayName("신고 접수시 신고 유형은 필수값이다.")
    @Test
    void createReportWithoutTypeId() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 유형은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 설명은 필수값이다.")
    @Test
    void createReportWithoutDescription() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 설명은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 설명은 최대 500자이다.")
    @Test
    void createReportMaxLengthDescription() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", getText(501))
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 설명은 최대 500자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(누가)은 필수값이다.")
    @Test
    void createReportWithoutWho() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", " ")
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(누가)은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(누가)은 최대 100자이다.")
    @Test
    void createReportMaxLengthWho() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", getText(101))
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(누가)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(언제)은 필수값이다.")
    @Test
    void createReportWithoutWhen() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", " ")
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(언제)은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(언제)은 최대 100자이다.")
    @Test
    void createReportMaxLengthWhen() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", getText(101))
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(언제)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(어디서)은 필수값이다.")
    @Test
    void createReportWithoutWhere() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", " ")
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(어디서)은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(어디서)은 최대 100자이다.")
    @Test
    void createReportMaxLengthWhere() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", getText(101))
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(어디서)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(무엇을)은 필수값이다.")
    @Test
    void createReportWithoutWhat() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", " ")
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(무엇을)은 필수입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(무엇을)은 최대 100자이다.")
    @Test
    void createReportMaxLengthWhat() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", getText(101))
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(무엇을)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(어떻게)은 최대 100자이다.")
    @Test
    void createReportMaxLengthHow() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", getText(101))
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(어떻게)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수시 신고 내용(왜)은 최대 100자이다.")
    @Test
    void createReportMaxLengthWhy() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", getText(101))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("신고 내용(왜)은 최대 100자 입니다."));
    }

    @DisplayName("신고 접수를 한다.")
    @Test
    void createReport() throws Exception {
        //given
        CreateReportRequest request = CreateReportRequest.builder()
            .typeId(VIOLENCE.getCode())
            .description("description")
            .who("who")
            .when("when")
            .where("where")
            .what("what")
            .how(null)
            .why(null)
            .files(new ArrayList<>())
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL, 2023, 100000)
                    .param("typeId", String.valueOf(request.getTypeId()))
                    .param("description", request.getDescription())
                    .param("who", request.getWho())
                    .param("when", request.getWhen())
                    .param("where", request.getWhere())
                    .param("what", request.getWhat())
                    .param("how", request.getHow())
                    .param("why", request.getWhy())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.code").value("201"))
            .andExpect(jsonPath("$.status").value("CREATED"))
            .andExpect(jsonPath("$.message").value("CREATED"));
    }

    private String getText(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("0");
        }
        return String.valueOf(builder);
    }
}