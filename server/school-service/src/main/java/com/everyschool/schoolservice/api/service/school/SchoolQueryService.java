package com.everyschool.schoolservice.api.service.school;

import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.repository.SchoolQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolQueryService {

    private final SchoolQueryRepository schoolQueryRepository;

    /**
     * 쿼리가 포함된 학교 목록 조회
     *
     * @param query 조회할 학교 쿼리
     * @return 조회된 학교 목록
     */
    public List<SchoolResponse> searchSchools(String query) {
        return schoolQueryRepository.findByName(query);
    }

    public SchoolResponse searchOneSchool(Long schoolId) {
        Optional<SchoolResponse> response = schoolQueryRepository.findById(schoolId);

        if(response.isEmpty()) {
            throw new NoSuchElementException("해당 학교는 없습니다.");
        }

        return response.get();
    }
}
