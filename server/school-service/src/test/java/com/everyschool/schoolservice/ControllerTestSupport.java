package com.everyschool.schoolservice;

import com.everyschool.schoolservice.api.app.controller.school.SchoolAppQueryController;
import com.everyschool.schoolservice.api.app.controller.schoolapply.SchoolApplyAppController;
import com.everyschool.schoolservice.api.app.service.schoolapply.SchoolApplyAppService;
import com.everyschool.schoolservice.api.service.school.SchoolAppQueryService;
import com.everyschool.schoolservice.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {SchoolAppQueryController.class,
    SchoolApplyAppController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected TokenUtils tokenUtils;

    @MockBean
    protected SchoolAppQueryService schoolQueryService;

    @MockBean
    protected SchoolApplyAppService schoolApplyAppService;
}
