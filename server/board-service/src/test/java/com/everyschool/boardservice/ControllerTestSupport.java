package com.everyschool.boardservice;

import com.everyschool.boardservice.api.app.controller.board.BoardAppController;
import com.everyschool.boardservice.api.controller.FileStore;
import com.everyschool.boardservice.api.service.board.BoardService;
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
    private TokenUtils tokenUtils;

    @MockBean
    private FileStore fileStore;

    @MockBean
    private BoardService boardService;
}