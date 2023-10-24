package com.everyschool.userservice.domain.codedetail.repository;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeDetailResponse;
import com.everyschool.userservice.domain.codedetail.CodeDetail;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CodeDetailQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CodeDetailQueryRepository codeDetailQueryRepository;

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("같은 그룹에 이미 등록된 코드 이름이 존재하면 true를 반환한다.")
    @Test
    void existCodeNameWithDuplicateCodeName() {
        //given
        CodeGroup group = saveCodeGroup("회원구분");
        CodeDetail code = saveCodeDetail(group, "학생");

        //when
        boolean isExist = codeDetailQueryRepository.existCodeName(group.getId(), "학생");

        //then
        assertThat(isExist).isTrue();
    }

    @DisplayName("같은 그룹에 이미 등록된 코드 이름이 존재하지 않으면 false를 반환한다.")
    @Test
    void existCodeNameWithoutDuplicateCodeName() {
        //given
        CodeGroup group1 = saveCodeGroup("회원구분");
        CodeDetail code = saveCodeDetail(group1, "학생");

        CodeGroup group2 = saveCodeGroup("카테고리");

        //when
        boolean isExist = codeDetailQueryRepository.existCodeName(group2.getId(), "학생");

        //then
        assertThat(isExist).isFalse();
    }

    @DisplayName("그룹으로 코드들을 조회한다.")
    @Test
    void findByGroupId() {
        //given
        CodeGroup group = saveCodeGroup("회원구분");
        CodeDetail code1 = saveCodeDetail(group, "학생");
        CodeDetail code2 = saveCodeDetail(group, "학부모");
        CodeDetail code3 = saveCodeDetail(group, "교직원");

        //when
        List<CodeDetailResponse> responses = codeDetailQueryRepository.findByGroupId(group.getId());

        //then
        assertThat(responses).hasSize(3)
            .extracting("codeName")
            .containsExactlyInAnyOrder("학생", "학부모", "교직원");
    }

    private CodeGroup saveCodeGroup(String groupName) {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName(groupName)
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