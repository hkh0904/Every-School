package com.everyschool.schoolservice.api.service.schooluser;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentParentInfo;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.ConsultUserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.DescendantInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassParentResponse;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.dto.MyClassStudentDto;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.everyschool.schoolservice.ErrorMessage.NOT_EXIST_MY_SCHOOL_CLASS;

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

        SchoolClass mySchoolClass = getMySchoolClass(userInfo.getUserId(), schoolYear);

        List<MyClassStudentDto> myClassStudents = schoolUserQueryRepository.findBySchoolClassId(mySchoolClass.getId());

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

    public List<MyClassParentResponse> searchMyClassParents(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        SchoolClass mySchoolClass = getMySchoolClass(userInfo.getUserId(), schoolYear);

        //classId로 회원 서비스에서 보호자관계와 부모타입 조회
        List<StudentParentInfo> infos = userServiceClient.searchStudentParentBySchoolClassId(mySchoolClass.getId());

        //classId로 학급 유저 전부 조회 (학생, 학부모) -> map으로 변환(key: userId, value: dto)
        List<SchoolUser> schoolUsers = schoolUserQueryRepository.findParentBySchoolClassId(mySchoolClass.getId());
        Map<Long, SchoolUser> map = schoolUsers.stream()
            .collect(Collectors.toMap(SchoolUser::getUserId, schoolUser -> schoolUser, (a, b) -> b));

        //반복문을 돌려서 자식타입이면 학부모 키로 map에서 조회해서 슝
        List<MyClassParentResponse> responses = new ArrayList<>();
        for (StudentParentInfo info : infos) {
            MyClassParentResponse response = MyClassParentResponse.builder()
                .userId(info.getParentId())
                .studentNumber(map.get(info.getStudentId()).getStudentNumber())
                .studentName(map.get(info.getStudentId()).getUserName())
                .parentType(info.getParentType())
                .name(map.get(info.getParentId()).getUserName())
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

    private SchoolClass getMySchoolClass(Long userId, int schoolYear) {
        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findByTeacherIdAndSchoolYear(userId, schoolYear);
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException(NOT_EXIST_MY_SCHOOL_CLASS.getMessage());
        }
        return findSchoolClass.get();
    }

    public List<ConsultUserInfo> searchConsultUser(List<Long> userIds) {
        List<SchoolUser> findSchoolUsers = schoolUserQueryRepository.findByUserIdIn(userIds);

        return findSchoolUsers.stream()
            .map(ConsultUserInfo::of)
            .collect(Collectors.toList());
    }

    public List<DescendantInfo> searchDescendantInfo(List<Long> userIds) {
        return schoolUserQueryRepository.findDescendantInfo(userIds);
    }
}
