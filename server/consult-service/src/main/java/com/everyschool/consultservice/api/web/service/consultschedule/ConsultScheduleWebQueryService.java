package com.everyschool.consultservice.api.web.service.consultschedule;

import com.everyschool.consultservice.api.client.UserServiceClient;
import com.everyschool.consultservice.api.client.response.UserInfo;
import com.everyschool.consultservice.api.web.controller.consultschedule.response.ConsultScheduleResponse;
import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import com.everyschool.consultservice.domain.consultschedule.repository.ConsultScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.everyschool.consultservice.error.ErrorMessage.NO_SUCH_CONSULT_SCHEDULE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ConsultScheduleWebQueryService {

    private final ConsultScheduleRepository consultScheduleRepository;
    private final UserServiceClient userServiceClient;

    public ConsultScheduleResponse searchMyConsultSchedule(String userKey) {
        UserInfo userInfo = userServiceClient.searchUserInfo(userKey);

        Optional<ConsultSchedule> findConsultSchedule = consultScheduleRepository.findByTeacherId(userInfo.getUserId());
        if (findConsultSchedule.isEmpty()) {
            throw new NoSuchElementException(NO_SUCH_CONSULT_SCHEDULE.getMessage());
        }
        ConsultSchedule consultSchedule = findConsultSchedule.get();

        return ConsultScheduleResponse.of(consultSchedule);
    }

}
