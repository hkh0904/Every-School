package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class SchoolClassQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SchoolClassQueryRepository schoolClassQueryRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @DisplayName("담임 id와 학년도가 모두 일치하는 학급이 존재하면 true를 반환한다.")
    @Test
    void existByTeacherIdAndSchoolYear() {
        //given
        SchoolClass schoolClass = saveSchoolClass();

        //when
        boolean isExist = schoolClassQueryRepository.existByTeacherIdAndSchoolYear(1L, 2023);

        //then
        assertThat(isExist).isTrue();
    }

    @DisplayName("학교, 학년도, 학년, 반 정보가 모두 일치하는 학급이 존재하면 true를 반환한다.")
    @Test
    void existSchoolClass() {
        //given
        SchoolClass schoolClass = saveSchoolClass();

        //when
        boolean isExist = schoolClassQueryRepository.existSchoolClass(1L, 2023, 1, 3);

        //then
        assertThat(isExist).isTrue();
    }

    private SchoolClass saveSchoolClass() {
        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .school(null)
            .teacherId(1L)
            .build();
        return schoolClassRepository.save(schoolClass);
    }
}