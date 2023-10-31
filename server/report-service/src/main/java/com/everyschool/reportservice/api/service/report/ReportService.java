package com.everyschool.reportservice.api.service.report;

import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.api.controller.report.response.CreateReportResponse;
import com.everyschool.reportservice.api.service.report.dto.CreateReportDto;
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
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserServiceClient userServiceClient;

    public CreateReportResponse createReport(String userKey, Long schoolId, CreateReportDto dto, List<UploadFile> uploadFiles) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        ReportContent content = dto.toContent();

        Report report = Report.createReport(dto.getTitle(), content, 2023, dto.getTypeId(), schoolId, userInfo.getUserId(), uploadFiles);

        Report savedReport = reportRepository.save(report);

        return CreateReportResponse.of(savedReport);
    }
}
