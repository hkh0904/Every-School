package com.everyschool.consultservice.domain.consulttime.repository;

import com.everyschool.consultservice.domain.consulttime.ConsultSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultScheduleRepository extends JpaRepository<ConsultSchedule, Long> {
}
