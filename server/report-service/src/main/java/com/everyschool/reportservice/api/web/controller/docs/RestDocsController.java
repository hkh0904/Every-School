package com.everyschool.reportservice.api.web.controller.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/report-service/v1/index")
    public String restDocs() {
        return "index";
    }
}
