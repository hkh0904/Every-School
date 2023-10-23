package com.everyschool.userservice.domain.codegroup.repository;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        saveCodeGroup();

        //when
        boolean isExistGroupName = codeGroupQueryRepository.existGroupName("직책");

        //then
        assertThat(isExistGroupName).isTrue();
    }

    private CodeGroup saveCodeGroup() {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName("직책")
            .build();
        return codeGroupRepository.save(codeGroup);
    }
}