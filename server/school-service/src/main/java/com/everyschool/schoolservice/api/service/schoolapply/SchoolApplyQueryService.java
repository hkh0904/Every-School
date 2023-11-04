package com.everyschool.schoolservice.api.service.schoolapply;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.client.response.UserInfo;
import com.everyschool.schoolservice.api.controller.schoolapply.response.SchoolApplyResponse;
import com.everyschool.schoolservice.domain.schoolapply.SchoolApply;
import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolApplyQueryService {

    private final SchoolApplyQueryRepository schoolApplyQueryRepository;
    private final UserServiceClient userServiceClient;

    public List<SchoolApplyResponse> searchSchoolApplies(String userKey, String status) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        List<SchoolApply> schoolApplies = schoolApplyQueryRepository.findByTeacherId(userInfo.getUserId(), status);

        List<Long> studentIds = schoolApplies.stream()
            .map(SchoolApply::getStudentId)
            .collect(Collectors.toList());

        List<StudentResponse> studentInfos = userServiceClient.searchByStudentIdIn(studentIds);

        Map<Long, StudentResponse> map = studentInfos.stream()
            .collect(Collectors.toMap(StudentResponse::getStudentId, studentInfo -> studentInfo, (a, b) -> b));

        List<SchoolApplyResponse> responses = schoolApplies.stream()
            .map(schoolApply -> SchoolApplyResponse.of(schoolApply, map.get(schoolApply.getStudentId())))
            .collect(Collectors.toList());

        return responses;
    }

    public SchoolApplyResponse searchSchoolApply(Long schoolApplyId) {
        Optional<SchoolApply> findSchoolApply = schoolApplyQueryRepository.findById(schoolApplyId);
        if (findSchoolApply.isEmpty()) {
            throw new NoSuchElementException();
        }
        SchoolApply schoolApply = findSchoolApply.get();

        List<StudentResponse> studentInfos = userServiceClient.searchByStudentIdIn(List.of(schoolApply.getStudentId()));

        return SchoolApplyResponse.of(schoolApply, studentInfos.get(0));
    }
}
