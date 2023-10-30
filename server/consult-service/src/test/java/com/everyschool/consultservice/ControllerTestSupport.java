package com.everyschool.consultservice;

import com.everyschool.consultservice.api.controller.consult.ConsultQueryController;
import com.everyschool.consultservice.api.service.consult.ConsultQueryService;
import com.everyschool.consultservice.api.service.consult.ConsultService;
import com.everyschool.consultservice.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ConsultQueryController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ConsultService consultService;

    @MockBean
    protected ConsultQueryService consultQueryService;

    @MockBean
    protected TokenUtils tokenUtils;
}
