package com.everyschool.consultservice.api.web.service.consultschedule;

import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.api.web.service.consultschedule.dto.EditScheduleDto;
import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import com.everyschool.consultservice.domain.consultschedule.repository.ConsultScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.NO_SUCH_CONSULT_SCHEDULE;

@RequiredArgsConstructor
@Service
@Transactional
public class ConsultScheduleWebService {

    private final ConsultScheduleRepository consultScheduleRepository;
    private final UserServiceClient userServiceClient;

    public ConsultScheduleResponse editDescription(Long consultScheduleId, String description) {
        ConsultSchedule consultSchedule = getConsultSchedule(consultScheduleId);

        ConsultSchedule editedConsultSchedule = consultSchedule.editDescription(description);

        return ConsultScheduleResponse.of(editedConsultSchedule);
    }

    public ConsultScheduleResponse editSchedule(Long consultScheduleId, EditScheduleDto dto) {
        ConsultSchedule consultSchedule = getConsultSchedule(consultScheduleId);

        ConsultSchedule editedConsultSchedule = consultSchedule.editSchedule(change(dto.getMonday()), change(dto.getTuesday()), change(dto.getWednesday()), change(dto.getThursday()), change(dto.getFriday()));

        return ConsultScheduleResponse.of(editedConsultSchedule);
    }

    private ConsultSchedule getConsultSchedule(Long consultScheduleId) {
        Optional<ConsultSchedule> findConsultSchedule = consultScheduleRepository.findById(consultScheduleId);
        if (findConsultSchedule.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT_SCHEDULE.getMessage());
        }
        return findConsultSchedule.get();
    }

    private String change(List<Boolean> times) {
        StringBuilder builder = new StringBuilder();
        for (Boolean time : times) {
            builder.append(time ? "1" : "0");
        }
        return String.valueOf(builder);
    }
}
