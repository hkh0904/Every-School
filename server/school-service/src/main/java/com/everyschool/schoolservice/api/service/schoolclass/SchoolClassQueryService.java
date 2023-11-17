package com.everyschool.schoolservice.api.service.schoolclass;

import com.everyschool.schoolservice.api.web.controller.client.response.SchoolClassInfo;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassQueryAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolClassQueryService {

    private final SchoolClassQueryAppRepository schoolClassQueryRepository;

    public SchoolClassInfo searchBySchoolClassId(Long schoolClassId) {
        return schoolClassQueryRepository.findInfoById(schoolClassId);
    }

    public Long searchTeacherId(Long schoolClassId) {
        return schoolClassQueryRepository.findTeacherById(schoolClassId);
    }
}
