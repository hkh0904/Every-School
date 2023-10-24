package com.everyschool.userservice.api.service.codegroup;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CodeGroupQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CodeGroupQueryService codeGroupQueryService;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("등록된 모든 코드 그룹을 조회할 수 있다.")
    @Test
    void searchCodeGroups() {
        //given
        CodeGroup group1 = saveCodeGroup("직책");
        CodeGroup group2 = saveCodeGroup("회원구분");
        CodeGroup group3 = saveCodeGroup("카테고리");

        //when
        List<CodeGroupResponse> responses = codeGroupQueryService.searchCodeGroups();

        //then
        assertThat(responses).hasSize(3)
            .extracting("groupName")
            .containsExactlyInAnyOrder("직책", "회원구분", "카테고리");
    }

    private CodeGroup saveCodeGroup(String groupName) {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName(groupName)
            .build();
        return codeGroupRepository.save(codeGroup);
    }
}