package com.everyschool.consultservice.api.app.service.consult;

import com.everyschool.consultservice.api.app.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.app.service.consult.dto.CreateConsultDto;
import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.ProgressStatus;
import com.everyschool.consultservice.domain.consult.Title;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Transactional
public class ConsultAppService {

    private final ConsultRepository consultRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    public CreateConsultResponse createConsult(String userKey, Integer schoolYear, Long schoolId, CreateConsultDto dto) {

        //학부모 정보 조회
        UserInfo parentInfo = userServiceClient.searchUserInfo(userKey);

        //학생 정보 조회
        UserInfo studentInfo = userServiceClient.searchUserInfo(dto.getStudentKey());

        //담임 정보 조회
        UserInfo teacherInfo = userServiceClient.searchUserInfo(dto.getTeacherKey());

        Integer studentNumber = schoolServiceClient.searchStudentNumber(studentInfo.getUserId(), schoolYear);

        int grade = studentNumber / 10000;
        int classNum = studentNumber / 100 % 100;
        int num = studentNumber % 100;

        //제목
        Title title = Title.builder()
            .parentTitle(String.format("%d학년 %d반 %d번 %s 어머님", grade, classNum, num, studentInfo.getUserName()))
            .teacherTitle(String.format("%d학년 %d반 %s 선생님", grade, classNum, teacherInfo.getUserName()))
            .build();

        Consult consult = Consult.builder()
            .consultDateTime(LocalDateTime.now())
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
}
