package com.everyschool.userservice.domain.codegroup.repository;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CodeGroupQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CodeGroupQueryRepository codeGroupQueryRepository;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("이미 등록된 그룹 이름이 존재하면 true를 반환한다.")
    @Test
    void existGroupName() {
        //given
        saveCodeGroup("직책");

        //when
        boolean isExistGroupName = codeGroupQueryRepository.existGroupName("직책");

        //then
        assertThat(isExistGroupName).isTrue();
    }

    @DisplayName("등록된 모든 그룹을 조회한다.")
    @Test
    void findAll() {
        //given
        CodeGroup group1 = saveCodeGroup("직책");
        CodeGroup group2 = saveCodeGroup("회원구분");
        CodeGroup group3 = saveCodeGroup("카테고리");

        //when
        List<CodeGroupResponse> responses = codeGroupQueryRepository.findAll();

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