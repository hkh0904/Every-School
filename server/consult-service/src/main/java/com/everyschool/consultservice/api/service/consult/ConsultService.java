package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.controller.consult.response.ApproveConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.CreateConsultResponse;
import com.everyschool.consultservice.api.controller.consult.response.RejectConsultResponse;
import com.everyschool.consultservice.api.service.consult.dto.CreateConsultDto;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.Title;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.*;

@RequiredArgsConstructor
@Service
@Transactional
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    public CreateConsultResponse createConsult(String userKey, Long schoolId, CreateConsultDto dto) {
        UserInfo parentInfo = userServiceClient.searchUserInfo(userKey);
        UserInfo studentInfo = userServiceClient.searchUserInfo(dto.getStudentKey());
        UserInfo teacherInfo = userServiceClient.searchUserInfo(dto.getTeacherKey());

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchSchoolClassByTeacherId(teacherInfo.getUserId());

        Title title = Title.builder()
            .parentTitle(String.format("%d학년 %d반 %s 선생님", schoolClassInfo.getGrade(), schoolClassInfo.getClassNum(), teacherInfo.getUserName()))
            .teacherTitle(String.format("%d학년 %d반 %s(%s) %s", schoolClassInfo.getGrade(), schoolClassInfo.getClassNum(),
                studentInfo.getUserName(), parentInfo.getUserType() == 'M' ? "부" : "모", parentInfo.getUserName()))
            .build();

        Consult consult = Consult.builder()
            .consultDateTime(dto.getConsultDateTime())
            .message(dto.getMessage())
            .title(title)
            .schoolYear(dto.getSchoolYear())
            .progressStatusId(3001)
            .typeId(dto.getTypeId())
            .schoolId(schoolId)
            .parentId(parentInfo.getUserId())
            .studentId(studentInfo.getUserId())
            .teacherId(teacherInfo.getUserId())
            .build();

        Consult savedConsult = consultRepository.save(consult);

        return CreateConsultResponse.of(savedConsult, teacherInfo, schoolClassInfo);
    }

    public ApproveConsultResponse approveConsult(Long consultId) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }
        Consult consult = findConsult.get();

        consult.approval();

        return ApproveConsultResponse.of(consult);
    }

    public RejectConsultResponse rejectConsult(Long consultId, String rejectedReason) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT.getMessage());
        }
        Consult consult = findConsult.get();

        consult.reject(rejectedReason);

        return RejectConsultResponse.of(consult);
    }
}
