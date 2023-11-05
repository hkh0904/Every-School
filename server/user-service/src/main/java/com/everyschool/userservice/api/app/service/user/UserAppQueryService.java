package com.everyschool.userservice.api.app.service.user;

import com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.StudentInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.TeacherInfoResponse;
import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import com.everyschool.userservice.api.client.school.SchoolServiceClient;
import com.everyschool.userservice.api.client.school.response.DescendantInfo;
import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.Teacher;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.StudentParentAppQueryRepository;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.everyschool.userservice.error.ErrorMessage.UNAUTHORIZED_USER;
import static com.everyschool.userservice.error.ErrorMessage.UNREGISTERED_USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAppQueryService {

    private final UserRepository userRepository;
    private final StudentParentAppQueryRepository studentParentAppQueryRepository;
    private final SchoolServiceClient schoolServiceClient;

    public StudentInfoResponse searchStudentInfo(String userKey) {
        User user = getUser(userKey);
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException(UNAUTHORIZED_USER.getMessage());
        }
        Student student = (Student) user;

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchBySchoolClassId(student.getSchoolClassId());

        School school = School.builder()
            .schoolId(student.getSchoolId())
            .name(schoolClassInfo.getSchoolName())
            .build();

        SchoolClass schoolClass = SchoolClass.builder()
            .schoolClassId(student.getSchoolClassId())
            .schoolYear(schoolClassInfo.getSchoolYear())
            .grade(schoolClassInfo.getGrade())
            .classNum(schoolClassInfo.getClassNum())
            .build();

        return StudentInfoResponse.builder()
            .userType(student.getUserCodeId())
            .email(student.getEmail())
            .name(student.getName())
            .birth(student.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(student.getCreatedDate())
            .build();
    }

    public ParentInfoResponse searchParentInfo(String userKey) {
        User user = getUser(userKey);
        if (!(user instanceof Parent)) {
            throw new IllegalArgumentException(UNAUTHORIZED_USER.getMessage());
        }
        Parent parent = (Parent) user;

        List<Student> students = studentParentAppQueryRepository.findByParentId(parent.getId());
        List<Long> studentIds = students.stream()
            .map(Student::getId)
            .collect(Collectors.toList());

        List<DescendantInfo> descendantInfos = schoolServiceClient.searchByUserId(studentIds);

        Map<Long, DescendantInfo> map = descendantInfos.stream()
            .collect(Collectors.toMap(DescendantInfo::getUserId, descendantInfo -> descendantInfo, (a, b) -> b));

        List<ParentInfoResponse.Descendant> descendants = new ArrayList<>();
        for (Student student : students) {
            School school = School.builder()
                .schoolId(student.getSchoolId())
                .name(map.get(student.getId()).getSchoolName())
                .build();

            SchoolClass schoolClass = SchoolClass.builder()
                .schoolClassId(student.getSchoolClassId())
                .schoolYear(map.get(student.getId()).getSchoolYear())
                .grade(map.get(student.getId()).getGrade())
                .classNum(map.get(student.getId()).getClassNum())
                .build();

            ParentInfoResponse.Descendant descendant = ParentInfoResponse.Descendant.builder()
                .userType(student.getUserCodeId())
                .name(student.getName())
                .studentNumber(map.get(student.getId()).getStudentNumber())
                .school(school)
                .schoolClass(schoolClass)
                .build();

            descendants.add(descendant);
        }

        return ParentInfoResponse.builder()
            .userType(parent.getUserCodeId())
            .email(parent.getEmail())
            .name(parent.getName())
            .birth(parent.getBirth())
            .descendants(descendants)
            .joinDate(parent.getCreatedDate())
            .build();
    }

    public TeacherInfoResponse searchTeacherInfo(String userKey) {
        User user = getUser(userKey);
        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException(UNAUTHORIZED_USER.getMessage());
        }
        Teacher teacher = (Teacher) user;

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchBySchoolClassId(teacher.getSchoolClassId());

        School school = School.builder()
            .schoolId(teacher.getSchoolId())
            .name(schoolClassInfo.getSchoolName())
            .build();

        SchoolClass schoolClass = SchoolClass.builder()
            .schoolClassId(teacher.getSchoolClassId())
            .schoolYear(schoolClassInfo.getSchoolYear())
            .grade(schoolClassInfo.getGrade())
            .classNum(schoolClassInfo.getClassNum())
            .build();

        return TeacherInfoResponse.builder()
            .userType(teacher.getUserCodeId())
            .email(teacher.getEmail())
            .name(teacher.getName())
            .birth(teacher.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(teacher.getCreatedDate())
            .build();
    }

    private User getUser(String userKey) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_USER.getMessage());
        }
        return findUser.get();
    }
}
