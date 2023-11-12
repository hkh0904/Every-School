package com.everyschool.consultservice.domain.consultschedule.repository;

import com.everyschool.consultservice.IntegrationTestSupport;
import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ConsultScheduleRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ConsultScheduleRepository consultScheduleRepository;

    @DisplayName("교직원 아이디로 상담 스케줄을 조회한다.")
    @Test
    void findByTeacherId() {
        //given
        ConsultSchedule consultSchedule = saveConsultSchedule();

        //when
        Optional<ConsultSchedule> findConsultSchedule = consultScheduleRepository.findByTeacherId(1L);

        //then
        assertThat(findConsultSchedule).isPresent();
    }

    private ConsultSchedule saveConsultSchedule() {
        ConsultSchedule consultSchedule = ConsultSchedule.builder()
            .teacherId(1L)
            .description("description")
            .monday("11111111")
            .tuesday("11111111")
            .wednesday("11111111")
            .thursday("11111111")
            .friday("11111111")
            .build();
        return consultScheduleRepository.save(consultSchedule);
    }
}