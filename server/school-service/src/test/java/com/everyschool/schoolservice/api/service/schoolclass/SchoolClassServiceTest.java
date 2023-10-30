package com.everyschool.schoolservice.api.service.schoolclass;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.controller.schoolclass.response.CreateSchoolClassResponse;
import com.everyschool.schoolservice.api.service.schoolclass.dto.CreateSchoolClassDto;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class SchoolClassServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("입력 받은 담임 정보가 이미 같은 학년도에 존재하면 예외가 발생한다.")
    @Test
    void createSchoolClassExistSameSchoolYear() {
        //given
        SchoolClass schoolClass = saveSchoolClass(null);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(1L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(2)
            .classNum(5)
            .build();

        //when //then
        assertThatThrownBy(() -> schoolClassService.createSchoolClass(null, dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 담임으로 등록된 교사입니다.");
    }

    @DisplayName("학교, 학년도, 학년, 반의 정보가 모두 일치하는 학급이 존재하면 예외가 발생한다.")
    @Test
    void createSchoolClassExistSameSchoolClassInfo() {
        //given
        School school = saveSchool(1003);
        SchoolClass schoolClass = saveSchoolClass(school);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(2L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .build();

        //when //then
        assertThatThrownBy(() -> schoolClassService.createSchoolClass(school.getId(), dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 학급이 존재합니다.");
    }

    @DisplayName("고등학교는 1~3학년 외의 학년이면 예외가 발생한다.")
    @Test
    void createSchoolClassOverGradeOfHighSchool() {
        //given
        School school = saveSchool(1003);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(1L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(4)
            .classNum(3)
            .build();

        //when //then
        assertThatThrownBy(() -> schoolClassService.createSchoolClass(school.getId(), dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("1 ~ 3학년만 등록할 수 있습니다.");
    }

    @DisplayName("중학교는 1~3학년 외의 학년이면 예외가 발생한다.")
    @Test
    void createSchoolClassOverGradeOfMiddleSchool() {
        //given
        School school = saveSchool(1002);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(1L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(4)
            .classNum(3)
            .build();

        //when //then
        assertThatThrownBy(() -> schoolClassService.createSchoolClass(school.getId(), dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("1 ~ 3학년만 등록할 수 있습니다.");
    }

    @DisplayName("초등학교는 1~6학년 외의 학년이면 예외가 발생한다.")
    @Test
    void createSchoolClassOverGradeOfElementarySchool() {
        //given
        School school = saveSchool(1001);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(1L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(7)
            .classNum(3)
            .build();

        //when //then
        assertThatThrownBy(() -> schoolClassService.createSchoolClass(school.getId(), dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("1 ~ 6학년만 등록할 수 있습니다.");
    }

    @DisplayName("학급 정보를 입력 받아 클래스를 생성할 수 있다.")
    @Test
    void createSchoolClass() {
        //given
        School school = saveSchool(1003);
        String userKey = UUID.randomUUID().toString();

        given(userServiceClient.searchByUserKey(userKey))
            .willReturn(1L);

        CreateSchoolClassDto dto = CreateSchoolClassDto.builder()
            .userKey(userKey)
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .build();

        //when
        CreateSchoolClassResponse response = schoolClassService.createSchoolClass(school.getId(), dto);

        //then
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(response.getSchoolClassId());
        assertThat(schoolClass).isPresent();
    }

    private School saveSchool(int schoolTypeCodeId) {
        School school = School.builder()
            .name("수완고등학교")
            .zipcode("62246")
            .address("광주광역시 광산구 장덕로 155")
            .url("http://suwan.gen.hs.kr")
            .tel("062-961-5746")
            .openDate(LocalDate.of(2009, 3, 1).atStartOfDay())
            .schoolTypeCodeId(schoolTypeCodeId)
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