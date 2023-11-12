package com.everyschool.consultservice.api.app.service.consultschedule;

import com.everyschool.consultservice.api.app.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.client.SchoolServiceClient;
import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import com.everyschool.consultservice.domain.consultschedule.repository.ConsultScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultScheduleAppQueryService {

    private final ConsultScheduleRepository consultScheduleRepository;
    private final SchoolServiceClient schoolServiceClient;

    public ConsultScheduleResponse searchTeacherConsultSchedule(Long schoolClassId) {
        //교직원 정보 조회
        Long teacherId = schoolServiceClient.searchTeacherId(schoolClassId);

        //상담 스케줄 조회
        ConsultSchedule consultSchedule = getConsultScheduleByTeacherId(teacherId);

        return ConsultScheduleResponse.of(consultSchedule);
    }

    private ConsultSchedule getConsultScheduleByTeacherId(Long teacherId) {
        Optional<ConsultSchedule> findConsultSchedule = consultScheduleRepository.findByTeacherId(teacherId);
        if (findConsultSchedule.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT_SCHEDULE.getMessage());
        }
        return findConsultSchedule.get();
    }
}
