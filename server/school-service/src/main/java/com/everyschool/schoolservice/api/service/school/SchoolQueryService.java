package com.everyschool.schoolservice.api.service.school;

import com.everyschool.schoolservice.api.controller.school.response.SchoolResponse;
import com.everyschool.schoolservice.domain.school.repository.SchoolQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolQueryService {

    private final SchoolQueryRepository schoolQueryRepository;

    public List<SchoolResponse> searchSchools(String search) {
        return schoolQueryRepository.findByName(search);
    }
}
