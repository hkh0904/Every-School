package com.everyschool.schoolservice.api.service.schooluser;

import com.everyschool.schoolservice.api.client.UserServiceClient;
import com.everyschool.schoolservice.api.client.response.StudentResponse;
import com.everyschool.schoolservice.api.controller.schooluser.response.MyClassStudentResponse;
import com.everyschool.schoolservice.api.service.schooluser.dto.SearchMyClassStudentDto;
import com.everyschool.schoolservice.domain.schoolclass.SchoolClass;
import com.everyschool.schoolservice.domain.schoolclass.repository.SchoolClassRepository;
import com.everyschool.schoolservice.domain.schooluser.repository.SchoolUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SchoolUserQueryService {

    private final SchoolUserQueryRepository schoolUserQueryRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final UserServiceClient userServiceClient;

    public List<MyClassStudentResponse> searchMyClassStudents(String userKey, Integer schoolYear) {
        Long teacherId = userServiceClient.searchByUserKey(userKey);

        Optional<SchoolClass> findSchoolClass = schoolClassRepository.findByTeacherIdAndSchoolYear(teacherId, schoolYear);
        if (findSchoolClass.isEmpty()) {
            throw new NoSuchElementException();
        }
        SchoolClass schoolClass = findSchoolClass.get();

        List<SearchMyClassStudentDto> searchMyClassStudentDtos = schoolUserQueryRepository.findBySchoolClassId(schoolClass.getId());

        List<Long> studentIds = searchMyClassStudentDtos.stream()
            .map(SearchMyClassStudentDto::getStudentId)
            .collect(Collectors.toList());

        List<StudentResponse> students = userServiceClient.searchByStudentIdIn(studentIds);

        Map<Long, SearchMyClassStudentDto> map = searchMyClassStudentDtos.stream()
            .collect(Collectors.toMap(SearchMyClassStudentDto::getStudentId, searchMyClassStudentDto -> searchMyClassStudentDto, (a, b) -> b));


        List<MyClassStudentResponse> responses = new ArrayList<>();
        for (StudentResponse student : students) {
            MyClassStudentResponse response = MyClassStudentResponse.builder()
                .userId(student.getStudentId())
                .studentId(String.format("%d%02d%02d", schoolClass.getGrade(), schoolClass.getClassNum(),  map.get(student.getStudentId()).getStudentNum()))
                .name(student.getName())
                .birth(student.getBirth())
                .build();
            responses.add(response);
        }

        return responses;
    }
}
