package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.client.response.StudentParentInfo;
import com.everyschool.userservice.domain.user.repository.StudentParentQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StudentParentQueryService {

    private final StudentParentQueryRepository studentParentQueryRepository;

    public List<StudentParentInfo> searchStudentParentBySchoolClassId(Long schoolClassId) {
        return studentParentQueryRepository.findBySchoolClassId(schoolClassId);
    }
}
