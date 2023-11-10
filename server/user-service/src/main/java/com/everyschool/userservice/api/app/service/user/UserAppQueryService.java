package com.everyschool.userservice.api.app.service.user;

import com.everyschool.userservice.api.app.controller.user.response.*;
import com.everyschool.userservice.api.app.controller.user.response.info.School;
import com.everyschool.userservice.api.app.controller.user.response.info.SchoolClass;
import com.everyschool.userservice.api.client.school.SchoolServiceClient;
import com.everyschool.userservice.api.client.school.response.DescendantInfo;
import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import com.everyschool.userservice.api.client.school.response.StudentInfo;
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
import static com.everyschool.userservice.error.ErrorMessage.NO_SUCH_USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAppQueryService {

    private final UserRepository userRepository;
    private final StudentParentAppQueryRepository studentParentAppQueryRepository;
    private final SchoolServiceClient schoolServiceClient;

    public StudentInfoResponse searchStudentInfo(String userKey) {
        User user = getUserByUserKey(userKey);
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException(UNAUTHORIZED_USER.getMessage());
        }
        Student student = (Student) user;

        if (student.getSchoolClassId() == null) {
            boolean result = schoolServiceClient.existApply(student.getId());
            if (result) {
                //승인 X
                School school = School.builder()
                    .schoolId(null)
                    .name("미승인")
                    .build();
                return StudentInfoResponse.builder()
                    .userType(student.getUserCodeId())
                    .email(student.getEmail())
                    .name(student.getName())
                    .birth(student.getBirth())
                    .school(school)
                    .schoolClass(null)
                    .joinDate(student.getCreatedDate())
                    .build();
            }
            School school = School.builder()
                .schoolId(null)
                .name("미신청")
                .build();
            return StudentInfoResponse.builder()
                .userType(student.getUserCodeId())
                .email(student.getEmail())
                .name(student.getName())
                .birth(student.getBirth())
                .school(school)
                .schoolClass(null)
                .joinDate(student.getCreatedDate())
                .build();
        }

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
        User user = getUserByUserKey(userKey);
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
        User user = getUserByUserKey(userKey);
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

    /**
     * 담임 선생님 연락처 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @return 조회된 담임 선생님 연락처
     */
    public TeacherContactInfoResponse searchContactInfo(String userKey, int schoolYear) {
        User user = getUserByUserKey(userKey);

        Long teacherId = schoolServiceClient.searchTeacherByUserId(user.getId(), schoolYear);

        User teacher = getUserById(teacherId);

        return TeacherContactInfoResponse.of(teacher);
    }

    public List<StudentContactInfoResponse> searchContactInfos(Integer schoolYear, String userKey) {
        User findUser = getUserByUserKey(userKey);

        List<StudentInfo> infos = schoolServiceClient.searchStudentsByUserId(findUser.getId(), schoolYear);

        Map<Long, Integer> map = infos.stream()
            .collect(Collectors.toMap(StudentInfo::getUserId, StudentInfo::getStudentNumber, (a, b) -> b));

        List<Long> temp = new ArrayList<>(map.keySet());
        List<User> students = userRepository.findByIdIn(temp);

        List<StudentContactInfoResponse> responses = new ArrayList<>();
        for (User user : students) {
            if (!(user instanceof Student)) {
                throw new IllegalArgumentException();
            }
            Student student = (Student) user;

            StudentContactInfoResponse response = StudentContactInfoResponse.builder()
                .userKey(student.getUserKey())
                .name(student.getName())
                .studentNumber(map.get(student.getId()))
                .build();

            responses.add(response);

            List<Parent> parents = studentParentAppQueryRepository.findByStudentId(student.getId());
            for (Parent parent : parents) {
                StudentContactInfoResponse.Parent parentResponse = StudentContactInfoResponse.Parent.builder()
                    .parentKey(parent.getUserKey())
                    .name(parent.getName())
                    .parentType(parent.getParentType())
                    .build();
                response.getParents().add(parentResponse);
            }
        }

        return responses;
    }

    /**
     * 회원 고유키로 회원 엔티티 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 회원 엔티티
     */
    private User getUserByUserKey(String userKey) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        return findUser.get();
    }

    /**
     * 회원 아이디로 회원 엔티티 조회
     *
     * @param userId 회원 아이디
     * @return 조회된 회원 엔티티
     */
    public User getUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        return findUser.get();
    }
}
