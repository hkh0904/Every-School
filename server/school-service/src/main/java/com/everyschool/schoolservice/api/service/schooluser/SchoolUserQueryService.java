package com.everyschool.schoolservice.api.service.schooluser;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentParentInfo;
import com.everyschool.schoolservice.api.client.response.UserResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.ConsultUserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.DescendantInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassParentResponse;
import com.everyschool.schoolservice.api.web.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.dto.MyClassStudentDto;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfoCon;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.UserType;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.everyschool.schoolservice.error.ErrorMessage.NOT_EXIST_MY_SCHOOL_CLASS;

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
     * @param userKey    담임 선생님 고유키
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

        List<UserResponse> students = userServiceClient.searchByStudentIdIn(studentIds);

        Map<Long, Integer> map = myClassStudents.stream()
                .collect(Collectors.toMap(MyClassStudentDto::getStudentId, MyClassStudentDto::getStudentNumber, (a, b) -> b));

        List<MyClassStudentResponse> responses = new ArrayList<>();
        for (UserResponse student : students) {
            MyClassStudentResponse response = MyClassStudentResponse.builder()
                    .userId(student.getUserId())
                    .studentNumber(map.get(student.getUserId()))
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
        List<StudentParentInfo> studentParentInfos = userServiceClient.searchStudentParentBySchoolClassId(mySchoolClass.getId());

        //classId로 학급 유저 전부 조회 (학생, 학부모) -> map으로 변환(key: userId, value: dto)
        List<SchoolUser> studentAndParentSchoolUsers = schoolUserQueryRepository.findStudentAndParentBySchoolClassId(mySchoolClass.getId());
        Map<Long, SchoolUser> map = studentAndParentSchoolUsers.stream()
                .collect(Collectors.toMap(SchoolUser::getUserId, schoolUser -> schoolUser, (a, b) -> b));

        //반복문을 돌려서 자식타입이면 학부모 키로 map에서 조회해서 슝
        List<MyClassParentResponse> responses = new ArrayList<>();
        for (StudentParentInfo studentParent : studentParentInfos) {
            MyClassParentResponse response = MyClassParentResponse.builder()
                    .userId(studentParent.getParentId())
                    .studentNumber(map.get(studentParent.getStudentId()).getStudentNumber())
                    .studentName(map.get(studentParent.getStudentId()).getUserName())
                    .parentType(studentParent.getParentType())
                    .name(map.get(studentParent.getParentId()).getUserName())
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

    public Long searchTeacherByUserId(Long userId, Integer schoolYear) {
        //클래스 id 조회
        Optional<Long> findSchoolClassId = schoolUserQueryRepository.findByUserIdAndSchoolYear(userId, schoolYear);
        if (findSchoolClassId.isEmpty()) {
            throw new NoSuchElementException();
        }
        Long schoolClassId = findSchoolClassId.get();

        Optional<SchoolUser> findTeacher = schoolUserQueryRepository.findTeacher(schoolClassId);
        if (findTeacher.isEmpty()) {
            throw new NoSuchElementException();
        }

        SchoolUser teacher = findTeacher.get();

        return teacher.getUserId();
    }

    public List<StudentInfoCon> searchStudentsByUserId(Long userId, Integer schoolYear) {
        Optional<Long> findSchoolClassId = schoolUserQueryRepository.findByUserIdAndSchoolYear(userId, schoolYear);
        if (findSchoolClassId.isEmpty()) {
            throw new NoSuchElementException();
        }
        Long schoolClassId = findSchoolClassId.get();

        List<SchoolUser> students = schoolUserQueryRepository.findStudent(schoolClassId);


        List<StudentInfoCon> infos = new ArrayList<>();
        for (SchoolUser student : students) {
            StudentInfoCon info = StudentInfoCon.builder()
                    .userId(student.getUserId())
                    .studentNumber(student.getStudentNumber())
                    .build();
            infos.add(info);
        }

        return infos;
    }
}
