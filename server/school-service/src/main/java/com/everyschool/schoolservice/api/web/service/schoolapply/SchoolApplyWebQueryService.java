package com.everyschool.schoolservice.api.web.service.schoolapply;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.client.response.UserResponse;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.SchoolApplyDetailResponse;
import com.everyschool.schoolservice.api.web.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyWebQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everyschool.schoolservice.error.ErrorMessage.*;

/**
 * 웹 학교 신청 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolApplyWebQueryService {

    private final SchoolApplyRepository schoolApplyRepository;
    private final SchoolApplyWebQueryRepository schoolApplyWebQueryRepository;
    private final UserServiceClient userServiceClient;

    /**
     * 승인 대기 중인 신청 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @return 조회된 신청 목록
     */
    public List<SchoolApplyResponse> searchWaitSchoolApply(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<SchoolApply> schoolApplies = schoolApplyWebQueryRepository.findWaitSchoolApply(userInfo.getSchoolClassId(), schoolYear);

        return schoolApplies.stream()
            .map(SchoolApplyResponse::of)
            .collect(Collectors.toList());
    }

    /**
     * 승인된 신청 목록 조회
     *
     * @param userKey    회원 고유키
     * @param schoolYear 학년도
     * @return 조회된 신청 목록
     */
    public List<SchoolApplyResponse> searchApproveSchoolApply(String userKey, Integer schoolYear) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<SchoolApply> schoolApplies = schoolApplyWebQueryRepository.findApproveSchoolApply(userInfo.getSchoolClassId(), schoolYear);

        return schoolApplies.stream()
            .map(SchoolApplyResponse::of)
            .collect(Collectors.toList());
    }

    /**
     * 신청 내역 상세 조회
     *
     * @param schoolApplyId 신청 아이디
     * @return 조회된 신청 내역
     */
    public SchoolApplyDetailResponse searchSchoolApply(Long schoolApplyId) {
        SchoolApply schoolApply = getSchoolApply(schoolApplyId);

        UserResponse student = userServiceClient.searchUserById(schoolApply.getStudentId());
        UserResponse parent = null;
        if (schoolApply.getParentId() != null) {
            parent = userServiceClient.searchUserById(schoolApply.getParentId());
        }

        return SchoolApplyDetailResponse.builder()
            .schoolApplyId(schoolApply.getId())
            .applyType(schoolApply.getCodeId())
            .parentName(parent == null ? null : parent.getName())
            .parentBirth(parent == null ? null : parent.getBirth())
            .studentInfo(schoolApply.getStudentInfo())
            .studentBirth(student.getBirth())
            .lastModifiedDate(schoolApply.getLastModifiedDate())
            .build();
    }

    private SchoolApply getSchoolApply(Long schoolApplyId) {
        Optional<SchoolApply> findSchoolApply = schoolApplyRepository.findById(schoolApplyId);
        if (findSchoolApply.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_SCHOOL_APPLY.getMessage());
        }
        return findSchoolApply.get();
    }
}
