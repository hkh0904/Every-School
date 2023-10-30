package com.everyschool.consultservice.api.service.consult;

import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.SchoolClassInfo;
import com.everyschool.consultservice.api.client.response.TeacherInfo;
import com.everyschool.consultservice.api.controller.consult.response.ConsultDetailResponse;
import com.everyschool.consultservice.api.controller.consult.response.ConsultResponse;
import com.everyschool.consultservice.domain.consult.Consult;
import com.everyschool.consultservice.domain.consult.repository.ConsultQueryRepository;
import com.everyschool.consultservice.domain.consult.repository.ConsultRepository;
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
public class ConsultQueryService {

    private final ConsultRepository consultRepository;
    private final ConsultQueryRepository consultQueryRepository;
    private final UserServiceClient userServiceClient;
    private final SchoolServiceClient schoolServiceClient;

    public List<ConsultResponse> searchConsults(String userKey) {
        Long parentId = userServiceClient.searchByUserKey(userKey);

        List<Consult> findConsults = consultQueryRepository.findByParentId(parentId);

        List<Long> teacherIds = findConsults.stream()
            .map(Consult::getTeacherId)
            .collect(Collectors.toList());

        List<TeacherInfo> teacherInfos = userServiceClient.searchTeacherByIdIn(teacherIds);
        Map<Long, TeacherInfo> teacherInfoMap = teacherInfos.stream()
            .collect(Collectors.toMap(TeacherInfo::getUserId, teacherInfo -> teacherInfo, (a, b) -> b));

        List<SchoolClassInfo> schoolClassInfos = schoolServiceClient.searchSchoolClassByTeacherIdIn(teacherIds);
        Map<Long, SchoolClassInfo> schoolClassInfoMap = schoolClassInfos.stream()
            .collect(Collectors.toMap(SchoolClassInfo::getTeacherId, schoolClassInfo -> schoolClassInfo, (a, b) -> b));

        return findConsults.stream()
            .map(consult -> ConsultResponse.of(consult,
                teacherInfoMap.get(consult.getTeacherId()),
                schoolClassInfoMap.get(consult.getTeacherId())))
            .collect(Collectors.toList());
    }

    public ConsultDetailResponse searchConsult(Long consultId) {
        Optional<Consult> findConsult = consultRepository.findById(consultId);
        if (findConsult.isEmpty()) {
            throw new NoSuchElementException();
        }
        Consult consult = findConsult.get();

        TeacherInfo teacherInfo = userServiceClient.searchTeacherById(consult.getTeacherId());

        SchoolClassInfo schoolClassInfo = schoolServiceClient.searchSchoolClassByTeacherId(consult.getTeacherId());

        return ConsultDetailResponse.of(consult, teacherInfo, schoolClassInfo);
    }
}
