package com.everyschool.consultservice.domain.consultschedule.repository;

import com.everyschool.consultservice.domain.consultschedule.ConsultSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultScheduleRepository extends JpaRepository<ConsultSchedule, Long> {

    Optional<ConsultSchedule> findByTeacherId(Long teacherId);
}
