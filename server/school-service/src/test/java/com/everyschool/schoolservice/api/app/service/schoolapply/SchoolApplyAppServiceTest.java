package com.everyschool.schoolservice.api.app.service.schoolapply;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.dto.CreateSchoolApplyDto;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.SchoolType;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class SchoolApplyAppServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolApplyAppService schoolApplyAppService;

    @Autowired
    private SchoolApplyRepository schoolApplyRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("회원 고유키, 학년도, 학교 아이디, 학급 정보를 입력 받아 학교 소속 신청을 등록한다.")
    @Test
    void createSchoolApply() {
        //given
        givenUserInfo();
        School school = saveSchool();
        SchoolClass schoolClass = saveSchoolClass(school);

        CreateSchoolApplyDto dto = CreateSchoolApplyDto.builder()
            .grade(1)
            .classNum(3)
            .build();
        //when
        CreateSchoolApplyResponse response = schoolApplyAppService.createSchoolApply(UUID.randomUUID().toString(), 2023, school.getId(), dto);

        //then
        Optional<SchoolApply> findSchoolApply = schoolApplyRepository.findById(response.getSchoolApplyId());
        assertThat(findSchoolApply).isPresent();
    }

    private School saveSchool() {
        School school = School.builder()
            .zipcode("31241")
            .name("수완고등학교")
            .address("광주광역시 광산구 장덕로 155")
            .url("url")
            .tel("062-1234-1234")
            .openDate(LocalDate.of(1990, 4, 20).atStartOfDay())
            .schoolTypeCodeId(SchoolType.HIGH.getCode())
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

    private void givenUserInfo() {
        UserInfo userInfo = UserInfo.builder()
            .userId(1L)
            .userType('S')
            .userName("이예리")
            .schoolClassId(null)
            .build();

        given(userServiceClient.searchUserInfo(anyString()))
            .willReturn(userInfo);
    }
}