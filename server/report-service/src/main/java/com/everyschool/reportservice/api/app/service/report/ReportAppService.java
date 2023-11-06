package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.app.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.api.app.service.report.dto.CreateReportDto;
import com.everyschool.reportservice.domain.report.Report;
import com.everyschool.reportservice.domain.report.ReportContent;
import com.everyschool.reportservice.domain.report.UploadFile;
import com.everyschool.reportservice.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportAppService {

    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    public CreateReportResponse createReport(String userKey, Long schoolId, Integer schoolYear, CreateReportDto dto, List<UploadFile> uploadFiles) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        ReportContent content = dto.toContent();

        //학번 정보 가져오기

        Report report = Report.createReport(dto.getTitle(), dto.getDescription(), content, schoolYear, dto.getTypeId(), schoolId, userInfo.getUserId(), uploadFiles);

        Report savedReport = reportRepository.save(report);

        return CreateReportResponse.of(savedReport);
    }
}
