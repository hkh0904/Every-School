package com.everyschool.reportservice.api.app.service.report;

import com.everyschool.reportservice.api.client.response.SchoolUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WitnessGeneratorTest {

    @DisplayName("학번이 존재하면 학생으로 생성된다.")
    @Test
    void createWitnessInfoForStudent() {
        //given
        SchoolUserInfo schoolUserInfo = generateSchoolUserInfo(10313);

        //when
        String witness = WitnessGenerator.createWitnessInfo(schoolUserInfo, "이예리");

        //then
        Assertions.assertThat(witness).isEqualTo("1학년 3반 13번 이예리 학생");
    }

    @DisplayName("학번이 존재하면 학생으로 생성된다.")
    @Test
    void createWitnessInfoForTeacher() {
        //given
        SchoolUserInfo schoolUserInfo = generateSchoolUserInfo(null);

        //when
        String witness = WitnessGenerator.createWitnessInfo(schoolUserInfo, "이예리");

        //then
        Assertions.assertThat(witness).isEqualTo("1학년 3반 이예리 선생님");
    }

    private SchoolUserInfo generateSchoolUserInfo(Integer studentNum) {
        return SchoolUserInfo.builder()
            .grade(1)
            .classNum(3)
            .studentNum(studentNum)
            .build();
    }
}