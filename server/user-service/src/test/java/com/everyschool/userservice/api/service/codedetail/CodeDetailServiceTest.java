package com.everyschool.userservice.api.service.codedetail;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailRepository;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

class CodeDetailServiceTest extends IntegrationTestSupport {

    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

}