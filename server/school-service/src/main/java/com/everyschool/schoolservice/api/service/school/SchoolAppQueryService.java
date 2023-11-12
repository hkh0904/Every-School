package com.everyschool.schoolservice.api.service.school;

import com.everyschool.schoolservice.api.controller.school.response.SchoolDetailResponse;
import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.repository.SchoolAppQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.schoolservice.error.ErrorMessage.NO_SUCH_SCHOOL;

/**
 * 앱 학교 조회용 서비스
 *
 * @author 임우택
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolAppQueryService {

    private final SchoolAppQueryRepository schoolQueryRepository;

    /**
     * 쿼리가 포함된 학교 목록 조회
     *
     * @param query 조회할 학교 쿼리
     * @return 조회된 학교 목록
     */
    public List<SchoolResponse> searchSchools(String query) {
        return schoolQueryRepository.findByNameLike(query);
    }

    /**
     * 학교 상세 정보 조회
     *
     * @param schoolId 학교 아이디
     * @return 조회된 학교 상세 정보
     */
    public SchoolDetailResponse searchSchoolInfo(Long schoolId) {
        Optional<SchoolDetailResponse> response = schoolQueryRepository.findById(schoolId);
        if (response.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_SCHOOL.getMessage());
        }
        return response.get();
    }
}
