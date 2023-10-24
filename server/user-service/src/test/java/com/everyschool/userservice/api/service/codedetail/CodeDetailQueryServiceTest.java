package com.everyschool.userservice.api.service.codedetail;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeResponse;
import com.everyschool.userservice.domain.codedetail.CodeDetail;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailRepository;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CodeDetailQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CodeDetailQueryService codeDetailQueryService;

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("코드 그룹에 등록된 모든 코드를 조회할 수 있다.")
    @Test
    void searchCodeDetails() {
        //given
        CodeGroup group = saveCodeGroup();
        CodeDetail code1 = saveCodeDetail(group, "학생");
        CodeDetail code2 = saveCodeDetail(group, "학부모");
        CodeDetail code3 = saveCodeDetail(group, "교직원");

        //when
        CodeResponse response = codeDetailQueryService.searchCodeDetails(group.getId());

        //then
        assertThat(response.getCodes()).hasSize(3)
            .extracting("codeName")
            .containsExactlyInAnyOrder("학생", "학부모", "교직원");
    }

    private CodeGroup saveCodeGroup() {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName("회원구분")
            .build();
        return codeGroupRepository.save(codeGroup);
    }

    private CodeDetail saveCodeDetail(CodeGroup codeGroup, String codeName) {
        CodeDetail codeDetail = CodeDetail.builder()
            .codeName(codeName)
            .group(codeGroup)
            .build();
        return codeDetailRepository.save(codeDetail);
    }

}