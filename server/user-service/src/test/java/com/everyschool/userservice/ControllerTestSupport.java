package com.everyschool.userservice;

import com.everyschool.userservice.api.controller.codedetail.CodeDetailController;
import com.everyschool.userservice.api.controller.codedetail.CodeDetailQueryController;
import com.everyschool.userservice.api.controller.codegroup.CodeGroupController;
import com.everyschool.userservice.api.controller.codegroup.CodeGroupQueryController;
import com.everyschool.userservice.api.controller.user.AccountController;
import com.everyschool.userservice.api.service.codedetail.CodeDetailQueryService;
import com.everyschool.userservice.api.service.codedetail.CodeDetailService;
import com.everyschool.userservice.api.service.codegroup.CodeGroupQueryService;
import com.everyschool.userservice.api.service.codegroup.CodeGroupService;
import com.everyschool.userservice.api.service.user.UserQueryService;
import com.everyschool.userservice.api.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {AccountController.class,
    CodeGroupController.class, CodeGroupQueryController.class,
    CodeDetailController.class, CodeDetailQueryController.class
})
@WithMockUser
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private CodeGroupService codeGroupService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserQueryService userQueryService;

    @MockBean
    private CodeGroupQueryService codeGroupQueryService;

    @MockBean
    private CodeDetailService codeDetailService;

    @MockBean
    private CodeDetailQueryService codeDetailQueryService;
}
