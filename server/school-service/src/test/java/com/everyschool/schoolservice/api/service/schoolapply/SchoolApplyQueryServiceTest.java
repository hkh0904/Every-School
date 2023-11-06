package com.everyschool.schoolservice.api.service.schoolapply;

import com.everyschool.schoolservice.IntegrationTestSupport;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

class SchoolApplyQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private SchoolApplyQueryService schoolApplyQueryService;

    @Autowired
    private SchoolApplyRepository schoolApplyRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @MockBean
    private UserServiceClient userServiceClient;

    @DisplayName("담임 고유키와 검색 조건을 입력받아 학급 승인 목록을 조회할 수 있다.")
    @Test
    void searchSchoolApplies() {
        //given
        SchoolClass schoolClass = saveSchoolClass();
        saveSchoolApply(1L, null, schoolClass);
        saveSchoolApply(2L, null, schoolClass);
        saveSchoolApply(3L, 20L, schoolClass);

        UserInfo userInfo = UserInfo.builder()
            .userId(100L)
            .userType('T')
            .userName("임우택")
            .schoolClassId(schoolClass.getId())
            .build();

        given(userServiceClient.searchUserInfo(anyString()))
            .willReturn(userInfo);

        StudentResponse response1 = createStudentResponse(1L, "이예리", "1998.04.12");
        StudentResponse response2 = createStudentResponse(2L, "이리온", "1998.12.10");
        StudentResponse response3 = createStudentResponse(3L, "임우택", "1998.01.03");

        given(userServiceClient.searchByStudentIdIn(anyList()))
            .willReturn(List.of(response1, response2, response3));


        //when
        List<SchoolApplyResponse> responses = schoolApplyQueryService.searchSchoolApplies(UUID.randomUUID().toString(), "wait");

        //then
        assertThat(responses).hasSize(3)
            .extracting("type", "childInfo", "relationship")
            .containsExactlyInAnyOrder(
                tuple("학생 신청", "1학년 3반 이예리", "본인"),
                tuple("학생 신청", "1학년 3반 이리온", "본인"),
                tuple("학부모 신청", "1학년 3반 임우택", "학부모")
            );
    }

    private SchoolClass saveSchoolClass() {
        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(2023)
            .grade(1)
            .classNum(3)
            .school(null)
            .teacherId(100L)
            .build();
        return schoolClassRepository.save(schoolClass);
    }

    private SchoolApply saveSchoolApply(Long studentId, Long parentId, SchoolClass schoolClass) {
        SchoolApply schoolApply = SchoolApply.builder()
            .schoolYear(2023)
            .codeId(1)
            .studentId(studentId)
            .parentId(parentId)
            .schoolClass(schoolClass)
            .build();
        return schoolApplyRepository.save(schoolApply);
    }

    private StudentResponse createStudentResponse(long studentId, String name, String birth) {
        return StudentResponse.builder()
            .studentId(studentId)
            .name(name)
            .birth(birth)
            .build();
    }

}