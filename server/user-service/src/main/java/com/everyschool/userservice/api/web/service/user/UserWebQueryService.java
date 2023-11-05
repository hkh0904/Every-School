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

import static com.everyschool.userservice.error.ErrorMessage.UNAUTHORIZED_USER;
import static com.everyschool.userservice.error.ErrorMessage.UNREGISTERED_USER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserWebQueryService {

    private final UserRepository userRepository;
    private final SchoolServiceClient schoolServiceClient;

    public UserInfoResponse searchUserInfo(String userKey) {
        Optional<User> findUser = userRepository.findByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_USER.getMessage());
        }
        User user = findUser.get();

        if (!(user instanceof Teacher)) {
            throw new IllegalArgumentException(UNAUTHORIZED_USER.getMessage());
        }

        Teacher teacher = (Teacher) user;

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchBySchoolClassId(teacher.getSchoolClassId());

        UserInfoResponse.School school = UserInfoResponse.School.builder()
            .schoolId(teacher.getSchoolId())
            .name(schoolClassInfo.getSchoolName())
            .build();

        UserInfoResponse.SchoolClass schoolClass = UserInfoResponse.SchoolClass.builder()
            .schoolClassId(teacher.getSchoolClassId())
            .grade(schoolClassInfo.getGrade())
            .classNum(schoolClassInfo.getClassNum())
            .build();

        return UserInfoResponse.builder()
            .userType(teacher.getUserCodeId())
            .email(teacher.getEmail())
            .name(teacher.getName())
            .birth(teacher.getBirth())
            .school(school)
            .schoolClass(schoolClass)
            .joinDate(teacher.getCreatedDate())
            .build();
    }
}
