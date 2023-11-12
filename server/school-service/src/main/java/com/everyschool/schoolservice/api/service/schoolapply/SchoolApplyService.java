package com.everyschool.schoolservice.api.service.schoolapply;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.controller.schoolapply.response.CreateSchoolApplyResponse;
import com.everyschool.schoolservice.api.service.schoolapply.dto.CreateSchoolApplyDto;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassQueryRepository;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.error.ErrorMessage.UNREGISTERED_SCHOOL_CLASS;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolApplyService {

    private final SchoolApplyRepository schoolApplyRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserServiceClient userServiceClient;

    public CreateSchoolApplyResponse createSchoolApply(Long schoolClassId, String userKey) {
        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findById(schoolClassId);
        if (findSchoolClass.isEmpty()) {
            throw new IllegalArgumentException();
        }
        SchoolClass schoolClass = findSchoolClass.get();

        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        SchoolApply schoolApply = SchoolApply.builder()
            .schoolYear(schoolClass.getSchoolYear())
            .codeId(2)
            .studentId(null)
            .parentId(userInfo.getUserId())
            .schoolClass(schoolClass)
            .build();
        SchoolApply savedSchoolApply = schoolApplyRepository.save(schoolApply);

        return CreateSchoolApplyResponse.of(savedSchoolApply);
    }

    public void createParentSchoolApply(Long parentId, Long studentId, Long schoolClassId) {
        // TODO: 11/11/23 구현 예정
    }
}
