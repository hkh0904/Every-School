package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.TeacherInfo;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.service.consult.dto.CreateConsultDto;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    public CreateConsultResponse createConsult(String userKey, Long schoolId, CreateConsultDto dto) {
        Long parentId = userServiceClient.searchByUserKey(userKey);
        Long studentId = userServiceClient.searchByUserKey(dto.getStudentKey());
        TeacherInfo teacherInfo = userServiceClient.searchTeacherByUserKey(dto.getTeacherKey());

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchSchoolClassByTeacherId(teacherInfo.getUserId());

        Consult consult = Consult.builder()
            .consultDateTime(dto.getConsultDateTime())
            .message(dto.getMessage())
            .schoolYear(dto.getSchoolYear())
            .progressStatusId(3001)
            .typeId(dto.getTypeId())
            .schoolId(schoolId)
            .parentId(parentId)
            .studentId(studentId)
            .teacherId(teacherInfo.getUserId())
            .build();
        Consult savedConsult = consultRepository.save(consult);

        return CreateConsultResponse.of(savedConsult, teacherInfo, schoolClassInfo);
    }
}
