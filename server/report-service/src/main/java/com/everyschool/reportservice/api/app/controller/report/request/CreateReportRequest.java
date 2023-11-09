package com.everyschool.reportservice.api.app.controller.report.request;

import com.everyschool.reportservice.api.app.service.report.dto.CreateReportDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateReportRequest {

    @NotNull(message = "신고 유형은 필수입니다.")
    private Integer typeId;

    @NotBlank(message = "신고 설명은 필수입니다.")
    @Size(max = 500, message = "신고 설명은 최대 500자 입니다.")
    private String description;

    @NotBlank(message = "신고 내용(누가)은 필수입니다.")
    @Size(max = 100, message = "신고 내용(누가)은 최대 100자 입니다.")
    private String who;

    @NotBlank(message = "신고 내용(언제)은 필수입니다.")
    @Size(max = 100, message = "신고 내용(언제)은 최대 100자 입니다.")
    private String when;

    @NotBlank(message = "신고 내용(어디서)은 필수입니다.")
    @Size(max = 100, message = "신고 내용(어디서)은 최대 100자 입니다.")
    private String where;

    @NotBlank(message = "신고 내용(무엇을)은 필수입니다.")
    @Size(max = 100, message = "신고 내용(무엇을)은 최대 100자 입니다.")
    private String what;

    @Size(max = 100, message = "신고 내용(어떻게)은 최대 100자 입니다.")
    private String how;

    @Size(max = 100, message = "신고 내용(왜)은 최대 100자 입니다.")
    private String why;

    private List<MultipartFile> files = new ArrayList<>();

    @Builder
    private CreateReportRequest(Integer typeId, String description, String who, String when, String where, String what, String how, String why, List<MultipartFile> files) {
        this.typeId = typeId;
        this.description = description;
        this.who = who;
        this.when = when;
        this.where = where;
        this.what = what;
        this.how = how;
        this.why = why;
        this.files = files;
    }

    public CreateReportDto toDto() {
        return CreateReportDto.builder()
            .typeId(this.typeId)
            .description(this.description)
            .who(this.who)
            .when(this.when)
            .where(this.where)
            .what(this.what)
            .how(this.how)
            .why(this.why)
            .build();
    }
}
