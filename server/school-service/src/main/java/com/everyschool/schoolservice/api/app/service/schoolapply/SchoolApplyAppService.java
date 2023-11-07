package com.everyschool.schoolservice.api.app.service.schoolapply;

import com.everyschool.schoolservice.api.app.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.app.service.schoolapply.dto.CreateSchoolApplyDto;
import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.domain.schoolapply.ApplyType;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.domain.schoolapply.ApplyType.*;
import static com.everyschool.schoolservice.error.ErrorMessage.UNREGISTERED_SCHOOL_CLASS;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolApplyAppService {

    private final SchoolApplyRepository schoolApplyRepository;
    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final UserServiceClient userServiceClient;

    public CreateSchoolApplyResponse createSchoolApply(String userKey, Long schoolId, Integer schoolYear, CreateSchoolApplyDto dto) {
        Optional<SchoolClass> findSchoolClass = schoolClassQueryRepository.findByInfo(schoolId, dto.getSchoolYear(), dto.getGrade(), dto.getClassNum());
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException(UNREGISTERED_SCHOOL_CLASS.getMessage());
        }
        SchoolClass schoolClass = findSchoolClass.get();

        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        SchoolApply schoolApply = SchoolApply.builder()
            .schoolYear(schoolYear)
            .codeId(STUDENT.getCode())
            .studentId(userInfo.getUserId())
            .parentId(null)
            .schoolClass(schoolClass)
            .build();

        SchoolApply savedSchoolApply = schoolApplyRepository.save(schoolApply);

        return CreateSchoolApplyResponse.of(savedSchoolApply);
    }
}
