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

import static com.everyschool.userservice.api.app.controller.user.response.ParentInfoResponse.*;
import static com.everyschool.userservice.message.ErrorMessage.*;

/**
 * 회원 앱 조회 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAppQueryService {

    private final UserRepository userRepository;
    private final StudentParentAppQueryRepository studentParentAppQueryRepository;
    private final SchoolServiceClient schoolServiceClient;

    /**
     * 학생 회원 정보 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 학생 회원 정보
     */
    public StudentInfoResponse searchStudentInfo(String userKey) {
        //회원 엔티티 조회
        User user = getUserByUserKey(userKey);

        //학생 엔티티로 변환
        Student student = convertToStudent(user);

        // TODO: 11/10/23 리팩토링 필수
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

        //학급 정보 조회
        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchBySchoolClassId(student.getSchoolClassId());

        //학교 정보 생성
        School school = School.of(student.getSchoolId(), schoolClassInfo.getSchoolName());

        //학급 정보 생성
        SchoolClass schoolClass = SchoolClass.of(student.getSchoolClassId(), schoolClassInfo);

        return StudentInfoResponse.of(student, school, schoolClass);
    }

    /**
     * 학부모 회원 정보 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 학부모 회원 정보
     */
    public ParentInfoResponse searchParentInfo(String userKey) {
        //회원 엔티티 조회
        User user = getUserByUserKey(userKey);

        //학부모 엔티티로 변환
        Parent parent = convertToParent(user);

        //학부모와 연결된 학생(자식) 엔티티 조회
        List<Student> students = studentParentAppQueryRepository.findByParentId(parent.getId());

        //학생 아이디 리스트로 변환
        List<Long> studentIds = students.stream()
            .map(Student::getId)
            .collect(Collectors.toList());

        //학생(자식)의 학급 정보 조회
        List<DescendantInfo> descendantInfos = schoolServiceClient.searchByUserId(studentIds);

        //key: 학생 아이디, value: 학생(자식) 학급 정보
        Map<Long, DescendantInfo> map = descendantInfos.stream()
            .collect(Collectors.toMap(DescendantInfo::getUserId, descendantInfo -> descendantInfo, (a, b) -> b));

        //학생(자식) 회원 정보 생성
        List<Descendant> descendants = new ArrayList<>();
        for (Student student : students) {
            //학교 정보 생성
            DescendantInfo descendantInfo = map.get(student.getId());
            School school = School.of(student.getSchoolId(), descendantInfo.getSchoolName());

            //학급 정보 생성
            SchoolClass schoolClass = SchoolClass.of(student.getSchoolClassId(), descendantInfo);

            //학생(자식) 회원 정보 생성
            Descendant descendant = Descendant.of(student, descendantInfo.getStudentNumber(), school, schoolClass);

            descendants.add(descendant);
        }

        return of(parent, descendants);
    }

    /**
     * 교직원 회원 정보 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 교직원 회원 정보
     */
    public TeacherInfoResponse searchTeacherInfo(String userKey) {
        //회원 엔티티 조회
        User user = getUserByUserKey(userKey);

        //교직원 엔티티로 변환
        Teacher teacher = convertToTeacher(user);

        //학급 정보 조회
        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchBySchoolClassId(teacher.getSchoolClassId());

        //학교 정보 생성
        School school = School.of(teacher.getSchoolId(), schoolClassInfo.getSchoolName());

        //학급 정보 생성
        SchoolClass schoolClass = SchoolClass.of(teacher.getSchoolClassId(), schoolClassInfo);

        return TeacherInfoResponse.of(teacher, school, schoolClass);
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

    /**
     * 학급 인원 연락처 목록 조회
     *
     * @param teacherKey 교직원 고유키
     * @param schoolYear 학년도
     * @return 조회된 학급 인원 연락처 목록
     */
    public List<StudentContactInfoResponse> searchContactInfos(String teacherKey, int schoolYear) {
        User teacher = getUserByUserKey(teacherKey);

        //내 학급의 학생 정보 조회
        List<StudentInfo> infos = schoolServiceClient.searchStudentsByUserId(teacher.getId(), schoolYear);

        //key: 회원(학생) 아이디, value: 학번
        Map<Long, Integer> map = infos.stream()
            .collect(Collectors.toMap(StudentInfo::getUserId, StudentInfo::getStudentNumber, (a, b) -> b));

        //회원(학생) 아이디를 리스트로 전환
        List<Long> temp = new ArrayList<>(map.keySet());

        //회원(학생) 아이디로 회원(학생) 엔티티 목록 조회
        List<User> students = userRepository.findByIdIn(temp);

        //학급 인원 연락처 목록 생성
        List<StudentContactInfoResponse> responses = new ArrayList<>();
        for (User user : students) {
            Student student = convertToStudent(user);

            //학생 연락처 생성
            Integer studentNumber = map.get(student.getId());
            StudentContactInfoResponse response = createStudentContactInfoResponse(student, studentNumber);
            responses.add(response);

            //학부모 연락처 생성
            List<Parent> parents = studentParentAppQueryRepository.findByStudentId(student.getId());
            for (Parent parent : parents) {
                StudentContactInfoResponse.Parent parentResponse = createStudentContactInfoResponseInnerParent(parent);
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
    private User getUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        return findUser.get();
    }

    /**
     * 회원 엔티티를 학생 엔티티로 변환
     *
     * @param user 회원 엔티티
     * @return 변환된 학생 엔티티
     * @throws IllegalArgumentException 학생 회원이 아닌 경우 발생
     */
    private Student convertToStudent(User user) {
        if (!(user instanceof Student)) {
            throw new IllegalArgumentException(NOT_STUDENT_USER.getMessage());
        }
        return (Student) user;
    }

    /**
     * 회원 엔티티를 학부모 엔티티로 변환
     *
     * @param user 회원 엔티티
     * @return 변환된 학부모 엔티티
     * @throws IllegalArgumentException 학부모 회원이 아닌 경우 발생
     */
    private Parent convertToParent(User user) {
        if (!(user instanceof Parent)) {
            throw new IllegalArgumentException(NOT_PARENT_USER.getMessage());
        }
        return (Parent) user;
    }

    /**
     * 회원 엔티티를 교직원 엔티티로 변환
     *
     * @param user 회원 엔티티
     * @return 변횐된 교직원 엔티티
     * @throws IllegalArgumentException 교직원 회원이 아닌 경우 발생
     */
    private Teacher convertToTeacher(User user) {
        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException(NOT_TEACHER_USER.getMessage());
        }
        return (Teacher) user;
    }

    /**
     * 학생 연락처 생성
     *
     * @param student       학생 엔티티
     * @param studentNumber 학번
     * @return 생성된 학생 연락처
     */
    private StudentContactInfoResponse createStudentContactInfoResponse(Student student, int studentNumber) {
        return StudentContactInfoResponse.builder()
            .userKey(student.getUserKey())
            .name(student.getName())
            .studentNumber(studentNumber)
            .build();
    }

    /**
     * 학부모 연락처 생성
     *
     * @param parent 학부모 엔티티
     * @return 생성된 학부모 연락처
     */
    private StudentContactInfoResponse.Parent createStudentContactInfoResponseInnerParent(Parent parent) {
        return StudentContactInfoResponse.Parent.builder()
            .parentKey(parent.getUserKey())
            .name(parent.getName())
            .parentType(parent.getParentType())
            .build();
    }
}
