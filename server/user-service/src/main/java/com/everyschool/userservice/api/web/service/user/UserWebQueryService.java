package com.everyschool.userservice.api.web.service.user;

import com.everyschool.userservice.api.client.school.SchoolServiceClient;
import com.everyschool.userservice.api.client.school.response.SchoolClassInfo;
import com.everyschool.userservice.api.web.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.domain.user.Teacher;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.userservice.api.web.controller.user.response.UserInfoResponse.*;
import static com.everyschool.userservice.message.ErrorMessage.*;

/**
 * 회원 웹 조회 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserWebQueryService {

    private final UserRepository userRepository;
    private final SchoolServiceClient schoolServiceClient;

    /**
     * 교직원 회원 정보 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 교직원 회원 정보
     */
    public UserInfoResponse searchUserInfo(String userKey) {
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

        return of(teacher, school, schoolClass);
    }

    /**
     * 회원 고유키로 회원 엔티티 조회
     *
     * @param userKey 회원 고유키
     * @return 조회된 회원 엔티티
     * @throws NoSuchElementException 등록된 회원이 아닌 경우 발생
     */
    private User getUserByUserKey(String userKey) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_USER.getMessage());
        }
        return findUser.get();
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
}
