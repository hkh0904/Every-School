package com.everyschool.schoolservice.api.web.controller.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * API docs 컨트롤러
 *
 * @author 임우택
 */
@Controller
public class RestDocsController {

    /**
     * API docs
     *
     * @return index.html
     */
    @GetMapping("/school-service/v1/index")
    public String restDocs() {
        return "index";
    }
}
