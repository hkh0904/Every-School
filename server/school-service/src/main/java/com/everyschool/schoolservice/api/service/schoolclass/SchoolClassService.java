package com.everyschool.schoolservice.api.service.schoolclass;

import com.everyschool.schoolservice.api.controller.schoolclass.response.CreateSchoolClassResponse;
import com.everyschool.schoolservice.api.service.schoolclass.dto.CreateSchoolClassDto;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public CreateSchoolClassResponse createSchoolClass(CreateSchoolClassDto dto) {
        return null;
    }
}
