package com.everyschool.callservice.api.controller.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {

    @GetMapping("/call-service/v1/index")
    public String restDocs() {
        return "index";
    }
}
