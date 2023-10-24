package com.everyschool.userservice.api.service.codedetail;

import com.everyschool.userservice.IntegrationTestSupport;
import com.everyschool.userservice.api.controller.codedetail.respnse.CreateCodeDetailResponse;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.codedetail.CodeDetail;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailRepository;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class CodeDetailServiceTest extends IntegrationTestSupport {

    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeGroupRepository codeGroupRepository;

    @DisplayName("이미 사용 중인 코드 이름이라면 예외가 발생한다.")
    @Test
    void createCodeDetailDuplicateCodeName() {
        //given
        CodeGroup codeGroup = createCodeGroup();
        CodeDetail codeDetail = createCodeDetail(codeGroup);

        //when //then
        assertThatThrownBy(() -> codeDetailService.createCodeDetail(codeGroup.getId(), "교장"))
            .isInstanceOf(DuplicateException.class)
            .hasMessage("이미 사용 중인 코드 이름입니다.");
    }

    @DisplayName("코드 그룹과 코드 이름을 입력 받아 상세 코드를 생성한다.")
    @Test
    void createCodeDetail() {
        //given
        CodeGroup codeGroup = createCodeGroup();

        //when
        CreateCodeDetailResponse response = codeDetailService.createCodeDetail(codeGroup.getId(), "교장");

        //then
        assertThat(response.getCodeName()).isEqualTo("교장");
    }

    private CodeGroup createCodeGroup() {
        CodeGroup codeGroup = CodeGroup.builder()
            .groupName("직책")
            .build();
        return codeGroupRepository.save(codeGroup);
    }

    private CodeDetail createCodeDetail(CodeGroup codeGroup) {
        CodeDetail codeDetail = CodeDetail.builder()
            .codeName("교장")
            .group(codeGroup)
            .build();
        return codeDetailRepository.save(codeDetail);
    }
}