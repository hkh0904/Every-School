package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.app.controller.report.response.ReportResponse;
import com.everyschool.reportservice.api.client.UserServiceClient;
import com.everyschool.reportservice.api.client.response.UserInfo;
import com.everyschool.reportservice.domain.report.repository.ReportAppQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReportAppQueryService {

    private final ReportAppQueryRepository reportAppQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<ReportResponse> searchReports(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchByUserKey(userKey);

        List<ReportResponse> response = reportAppQueryRepository.findByUserId(userInfo.getUserId(), schoolYear);

        return response;
    }
}
