package com.everyschool.schoolservice.api.service.schooluser;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.UserType;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.everyschool.schoolservice.domain.schooluser.UserType.STUDENT;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class SchoolUserQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolUserQueryService schoolUserQueryService;

    @Autowired
    private SchoolUserRepository schoolUserRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("담임은 본인이 담당하는 학급의 학생들을 조회할 수 있다.")
    @Test
    void searchMyClassStudent() {
        //given
        School school = saveSchool();
        SchoolClass schoolClass = saveSchoolClass(school);
        SchoolUser schoolUser1 = saveSchoolUser(school, schoolClass, 1L, 10301);
        SchoolUser schoolUser2 = saveSchoolUser(school, schoolClass, 2L, 10302);

        UserInfo userInfo = UserInfo.builder()
            .userId(100L)
            .userType('T')
            .userName("임우택")
            .schoolClassId(schoolClass.getId())
            .build();

        given(userServiceClient.searchUserInfo(anyString()))
            .willReturn(userInfo);

        StudentResponse response1 = StudentResponse.builder()
            .studentId(1L)
            .name("이예리")
            .birth("1998.04.12")
            .build();
        StudentResponse response2 = StudentResponse.builder()
            .studentId(2L)
            .name("이리온")
            .birth("1998.12.10")
            .build();
        given(userServiceClient.searchByStudentIdIn(anyList()))
            .willReturn(List.of(response1, response2));

        //when
        List<MyClassStudentResponse> responses = schoolUserQueryService.searchMyClassStudents(UUID.randomUUID().toString(), 2023);

        //then
        assertThat(responses).hasSize(2)
            .extracting("studentNumber", "name", "birth")
            .containsExactlyInAnyOrder(
                tuple(10301, "이예리", "1998.04.12"),
                tuple(10302, "이리온", "1998.12.10")
            );
    }

    private School saveSchool() {
        School school = School.builder()
            .name("수완고등학교")
            .zipcode("62246")
            .address("광주광역시 광산구 장덕로 155")
            .url("http://suwan.gen.hs.kr")
            .tel("062-961-5746")
            .openDate(LocalDate.of(2009, 3, 1).atStartOfDay())
            .schoolTypeCodeId(1003)
            .build();
        return schoolRepository.save(school);
    }

    private SchoolClass saveSchoolClass(School school) {
        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .school(school)
            .teacherId(100L)
            .build();
        return schoolClassRepository.save(schoolClass);
    }

    private SchoolUser saveSchoolUser(School school, SchoolClass schoolClass, Long userId, int studentNumber) {
        SchoolUser schoolUser = SchoolUser.builder()
            .userTypeId(STUDENT.getCode())
            .studentNumber(studentNumber)
            .userName("이예리")
            .schoolYear(2023)
            .userId(userId)
            .school(school)
            .schoolClass(schoolClass)
            .build();
        return schoolUserRepository.save(schoolUser);
    }
}