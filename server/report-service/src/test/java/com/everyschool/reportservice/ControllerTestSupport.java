package com.everyschool.reportservice;

import com.everyschool.reportservice.api.FileStore;
import com.everyschool.reportservice.api.app.controller.report.ReportAppController;
import com.everyschool.reportservice.api.app.service.report.ReportAppService;
import com.everyschool.reportservice.api.web.controller.report.ReportWebController;
import com.everyschool.reportservice.api.web.controller.report.ReportWebQueryController;
import com.everyschool.reportservice.api.web.service.report.ReportWebQueryService;
import com.everyschool.reportservice.api.web.service.report.ReportWebService;
import com.everyschool.reportservice.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ReportAppController.class, ReportWebController.class, ReportWebQueryController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private TokenUtils tokenUtils;

    @MockBean
    private FileStore fileStore;

    @MockBean
    private ReportAppService reportAppService;

    @MockBean
    private ReportWebService reportWebService;

    @MockBean
    private ReportWebQueryService reportWebQueryService;
}
