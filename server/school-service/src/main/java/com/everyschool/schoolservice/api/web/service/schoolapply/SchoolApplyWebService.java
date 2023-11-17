package com.everyschool.schoolservice.api.web.service.schoolapply;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.web.controller.client.response.StudentInfo;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.EditSchoolApplyResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schooluser.SchoolUser;
import com.everyschool.schoolservice.domain.schooluser.UserType;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserQueryRepository;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserRepository;
import com.everyschool.schoolservice.messagequeue.KafkaProducer;
import com.everyschool.schoolservice.messagequeue.dto.CreateStudentParentDto;
import com.everyschool.schoolservice.messagequeue.dto.EditStudentClassInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.error.ErrorMessage.NO_SUCH_SCHOOL_APPLY;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class SchoolApplyWebService {

    private final SchoolApplyRepository schoolApplyRepository;
    private final SchoolUserRepository schoolUserRepository;
    private final SchoolUserQueryRepository schoolUserQueryRepository;
    private final UserServiceClient userServiceClient;
    private final KafkaProducer kafkaProducer;

    public EditSchoolApplyResponse approveSchoolApply(Long schoolApplyId) {
        SchoolApply schoolApply = getSchoolApplyEntity(schoolApplyId);

        SchoolApply approvedschoolApply = schoolApply.approve();

        if (schoolApply.getParentId() == null) {
            log.debug("[자녀 학급 신청]");
            kafkaProducer.editStudentClassInfo("edit-student-class-info", createEditStudentClassInfoDto(schoolApply));
            return EditSchoolApplyResponse.of(approvedschoolApply);
        }

        log.debug("[부모 자녀 연결] 부모 학급 신청임");
        kafkaProducer.createStudentParent("create-student-parent", createCreateStudentParentDto(schoolApply));

        saveParentToSchoolUser(schoolApply, approvedschoolApply);

        return EditSchoolApplyResponse.of(approvedschoolApply);
    }

    private void saveParentToSchoolUser(SchoolApply schoolApply, SchoolApply approvedschoolApply) {
        StudentInfo studentInfo = schoolUserQueryRepository.findByUserId(schoolApply.getStudentId()).orElseThrow(()
                -> new NoSuchElementException("자녀가 학급에 등록되어있지 않습니다."));
        UserInfo parentUserInfo = userServiceClient.searchUserInfoById(approvedschoolApply.getParentId());

        SchoolUser parentSchoolUser = SchoolUser.builder()
                .studentNumber(studentInfo.getStudentNum())
                .userName(parentUserInfo.getUserName())
                .schoolYear(approvedschoolApply.getSchoolYear())
                .userTypeId(parentUserInfo.getUserType() == 'M' ? UserType.FATHER.getCode() : UserType.MOTHER.getCode())
                .userId(parentUserInfo.getUserId())
                .school(approvedschoolApply.getSchoolClass().getSchool())
                .schoolClass(approvedschoolApply.getSchoolClass())
                .build();
        schoolUserRepository.save(parentSchoolUser);
    }

    public EditSchoolApplyResponse rejectSchoolApply(Long schoolApplyId, String rejectedReason) {
        SchoolApply schoolApply = getSchoolApplyEntity(schoolApplyId);

        SchoolApply rejectedschoolApply = schoolApply.reject(rejectedReason);

        return EditSchoolApplyResponse.of(rejectedschoolApply);
    }

    private SchoolApply getSchoolApplyEntity(Long schoolApplyId) {
        Optional<SchoolApply> findSchoolApply = schoolApplyRepository.findById(schoolApplyId);
        if (findSchoolApply.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_SCHOOL_APPLY.getMessage());
        }
        return findSchoolApply.get();
    }

    private static EditStudentClassInfoDto createEditStudentClassInfoDto(SchoolApply schoolApply) {
        return EditStudentClassInfoDto.builder()
                .studentId(schoolApply.getStudentId())
                .schoolId(schoolApply.getSchoolClass().getSchool().getId())
                .schoolClassId(schoolApply.getSchoolClass().getId())
                .build();
    }

    private static CreateStudentParentDto createCreateStudentParentDto(SchoolApply schoolApply) {
        return CreateStudentParentDto.builder()
                .studentId(schoolApply.getStudentId())
                .parentId(schoolApply.getParentId())
                .build();
    }
}
