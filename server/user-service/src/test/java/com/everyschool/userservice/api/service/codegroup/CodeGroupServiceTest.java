package com.everyschool.userservice.api.service.codegroup;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class CodeGroupServiceTest extends IntegrationTestSupport {

    @Autowired
    private CodeGroupService codeGroupService;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("이미 사용 중인 그룹 이름이라면 예외가 발생한다.")
    @Test
    void createCodeGroupDuplicateGroupName() {
        //given
        saveCodeGroup();
        String groupName = "직책";

        //when //then
        assertThatThrownBy(() -> codeGroupService.createCodeGroup(groupName))
            .isInstanceOf(DuplicateException.class)
            .hasMessage("이미 사용 중인 그룹 이름입니다.");
    }

    @DisplayName("그룹 이름을 입력 받아 코드 그룹을 생성한다.")
    @Test
    void createCodeGroup() {
        //given
        String groupName = "직책";

        //when
        CreateCodeGroupResponse response = codeGroupService.createCodeGroup(groupName);

        //then
        assertThat(response.getGroupName()).isEqualTo("직책");
    }

    private CodeGroup saveCodeGroup() {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName("직책")
            .build();
        return codeGroupRepository.save(codeGroup);
    }
}