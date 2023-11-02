package com.everyschool.schoolservice.api.service.schooluser;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.dto.MyClassStudentDto;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolUserQueryService {

    private final SchoolUserQueryRepository schoolUserQueryRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 담임 선생님의 학생 목록 조회
     *
     * @param userKey 담임 선생님 고유키
     * @param schoolYear 조회 학년도
     * @return 나의 학생 목록
     */
    public List<MyClassStudentResponse> searchMyClassStudents(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findByTeacherIdAndSchoolYear(userInfo.getUserId(), schoolYear);
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException();
        }
        SchoolClass schoolClass = findSchoolClass.get();

        List<MyClassStudentDto> myClassStudents = schoolUserQueryRepository.findBySchoolClassId(schoolClass.getId());

        List<Long> studentIds = myClassStudents.stream()
            .map(MyClassStudentDto::getStudentId)
            .collect(Collectors.toList());

        List<StudentResponse> students = userServiceClient.searchByStudentIdIn(studentIds);

        Map<Long, Integer> map = myClassStudents.stream()
            .collect(Collectors.toMap(MyClassStudentDto::getStudentId, MyClassStudentDto::getStudentNumber, (a, b) -> b));

        List<MyClassStudentResponse> responses = new ArrayList<>();
        for (StudentResponse student : students) {
            MyClassStudentResponse response = MyClassStudentResponse.builder()
                .userId(student.getStudentId())
                .studentNumber(map.get(student.getStudentId()))
                .name(student.getName())
                .birth(student.getBirth())
                .build();
            responses.add(response);
        }

        return responses;
    }

    public StudentInfo searchStudentInfo(Long userId) {
        Optional<StudentInfo> studentInfo = schoolUserQueryRepository.findByUserId(userId);
        if (studentInfo.isEmpty()) {
            throw new NoSuchElementException();
        }
        return studentInfo.get();
    }
}
