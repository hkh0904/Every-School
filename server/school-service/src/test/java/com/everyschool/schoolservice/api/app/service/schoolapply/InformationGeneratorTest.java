package com.everyschool.schoolservice.api.app.service.schoolapply;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InformationGeneratorTest {

    @DisplayName("학생 정보를 생성한다.")
    @Test
    void createInformation() {
        //given

        //when
        String info = InformationGenerator.createInformation(1, 3, "이예리");

        //then
        Assertions.assertThat(info).isEqualTo("1학년 3반 이예리 학생");
    }
}