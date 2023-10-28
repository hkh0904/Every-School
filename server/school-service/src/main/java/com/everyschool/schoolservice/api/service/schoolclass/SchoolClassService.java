package com.everyschool.schoolservice.api.service.schoolclass;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.controller.schoolclass.response.CreateSchoolClassResponse;
import com.everyschool.schoolservice.api.service.schoolclass.dto.CreateSchoolClassDto;
import com.everyschool.schoolservice.domain.school.School;
import com.everyschool.schoolservice.domain.school.repository.SchoolRepository;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassQueryRepository;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final SchoolRepository schoolRepository;
    private final UserServiceClient userServiceClient;

    public CreateSchoolClassResponse createSchoolClass(CreateSchoolClassDto dto) {
        Long teacherId = userServiceClient.searchByUserKey(dto.getUserKey());

        boolean isExistTeacher = schoolClassQueryRepository.existByTeacherIdAndSchoolYear(teacherId, dto.getSchoolYear());
        if (isExistTeacher) {
            throw new IllegalArgumentException("이미 담임으로 등록된 교사입니다.");
        }

        boolean isExistSchoolClass = schoolClassQueryRepository.existSchoolClass(dto.getSchoolId(), dto.getSchoolYear(), dto.getGrade(), dto.getClassNum());
        if (isExistSchoolClass) {
            throw new IllegalArgumentException("해당 학급이 존재합니다.");
        }

        Optional<School> findSchool = schoolRepository.findById(dto.getSchoolId());
        if (findSchool.isEmpty()) {
            throw new NoSuchElementException("등록되지 않은 학교입니다.");
        }
        School school = findSchool.get();

        int schoolTypeCodeId = school.getSchoolTypeCodeId();
        if (schoolTypeCodeId == 1001 && dto.getGrade() > 6) {
            throw new IllegalArgumentException("1 ~ 6학년만 등록할 수 있습니다.");
        }

        if ((schoolTypeCodeId == 1002 || schoolTypeCodeId == 1003) && dto.getGrade() > 3) {
            throw new IllegalArgumentException("1 ~ 3학년만 등록할 수 있습니다.");
        }

        SchoolClass schoolClass = SchoolClass.builder()
            .schoolYear(dto.getSchoolYear())
            .grade(dto.getGrade())
            .classNum(dto.getClassNum())
            .school(school)
            .teacherId(teacherId)
            .build();
        SchoolClass savedSchoolClass = schoolClassRepository.save(schoolClass);

        return CreateSchoolClassResponse.of(savedSchoolClass);
    }
}
