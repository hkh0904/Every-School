package com.everyschool.schoolservice.domain.schoolclass.repository;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class SchoolClassRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @DisplayName("담임 id와 학년도로 학급을 조회한다.")
    @Test
    void findByTeacherIdAndSchoolYear() {
        //given
        School school = saveSchool();
        SchoolClass schoolClass = saveSchoolClass(school);

        //when
        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findByTeacherIdAndSchoolYear(1L, 2023);

        //then
        assertThat(findSchoolClass).isPresent();
    }

    private School saveSchool() {
        School school = School.builder()
            .name("수완고등학교")
            .zipcode("62246")
            .address("광주광역시 광산구 장덕로 155")
            .url("http://suwan.gen.hs.kr")
            .tel("062-961-5746")
            .openDate(LocalDate.of(2009, 3, 1).atStartOfDay())
            .schoolTypeCodeId(7)
            .build();
        return schoolRepository.save(school);
    }

    private SchoolClass saveSchoolClass(School school) {
        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .school(school)
            .teacherId(1L)
            .build();
        return schoolClassRepository.save(schoolClass);
    }
}