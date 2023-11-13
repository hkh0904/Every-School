package com.everyschool.consultservice.api.app.service.consult;

import com.everyschool.consultservice.api.app.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.app.service.consult.dto.CreateConsultDto;
import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.StudentSchoolClassInfo;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.domain.consult.Title;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.everyschool.consultservice.api.app.service.consult.InformationGenerator.*;

/**
 * 앱 상담 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional
public class ConsultAppService {

    private final ConsultRepository consultRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    /**
     * 상담 등록
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @param schoolId   학교 아이디
     * @param dto        상담 등록 정보
     * @return 등록된 상담 정보
     */
    public CreateConsultResponse createConsult(String userKey, int schoolYear, Long schoolId, CreateConsultDto dto) {

        //학부모 정보 조회
        UserInfo parentInfo = userServiceClient.searchUserInfo(userKey);

        //학생 정보 조회
        UserInfo studentInfo = userServiceClient.searchUserInfo(dto.getStudentKey());

        //담임 정보 조회
        UserInfo teacherInfo = userServiceClient.searchUserInfo(dto.getTeacherKey());

        //학급 정보 조회
        StudentSchoolClassInfo studentSchoolClassInfo = schoolServiceClient.searchByUserId(studentInfo.getUserId());

        Title title = createTitle(studentInfo, parentInfo, teacherInfo, studentSchoolClassInfo);

        Consult consult = Consult.builder()
            .consultDateTime(dto.getConsultDateTime())
            .message(dto.getMessage())
            .title(title)
            .schoolYear(schoolYear)
            .progressStatusId(ProgressStatus.WAIT.getCode())
            .typeId(dto.getTypeId())
            .schoolId(schoolId)
            .parentId(parentInfo.getUserId())
            .studentId(studentInfo.getUserId())
            .teacherId(teacherInfo.getUserId())
            .build();

        Consult savedConsult = consultRepository.save(consult);

        return CreateConsultResponse.of(savedConsult);
    }

    /**
     * 제목 정보 생성
     *
     * @param studentInfo 학생 정보
     * @param parentInfo  학부모 정보
     * @param teacherInfo 교직원 정보
     * @param info        학급 정보
     * @return 생성된 제목 정보
     */
    private Title createTitle(UserInfo studentInfo, UserInfo parentInfo, UserInfo teacherInfo, StudentSchoolClassInfo info) {
        String parentTitle = createParentInfo(info, studentInfo.getUserName(), parentInfo.getUserType());
        String teacherTitle = createTeacherInfo(info, teacherInfo.getUserName());

        return Title.builder()
            .parentTitle(parentTitle)
            .teacherTitle(teacherTitle)
            .build();
    }
}
