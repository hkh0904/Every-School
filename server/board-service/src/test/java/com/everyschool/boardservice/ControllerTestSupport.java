package com.everyschool.boardservice;

import com.everyschool.boardservice.api.app.controller.board.BoardAppController;
import com.everyschool.boardservice.api.FileStore;
import com.everyschool.boardservice.api.app.service.board.BoardAppService;
import com.everyschool.boardservice.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {BoardAppController.class})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected TokenUtils tokenUtils;

    @MockBean
    protected FileStore fileStore;

    @MockBean
    protected BoardAppService boardService;
}