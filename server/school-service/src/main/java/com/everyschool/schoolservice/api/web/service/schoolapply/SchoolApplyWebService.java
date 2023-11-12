package com.everyschool.schoolservice.api.web.service.schoolapply;

import com.everyschool.schoolservice.api.web.controller.schoolapply.response.EditSchoolApplyResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.error.ErrorMessage.NO_SUCH_SCHOOL_APPLY;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolApplyWebService {

    private final SchoolApplyRepository schoolApplyRepository;

    public EditSchoolApplyResponse approveSchoolApply(Long schoolApplyId) {
        SchoolApply schoolApply = getSchoolApplyEntity(schoolApplyId);

        SchoolApply approvedschoolApply = schoolApply.approve();

        return EditSchoolApplyResponse.of(approvedschoolApply);
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
}
